package samples.playtika;

import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static java.lang.Math.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.commons.lang3.ObjectUtils.allNotNull;

@Log
public class UserXpProcessorImpl<C extends Consumer<LevelChangedEvent>> implements UserXpProcessor<C> {
    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer DEFAULT_XP = 0;
    private final ConfigurationProvider configurationProvider;
    private final Entry<Integer, Integer> minLevelToXp;
    private final Entry<Integer, Integer> maxLevelToXp;
    private final Map<Integer, Integer> userIdToUserXp = new ConcurrentHashMap<>();
    private final Set<C> consumers = new HashSet<>();

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
    public void subscribe(C consumer) {
        consumers.add(consumer);
    }

    @Override
    public void process(Integer userId, Integer deltaXp) {
        if (!allNotNull(userId, deltaXp) || userId <= 0 || deltaXp == 0) return;
        sendEvents(updateXp(userId, deltaXp));
    }

    @Override
    public Map<Integer, Integer> getUserIdToUserXp() {
        return userIdToUserXp;
    }

    private Set<LevelChangedEvent> updateXp(Integer userId, Integer deltaXp) {
        Integer oldLevel = getLevel(userId);
        Integer newXp = userIdToUserXp.merge(userId, deltaXp, xpMerger());
        Integer newLevel = getConfigLevel(newXp);
        int from = min(oldLevel, newLevel);
        int to = max(oldLevel, newLevel);
        Set<LevelChangedEvent> levels = rangeClosed(from, to)
                .skip(deltaXp < 0 ? 0 : 1)
                .limit(abs(oldLevel - newLevel))
                .mapToObj(level -> LevelChangedEvent.builder().userId(userId).level(level).build())
                .collect(toSet());
        return levels;
    }

    private BiFunction<Integer, Integer, Integer> xpMerger() {
        return (oldXp, diff) -> {
            int sum = oldXp + diff;
            if (sum < minLevelToXp.getValue()) return minLevelToXp.getValue();
            if (maxLevelToXp.getValue() < sum) return maxLevelToXp.getValue();
            return sum;
        };
    }

    private void sendEvents(Set<LevelChangedEvent> events) {
        events.parallelStream().forEach(e -> consumers.parallelStream().forEach(c -> c.accept(e)));
    }
}
