import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
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

        public long getLow() {
            return low;
        }

        public long getHigh() {
            return high;
        }

        public long size() {
            return high - low + 1;
        }

        public boolean extend(AddressRange addressRange) {
            if (addressRange.getLow() <= this.high) {
                if (this.high < addressRange.getHigh()) {
                    this.high = addressRange.getHigh();
                }
                return true;
            }
            return false;
        }
    }

    private LinkedList<AddressRange> blocklist = new LinkedList<>();

    public CorporateFirewall() {
        try {
            Files.lines(Paths.get("day_20/blocklist"))
                    .map(s -> s.split("-"))
                    .map(sa -> new AddressRange(Long.parseLong(sa[0]), Long.parseLong(sa[1])))
                    .sorted(Comparator.comparing(AddressRange::getLow).thenComparing(AddressRange::getHigh))
                    .forEachOrdered(a -> {
                        if (blocklist.isEmpty() || !blocklist.getLast().extend(a)) {
                            blocklist.add(a);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long lowestAvailableAddress() {
        return LongStream.rangeClosed(0, 4294967295L)
                .filter(this::notInBlocklist).findFirst().orElse(-1);
    }

    public long numOfAllowedAddresses() {
        return 4294967296L - blocklist.parallelStream()
                .mapToLong(AddressRange::size)
                .sum();
    }

    private boolean notInBlocklist(long address) {
        return blocklist.parallelStream()
                .allMatch(a -> a.notInRange(address));
    }

    public static void main(String args[]) {
        CorporateFirewall firewall = new CorporateFirewall();
        System.out.println("Part 1: " + firewall.lowestAvailableAddress());
        System.out.println("Part 2: " + firewall.numOfAllowedAddresses());
    }
}
