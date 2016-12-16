import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomData {

    public static String generateRandomData(String a, int length) {
        if (a.length() > length) {
            return a.substring(0, length);
        }
        String b = IntStream.rangeClosed(1, a.length())
                .mapToObj(i -> a.charAt(a.length() - i))
                .map(c -> c == '0' ? "1" : "0")
                .collect(Collectors.joining());
        return generateRandomData(a + "0" + b, length);
    }

    public static String calculateChecksum(String str) {
        if ((str.length() & 1) == 1) {
            return str;
        }
        return calculateChecksum(IntStream.range(0, str.length() / 2)
                .mapToObj(i -> str.charAt(i*2) == str.charAt(i*2 + 1) ? "1" : "0")
                .collect(Collectors.joining()));
    }

    public static void main(String args[]) {
        System.out.println(calculateChecksum(generateRandomData("01110110101001000", 272)));
        System.out.println(calculateChecksum(generateRandomData("01110110101001000", 35651584)));
    }
}
