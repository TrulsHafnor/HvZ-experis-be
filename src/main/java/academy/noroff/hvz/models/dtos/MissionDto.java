package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class MissionDto {
    private int id;
    private String missionName;
    private boolean isHumanVisible;
    private boolean isZombieVisible;
    private String missionDescription;
    private String startTime;
    private String endTime;
    private float missionLat;
    private float missionLng;
    private int game;
}
