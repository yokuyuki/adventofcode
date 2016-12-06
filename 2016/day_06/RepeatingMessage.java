import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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

    public String getErrorCorrectedMessage() {
        return IntStream.range(0, message.get(0).length())
                .mapToObj(i -> message.stream()
                        .map(s -> s.charAt(i))
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                        .map(e -> String.valueOf(e.getKey()))
                        .findFirst()
                        .get())
                .collect(Collectors.joining());
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new RepeatingMessage().getErrorCorrectedMessage());
    }
}
