package samples.playtika

import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeoutException
import java.util.function.Consumer

import static com.google.common.collect.Sets.difference
import static java.lang.Math.abs
import static java.lang.String.format
import static java.util.Collections.newSetFromMap
import static java.util.Collections.shuffle

class UserXpProcessorImplSpec extends Specification {
    def levelToXpConfigMap = [
            1: 0,
            2: 100,
            3: 200,
            4: 500,
            5: 1000,
            6: 2000,
            7: 5000,
            8: 10000
    ]
    def configProvider = Mock(ConfigurationProvider) {
        levelToXp() >> levelToXpConfigMap
    }
    def processor = new UserXpProcessorImpl(configProvider)

    def "GetConfigLevel"() {
        when:
        def level = processor.getConfigLevel(xp)

        then:
        expectedLevel == level

        where:
        xp       | expectedLevel
        -9999999 | 1
        -10      | 1
        0        | 1
        1        | 1
        99       | 1
        100      | 2
        101      | 2
        199      | 2
        200      | 3
        5000     | 7
        5001     | 7
        9999     | 7
        10000    | 8
        10001    | 8
        99999    | 8
        99999999 | 8

    }

    def "GetLevel"() {
        given:
        def userId = 42

        when:
        def level = processor.getLevel(userId)

        then:
        1 == level
    }

    def "GetXp"() {
        given:
        def userId = 42

        when:
        def xp = processor.getXp(userId)

        then:
        0 == xp
    }

    def "Process without changing level"() {
        given:
        def userId = 42
        def deltaXp = 13
//        def consumer1 = new Consumer<LevelChangedEvent>() {
//            void accept(LevelChangedEvent e) {
//                println "Consumer1 ${Thread.currentThread().name} consums event $e"
//            }
//        }
//        def consumer2 = new Consumer<LevelChangedEvent>() {
//            void accept(LevelChangedEvent e) {
//                println "Consumer2 ${Thread.currentThread().name} consums event $e"
//            }
//        }
        def consumer1 = Mock Consumer
        def consumer2 = Mock Consumer
        processor.subscribe(consumer1)
        processor.subscribe(consumer2)

        when:
        processor.process(userId, deltaXp)

        then:
        1 * configProvider.levelToXp() >> levelToXpConfigMap
        0 * _
    }

    def "Process with 1 time level changing"() {
        given:
        def userId = 42
        def deltaXp = 130
        def consumer1 = Mock Consumer
        def consumer2 = Mock Consumer
        def consumers = [consumer1, consumer2]
        consumers.each { processor.subscribe(it) }

        when:
        processor.process(userId, deltaXp)

        then:
        1 * configProvider.levelToXp() >> levelToXpConfigMap
        1 * consumer1.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        0 * _
    }

    def "Process with 3 times level changing"() {
        given:
        def userId = 42
        def deltaXp = 666
        def consumer1 = Mock Consumer
        def consumer2 = Mock Consumer
        def consumers = [consumer1, consumer2]
        consumers.each { processor.subscribe(it) }

        when:
        processor.process(userId, deltaXp)

        then:
        1 * configProvider.levelToXp() >> levelToXpConfigMap
        1 * consumer1.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        1 * consumer1.accept({ it.userId == userId && it.level == 3 } as LevelChangedEvent)
        1 * consumer1.accept({ it.userId == userId && it.level == 4 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 3 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 4 } as LevelChangedEvent)
        0 * _
    }

    def "Process with 5 times level changing up and down"() {
        given:
        def userId = 42
        def deltaXp1 = 666
        def deltaXp2 = -200
        def deltaXp3 = -300
        def consumer1 = Mock Consumer
        def consumer2 = Mock Consumer
        def consumers = [consumer1, consumer2]
        consumers.each { processor.subscribe(it) }

        when:
        processor.process(userId, deltaXp1)
        then:
        1 * consumer1.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        1 * consumer1.accept({ it.userId == userId && it.level == 3 } as LevelChangedEvent)
        1 * consumer1.accept({ it.userId == userId && it.level == 4 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 3 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 4 } as LevelChangedEvent)

        when:
        processor.process(userId, deltaXp2)
        then:
        1 * consumer1.accept({ it.userId == userId && it.level == 3 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 3 } as LevelChangedEvent)

        when:
        processor.process(userId, deltaXp3)
        then:
        1 * consumer1.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)
        1 * consumer2.accept({ it.userId == userId && it.level == 2 } as LevelChangedEvent)

        2 * configProvider.levelToXp() >> levelToXpConfigMap
        0 * _
    }

    def "Concurrent test"() {
        given:
        def threadsAmount = 16
        def consumersAmount = 5
        def iterationsAmountPerThread = 1250000
        def userMaxId = threadsAmount * iterationsAmountPerThread
        def consumers = (1..consumersAmount).collect { new EventConsumer(it) }
        consumers.each { processor.subscribe(it) }

        when:
        def threadTask = (1..threadsAmount).collect() {
            def task = new Task(iterationsAmountPerThread, userMaxId, processor)
            def thread = new Thread(task)
            thread.start()
            return new Tuple2<Thread, Task>(thread, task)
        }
        threadTask.each { it.first.join() }
        def etalonConsumed = new HashSet<>(consumers.first().eventsConsumed.collect { it.userId })
        def userInfo = processor.userIdToUserXp.sort()
        def userIdsSent = new TreeSet<>(threadTask.collect { it.second.processedUserIds }.flatten())

        then:
        consumers.each {
            def set = new HashSet<>(it.eventsConsumed.collect { it.userId })
            def sameSize = etalonConsumed.size() == set.size()
            if (!sameSize) {
                println difference(etalonConsumed, set)
            }
            assert sameSize
        }
        userIdsSent.size() == userInfo.size()
    }

    def "Concurrent test 2"() {
        given:
        def usersAmount = 1000
        def xpChangesAmount = 1000
        def maxXpDelta = 10
        def threadsAmount = 128
        def consumersAmount = 5
        def random = new Random()
        def consumers = (1..consumersAmount).collect { new EventConsumer(it) }
        consumers.each { processor.subscribe(it) }
        def usersIdToXpChanges = (1..usersAmount).collect {
            (1..xpChangesAmount).collect { i ->
                new Tuple2<>(it, random.nextInt(maxXpDelta - 1) + 1)
            }
        }
        def usersChanges = usersIdToXpChanges.inject([]) { acc, list -> acc + list } as List
        shuffle(usersChanges)
        def usersPerThreadId = usersChanges.collate(usersChanges.size() / threadsAmount as int)
        def userIdToFinalXp = usersChanges.groupBy { (it as Tuple2<Integer, Integer>).first }.collectEntries {
            [(it.key): it.value.sum { (it as Tuple2<Integer, Integer>).second }]
        }
        def expectedUserIdToFinalLevel = new TreeMap<>(userIdToFinalXp.collectEntries {
            [it.key, processor.getConfigLevel(it.value as int)]
        })

        when:
        def threadTask = (1..threadsAmount).collect() {
            def task = new Task2(it, usersPerThreadId.get(it - 1), processor)
            def thread = new Thread(task)
            thread.start()
            return new Tuple2<Thread, Task2>(thread, task)
        }
        threadTask.each { it.first.join() }
        threadTask.each {
            println "Thread task with id ${it.second.threadId} finished after ${it.second.spendTimeSec} sec"
        }

        then:
        def etalonConsumedSortedEvents = consumers.first().eventsConsumed.toSorted { e1, e2 ->
            e1.userId <=> e2.userId ?: e1.level <=> e2.level
        }
        def expectedAllUserIdWithLevel = (expectedUserIdToFinalLevel.collect {
            (2..it.value).collect { level -> new Tuple2<Integer, Integer>(it.key as int, level as int) }
        }.inject([]) { acc, userLevels ->
            acc + userLevels
        } as List<Tuple2<Integer, Integer>>).toSorted { t1, t2 ->
            t1.first <=> t2.first ?: t1.second <=> t2.second
        }
        def actualAllUserIdWithLevel = (etalonConsumedSortedEvents.groupBy { it.userId }.inject([]) { acc, it ->
            acc + it.value.collect { event -> new Tuple2<Integer, Integer>(it.key, event.level) }
        } as List<Tuple2<Integer, Integer>>).toSorted { t1, t2 ->
            t1.first <=> t2.first ?: t1.second <=> t2.second
        }
        def equal = expectedAllUserIdWithLevel == actualAllUserIdWithLevel
        if (!equal) println "Diff: ${expectedAllUserIdWithLevel - actualAllUserIdWithLevel}"
        equal
    }

    static class EventConsumer implements Consumer<LevelChangedEvent> {
        private final int id
        private Set<LevelChangedEvent> eventsConsumed = newSetFromMap(new ConcurrentHashMap<LevelChangedEvent, Boolean>());

        EventConsumer(int id) {
            this.id = id
        }

        void accept(LevelChangedEvent event) {
            eventsConsumed << event
        }
    }

    static class Task implements Runnable {
        private def counter = 0
        private def iterationsAmount
        private def timeoutSec = 30
        private def newEventDelayMs = 10
        private def userIdGenerator = new Random()
        private def userMaxId
        private def xpGenerator = new Random()
        private def minXp = -1000
        private UserXpProcessor<? extends EventConsumer> xpProcessor
        private Set<Integer> processedUserIds = new HashSet<>()

        Task(iterationsAmount, userMaxId, xpProcessor) {
            this.iterationsAmount = iterationsAmount
            this.userMaxId = userMaxId
            this.xpProcessor = xpProcessor
        }

        @Override
        void run() {
            def startTime = System.currentTimeSeconds()
            while (counter++ < iterationsAmount && System.currentTimeSeconds() - startTime < timeoutSec) {
                def userId = userIdGenerator.nextInt(userMaxId - 1) + 1
                def xp = xpGenerator.nextInt((2.22 * abs(minXp)) as int)
                def deltaXp = 0
                while (deltaXp == 0) deltaXp = xp - abs(minXp)
                xpProcessor.process(userId, deltaXp)
                processedUserIds << userId
                Thread.sleep(newEventDelayMs)
            }
        }
    }

    static class Task2 implements Runnable {
        private def timeoutSec = 15 //13-15s - 128t, 22-25s - 64t
        private def newEventDelayMs = 1
        private def threadId
        private List<Tuple2<Integer, Integer>> userInfos
        private UserXpProcessor<? extends EventConsumer> xpProcessor
        private String timeoutExceptionMessage
        private def spendTimeSec

        Task2(threadId, userInfos, xpProcessor) {
            this.threadId = threadId
            this.userInfos = userInfos
            this.xpProcessor = xpProcessor
            this.timeoutExceptionMessage = "Thread with id ${threadId}: " +
                    "Not enough $timeoutSec sec timeout to process ${userInfos.size()} user's xp change events. " +
                    "Stopped on xp change #%s"
        }

        @Override
        void run() {
            def startTime = System.currentTimeSeconds()
            for (int i = 0; i < userInfos.size(); i++) {
                spendTimeSec = System.currentTimeSeconds() - startTime
                if (timeoutSec < spendTimeSec) throw new TimeoutException(format(timeoutExceptionMessage, i))
                def userIdXpChange = userInfos[i]
                xpProcessor.process(userIdXpChange.first, userIdXpChange.second)
                Thread.sleep(newEventDelayMs)
            }
        }
    }
}
