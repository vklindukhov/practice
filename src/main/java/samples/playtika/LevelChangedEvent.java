package samples.playtika;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class LevelChangedEvent {
    private Integer userId;
    private Integer level;
}
