package academy.noroff.hvz.exeptions;

public class MissionNotFoundException extends RuntimeException {
    public MissionNotFoundException(String message) {
        super(message);
    }
}
