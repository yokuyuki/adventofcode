import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IPV7 {

    private Stream<String> addresses;

    public IPV7() {
        try {
            addresses = Files.lines(Paths.get("day_07/addresses"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTLSSupportCount() {
        return addresses.filter(IPV7::supportsTLS).count();
    }

    private static boolean supportsTLS(String address) {
        String[] sequences = address.split("\\[|\\]");
        boolean hasAbba = IntStream.range(0, sequences.length)
                .filter(i -> (i & 1) == 0)
                .mapToObj(i -> sequences[i])
                .anyMatch(IPV7::containsABBA);
        boolean hasAbbaInHypernet = IntStream.range(0, sequences.length)
                .filter(i -> (i & 1) != 0)
                .mapToObj(i -> sequences[i])
                .anyMatch(IPV7::containsABBA);

        return hasAbba && !hasAbbaInHypernet;
    }

    private static boolean containsABBA(String sequence) {
        return IntStream.rangeClosed(0, sequence.length()-4)
                .mapToObj(i -> sequence.substring(i, i+4))
                .anyMatch(IPV7::isABBA);
    }

    private static boolean isABBA(String sequence) {
        return sequence.charAt(0) == sequence.charAt(3) &&
                sequence.charAt(0) != sequence.charAt(1) &&
                sequence.charAt(1) == sequence.charAt(2);
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new IPV7().getTLSSupportCount());
    }
}
