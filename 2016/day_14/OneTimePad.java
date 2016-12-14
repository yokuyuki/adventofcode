import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

public class OneTimePad {

    private String salt;
    private MessageDigest md5;
    private ArrayList<String> hashes;
    private int extraHashings;

    public OneTimePad(String salt) {
        this.salt = salt;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public int[] generateKeys(int numOfKeys, boolean keyStretching) {
        extraHashings = keyStretching ? 2016 : 0;
        hashes = new ArrayList<>();
        return IntStream.iterate(0, i -> i + 1)
                .peek(i -> {
                    if (i == hashes.size()) {
                        generate1000Hashes(i);
                    }
                })
                .filter(this::isValidKey)
                .limit(numOfKeys)
                .toArray();
    }

    private boolean isValidKey(int index) {
        String hash = hashes.get(index);
        Optional<Character> tripleChar = IntStream.rangeClosed(0, hash.length() - 3)
                .filter(i -> hash.charAt(i) == hash.charAt(i+1) && hash.charAt(i) == hash.charAt(i+2))
                .mapToObj(hash::charAt)
                .findFirst();

        return tripleChar.isPresent() && IntStream.rangeClosed(index + 1, index + 1000)
                .peek(i -> {
                    if (i == hashes.size()) {
                        generate1000Hashes(i);
                    }
                })
                .anyMatch(i -> isFiveOfAKind(i, tripleChar.get()));
    }

    private boolean isFiveOfAKind(int index, char kind) {
        String hash = hashes.get(index);
        return IntStream.rangeClosed(0, hash.length() - 5)
                .anyMatch(i -> IntStream.range(i, i + 5)
                        .allMatch(j -> hash.charAt(j) == kind));
    }

    private void generate1000Hashes(int index) {
        IntStream.range(index, index + 1000)
                .mapToObj(this::generateHash).forEachOrdered(hashes::add);
    }

    private String generateHash(int index) {
        return IntStream.rangeClosed(0, extraHashings)
                .mapToObj(i -> "")
                .reduce(salt + index, (previous, empty) -> {
                    md5.update((previous).getBytes());
                    return String.format("%032x", new BigInteger(1, md5.digest()));
                });
    }

    public static void main(String args[]) {
        OneTimePad otp = new OneTimePad("jlmsuwbz");
        int[] keys = otp.generateKeys(64, false);
        System.out.println("Part 1: " + keys[63]);
        keys = otp.generateKeys(64, true);
        System.out.println("Part 2: " + keys[63]);
    }
}
