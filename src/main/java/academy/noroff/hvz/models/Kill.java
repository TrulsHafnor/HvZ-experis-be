package academy.noroff.hvz.models;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @JoinColumn(name = "player_id")
    private Player playerKiller;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    private Player playerVictim;


    public void setTimeOfDeath(String timeOfDeath) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.timeOfDeath = formatter.format(date);
    }
}
