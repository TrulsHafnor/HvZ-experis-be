package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadMemberDto {
    private int id;
    private int rank;
    private int squad;
    private int squadCheckin;
}
