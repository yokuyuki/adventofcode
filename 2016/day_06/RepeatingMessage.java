import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RepeatingMessage {

    private List<String> message;

    public RepeatingMessage() {
        try {
            message = Files.readAllLines(Paths.get("day_06/repeating_message"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getErrorCorrectedMessage(boolean leastCommonCharacter) {
        Comparator<Map.Entry<Character, Long>> ascendingOrder = Map.Entry.comparingByValue();
        return IntStream.range(0, message.get(0).length())
                .mapToObj(i -> message.stream()
                        .map(s -> s.charAt(i))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .sorted(leastCommonCharacter ? ascendingOrder : ascendingOrder.reversed())
                        .map(e -> String.valueOf(e.getKey()))
                        .findFirst()
                        .get())
                .collect(Collectors.joining());
    }

    public static void main(String args[]) {
        RepeatingMessage rm = new RepeatingMessage();
        System.out.println("Part 1: " + rm.getErrorCorrectedMessage(false));
        System.out.println("Part 2: " + rm.getErrorCorrectedMessage(true));
    }
}
