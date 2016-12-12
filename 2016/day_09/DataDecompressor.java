import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataDecompressor {

    private ArrayList<Character> compressed;
    private LinkedList<Character> decompressed = new LinkedList<>();
    private static final Pattern markerPattern = Pattern.compile("(\\d+)x(\\d+)");

    public DataDecompressor() {
        try {
            compressed = Files.lines(Paths.get("day_09/compressed"))
                    .flatMap(s -> s.chars().mapToObj(i -> (char)i))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int decompress() {
        int i = 0;
        while (i < compressed.size()) {
            Character currentChar = compressed.get(i);
            if ('(' == currentChar) {
                OptionalInt closingParenIndex = IntStream.range(i+1, compressed.size())
                        .filter(j -> compressed.get(j) == ')')
                        .findFirst();
                if (closingParenIndex.isPresent()) {
                    String potentialMarker = compressed.subList(i+1, closingParenIndex.getAsInt()).stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining());
                    Matcher markerMatch = markerPattern.matcher(potentialMarker);
                    if (markerMatch.matches()) {
                        int reps = Integer.parseInt(markerMatch.group(1));
                        int sets = Integer.parseInt(markerMatch.group(2));
                        int end = Math.min(closingParenIndex.getAsInt()+1+reps, compressed.size());
                        IntStream.range(0, sets).forEachOrdered(set -> IntStream.range(closingParenIndex.getAsInt()+1, end)
                                .forEachOrdered(rep -> decompressed.add(compressed.get(rep))));
                        i = end;
                        continue;
                    }
                }
            } else {
                decompressed.add(currentChar);
            }
            i++;
        }
        return decompressed.size();
    }

    public void printDecompressed() {
        decompressed.stream().forEachOrdered(System.out::print);
        System.out.println();
    }

    public static void main(String args[]) {
        DataDecompressor dd = new DataDecompressor();
        System.out.println("Part 1: " + dd.decompress());
    }
}
