import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BathroomSecurity {

    private char[][] keypad1 = new char[][] {
            "00000".toCharArray(),
            "01230".toCharArray(),
            "04560".toCharArray(),
            "07890".toCharArray(),
            "00000".toCharArray()
    };
    private int x1 = 2, y1 = 2;
    private String code1 = "";

    private char[][] keypad2 = new char[][] {
            "0000000".toCharArray(),
            "0001000".toCharArray(),
            "0023400".toCharArray(),
            "0567890".toCharArray(),
            "00ABC00".toCharArray(),
            "000D000".toCharArray(),
            "0000000".toCharArray()
    };
    private int x2 = 1, y2 = 3;
    private String code2 = "";

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
        code1 += keypad1[y1][x1];
        code2 += keypad2[y2][x2];
    }

    private void moveFinger(char direction) {
        int xModifier = 0, yModifier = 0;
        switch(direction) {
            case 'U':
                yModifier -= 1; break;
            case 'D':
                yModifier += 1; break;
            case 'L':
                xModifier -= 1; break;
            case 'R':
                xModifier += 1; break;
        }

        int x1Projected = x1 + xModifier;
        int y1Projected = y1 + yModifier;
        if (keypad1[y1Projected][x1Projected] != '0') {
            x1 = x1Projected;
            y1 = y1Projected;
        }

        int x2Projected = x2 + xModifier;
        int y2Projected = y2 + yModifier;
        if (keypad2[y2Projected][x2Projected] != '0') {
            x2 = x2Projected;
            y2 = y2Projected;
        }
    }

    public String getBathroomCode1() {
        return code1;
    }

    public String getBathroomCode2() {
        return code2;
    }

    public static void main(String args[]) {
        BathroomSecurity bathroom = new BathroomSecurity();
        System.out.println("Part 1: " + bathroom.getBathroomCode1());
        System.out.println("Part 2: " + bathroom.getBathroomCode2());
    }
}
