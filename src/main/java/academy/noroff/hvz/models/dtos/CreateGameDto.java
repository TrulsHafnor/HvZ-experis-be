package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.enums.GameState;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateGameDto {
    private String gameTitle;
    private String gameDescription;
    private float nw_lat;
    private float nw_lng;
    private float se_lat;
    private float se_lng;
}
