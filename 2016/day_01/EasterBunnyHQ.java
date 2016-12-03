import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

public class EasterBunnyHQ {

    private int x = 0;
    private int y = 0;
    private int angle = 0;

    // Part 2
    private HashSet<AbstractMap.Entry<Integer, Integer>> visited = new HashSet<AbstractMap.Entry<Integer, Integer>>() {{
        this.add(new AbstractMap.SimpleEntry<>(0, 0));
    }};
    private AbstractMap.Entry<Integer, Integer> firstRevisitedLocation = null;

    public EasterBunnyHQ() {
        try {
            String[] instructions = new String(Files.readAllBytes(Paths.get("day_01/instructions"))).split(", ");
            Arrays.asList(instructions).stream().forEachOrdered(this::processInstruction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processInstruction(String instruction) {
        switch (instruction.charAt(0)) {
            case 'L': angle -= 90; break;
            case 'R': angle += 90; break;
        }

        int steps = Integer.parseInt(instruction.substring(1));
        int xModifier = (int) Math.sin(Math.toRadians(angle));
        int yModifier = (int) Math.cos(Math.toRadians(angle));
        IntStream.rangeClosed(1, steps).forEachOrdered(i -> {    // Part 2
            AbstractMap.Entry<Integer, Integer> point = new AbstractMap.SimpleEntry<>(xModifier*i + x, yModifier*i + y);
            if (firstRevisitedLocation == null && visited.contains(point)) {
                firstRevisitedLocation = point;
            } else if (firstRevisitedLocation == null) {
                visited.add(point);
            }
        });
        x += xModifier * steps;
        y += yModifier * steps;
    }

    public int getFinalManhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public int getFirstRevisitedLocationManhattanDistance() {
        return Math.abs(firstRevisitedLocation.getKey()) + Math.abs(firstRevisitedLocation.getValue());
    }

    public static void main(String[] args) {
        EasterBunnyHQ location = new EasterBunnyHQ();
        System.out.println("Part 1: " + location.getFinalManhattanDistance());
        System.out.println("Part 2: " + location.getFirstRevisitedLocationManhattanDistance());
    }
}
