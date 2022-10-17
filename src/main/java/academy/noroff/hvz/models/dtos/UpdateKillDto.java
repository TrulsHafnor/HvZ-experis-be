package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateKillDto {
    private int id;
    private int game;
    private float lat;
    private float lng;
}
