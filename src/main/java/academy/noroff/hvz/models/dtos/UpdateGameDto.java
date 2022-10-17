package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.enums.GameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameDto {
    private int id;
    private String gameTitle;
    private String gameDescription;
    private GameState gameState;
    private float nw_lat;
    private float nw_lng;
    private float se_lat;
    private float se_lng;
}
