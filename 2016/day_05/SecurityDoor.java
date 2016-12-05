import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.stream.IntStream;

public class SecurityDoor {

    private MessageDigest md5;
    private String doorID;
    private char[] password = "________".toCharArray();
    private long index = 0;

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
            password[i] = generateNextPasswordCharacter().getKey();
            printPassword();
        });
    }

    public void getBetterPassword() {
        printPassword();
        do {
            AbstractMap.Entry<Character, Character> pair = generateNextPasswordCharacter();
            int position = Character.digit(pair.getKey(), 16);
            if (position >= 0 && position <= 7 && password[position] == '_') {
                password[position] = pair.getValue();
                printPassword();
            }
        } while(String.valueOf(password).contains("_"));
    }

    private AbstractMap.Entry<Character, Character> generateNextPasswordCharacter() {
        String hash;
        do {
            md5.update((doorID + index++).getBytes());
            hash = String.format("%032x", new BigInteger(1, md5.digest()));
        } while(!hash.startsWith("00000"));

        return new AbstractMap.SimpleEntry<>(hash.charAt(5), hash.charAt(6));
    }

    public static void main(String args[]) {
        System.out.println("Part 1:");
        new SecurityDoor("ojvtpuvg").getPassword();
        System.out.println("Part 2:");
        new SecurityDoor("ojvtpuvg").getBetterPassword();
    }
}
