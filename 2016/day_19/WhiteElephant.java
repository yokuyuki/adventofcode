import java.util.ArrayList;
import java.util.stream.IntStream;

public class WhiteElephant {

    private ArrayList<Integer> presents = new ArrayList<>();

    public WhiteElephant(int elves) {
        IntStream.range(0, elves)
                .forEachOrdered(i -> presents.add(1));
    }

    public int exchange() {
        return IntStream.iterate(0, i -> i + 1)
                .map(i -> i % presents.size())
                .filter(i -> presents.get(i) != 0)
                .peek(i -> stealPresents(i))
                .filter(i -> presents.get(i) == presents.size())
                .findFirst().orElse(Integer.MAX_VALUE) + 1;
    }

    private void stealPresents(int stealer) {
        int stealee = IntStream.iterate(stealer + 1, i -> i + 1)
                .map(i -> i % presents.size())
                .filter(i -> presents.get(i) != 0)
                .findFirst().orElse(Integer.MAX_VALUE);
        presents.set(stealer, presents.get(stealer) + presents.get(stealee));
        presents.set(stealee, 0);
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new WhiteElephant(3005290).exchange());
    }
}
