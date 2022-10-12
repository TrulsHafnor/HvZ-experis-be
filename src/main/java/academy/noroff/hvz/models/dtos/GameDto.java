package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.enums.GameState;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GameDto {
    private int id;
    private String gameTitle;
    private GameState gameState;
    private String gameDescription;
    private float nw_lat;
    private float nw_lng;
    private float se_lat;
    private float se_lng;
    private Set<Integer> players;
    private Set<Integer> missions;
    private Set<Integer> kills;
}
