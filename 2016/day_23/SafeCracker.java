import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SafeCracker {

    private ArrayList<String> instructions;
    private Map<Character, Integer> registers;

    public SafeCracker(Map<Character, Integer> initialValues) {
        try {
            registers = initialValues;
            instructions = Files.lines(Paths.get("day_23/instructions")).collect(Collectors.toCollection(ArrayList::new));
            executeInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRegister(char register) {
        return registers.get(register);
    }

    public int getValue(String value) {
        char check = value.charAt(0);
        return Character.isLetter(check) ? registers.get(check) : Integer.parseInt(value);
    }

    private void executeInstructions() {
        int index = 0;
        while (index < instructions.size()) {
            String[] parsedInstruction = instructions.get(index).split(" ");
            switch (parsedInstruction[0]) {
                case "cpy":
                    char dest = parsedInstruction[2].charAt(0);
                    char src = parsedInstruction[1].charAt(0);
                    if (Character.isLetter(src)) {
                        registers.put(dest, registers.get(src));
                    } else if (Character.isLetter(dest)) {
                        registers.put(dest, Integer.parseInt(parsedInstruction[1]));
                    }
                    break;
                case "inc":
                    dest = parsedInstruction[1].charAt(0);
                    registers.put(dest, registers.get(dest) + 1);
                    break;
                case "dec":
                    dest = parsedInstruction[1].charAt(0);
                    registers.put(dest, registers.get(dest) - 1);
                    break;
                case "jnz":
                    int value = getValue(parsedInstruction[1]);
                    if (value != 0) {
                        index += getValue(parsedInstruction[2]);
                        continue;
                    }
                    break;
                case "tgl":
                    toggleInstruction(index + getValue(parsedInstruction[1]));
                    break;
            }
            index++;
        }
    }

    private void toggleInstruction(int index) {
        if (index < instructions.size()) {
            String instruction = instructions.get(index);
            String[] parsedInstruction = instruction.split(" ");
            String replacement;
            switch (parsedInstruction[0]) {
                case "cpy":
                    replacement = "jnz";
                    break;
                case "jnz":
                    replacement = "cpy";
                    break;
                case "inc":
                    replacement = "dec";
                    break;
                default:
                    replacement = "inc";
                    break;
            }
            instruction = instruction.replace(parsedInstruction[0], replacement);
            instructions.set(index, instruction);
        }
    }

    public static void main(String args[]) {
        SafeCracker sc = new SafeCracker(new HashMap<Character, Integer>() {{
            this.put('a', 7);
            this.put('b', 0);
            this.put('c', 0);
            this.put('d', 0);
        }});
        System.out.println("Part 1: " + sc.getRegister('a'));
    }
}
