import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assembunny {

    private List<String> instructions;
    private Map<Character, Integer> registers;

    public Assembunny() {
        try {
            registers = new HashMap<>();
            registers.put('a', 0);
            registers.put('b', 0);
            registers.put('c', 0);
            registers.put('d', 0);
            instructions = Files.readAllLines(Paths.get("day_12/instructions"));
            executeInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getRegister(char register) {
        return registers.get(register);
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
                    } else {
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
                    char check = parsedInstruction[1].charAt(0);
                    int value = Character.isLetter(check) ? registers.get(check) : Integer.parseInt(parsedInstruction[1]);
                    if (value != 0) {
                        index += Integer.parseInt(parsedInstruction[2]);
                        continue;
                    }
            }
            index++;
        }
    }

    public static void main(String args[]) {
        Assembunny asm = new Assembunny();
        System.out.println("Part 1: " + asm.getRegister('a'));
    }

}
