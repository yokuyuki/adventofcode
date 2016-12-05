import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecurityDoor {

    private MessageDigest md5;
    private String doorID;
    private long index = 0;

    public SecurityDoor(String doorID) {
        this.doorID = doorID;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getPassword() {
        return IntStream.range(0, 8)
                .mapToObj(i -> generateNextPasswordCharacter())
                .collect(Collectors.joining());
    }

    private String generateNextPasswordCharacter() {
        String hash;
        do {
            md5.update((doorID + index++).getBytes());
            hash = String.format("%032x", new BigInteger(1, md5.digest()));
        } while(!hash.startsWith("00000"));

        return hash.substring(5, 6);
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new SecurityDoor("ojvtpuvg").getPassword());
    }
}
