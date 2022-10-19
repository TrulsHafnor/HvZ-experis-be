package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SquadCheckinDto {
    private int id;
    private int squadMember;
    private int game;
    private float lat;
    private float lng;
    private String startTime;
    private String endTime;
}
