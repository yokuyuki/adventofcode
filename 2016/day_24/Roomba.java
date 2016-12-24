import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Roomba {

    private static class RoombaState {
        public int y;
        public int x;
        public int steps;

        RoombaState(int y, int x, int steps) {
            this.y = y;
            this.x = x;
            this.steps = steps;
        }
    }

    private ArrayList<ArrayList<Integer>> map;
    private HashMap<Integer, RoombaState> points = new HashMap<>();
    private HashMap<String, Integer> distances = new HashMap<>();

    public Roomba() {
        try {
            map = Files.lines(Paths.get("day_24/map"))
                    .map(s -> s.chars()
                            .mapToObj(i -> {
                                switch ((char)i) {
                                    case '#':
                                        return -1;
                                    case '.':
                                        return Integer.MAX_VALUE;
                                    default:
                                        return Character.getNumericValue(i);
                                }
                            }).collect(Collectors.toCollection(ArrayList::new)))
                    .collect(Collectors.toCollection(ArrayList::new));
            IntStream.range(0, map.size())
                    .forEach(i -> IntStream.range(0, map.get(i).size())
                            .forEach(j -> {
                                int location = map.get(i).get(j);
                                if (location >= 0 && location != Integer.MAX_VALUE) {
                                    points.put(location, new RoombaState(i, j, 0));
                                }
                            }));
            computeDistances();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void computeDistances() {
        points.entrySet().parallelStream()
                .forEach(e -> points.entrySet().parallelStream()
                        .filter(f -> e.getKey() != f.getKey())
                        .forEach(f -> distances.put("" + e.getKey() + "," + f.getKey(), getShortestPath(e.getValue(), f.getValue()))));
    }

    public int getShortestVisit() {
        ArrayList<ArrayList<Integer>> pathPermutations = generatePermutations(IntStream.range(1, points.size()).boxed().collect(Collectors.toSet()));
        return pathPermutations.parallelStream()
                .mapToInt(a -> IntStream.range(1, a.size())
                            .map(i -> distances.get("" + a.get(i-1) + "," + a.get(i)))
                            .sum())
                .min().getAsInt();
    }

    private int getShortestPath(RoombaState begin, RoombaState end) {
        LinkedList<RoombaState> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        queue.addLast(begin);
        while (true) {
            RoombaState state = queue.removeFirst();
            if (state.y == end.y && state.x == end.x) {
                return state.steps;
            }
            String position = state.y + "," + state.x;
            if (!visited.contains(position) && map.get(state.y).get(state.x) >= 0) {
                visited.add(position);
                queue.addLast(new RoombaState(state.y - 1, state.x, state.steps + 1));
                queue.addLast(new RoombaState(state.y + 1, state.x, state.steps + 1));
                queue.addLast(new RoombaState(state.y, state.x - 1, state.steps + 1));
                queue.addLast(new RoombaState(state.y, state.x + 1, state.steps + 1));
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> generatePermutations(Set<Integer> remaining) {
        if (remaining.isEmpty()) {
            ArrayList<ArrayList<Integer>> outer = new ArrayList<>();
            ArrayList<Integer> inner = new ArrayList<>();
            inner.add(0);
            outer.add(inner);
            return outer;
        }

        HashSet<Integer> nextRemaining = new HashSet<>(remaining);
        return remaining.stream()
                .flatMap(i -> {
                    nextRemaining.remove(i);
                    ArrayList<ArrayList<Integer>> subset = generatePermutations(nextRemaining);
                    nextRemaining.add(i);
                    return subset.stream().peek(a -> a.add(i));
                }).collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new Roomba().getShortestVisit());
    }
}
