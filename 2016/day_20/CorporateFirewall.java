import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CorporateFirewall {

    private class AddressRange {
        private long low;
        private long high;

        public AddressRange(long low, long high) {
            this.low = low;
            this.high = high;
        }

        public boolean notInRange(long address) {
            return address < low || address > high;
        }
    }

    private List<AddressRange> blocklist;

    public CorporateFirewall() {
        try {
            blocklist = Files.lines(Paths.get("day_20/blocklist"))
                    .map(s -> s.split("-"))
                    .map(sa -> new AddressRange(Long.parseLong(sa[0]), Long.parseLong(sa[1])))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long lowestAvailableAddress() {
        return LongStream.rangeClosed(0, 4294967295L)
                .filter(this::notInBlocklist).findFirst().orElse(-1);
    }

    private boolean notInBlocklist(long address) {
        return blocklist.parallelStream()
                .allMatch(a -> a.notInRange(address));
    }

    public static void main(String args[]) {
        CorporateFirewall firewall = new CorporateFirewall();
        System.out.println("Part 1: " + firewall.lowestAvailableAddress());
    }
}
