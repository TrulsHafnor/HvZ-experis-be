package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SquadDto {
    private int id;
    private String name;
    private boolean isHuman;
    private int game;
    private int player;
    private Set<Integer> members;
}
