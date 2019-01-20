package samples.playtika;

import java.util.Map;
import java.util.function.Consumer;

public interface UserXpProcessor<C extends Consumer<LevelChangedEvent>> {
    Integer getConfigLevel(Integer xp);

    Integer getLevel(Integer xp);

    Integer getXp(Integer userId);

    void process(Integer userId, Integer xp);

    void subscribe(C consumer);

    Map<Integer, Integer> getUserIdToUserXp();
}
