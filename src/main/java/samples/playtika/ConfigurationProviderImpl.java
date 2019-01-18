package samples.playtika;


import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ConfigurationProviderImpl implements ConfigurationProvider {
    @Override
    public Map<Integer, Integer> levelToXp() {
        return ImmutableMap.<Integer, Integer>builder()
                .put(1, 0)
                .put(2, 100)
                .put(3, 200)
                .put(4, 500)
                .put(5, 1000)
                .put(6, 2000)
                .put(7, 5000)
                .put(8, 10000)
                .build();
    }
}
