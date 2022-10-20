package academy.noroff.hvz.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AllReadyInSquadException extends RuntimeException {
    public AllReadyInSquadException(String message) {
        super(message);
    }
}
