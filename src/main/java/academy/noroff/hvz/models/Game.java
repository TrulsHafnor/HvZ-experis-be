package academy.noroff.hvz.models;

import academy.noroff.hvz.enums.GameState;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int id;
    @NotNull
    @Column(length = 50, nullable = false)
    private String gameTitle;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GameState gameState;
    private float nw_lat;
    private float nw_lng;
    private float se_lat;
    private float se_lng;


}
