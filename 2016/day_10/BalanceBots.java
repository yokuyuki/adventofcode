import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BalanceBots {

    private static final Pattern assignPattern = Pattern.compile("value (\\d+) goes to bot (\\d+)");
    private static final Pattern exchangePattern = Pattern.compile("bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)");

    private HashMap<Integer, Bot> bots = new HashMap<>();
    private HashMap<Integer, Integer> outputBins = new HashMap<>();

    private class Bot {
        public ArrayList<String> instructions = new ArrayList<>();
        public LinkedList<Integer> microchips = new LinkedList<>();
        public ArrayList<String> comparisons = new ArrayList<>();
    }

    public BalanceBots() {
        try {
            Files.lines(Paths.get("day_10/instructions")).forEachOrdered(this::processInstructions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBotHandlingComparison(int low, int high) {
        String comparison = low + ":" + high;
        return bots.entrySet().parallelStream()
                .filter(e -> e.getValue().comparisons.parallelStream().anyMatch(s -> s.equals(comparison)))
                .mapToInt(Map.Entry::getKey)
                .findFirst().getAsInt();
    }

    public int getOutputValue(int outputBin) {
        return outputBins.get(outputBin);
    }

    private void printState() {
        bots.forEach((i, b) -> b.microchips.forEach(j -> System.out.println("Bot " + i + ": " + j)));
        outputBins.forEach((a, b) -> System.out.println("Output " + a + ": " + b));
    }

    private void processInstructions(String instruction) {
        Matcher m = assignPattern.matcher(instruction);
        if (m.matches()) {
            Bot bot = getBot(m.group(2));
            addMicrochip(bot, Integer.parseInt(m.group(1)));
        }

        m = exchangePattern.matcher(instruction);
        if (m.matches()) {
            Bot bot = getBot(m.group(1));
            if (bot.microchips.size() < 2) {
                bot.instructions.add(instruction);
            } else {
                int low = bot.microchips.removeFirst();
                int high = bot.microchips.removeFirst();
                bot.comparisons.add("" + low + ":" + high);
                if (m.group(2).equals("bot")) {
                    addMicrochip(getBot(m.group(3)), low);
                } else {
                    outputBins.put(Integer.parseInt(m.group(3)), low);
                }
                if (m.group(4).equals("bot")) {
                    addMicrochip(getBot(m.group(5)), high);
                } else {
                    outputBins.put(Integer.parseInt(m.group(5)), high);
                }
            }
        }

    }

    private Bot getBot(String name) {
        Integer botName = Integer.parseInt(name);
        Bot bot = bots.getOrDefault(botName, new Bot());
        bots.put(botName, bot);
        return bot;
    }

    private void addMicrochip(Bot bot, Integer chipNumber) {
        if (bot.microchips.isEmpty()) {
            bot.microchips.add(chipNumber);
        } else {
            if (bot.microchips.peek() > chipNumber) {
                bot.microchips.addFirst(chipNumber);
            } else {
                bot.microchips.addLast(chipNumber);
            }
            reevaluateInstructions(bot);
        }
    }

    private void reevaluateInstructions(Bot bot) {
        bot.instructions.forEach(this::processInstructions);
    }

    public static void main(String args[]) {
        BalanceBots bb = new BalanceBots();
        System.out.println("Part 1: " + bb.getBotHandlingComparison(17, 61));
        System.out.println("Part 2: " + bb.getOutputValue(0)*bb.getOutputValue(1)*bb.getOutputValue(2));
    }

}
