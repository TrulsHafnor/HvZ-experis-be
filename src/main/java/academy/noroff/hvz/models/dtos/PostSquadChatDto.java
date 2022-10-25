package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostSquadChatDto {
    private String senderName;
    private String message;
    private boolean human;
    private boolean global;
    private String Status;
    private int game;
    private int player;
    private int squad;
}
