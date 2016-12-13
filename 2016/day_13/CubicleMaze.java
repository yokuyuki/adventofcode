import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

public class CubicleMaze {

    private int seed;

    public CubicleMaze(int seed) {
        this.seed = seed;
    }

    public int minWalk(int x, int y) {
        return walk(1, 1, x, y, new HashSet<>());
    }

    private int walk(int posX, int posY, int destX, int destY, HashSet<String> visited) {
        String position = posX + "," + posY;
        if (posX < 0 || posX < 0 || isWall(posX, posY) || visited.contains(position)) {
            return Integer.MAX_VALUE;
        }

        if (posX == destX && posY == destY) {
            return 0;
        }

        visited.add(position);
        ArrayList<Integer> steps = new ArrayList<>(4);
        steps.add(walk(posX + 1, posY, destX, destY, visited));
        steps.add(walk(posX - 1, posY, destX, destY, visited));
        steps.add(walk(posX, posY + 1, destX, destY, visited));
        steps.add(walk(posX, posY - 1, destX, destY, visited));
        Optional<Integer> minSteps = steps.parallelStream()
                .filter(i -> i != Integer.MAX_VALUE)
                .min(Integer::compareTo);
        visited.remove(position);

        return minSteps.map(i -> i + 1).orElse(Integer.MAX_VALUE);
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
        System.out.println("Part 1: " + maze.minWalk(31, 39));
    }

}
