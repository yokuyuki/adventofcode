import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TwoFactorDisplay {

    private static final Pattern rectangleRegex = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern rotateColumnRegex = Pattern.compile("rotate column x=(\\d+) by (\\d+)");
    private static final Pattern rotateRowRegex = Pattern.compile("rotate row y=(\\d+) by (\\d+)");

    private int width, height;
    private int[][] display;

    public TwoFactorDisplay(int width, int height) {
        try {
            this.width = width;
            this.height = height;
            display = new int[height][width];
            Files.lines(Paths.get("day_08/operations"))
                    .forEachOrdered(this::executeOperation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int countLitPixels() {
        return Arrays.stream(display).flatMapToInt(a -> Arrays.stream(a)).sum();
    }

    public void printDisplay() {
        Arrays.stream(display).forEachOrdered(a -> {
            Arrays.stream(a).forEachOrdered(System.out::print);
            System.out.println();
        });
    }

    public void executeOperation(String operation) {
        Matcher m = rectangleRegex.matcher(operation);
        if (m.matches()) {
            executeRect(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            return;
        }

        m = rotateColumnRegex.matcher(operation);
        if (m.matches()) {
            rotateColumn(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            return;
        }

        m = rotateRowRegex.matcher(operation);
        if (m.matches()) {
            rotateRow(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
        }
    }

    private void executeRect(int width, int height) {
        IntStream.range(0, height).parallel()
                .forEach(i -> IntStream.range(0, width).parallel()
                        .forEach(j -> display[i][j] = 1));
    }

    private void rotateColumn(int x, int amount) {
        LinkedList<Integer> column = IntStream.range(0, height)
                .map(i -> display[i][x])
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));
        rotateList(column, amount);
        final AtomicInteger index = new AtomicInteger();
        column.forEach(i -> display[index.getAndIncrement()][x] = i);
    }

    private void rotateRow(int y, int amount) {
        LinkedList<Integer> row = Arrays.stream(display[y]).boxed().collect(Collectors.toCollection(LinkedList::new));
        rotateList(row, amount);
        display[y] = row.stream().mapToInt(i->i).toArray();
    }

    private static void rotateList(LinkedList<Integer> list, int rotationAmount) {
        IntStream.range(0, rotationAmount)
                .forEachOrdered(i -> list.addFirst(list.removeLast()));
    }

    public static void main(String args[]) {
        TwoFactorDisplay display = new TwoFactorDisplay(50, 6);
        System.out.println("Part 1: " + display.countLitPixels());
        System.out.println("Part 2: ");
        display.printDisplay();
    }
}
