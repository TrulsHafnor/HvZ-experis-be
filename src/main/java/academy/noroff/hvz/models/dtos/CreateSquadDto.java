package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSquadDto {
    private String name;
    private int game;
    private int player;
}
