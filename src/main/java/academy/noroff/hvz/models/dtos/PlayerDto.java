package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {
    private int id;
    private String biteCode;
    private boolean isHuman;
    private int game;
}
