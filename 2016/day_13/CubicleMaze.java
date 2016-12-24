import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class CubicleMaze {

    private static class CubicleState {
        public int y, x;
        public int steps;

        CubicleState(int y, int x, int steps) {
            this.y = y;
            this.x = x;
            this.steps = steps;
        }
    }

    private int seed;

    public CubicleMaze(int seed) {
        this.seed = seed;
    }

    private void print(int width, int height) {
        IntStream.range(0, height)
                .forEachOrdered(row -> {
                    IntStream.range(0, width)
                            .mapToObj(col -> isWall(col, row) ? '#' : '.')
                            .forEachOrdered(System.out::print);
                    System.out.println();
                });
    }

    public int getMinWalk(int x, int y) {
        LinkedList<CubicleState> queue = new LinkedList<>();
        queue.addLast(new CubicleState(1, 1, 0));
        HashSet<String> visited = new HashSet<>();
        return IntStream.iterate(0, i -> i+1)
                .mapToObj(i -> queue.removeFirst())
                .peek(state -> nextWalk(state, queue, visited))
                .filter(state -> state.y == y && state.x == x)
                .mapToInt(state -> state.steps)
                .findFirst().getAsInt();
    }

    public int getDistinctWalk(int steps) {
        LinkedList<CubicleState> queue = new LinkedList<>();
        queue.addLast(new CubicleState(1, 1, 0));
        HashSet<String> visited = new HashSet<>();
        while (true) {
            CubicleState state = queue.removeFirst();
            if (state.steps > steps) {
                return visited.size();
            }
            nextWalk(state, queue, visited);
        }
    }

    private void nextWalk(CubicleState state, LinkedList<CubicleState> queue, HashSet<String> visited) {
        String position = state.y + "," + state.x;
        if (state.y >= 0 && state.x >= 0 && !isWall(state.x, state.y) && !visited.contains(position)) {
            visited.add(position);
            queue.addLast(new CubicleState(state.y-1, state.x, state.steps+1));
            queue.addLast(new CubicleState(state.y+1, state.x, state.steps+1));
            queue.addLast(new CubicleState(state.y, state.x-1, state.steps+1));
            queue.addLast(new CubicleState(state.y, state.x+1, state.steps+1));
        }
    }

    private boolean isWall(int x, int y) {
        int sum = x*x + 3*x + 2*x*y + y + y*y + seed;
        int ones;
        for (ones = 0; sum != 0; sum >>= 1) {
            ones += sum & 1;
        }
        return (ones & 1) == 1;
    }

    public static void main(String args[]) {
        CubicleMaze maze = new CubicleMaze(1350);
        System.out.println("Part 1: " + maze.getMinWalk(31, 39));
        System.out.println("Part 2: " + maze.getDistinctWalk(50));
    }
}
