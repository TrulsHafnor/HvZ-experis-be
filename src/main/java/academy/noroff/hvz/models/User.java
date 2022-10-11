package academy.noroff.hvz.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
public class User {
    @Id
    private String id;
    @NotNull
    private String email;
    private String nickname;
}
