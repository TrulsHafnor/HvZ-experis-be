package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserDto {
    private String id;
    private String email;
    private String nickname;
    private int player;
}
