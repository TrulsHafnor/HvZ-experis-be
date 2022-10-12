package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.models.Kill;
import academy.noroff.hvz.models.Player;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KillDto {
    private int id;
    private String timeOfDeath;
    private float lat;
    private float lng;
    private int game;
    private int playerKiller;
    private int playerDeath;
}
