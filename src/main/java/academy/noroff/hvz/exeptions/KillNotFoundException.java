package academy.noroff.hvz.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class KillNotFoundException extends RuntimeException{
    public KillNotFoundException(String message) {
        super(message);
    }
}
