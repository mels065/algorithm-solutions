/*
// preloaded record

public record Hand(String type, String[] ranks) {}
*/

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import org.apache.commons.lang3.ArrayUtils;

class FlushData {
  public char suit;
  public int count, strength;
  public String[] cards;
  
  FlushData(char s, int c, int st, String[] crds) {
    suit = s;
    count = c;
    strength = st;
    cards = crds;
  }
  
  FlushData(char s) {
    this(s, 0, 0, new String[]{});
  }
}

public class Kata {
    public static Hand hand(String[] holeCards, String[] communityCards) {
        String[] cards = ArrayUtils.addAll(holeCards, communityCards);
        Arrays.sort(cards, Comparator.comparing(Kata::getRank));
        HashMap<String, FlushData> flushMap = generateFlushMap(cards);
      
        for (FlushData fd: flushMap.values()) {
          System.out.println(fd.suit);
          System.out.println(fd.count);
          System.out.println(fd.strength);

          for (String card: fd.cards) {
            System.out.println(card);
          }
        }

        return new Hand("nothing", new String[] { "A", "Q", "9", "6", "3" });
    }
  
    private static HashMap<String, FlushData> generateFlushMap(String[] cards) {
      HashMap<String, FlushData> flushMap = new HashMap<String, FlushData>();
        flushMap.put("♣", new FlushData('♣'));
        flushMap.put("♥", new FlushData('♥'));
        flushMap.put("♠", new FlushData('♠'));
        flushMap.put("♦", new FlushData('♦'));
      
      for (String card : cards) {
        String suit = getSuit(card);
        int rank = getRank(card);
        
        FlushData fd = flushMap.get(suit);
        fd.count += 1;
        fd.strength += rank;
        ArrayUtils.add(fd.cards, card);
      }
      
      return flushMap;
    }
  
    private static String getSuit(String card) {
      return card.substring(1, 2);
    }
  
    private static int getRank(String card) {
      String rank = card.substring(0, 1);
      return switch(rank) {
          case "A" -> 1;
          case "J" -> 11;
          case "Q" -> 12;
          case "K" -> 13;
          default -> Integer.parseInt(rank);
      };
    }
}
