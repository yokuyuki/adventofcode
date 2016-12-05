import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TriangleInequality {

    private List<List<Integer>> triangles;

    public TriangleInequality() {
        try {
            triangles = Files.lines(Paths.get("day_03/triangles"))
                    .map(s -> Stream.of(s.trim().split("\\s+")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long countValidTrianglesNormally() {
        return countValidTriangles(triangles.parallelStream());
    }

    public long countValidTrianglesInColumnTriplets() {
        return countValidTriangles(IntStream.range(0, 3)
                .mapToObj(i -> IntStream.range(0, triangles.size())
                        .filter(j -> j % 3 == 0)
                        .mapToObj(k -> triangles.subList(k, k+3).stream().mapToInt(l -> l.get(i)).boxed().collect(Collectors.toList())))
                .flatMap(Function.identity()));
    }

    private long countValidTriangles(Stream<List<Integer>> triangleStream) {
        return triangleStream.filter(this::isValidTriangle).count();
    }

    private boolean isValidTriangle(List<Integer> sides) {
        return 2*Collections.max(sides) < sides.stream().mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) {
        TriangleInequality ti = new TriangleInequality();
        System.out.println("Part 1: " + ti.countValidTrianglesNormally());
        System.out.println("Part 2: " + ti.countValidTrianglesInColumnTriplets());
    }
}
