import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KineticSculpture {

    private class Disc {
        private int totalPositions;
        private int startingPosition;
        private int position;

        public Disc(int totalPositions, int startingPosition) {
            this.totalPositions = totalPositions;
            this.startingPosition = startingPosition;
        }

        public void reset() {
            position = startingPosition;
        }

        public int advanceTime(int seconds) {
            position = (position + seconds) % totalPositions;
            return position;
        }
    }

    private ArrayList<Disc> discs;
    private Pattern pattern = Pattern.compile("Disc #\\d+ has (\\d+) positions; at time=0, it is at position (\\d+).");

    public KineticSculpture() {
        try {
            discs = Files.lines(Paths.get("day_15/arrangement")).map(s -> {
                Matcher m = pattern.matcher(s);
                m.matches();
                return new Disc(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
            }).collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendDisc(int totalPositions, int startingPosition) {
        discs.add(new Disc(totalPositions, startingPosition));
    }

    public int getCapsule() {
        OptionalInt firstTime =  IntStream.iterate(0, i -> i + 1)
                .filter(this::canGetCapsule)
                .findFirst();
        return firstTime.orElse(Integer.MAX_VALUE);
    }

    private boolean canGetCapsule(int startingTime) {
        return IntStream.range(0, discs.size())
                .map(i -> {
                    Disc disc = discs.get(i);
                    disc.reset();
                    return disc.advanceTime(startingTime + i + 1);
                }).allMatch(i -> i == 0);
    }

    public static void main(String args[]) {
        KineticSculpture ks = new KineticSculpture();
        System.out.println("Part 1: " + ks.getCapsule());
        ks.appendDisc(11, 0);
        System.out.println("Part 1: " + ks.getCapsule());
    }
}
