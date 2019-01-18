package samples.playtika;

import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;

@Log
public class UserXpProcessorImpl implements UserXpProcessor {
    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer DEFAULT_XP = 0;
    private final ConfigurationProvider configurationProvider;
    private final Entry<Integer, Integer> minLevelToXp;
    private final Entry<Integer, Integer> maxLevelToXp;
    private final Map<Integer, Integer> userIdToUserXp = new ConcurrentHashMap<>();
    private final Set<Consumer<LevelChangedEvent>> consumers = new HashSet<>();

    public UserXpProcessorImpl(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
        Map<Integer, Integer> levelToXp = configurationProvider.levelToXp();
        TreeMap<Integer, Integer> sortedLevelToXp = new TreeMap<>(levelToXp);
        this.minLevelToXp = sortedLevelToXp.firstEntry();
        this.maxLevelToXp = sortedLevelToXp.lastEntry();
    }

    @Override
    public Integer getConfigLevel(Integer xp) {
        if (xp <= minLevelToXp.getValue()) return minLevelToXp.getKey();
        if (maxLevelToXp.getValue() <= xp) return maxLevelToXp.getKey();
        return configurationProvider.levelToXp().entrySet().stream()
                .filter(e -> e.getValue() <= xp)
                .max(comparing(Entry::getKey))
                .map(Entry::getKey)
                .orElse(DEFAULT_LEVEL);
    }

    @Override
    public Integer getLevel(Integer userId) {
        return getConfigLevel(getXp(userId));
    }

    @Override
    public Integer getXp(Integer userId) {
        userIdToUserXp.putIfAbsent(userId, DEFAULT_XP);
        return userIdToUserXp.get(userId);
    }

    @Override
    public void subscribe(Consumer<LevelChangedEvent> consumer) {
        consumers.add(consumer);
    }

    @Override
    public void process(Integer userId, Integer deltaXp) {
        if (!allNotNull(userId, deltaXp) || userId <= 0 || deltaXp == 0) return;
        sendEvents(updateXp(userId, deltaXp));
    }

    private Set<LevelChangedEvent> updateXp(Integer userId, Integer deltaXp) {
        Integer newXp = getXp(userId) + deltaXp;
        Integer oldLevel = getLevel(userId);
        Integer newLevel = getConfigLevel(newXp);
        userIdToUserXp.merge(userId, oldLevel, (id1, id2) -> newXp);
        return rangeClosed(min(oldLevel, newLevel), max(oldLevel, newLevel))
                .skip(deltaXp < 0 ? 0 : 1)
                .limit(abs(oldLevel - newLevel))
                .mapToObj(level -> LevelChangedEvent.builder().userId(userId).level(level).build())
                .collect(toSet());
    }

    private void sendEvents(Set<LevelChangedEvent> events) {
        events.parallelStream().forEach(e -> consumers.parallelStream().forEach(c -> c.accept(e)));
    }
}
