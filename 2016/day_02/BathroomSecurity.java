import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BathroomSecurity {

    private int button = 5;
    private String combination = "";

    public BathroomSecurity() {
        try {
            Files.lines(Paths.get("day_02/instructions")).forEachOrdered(this::processInstructions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processInstructions(String instruction) {
        instruction.chars()
                .mapToObj(i -> (char) i)
                .forEachOrdered(this::moveFinger);
        combination += button;
    }

    private void moveFinger(char direction) {
        int projectedButton = button;
        boolean isMovingHorizontally = false;
        switch(direction) {
            case 'U':
                projectedButton -= 3;
                break;
            case 'D':
                projectedButton += 3;
                break;
            case 'L':
                projectedButton -= 1;
                isMovingHorizontally = true;
                break;
            case 'R':
                projectedButton += 1;
                isMovingHorizontally = true;
                break;
        }

        if (projectedButton < 1 || projectedButton > 9 ||
                (isMovingHorizontally && (projectedButton-1)/3 != (button-1)/3)) {
            return;
        }

        button = projectedButton;
    }

    public String getBathroomCode() {
        return combination;
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new BathroomSecurity().getBathroomCode());
    }
}
