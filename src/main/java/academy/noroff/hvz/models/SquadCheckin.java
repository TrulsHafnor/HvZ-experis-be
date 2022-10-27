package academy.noroff.hvz.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Setter
@Getter
public class SquadCheckin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squad_checkin_id")
    private int id;

    @NotNull
    private float lat;
    @NotNull
    private float lng;
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(length = 19)
    private String startTime;
    @NotNull
    @Column(length = 19)
    private String endTime;

    @NotNull
    @OneToOne
    @JoinColumn(name = "squadMember_id")
    private SquadMember squadMember;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    public SquadCheckin(){}

    public SquadCheckin (float lat, float lng, String endTime, String startTime, SquadMember squadMember ,Game game) {
        this.lat = lat;
        this.lng=lng;
        this.startTime = startTime;
        this.endTime =endTime;
        this.squadMember=squadMember;
        this.game=game;
    }

    public void setStartTime(String startTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.startTime = formatter.format(date);;
    }
}
