import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.IntStream;

public class VaultMaze {

    private static class MazeState {
        public int y, x;
        public String path;

        MazeState(int y, int x, String path) {
            this.y = y;
            this.x = x;
            this.path = path;
        }
    }

    private MessageDigest md5;
    private String passcode;

    public VaultMaze(String passcode) {
        this.passcode = passcode;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getShortestPath() {
        LinkedList<MazeState> queue = new LinkedList<>();
        queue.addLast(new MazeState(0, 0, ""));
        return IntStream.iterate(0, i -> i + 1)
                .mapToObj(i -> queue.removeFirst())
                .filter(state -> state.y >= 0 && state.y < 4 && state.x >= 0 && state.x < 4)
                .peek(state -> {
                    int[] permissions = getDoorPermissions(state.path);
                    if (permissions[0] > 10) {
                        queue.addLast(new MazeState(state.y-1, state.x, state.path + "U"));
                    }
                    if (permissions[1] > 10) {
                        queue.addLast(new MazeState(state.y+1, state.x, state.path + "D"));
                    }
                    if (permissions[2] > 10) {
                        queue.addLast(new MazeState(state.y, state.x-1, state.path + "L"));
                    }
                    if (permissions[3] > 10) {
                        queue.addLast(new MazeState(state.y, state.x+1, state.path + "R"));
                    }
                }).filter(state -> state.y == 3 && state.x == 3)
                .map(state -> state.path)
                .findFirst().orElse("");
    }

    public int getLongestPathLength() {
        return getLongestPathLength(new MazeState(0, 0, ""));
    }

    private int getLongestPathLength(MazeState state) {
        if (state.y == 3 && state.x == 3) {
            return state.path.length();
        }

        if (state.y >= 0 && state.y < 4 && state.x >= 0 && state.x < 4) {
            int[] permissions = getDoorPermissions(state.path);
            ArrayList<Integer> pathLengths = new ArrayList<>(4);
            if (permissions[0] > 10) {
                pathLengths.add(getLongestPathLength(new MazeState(state.y-1, state.x, state.path + "U")));
            }
            if (permissions[1] > 10) {
                pathLengths.add(getLongestPathLength(new MazeState(state.y+1, state.x, state.path + "D")));
            }
            if (permissions[2] > 10) {
                pathLengths.add(getLongestPathLength(new MazeState(state.y, state.x-1, state.path + "L")));
            }
            if (permissions[3] > 10) {
                pathLengths.add(getLongestPathLength(new MazeState(state.y, state.x+1, state.path + "R")));
            }
            return pathLengths.size() > 0 ? Collections.max(pathLengths) : -1;
        } else {
            return -1;
        }
    }

    private int[] getDoorPermissions(String path) {
        md5.update((this.passcode + path).getBytes());
        String hash = String.format("%032x", new BigInteger(1, md5.digest()));
        return IntStream.range(0, 4)
                .map(hash::charAt)
                .map(c -> Character.digit(c, 16))
                .toArray();
    }

    public static void main(String args[]) {
        VaultMaze maze = new VaultMaze("vwbaicqe");
        System.out.println("Part 1: " + maze.getShortestPath());
        System.out.println("Part 2: " + maze.getLongestPathLength());
    }
}
