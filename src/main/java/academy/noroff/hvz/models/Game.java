package academy.noroff.hvz.models;

import academy.noroff.hvz.enums.GameState;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @Column(length = 250)
    private String gameDescription;
    private float nw_lat;
    private float nw_lng;
    private float se_lat;
    private float se_lng;

    @OneToMany(mappedBy = "game")
    private Set<Player> players;

    @OneToMany(mappedBy = "game")
    private Set<Kill> kills;

    @OneToMany(mappedBy = "game")
    private Set<Mission> missions;

    @OneToMany(mappedBy = "game")
    private Set<Chat> chats;

    @OneToMany(mappedBy = "game")
    private Set<Squad> squads;

    public void setStartState() {
        this.gameState = GameState.REGISTRATION;
    }
}
