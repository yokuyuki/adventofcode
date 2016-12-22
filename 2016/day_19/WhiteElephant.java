import java.util.ArrayList;
import java.util.stream.IntStream;

public class WhiteElephant {

    private ArrayList<Integer> presents = new ArrayList<>();
    private int elves;
    private boolean isOptimized;
    private int stealee;

    public WhiteElephant(int elves, boolean isOptimized) {
        this.elves = elves;
        this.isOptimized = isOptimized;
        IntStream.range(0, elves)
                .forEachOrdered(i -> presents.add(1));
        stealee = isOptimized ? elves/2 : 1;
    }

    public int exchange() {
        return IntStream.iterate(0, i -> i + 1)
                .map(i -> i % presents.size())
                .filter(i -> presents.get(i) != 0)
                .peek(this::stealPresents)
                .peek(i -> findNextStealee())
                .filter(i -> presents.get(i) == presents.size())
                .findFirst().orElse(Integer.MAX_VALUE) + 1;
    }

    private void findNextStealee() {
        if (isOptimized) {
            stealee = IntStream.iterate(stealee + 1, i -> i + 1)
                .map(i -> i % presents.size())
                .filter(i -> presents.get(i) != 0)
                .skip((elves & 1) == 1 ? 0 : 1)
                .findFirst().orElse(Integer.MAX_VALUE);
        } else {
            stealee = IntStream.iterate(stealee + 1, i -> i + 1)
                .map(i -> i % presents.size())
                .filter(i -> presents.get(i) != 0)
                .skip(1)
                .findFirst().orElse(Integer.MAX_VALUE);
        }
    }

    private void stealPresents(int stealer) {
        presents.set(stealer, presents.get(stealer) + presents.get(stealee));
        presents.set(stealee, 0);
        elves--;
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new WhiteElephant(3005290, false).exchange());
        System.out.println("Part 2: " + new WhiteElephant(3005290, true).exchange());
    }
}
