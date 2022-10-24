package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.models.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadMemberLessDetailsDto {
    private int id;
    private String rank;
    private int member;
}
