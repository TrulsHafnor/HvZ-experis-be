package academy.noroff.hvz.models.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SquadChatDto {
    private int id;
    private String senderName;
    private String message;
    private String chatTime;
    private boolean human;
    private boolean global;
    private String Status;
    private int game;
    private int player;
    private int squad;
}
