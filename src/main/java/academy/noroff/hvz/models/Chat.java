package academy.noroff.hvz.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int id;

    @NotNull
    @Column(length = 150)
    private String message;
    private String senderName;
    private boolean human;
    private boolean global;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Setter(AccessLevel.NONE)
    private String chatTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "squad_id")
    private Squad squad;

    public void setChatTime(String chatTime) {
        this.chatTime = chatTimestamp();
    }
    private String chatTimestamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
