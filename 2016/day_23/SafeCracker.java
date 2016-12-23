import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SafeCracker {

    private static class Instruction {
        enum TYPE {
            CPY, INC, DEC, JNZ, TGL
        }

        private TYPE type;
        private String param1, param2;

        Instruction(String instruction) {
            String[] parsed = instruction.split(" ");
            switch (parsed[0]) {
                case "cpy":
                    this.type = TYPE.CPY;
                    break;
                case "inc":
                    this.type = TYPE.INC;
                    break;
                case "dec":
                    this.type = TYPE.DEC;
                    break;
                case "jnz":
                    this.type = TYPE.JNZ;
                    break;
                case "tgl":
                    this.type = TYPE.TGL;
                    break;
            }
            this.param1 = parsed[1];
            this.param2 = parsed.length < 3 ? null : parsed[2];
        }

        TYPE getType() {
            return type;
        }

        String getParam1() {
            return param1;
        }

        String getParam2() {
            return param2;
        }

        void toggle() {
            switch (type) {
                case CPY:
                    type = TYPE.JNZ;
                    break;
                case JNZ:
                    type = TYPE.CPY;
                    break;
                case INC:
                    type = TYPE.DEC;
                    break;
                case DEC:
                case TGL:
                    type = TYPE.INC;
                    break;
            }
        }
    }

    private ArrayList<Instruction> instructions;
    private Map<Character, Integer> registers;

    public SafeCracker(Map<Character, Integer> initialValues) {
        try {
            registers = initialValues;
            instructions = Files.lines(Paths.get("day_23/instructions"))
                    .map(Instruction::new)
                    .collect(Collectors.toCollection(ArrayList::new));
            executeInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getValue(String value) {
        char check = value.charAt(0);
        return Character.isLetter(check) ? registers.get(check) : Integer.parseInt(value);
    }

    private void executeInstructions() {
        AtomicInteger index = new AtomicInteger();
        IntStream.iterate(0, i -> i + 1)
                .allMatch(i -> index.updateAndGet(this::executeInstruction) < instructions.size());
    }

    private int executeInstruction(int index) {
        Instruction instruction = instructions.get(index);
        switch (instruction.getType()) {
            case CPY:
                copy(instruction);
                break;
            case INC:
                increment(instruction);
                break;
            case DEC:
                decrement(instruction);
                break;
            case JNZ:
                return index + jumpIfNotZero(instruction);
            case TGL:
                toggle(instruction, index);
                break;
        }
        return index + 1;
    }

    private void copy(Instruction instruction) {
        char dest = instruction.getParam2().charAt(0);
        if (Character.isLetter(dest)) {
            registers.put(dest, getValue(instruction.getParam1()));
        }
    }

    private void increment(Instruction instruction) {
        char dest = instruction.getParam1().charAt(0);
        registers.put(dest, registers.get(dest) + 1);
    }

    private void decrement(Instruction instruction) {
        char dest = instruction.getParam1().charAt(0);
        registers.put(dest, registers.get(dest) - 1);
    }

    private int jumpIfNotZero(Instruction instruction) {
        return getValue(instruction.getParam1()) != 0 ? getValue(instruction.getParam2()) : 1;
    }

    private void toggle(Instruction instruction, int currentIndex) {
        int toggleIndex = currentIndex + getValue(instruction.getParam1());
        if (toggleIndex < instructions.size()) {
            instructions.get(toggleIndex).toggle();
        }
    }

    public static void main(String args[]) {
        SafeCracker sc = new SafeCracker(new HashMap<Character, Integer>() {{
            this.put('a', 7);
            this.put('b', 0);
            this.put('c', 0);
            this.put('d', 0);
        }});
        System.out.println("Part 1: " + sc.getValue("a"));
        sc = new SafeCracker(new HashMap<Character, Integer>() {{
            this.put('a', 12);
            this.put('b', 0);
            this.put('c', 0);
            this.put('d', 0);
        }});
        System.out.println("Part 2: " + sc.getValue("a"));
    }
}
