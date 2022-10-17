package academy.noroff.hvz.utils;

public class ExtractId {

    public static String extractId(String input) {
        int offset = input.indexOf("|");
        return input.substring(offset + 1);
    }
}
