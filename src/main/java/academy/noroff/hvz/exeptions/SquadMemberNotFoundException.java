package academy.noroff.hvz.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SquadMemberNotFoundException extends RuntimeException {
    public SquadMemberNotFoundException(String message) {
        super(message);
    }
}