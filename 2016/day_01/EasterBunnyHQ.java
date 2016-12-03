import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class EasterBunnyHQ {

    private int x = 0;
    private int y = 0;
    private int angle = 0;

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
        x += Math.round(Math.sin(Math.toRadians(angle))) * steps;
        y += Math.round(Math.cos(Math.toRadians(angle))) * steps;
    }

    public int getManhattanDistance() {
        return Math.abs(x) + Math.abs(y);
    }

    public static void main(String[] args) {
        System.out.println(new EasterBunnyHQ().getManhattanDistance());
    }
}
