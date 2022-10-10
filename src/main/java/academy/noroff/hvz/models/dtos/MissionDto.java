package academy.noroff.hvz.models.dtos;

import academy.noroff.hvz.enums.MissionVisibility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionDto {
    private int id;
    private String missionName;
    private MissionVisibility missionVisibility;
    private String missionDescription;
    private String startTime;
    private String endTime;
    private float missionLat;
    private float missionLng;
    private int game;
}
