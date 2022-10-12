package academy.noroff.hvz.models.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatDto {
    private int id;
    private String message;
    private boolean isHuman;
    private boolean isGlobal;
    private String chatTime;
    private int game;
}
