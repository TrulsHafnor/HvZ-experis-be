package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.models.Kill;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.util.Set;

@Getter
@Setter
public class PlayerDto {
    private int id;
    private String biteCode;
    private boolean isHuman;
    private int game;
    private Set<Integer> kills;
    private int death;
}
