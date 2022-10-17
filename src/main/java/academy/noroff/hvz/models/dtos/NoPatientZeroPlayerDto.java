package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class NoPatientZeroPlayerDto {
    private int id;
    private String biteCode;
    private boolean isHuman;
    private int game;
    private Set<Integer> kills;
    private int death;
    private Set<Integer> messages;
}
