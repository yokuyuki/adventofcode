import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomSecurity {

    private final Pattern roomPattern = Pattern.compile("([a-z-]+)(\\d+)\\[([a-z]+)\\]");
    private List<String> rooms;

    public RoomSecurity() {
        try {
            rooms = Files.readAllLines(Paths.get("day_04/rooms"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRealRoomSum() {
        return rooms.parallelStream()
                .map(roomPattern::matcher)
                .peek(Matcher::matches)
                .filter(this::isRealRoom)
                .mapToInt(m -> Integer.parseInt(m.group(2)))
                .sum();
    }

    private boolean isRealRoom(Matcher roomMatch) {
        String checksum = new String(roomMatch.group(1).chars()
                .mapToObj(i -> (char) i)
                .filter(c -> c != '-')
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted((o1, o2) ->  {
                        int frequencyOrder = o2.getValue().compareTo(o1.getValue());
                        if (frequencyOrder != 0) {
                            return frequencyOrder;
                        }
                        return o1.getKey().compareTo(o2.getKey());
                })
                .mapToInt(e -> e.getKey())
                .limit(5)
                .toArray(), 0, 5);

        return roomMatch.group(3).equals(checksum);
    }

    public static void main(String[] args) {
        System.out.println("Part 1: " + new RoomSecurity().getRealRoomSum());
    }

}
