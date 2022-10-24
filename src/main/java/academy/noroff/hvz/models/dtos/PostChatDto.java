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
    private boolean human;
    private boolean global;
    private String Status;
    private int game;
    private int player;
}
