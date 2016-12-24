import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EasterBunnyHQ {

    private static class Position {
        public int y;
        public int x;
        public int angle;

        Position(int y, int x, int angle) {
            this.y = y;
            this.x = x;
            this.angle = angle;
        }

        public void setAngle(int angle) {
            this.angle = angle;
        }
    }

    private String[] instructions;

    public EasterBunnyHQ() {
        try {
            instructions = new String(Files.readAllBytes(Paths.get("day_01/instructions"))).split(",\\s+");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getFinalManhattanDistance() {
        Position position = new Position(0, 0, 0);
        getStepStream(position).forEach(i -> {
            position.x += (int) Math.sin(Math.toRadians(position.angle)) * i;
            position.y += (int) Math.cos(Math.toRadians(position.angle)) * i;
        });
        return Math.abs(position.x) + Math.abs(position.y);
    }

    public int getRevisitedManhattanDistance() {
        Position position = new Position(0, 0, 0);
        HashSet<String> visited = new HashSet<>();
        getStepStream(position).anyMatch(i -> {
            int xModifier = (int) Math.sin(Math.toRadians(position.angle));
            int yModifier = (int) Math.cos(Math.toRadians(position.angle));
            return IntStream.rangeClosed(1, i).anyMatch(j -> {
                position.x += xModifier;
                position.y += yModifier;
                String positionStr = position.y + "," + position.x;
                boolean revisited = visited.contains(positionStr);
                visited.add(positionStr);
                return revisited;
            });
        });
        return Math.abs(position.x) + Math.abs(position.y);
    }

    private IntStream getStepStream(Position state) {
        return Stream.of(instructions)
                .peek(s -> state.setAngle(nextAngle(s, state.angle)))
                .mapToInt(s -> Integer.parseInt(s.substring(1)));
    }

    private int nextAngle(String instruction, int currentAngle) {
        switch (instruction.charAt(0)) {
            case 'L': return currentAngle - 90;
            case 'R': return currentAngle + 90;
            default: return currentAngle;
        }
    }

    public static void main(String[] args) {
        EasterBunnyHQ location = new EasterBunnyHQ();
        System.out.println("Part 1: " + location.getFinalManhattanDistance());
        System.out.println("Part 2: " + location.getRevisitedManhattanDistance());
    }
}
