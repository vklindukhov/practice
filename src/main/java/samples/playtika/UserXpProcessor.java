package samples.playtika;

import java.util.function.Consumer;

public interface UserXpProcessor {
    Integer getConfigLevel(Integer xp);

    Integer getLevel(Integer xp);

    Integer getXp(Integer userId);

    void process(Integer userId, Integer xp);

    void subscribe(Consumer<LevelChangedEvent> consumer);
}
