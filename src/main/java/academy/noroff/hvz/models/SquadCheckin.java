package academy.noroff.hvz.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
public class SquadCheckin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squad_checkin_id")
    private int id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squadMember_id")
    private SquadMember squadMember;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
