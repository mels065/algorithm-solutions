/*
// preloaded record

public record Hand(String type, String[] ranks) {}
*/

import java.lang.IllegalArgumentException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.*;
import org.apache.commons.lang3.ArrayUtils;

class Card implements Comparable<Card> {
  final Pattern cardPattern = Pattern.compile("(10|[0-9AJQK])([♣♥♠♦])");
  
  private char suit;
  private String rank;
  
  public Card(String card) throws IllegalArgumentException {
    Matcher cardMatcher = cardPattern.matcher(card);
    
    if (!cardMatcher.find()) {
      throw new IllegalArgumentException("Card string must be the format of rank and then suit (e.g. K♣, 10♦, etc.)");
    }
    
    rank = cardMatcher.group(1);
    suit = cardMatcher.group(2).charAt(0);
  }
  
  public char getSuit() {
    return suit;
  }
  
  public String getRankString() {
    return rank;
  }
  
  private int getRankValue() {
    return switch(rank) {
        case "A" -> 1;
        case "J" -> 11;
        case "Q" -> 12;
        case "K" -> 13;
        default -> Integer.parseInt(rank);
    };
  }
  
  @Override
  public String toString() {
    return rank + String.valueOf(suit);
  }
  
  @Override
  public int compareTo(Card otherCard) {
    return Integer.compare(getRankValue(), otherCard.getRankValue());
  }
  
  public static Card[] convertStringsToCards(String[] cardStrings) {
    Card[] newCards = new Card[]{};
    for (String cardStr: cardStrings) {
      newCards = ArrayUtils.add(newCards, new Card(cardStr));
    }
    
    return newCards;
  }
  
  public static int compare(Card card1, Card card2) {
    return card1.compareTo(card2);
  }
}

class FlushData implements Comparable<FlushData> {
  private char suit;
  public Card[] cards;
  
  public FlushData(char s, Card[] crds) {
    suit = s;
    cards = crds;
  }
  
  public FlushData(char s) {
    this(s, new Card[]{});
  }
  
  public char getSuit() {
    return suit;
  }
  
  public void addCard(Card card) {
    cards = ArrayUtils.add(cards, card);
  }
  
  public boolean hasFlush() {
    return cards.length >= 5;
  }
  
  @Override
  public int compareTo(FlushData otherFlushData) {
    return Card.compare(
      getStrongestCard(),
      otherFlushData.getStrongestCard()
    );
  }
  
  private Card getStrongestCard() throws NoSuchElementException {
    return Arrays.stream(cards)
      .max(Card::compare).get();
  }
  
  public static int compare(FlushData fd1, FlushData fd2) {
    return fd1.compareTo(fd2);
  }
  
  public static Optional<FlushData> getFlushData(Card[] cards) {
    HashMap<String, FlushData> flushDataMap = new HashMap<String, FlushData>();
    flushDataMap.put("♣", new FlushData('♣'));
    flushDataMap.put("♥", new FlushData('♥'));
    flushDataMap.put("♠", new FlushData('♠'));
    flushDataMap.put("♦", new FlushData('♦'));
    
    for (Card crd: cards) {
      FlushData fd = flushDataMap.get(String.valueOf(crd.getSuit()));
      fd.addCard(crd);
    }
    
    return flushDataMap
      .values()
      .stream()
      .filter(fd -> fd.hasFlush())
      .max(FlushData::compare);
  }
}

public class Kata {
    public static Hand hand(String[] holeCards, String[] communityCards) {
        Card[] cards = Card.convertStringsToCards(ArrayUtils.addAll(holeCards, communityCards));
        
        Arrays.sort(cards);
        Optional<FlushData> flushData = FlushData.getFlushData(cards);

        return new Hand("nothing", new String[] { "A", "Q", "9", "6", "3" });
    }
}
