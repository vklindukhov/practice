package samples.playtika

import spock.lang.Specification

import java.util.function.Consumer

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
}
