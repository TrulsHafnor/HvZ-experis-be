package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PlayerDto {
    private int id;
    private String biteCode;
    private boolean isHuman;
    private boolean isPatientZero;
    private int game;
    private Set<Integer> kills;
    private int death;
    private Set<Integer> messages;
}
