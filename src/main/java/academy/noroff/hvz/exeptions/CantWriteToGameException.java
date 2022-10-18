package academy.noroff.hvz.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class CantWriteToGameException extends RuntimeException{
    public CantWriteToGameException(String message) {
        super(message);
    }
}
