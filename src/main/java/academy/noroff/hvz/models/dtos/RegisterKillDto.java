package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterKillDto {
    private float lat;
    private float lng;
    private int game;
    private int playerKiller;
    private String biteCode;
}
