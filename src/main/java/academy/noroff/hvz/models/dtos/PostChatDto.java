package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostChatDto {
    private String senderName;
    private String message;
    private boolean isHuman;
    private boolean isGlobal;
    private String Status;
    private int game;
    private int player;
}
