package academy.noroff.hvz.models;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Kill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kill_id")
    private int id;
    @Setter(AccessLevel.NONE)
    private String timeOfDeath;
    private float lat;
    private float lng;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    //killer
    @NotNull
    @ManyToOne
    @JoinColumn(name = "kills_id")
    private Player playerKiller;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playerDeath_id")
    private Player playerDeath;

    public void setTimeOfDeath(String timeOfDeath) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.timeOfDeath = formatter.format(date);
    }
}
