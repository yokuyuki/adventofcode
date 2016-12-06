import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomSecurity {

    private final Pattern roomPattern = Pattern.compile("([a-z-]+)-(\\d+)\\[([a-z]+)\\]");
    private List<String> rooms;

    public RoomSecurity() {
        try {
            rooms = Files.readAllLines(Paths.get("day_04/rooms"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRealRoomSum() {
        return getRealRooms()
                .mapToInt(m -> Integer.parseInt(m.group(2)))
                .sum();
    }

    private Stream<Matcher> getRealRooms() {
        return rooms.parallelStream()
                .map(roomPattern::matcher)
                .peek(Matcher::matches)
                .filter(this::isRealRoom);
    }

    private boolean isRealRoom(Matcher roomMatch) {
        String checksum = roomMatch.group(1).chars()
                .mapToObj(i -> (char) i)
                .filter(c -> c != '-')
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted((o1, o2) -> o1.getValue().equals(o2.getValue()) ? o1.getKey().compareTo(o2.getKey()) : o2.getValue().compareTo(o1.getValue()))
                .limit(5)
                .map(e -> String.valueOf(e.getKey()))
                .collect(Collectors.joining());

        return roomMatch.group(3).equals(checksum);
    }

    public int findNorthPoleObjectStorage() {
        return decryptRooms()
                .filter(e -> e.getKey().contains("northpole"))
                .findFirst()
                .get()
                .getValue();
    }

    private Stream<AbstractMap.Entry<String, Integer>> decryptRooms() {
        return getRealRooms()
                .map(m -> new AbstractMap.SimpleEntry<>(m.group(1), Integer.parseInt(m.group(2))))
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey().chars()
                            .mapToObj(i -> (char) i)
                            .map(c -> c == '-' ? ' ' : reverseShiftCipher(c, e.getValue()))
                            .map(String::valueOf)
                            .collect(Collectors.joining()), e.getValue()));
    }

    private char reverseShiftCipher(char letter, int sectorID) {
        return (char) ((letter - 'a' + sectorID) % 26 + 'a');
    }

    public static void main(String[] args) {
        RoomSecurity rs = new RoomSecurity();
        System.out.println("Part 1: " + rs.getRealRoomSum());
        System.out.println("Part 2: " + rs.findNorthPoleObjectStorage());
    }

}
