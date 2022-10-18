package academy.noroff.hvz.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
public class Squad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squad_id")
    int id;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    private boolean isHuman;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "squad")
    private Set<Chat> chat;

    @NotNull
    @OneToMany(mappedBy = "squad")
    private Set<SquadMember> members;

}
