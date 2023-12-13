package combinations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.cs.vsu.oop.poker.base.Card;
import ru.cs.vsu.oop.poker.base.SearchResult;
import ru.cs.vsu.oop.poker.base.combinations.ClassicCombinationsSet;
import ru.cs.vsu.oop.poker.base.combinations.SearchResultFactory;

import java.util.LinkedList;

public class CombinationsTests {
    @Test
    public void straightFlushFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.THREE));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.NINE));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FOUR));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Straight Flush", searchResult.getName());
    }
    @Test
    public void straightFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.THREE));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.NINE));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FOUR));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Straight Flush", searchResult.getName());
    }
    @Test
    public void pairFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.HEARTS, Card.CardNames.THREE));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.NINE));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.DIAMONDS, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Pair", searchResult.getName());
    }
    @Test
    public void threeOfAKindFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.HEARTS, Card.CardNames.THREE));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.DIAMONDS, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Three of a Kind", searchResult.getName());
    }
    @Test
    public void fourOfAKindFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.HEARTS, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.DIAMONDS, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Four of a Kind", searchResult.getName());
    }
    @Test
    public void fullHouseFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.HEARTS, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.DIAMONDS, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Full House", searchResult.getName());
    }
    @Test
    public void bestCardFound() {
        LinkedList<Card> buffer = new LinkedList<>();
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.ACE));
        buffer.add(new Card(Card.Suits.HEARTS, Card.CardNames.FIVE));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.FOUR));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
        buffer.add(new Card(Card.Suits.DIAMONDS, Card.CardNames.TWO));
        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.THREE));
        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.NINE));


        SearchResult searchResult = SearchResultFactory.getSearchResult(buffer, ClassicCombinationsSet.class);
        Assertions.assertEquals("Straight", searchResult.getName());
    }
//    boolean isDebug = false;
//        if(isDebug) {
//        LinkedList<Card> buffer = new LinkedList<>();
//        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.SIX));
//        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.THREE));
//        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.NINE));
//        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FOUR));
//        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.TWO));
//        buffer.add(new Card(Card.Suits.SPADES, Card.CardNames.JACK));
//        buffer.add(new Card(Card.Suits.CROSSES, Card.CardNames.FIVE));
//
//
//        SearchResult searchResult = AbstractCombination.findCombination(buffer);
//        System.out.println(searchResult.getName());
//        for (Card c: searchResult.getBestCombo()) {
//            System.out.println(c.getName().name() + " " + c.getSuit().name()) ;
//        }
//        return;
//    }
}