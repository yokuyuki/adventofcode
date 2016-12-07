import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IPV7 {

    private List<String> addresses;

    public IPV7() {
        try {
            addresses = Files.readAllLines(Paths.get("day_07/addresses"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTLSSupportCount() {
        return addresses.parallelStream().filter(IPV7::supportsTLS).count();
    }

    private static boolean supportsTLS(String address) {
        String[] sequences = address.split("\\[|\\]");
        boolean hasAbbaInSupernet = IntStream.range(0, sequences.length).parallel()
                .filter(i -> (i & 1) == 0)
                .mapToObj(i -> sequences[i])
                .anyMatch(IPV7::containsABBA);
        boolean hasAbbaInHypernet = IntStream.range(0, sequences.length).parallel()
                .filter(i -> (i & 1) != 0)
                .mapToObj(i -> sequences[i])
                .anyMatch(IPV7::containsABBA);

        return hasAbbaInSupernet && !hasAbbaInHypernet;
    }

    private static boolean containsABBA(String sequence) {
        return IntStream.rangeClosed(0, sequence.length()-4).parallel()
                .mapToObj(i -> sequence.substring(i, i+4))
                .anyMatch(IPV7::isABBA);
    }

    private static boolean isABBA(String sequence) {
        return sequence.charAt(0) == sequence.charAt(3) &&
                sequence.charAt(0) != sequence.charAt(1) &&
                sequence.charAt(1) == sequence.charAt(2);
    }

    public long getSSLSupportCount() {
        return addresses.parallelStream().filter(IPV7::supportsSSL).count();
    }

    private static boolean supportsSSL(String address) {
        String[] sequences = address.split("\\[|\\]");
        Set<String> abaInSupernet = IntStream.range(0, sequences.length).parallel()
                .filter(i -> (i & 1) == 0)
                .mapToObj(i -> getABAList(sequences[i]))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        boolean hasBAB = IntStream.range(0, sequences.length).parallel()
                .filter(i -> (i & 1) != 0)
                .anyMatch(i -> IPV7.hasCorrespondingBABInHypernet(sequences[i], abaInSupernet));

        return !abaInSupernet.isEmpty() && hasBAB;
    }

    private static List<String> getABAList(String sequence) {
        return IntStream.rangeClosed(0, sequence.length()-3).parallel()
                .mapToObj(i -> sequence.substring(i, i+3))
                .filter(IPV7::isABA)
                .collect(Collectors.toList());
    }

    private static boolean isABA(String sequence) {
        return sequence.charAt(0) == sequence.charAt(2) &&
                sequence.charAt(0) != sequence.charAt(1);
    }

    private static boolean hasCorrespondingBABInHypernet(String sequence, Set<String> matchingABA) {
        return IntStream.rangeClosed(0, sequence.length()-3).parallel()
                .mapToObj(i -> sequence.substring(i, i+3))
                .filter(IPV7::isABA)
                .anyMatch(s -> IPV7.hasCorrespondingBABInSequence(s, matchingABA));
    }

    private static boolean hasCorrespondingBABInSequence(String bab, Set<String> matchingABA) {
        return matchingABA.parallelStream()
                .anyMatch(aba -> bab.charAt(0) == aba.charAt(1) && aba.charAt(0) == bab.charAt(1));
    }

    public static void main(String args[]) {
        IPV7 ip = new IPV7();
        System.out.println("Part 1: " + ip.getTLSSupportCount());
        System.out.println("Part 2: " + ip.getSSLSupportCount());
    }
}
