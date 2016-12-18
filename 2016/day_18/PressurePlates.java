import java.util.ArrayList;
import java.util.stream.IntStream;

public class PressurePlates {

    private ArrayList<ArrayList<Boolean>> plates = new ArrayList<>();

    public PressurePlates(String firstRow) {
        ArrayList<Boolean> row = new ArrayList<>();
        row.add(false);
        firstRow.chars()
                .forEachOrdered(i -> row.add((char)i == '^' ? true : false));
        row.add(false);
        plates.add(row);
    }

    public int generateRows(int numOfRows) {
        IntStream.range(plates.size(), numOfRows)
                .forEachOrdered(i -> generateNextRow());
        return (int)plates.stream()
                .mapToLong(r -> r.stream().filter(b -> !b).count() - 2)
                .sum();
    }

    private void generateNextRow() {
        ArrayList<Boolean> lastRow = plates.get(plates.size() - 1);
        ArrayList<Boolean> row = new ArrayList<>(lastRow.size());
        row.add(false);
        IntStream.range(1, lastRow.size() - 1)
                .forEachOrdered(i -> row.add(isTrap(lastRow.get(i-1), lastRow.get(i), lastRow.get(i+1))));
        row.add(false);
        plates.add(row);
    }

    private boolean isTrap(boolean left, boolean center, boolean right) {
        return (left && center && !right)
                || (!left && center && right)
                || (left && !center && !right)
                || (!left && !center && right);
    }

    public static void main(String args[]) {
        PressurePlates pp = new PressurePlates(".^^^.^.^^^.^.......^^.^^^^.^^^^..^^^^^.^.^^^..^^.^.^^..^.^..^^...^.^^.^^^...^^.^.^^^..^^^^.....^....");
        System.out.println("Part 1: " + pp.generateRows(40));
        System.out.println("Part 2: " + pp.generateRows(400000));
    }
}
