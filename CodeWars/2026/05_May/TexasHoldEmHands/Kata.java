/*
// preloaded record

public record Hand(String type, String[] ranks) {}
*/

import java.util.HashMap;
import org.apache.commons.lang3.ArrayUtils;

record SuitCount(char suit, int count) {}

public class Kata {
    public static Hand hand(String[] holeCards, String[] communityCards) {
        String[] cards = ArrayUtils.addAll(holeCards, communityCards);
        System.out.println(highestSuitCount(cards));
        return new Hand("nothing", new String[] { "A", "Q", "9", "6", "3" });
    }
  
    private static SuitCount highestSuitCount(String[] cards) {
      HashMap<String, Integer> suitCountMap = new HashMap<String, Integer>();
        suitCountMap.put("♣", 0);
        suitCountMap.put("♥", 0);
        suitCountMap.put("♠", 0);
        suitCountMap.put("♦", 0);

      System.out.println(suitCountMap);
      
      for (String card : cards) {
        char suit = card.charAt(1);
      }
      
      return new SuitCount("♣".charAt(0), 0);
    }
}
