/*
// preloaded record

public record Hand(String type, String[] ranks) {}
*/

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import org.apache.commons.lang3.ArrayUtils;

record SuitCount(char suit, int count, int strength, String[] hand) {
  SuitCount(char suit) {
    this(suit, 0, 0, new String[]{});
  }
}
record FlushData(boolean isFlush, SuitCount suitCount) {}

public class Kata {
    public static Hand hand(String[] holeCards, String[] communityCards) {
        String[] cards = ArrayUtils.addAll(holeCards, communityCards);
        Arrays.sort(cards, Comparator.comparing(Kata::getRank));
      
        System.out.println(highestSuitCount(cards));
        return new Hand("nothing", new String[] { "A", "Q", "9", "6", "3" });
    }
  
    private static FlushData highestSuitCount(String[] cards) {
      HashMap<String, SuitCount> suitCountMap = new HashMap<String, SuitCount>();
        suitCountMap.put("♣", new SuitCount('♣'));
        suitCountMap.put("♥", new SuitCount('♥'));
        suitCountMap.put("♠", new SuitCount('♠'));
        suitCountMap.put("♦", new SuitCount('♦'));
      
      for (String card : cards) {
        String suit = card.substring(1, 2);
        String rank = card.substring(0, 1);
      }
      
      return new FlushData(true, suitCountMap.get("♣"));
    }
  
    private static String getSuit(String card) {
      return card.substring(1, 2);
    }
  
    private static int getRank(String card) {
      String rank = card.substring(1, 2);
      return switch(rank) {
          case "A" -> 1;
          case "J" -> 11;
          case "Q" -> 12;
          case "K" -> 13;
          default -> Integer.parseInt(rank);
      };
    }
}
