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
import java.util.ArrayList;
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
  
  public int getRankValue() {
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

class StraightDataTree implements Comparable<StraightDataTree> {
  class StraightDataTreeNode {
    private int rank; // Indicates the rank of cards
    private Card[] cards; // Can hold multiple cards if rank is equal
    private StraightDataTreeNode nextNode;
    
    public StraightDataTreeNode(Card card) {
      rank = card.getRankValue();
      ArrayUtils.add(cards, card);
    }
    
    public void connectNode(StraightDataTreeNode otherNode) {
      nextNode = otherNode;
    }
    
    public void addSibling(Card card) {
      ArrayUtils.add(cards, card);
    }
    
    public Optional<StraightDataTreeNode> getNextNode() {
      if (isLast()) {
        return Optional.empty();
      }
      
      return Optional.of(nextNode);
    }
    
    public int getRankValue() {
      return rank;
    }
    
    public Card[] getCards() {
      return cards.clone();
    }
    
    public Optional<Card> findCardWithSuit(char suit) {
      for (Card crd: cards) {
        if (suit == crd.getSuit()) {
          return Optional.of(crd);
        }
      }
      
      return Optional.empty();
    }
    
    public boolean isLast() {
      return nextNode == null;
    }
    
    public Optional<Card> getMatchingSuitCard(char suit) {
      for (Card card: cards) {
        if (card.getSuit() == suit) {
          return Optional.of(card);
        }
      }
      return Optional.empty();
    }
  }
  
  private final int MAX_SIZE = 5;
  
  private StraightDataTreeNode root;
  private int size;
  
  public StraightDataTree(Card card) {
    root = new StraightDataTreeNode(card);
    size = 1;
  }
  
  public int getRootRank() {
    return root.getRankValue();
  }
  
  public void addCardToTree(Card card) {
    StraightDataTreeNode lastNode = getLastNode();
    
    if (lastNode.getRankValue() == card.getRankValue()) {
      lastNode.addSibling(card);
    } else {
      lastNode.connectNode(new StraightDataTreeNode(card));
      
      if (size == MAX_SIZE) {
        shiftRoot();
      } else {
        size++;
      }
    }
  }
  
  public boolean hasStraight() {
    return size == MAX_SIZE;
  }
  
  public Optional<Card[]> getFlushStraight() {
    if (!hasStraight()) {
      return Optional.empty();
    }
    
    Optional<Card[]> strongestPotentialFlushStraight = traverseForFlushStraight(getRootNode());
    
    return strongestPotentialFlushStraight;
  }
  
  @Override
  public int compareTo(StraightDataTree otherTree) {
    return 0;
  }
  
  private StraightDataTreeNode getRootNode() {
    return root;
  }
  
  private boolean shiftRoot() {
    Optional<StraightDataTreeNode> nextNode = root.getNextNode();
    if (nextNode.isPresent()) {
      root = nextNode.get();
      return true;
    }
    return false;
  }
  
  private StraightDataTreeNode getLastNode() {
    StraightDataTreeNode next = root;
    while (!next.isLast()) {
      next = next.getNextNode().get();
    }
    
    return next;
  }
  
  // Initial call
  private Optional<Card[]> traverseForFlushStraight(StraightDataTreeNode node) {
    Card[] nodeCards = node.getCards();
    ArrayList<Card> potentialFlush;
    
    if (node.isLast()) {
      return Optional.empty();
    }
    
    for (Card card: nodeCards) {
      potentialFlush = new ArrayList<Card>();
      potentialFlush.add(card);
      
      Optional<Card[]> nextCards = traverseForFlushStraight(node.getNextNode().get(), card.getSuit());
      
      if (nextCards.isPresent()) {
        for (Card otherCard: nextCards.get()) {
          potentialFlush.add(otherCard);
        }

        if (potentialFlush.size() == MAX_SIZE) {
          return Optional.of((Card[]) potentialFlush.toArray());
        }
      }
    }
    
    return Optional.empty();
  }
  
  // Recursive call
  private Optional<Card[]> traverseForFlushStraight(StraightDataTreeNode node, char suit) {
    Card[] nodeCards = node.getCards();
    ArrayList<Card> potentialFlush;
    if (node.isLast()) {
      Optional<Card> matchingSuitCard = node.getMatchingSuitCard(suit);
      
      if (!matchingSuitCard.isPresent()) {
        return Optional.empty();
      }
      
      potentialFlush = new ArrayList<Card>();
      potentialFlush.add(matchingSuitCard.get());
      return Optional.of((Card[]) potentialFlush.toArray());
    }
    
    for (Card card: nodeCards) {
      if (card.getSuit() == suit) {
        potentialFlush = new ArrayList<Card>();
        potentialFlush.add(card);
        
        Optional<Card[]> nextCards = traverseForFlushStraight(node.getNextNode().get(), card.getSuit());
        
        if (nextCards.isPresent()) {
          for (Card otherCard: nextCards.get()) {
            potentialFlush.add(otherCard);
          }
          
          return Optional.of((Card[]) potentialFlush.toArray());
        }
      }
    }
    
    return Optional.empty();
  }
  
  public static Optional<StraightDataTree> getStraightDataTree(Card[] card) {
    return Optional.of(new StraightDataTree(card[0]));
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