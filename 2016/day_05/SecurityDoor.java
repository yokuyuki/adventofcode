import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

public class SecurityDoor {

    private MessageDigest md5;
    private String doorID;
    private char[] password = "________".toCharArray();
    private int index = 0;

    public SecurityDoor(String doorID) {
        this.doorID = doorID;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void printPassword() {
        System.out.print("\r");
        System.out.print(password);
    }

    public void getPassword() {
        printPassword();
        IntStream.range(0, 8).forEachOrdered(i -> {
            password[i] = generateNextPasswordHash().charAt(5);
            printPassword();
        });
    }

    public void getBetterPassword() {
        printPassword();
        IntStream.iterate(0, i -> i + 1)
                .mapToObj(i -> generateNextPasswordHash())
                .filter(s -> {
                    int position = Character.digit(s.charAt(5), 16);
                    return position >= 0 && position <= 7 && password[position] == '_';
                }).peek(s -> {
                    password[Character.digit(s.charAt(5), 16)] = s.charAt(6);
                    printPassword();
                }).anyMatch(s -> !String.valueOf(password).contains("_"));
    }

    private String generateNextPasswordHash() {
        index = IntStream.iterate(index, i -> i + 1)
                .filter(i -> hash(doorID + i).startsWith("00000"))
                .findFirst().getAsInt();
        return hash(doorID + index++);
    }

    private String hash(String input) {
        md5.update(input.getBytes());
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }

    public static void main(String args[]) {
        System.out.println("Part 1:");
        new SecurityDoor("ojvtpuvg").getPassword();
        System.out.println("\nPart 2:");
        new SecurityDoor("ojvtpuvg").getBetterPassword();
    }
}
