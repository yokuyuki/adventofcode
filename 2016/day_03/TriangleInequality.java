import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TriangleInequality {

    private List<String> triangles;

    public TriangleInequality() {
        try {
            triangles = Files.readAllLines(Paths.get("day_03/triangles"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long countValidTriangles() {
        return triangles.parallelStream()
                .map(s -> Stream.of(s.trim().split("\\s+")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()))
                .filter(this::isValidTriangle).count();
    }

    private boolean isValidTriangle(List<Integer> sides) {
        return 2*Collections.max(sides) < sides.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        System.out.println("Part 1: " + new TriangleInequality().countValidTriangles());
    }
}
