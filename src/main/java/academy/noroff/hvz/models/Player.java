package academy.noroff.hvz.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int id;
    @NotNull
    private String biteCode;
    @NotNull
    private boolean isHuman;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
