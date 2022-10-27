package academy.noroff.hvz.models;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Random;
import java.util.Set;

@Entity
@Setter
@Getter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int id;
    @Setter(AccessLevel.NONE)
    @NotNull
    private String biteCode;
    @NotNull
    private boolean isHuman;
    @NotNull
    private boolean isPatientZero;

    //relations
    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "player")
    private Set<Chat> messages;

    @OneToMany(mappedBy = "playerKiller")
    private Set<Kill> kills;

    @OneToOne(mappedBy = "playerDeath")
    private Kill death;

    @OneToOne(mappedBy = "member")
    private SquadMember membership;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToOne(mappedBy = "player")
    private Squad squad;

    public void setBiteCode(String biteCode) {
        this.biteCode = generateBitCode();
    }

    private String generateBitCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 5;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
