package academy.noroff.hvz.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class SquadMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "squad_member_id")
    private int id;

    @NotNull
    String rank;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    @OneToOne(mappedBy = "squadMember")
    private SquadCheckin squadCheckin;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Player member;

    public  SquadMember() {}

    public SquadMember(String rank, Squad squad, Player member) {
        this.rank = rank;
        this.squad=squad;
        this.member=member;
    }
}
