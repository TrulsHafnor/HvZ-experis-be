package academy.noroff.hvz.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Setter
@Getter
public class AppUser {
    @Id
    @Column(name = "user_id")
    private String id;
    private String email;
    private String nickname;

    //relations
    @OneToOne(mappedBy = "user")
    private Player player;
}
