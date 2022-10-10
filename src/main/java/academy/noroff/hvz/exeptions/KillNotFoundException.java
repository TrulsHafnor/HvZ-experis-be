package academy.noroff.hvz.exeptions;

public class KillNotFoundException extends RuntimeException{
    public KillNotFoundException(String message) {
        super(message);
    }
}
