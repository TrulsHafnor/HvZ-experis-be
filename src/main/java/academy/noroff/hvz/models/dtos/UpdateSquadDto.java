package academy.noroff.hvz.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateSquadDto {
    private int id;
    private String name;
    private int player;
    private int game;
    // TODO: 10/18/2022 skal denne være her? 
    private Set<Integer> members;
}
