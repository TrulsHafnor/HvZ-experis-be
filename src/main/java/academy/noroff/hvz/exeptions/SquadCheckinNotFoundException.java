package academy.noroff.hvz.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SquadCheckinNotFoundException extends RuntimeException {
    public SquadCheckinNotFoundException(String message) {
        super(message);
    }

}