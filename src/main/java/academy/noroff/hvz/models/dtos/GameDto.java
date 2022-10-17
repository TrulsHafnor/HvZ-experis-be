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
    /*private Set<Integer> missions;
    private Set<Integer> kills;
    private Set<Integer> chats;*/

    public GameDto () {}

    public GameDto (String gameTitle, String gameDescription, float nw_lat, float nw_lng, float se_lat, float se_lng) {
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
        this.nw_lat = nw_lat;
        this.nw_lng = nw_lng;
        this.se_lat=se_lat;
        this.se_lng = se_lng;
    }
}
