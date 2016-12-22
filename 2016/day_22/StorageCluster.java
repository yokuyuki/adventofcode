import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StorageCluster {

    private class StorageNode {

        private String name;
        private int size;
        private int used;
        private int avail;

        public StorageNode(String name, int size, int used, int avail) {
            this.name = name;
            this.size = size;
            this.used = used;
            this.avail = avail;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }

        public int getUsed() {
            return used;
        }

        public int getAvail() {
            return avail;
        }
    }

    private List<StorageNode> nodes;

    public StorageCluster() {
        try {
            nodes = Files.lines(Paths.get("day_22/df"))
                    .skip(2)
                    .map(s -> {
                        String[] cols = s.split("\\s+");
                        return new StorageNode(cols[0],
                                Integer.parseInt(cols[1].replace("T", "")),
                                Integer.parseInt(cols[2].replace("T", "")),
                                Integer.parseInt(cols[3].replace("T", "")));
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNumOfViableNodePairs() {
        return (int)nodes.parallelStream()
                .mapToLong(a -> nodes.parallelStream()
                        .filter(b -> isViableNodePair(a, b))
                        .count())
                .sum();
    }

    private boolean isViableNodePair(StorageNode a, StorageNode b) {
        return a.getUsed() != 0
                && !a.equals(b)
                && a.getUsed() <= b.getAvail();
    }

    public static void main(String args[]) {
        System.out.println("Part 1: " + new StorageCluster().getNumOfViableNodePairs());
    }
}
