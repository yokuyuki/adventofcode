import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scrambler {

    private static final Pattern swapPositionPattern = Pattern.compile("swap position (\\d+) with position (\\d+)");
    private static final Pattern swapLetterPattern = Pattern.compile("swap letter ([a-z]) with letter ([a-z])");
    private static final Pattern rotateLeftPattern = Pattern.compile("rotate left (\\d+) steps?");
    private static final Pattern rotateRightPattern = Pattern.compile("rotate right (\\d+) steps?");
    private static final Pattern rotatePositionPattern = Pattern.compile("rotate based on position of letter ([a-z])");
    private static final Pattern reversePositionPattern = Pattern.compile("reverse positions (\\d+) through (\\d+)");
    private static final Pattern movePositionPattern = Pattern.compile("move position (\\d+) to position (\\d+)");

    private ArrayList<Character> password;

    public Scrambler(String password) {
        this.password = password.chars()
                .mapToObj(i -> (char)i)
                .collect(Collectors.toCollection(ArrayList::new));
        try {
            Files.lines(Paths.get("day_21/operations"))
                    .forEachOrdered(this::parseOperation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseOperation(String operation) {
        Matcher m;
        if ((m = swapPositionPattern.matcher(operation)).matches()) {
            swapPosition(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        } else if ((m = swapLetterPattern.matcher(operation)).matches()) {
            swapLetter(m.group(1).charAt(0), m.group(2).charAt(0));
        } else if ((m = rotateLeftPattern.matcher(operation)).matches()) {
            rotateLeft(Integer.parseInt(m.group(1)));
        } else if ((m = rotateRightPattern.matcher(operation)).matches()) {
            rotateRight(Integer.parseInt(m.group(1)));
        } else if ((m = rotatePositionPattern.matcher(operation)).matches()) {
            rotatePosition(m.group(1).charAt(0));
        } else if ((m = reversePositionPattern.matcher(operation)).matches()) {
            reversePosition(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        } else if ((m = movePositionPattern.matcher(operation)).matches()) {
            movePosition(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        }
    }

    private void swapPosition(int x, int y) {
        char temp = password.get(x);
        password.set(x, password.get(y));
        password.set(y, temp);
    }

    private void swapLetter(char a, char b) {
        int x = password.indexOf(a);
        int y = password.indexOf(b);
        swapPosition(x, y);
    }

    private void rotateLeft(int steps) {
        LinkedList<Character> passwordCopy = new LinkedList<>(password);
        IntStream.range(0, steps)
                .forEach(i -> passwordCopy.addLast(passwordCopy.removeFirst()));
        password = new ArrayList<>(passwordCopy);
    }

    private void rotateRight(int steps) {
        LinkedList<Character> passwordCopy = new LinkedList<>(password);
        IntStream.range(0, steps)
                .forEach(i -> passwordCopy.addFirst(passwordCopy.removeLast()));
        password = new ArrayList<>(passwordCopy);
    }

    private void rotatePosition(char letter) {
        int index = password.indexOf(letter);
        int steps = index >= 4 ? index + 2 : index + 1;
        rotateRight(steps);
    }

    private void reversePosition(int x, int y) {
        AtomicInteger reverseIndex = new AtomicInteger(y);
        IntStream.rangeClosed(x, (x+y)/2)
                .forEachOrdered(i -> {
                    swapPosition(i, reverseIndex.getAndDecrement());
                });
    }

    private void movePosition(int x, int y) {
        Character target = password.get(x);
        password.remove(x);
        password.add(y, target);
    }

    public String getScrambledPassword() {
        return password.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new Scrambler("abcdefgh").getScrambledPassword());
    }
}
