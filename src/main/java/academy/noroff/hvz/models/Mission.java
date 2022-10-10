package academy.noroff.hvz.models;

import academy.noroff.hvz.enums.MissionVisibility;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private int id;
    @NotNull
    @Column(length = 50, nullable = false)
    private String missionName;
    @Enumerated(EnumType.STRING)
    private MissionVisibility missionVisibility;
    private String missionDescription;

    // TODO: 10/7/2022 Needs to be revised
    private String startTime;
    private String endTime;

    private float missionLat;
    private float missionLng;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
