package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlayerDto {
    private int id;
    private boolean isHuman;
    private boolean isPatientZero;
    private int game;
}
