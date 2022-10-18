package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadCheckinDto {
    private int id;
    private int squadMember;
    private int game;
}
