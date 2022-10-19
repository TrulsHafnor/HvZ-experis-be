package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCheckinDto {
    private float lat;
    private float lng;
    private String endTime;
    private int game;
    private int squadMember;

}
