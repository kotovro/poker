import com.sun.source.tree.BreakTree;

public class GameHelper {
    public static void sortHand(Card[] hand){
        Card temp;
        for (int i = 0; i <  hand.length - 1; i++) {
            for (int j = i + 1; j < hand.length; j++) {
                if (hand[i].compareTo(hand[j]) < 0) {
                    temp = hand[i];
                    hand[i] = hand[j];
                    hand[j] = temp;
                }
            }
        }
    }


    public static Combinations findBestCombo(Card[] hand) {
        sortHand(hand);
        // all isXXX() are called in order of descension of priority
        //so all isXXX() methods assuming that hand can not contain
        //combinations with higher priority
        boolean isStraight = isStraight(hand);
        boolean isFlush = isFlush(hand);
        if (isFlush && isStraight) {
            return Combinations.STRAIGHT_FLUSH;
        }
        if (isFlush) {
            return Combinations.FLUSH;
        }
        if (isStraight) {
            return Combinations.STRAIGHT;
        }
        if (isFourOfAKind(hand)) {
            return Combinations.FOUR_OF_A_KIND;
        }
        if (isFullHouse(hand)) {
            return Combinations.FULL_HOUSE;
        }
        if (isThreeOfAKind(hand)) {
            return Combinations.THREE_OF_A_KIND;
        }
        if (isTwoPair(hand)) {
            return Combinations.TWO_PAIR;
        }
        if (isPair(hand)) {
            return Combinations.PAIR;
        }
        return Combinations.NONE;
    }
    private static void swap(Card[] hand, int index1, int index2) {
        Card temp = hand[index1];
        hand[index1] = hand[index2];
        hand[index2] = temp;
    }
    // all isXXX() are called in order of descension of priority
    //so all isXXX() methods assuming that hand can not contain
    //combinations with higher priority

    private static boolean isTwoPair(Card[] hand) {
        if (hand[0].getName().equals(hand[1].getName())) {
            if(hand[2].getName().equals(hand[3].getName())) {
                return true;
            }
            if(hand[3].getName().equals(hand[4].getName())) {
                swap(hand,2, 4);
                return true;
            }
        } else if (hand[1].getName().equals(hand[2].getName())
            && hand[3].getName().equals(hand[4].getName())) {
            swap(hand, 0, 2);
            swap(hand, 2, 4);
            return true;
        }
        return false;
    }

    private static boolean isPair(Card[] hand) {
        for (int i = 0; i < hand.length - 1; i++) {
            if (hand[i].getName().equals(hand[i+1].getName())) {
                if (i != 0) {
                    if (i != 3) {
                        swap(hand, 0, i);
                        swap(hand, 1, i+1);
                    } else {
                        swap(hand, 0, 2);
                        swap(hand, 1, 3);
                        swap(hand, 0, 4);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private static boolean isThreeOfAKind(Card[] hand) {
        if (hand[0].getName().equals(hand[1].getName())) {
            if (hand[1].getName().equals(hand[2].getName())) {
                return true;
            }
        }
        if (hand[1].getName().equals(hand[2].getName())) {
            if (hand[2].getName().equals(hand[3].getName())) {
                swap(hand, 0, 3);
                return true;
            }
        }
        if (hand[2].getName().equals(hand[3].getName())) {
            if (hand[3].getName().equals(hand[4].getName())) {
                swap(hand, 0, 3);
                swap(hand, 1, 4);
                return true;
            }
        }
        return false;
    }

    private static boolean isFullHouse(Card[] hand) {
        if (hand[0].getName().equals(hand[1].getName())
                && hand[3].getName().equals(hand[4].getName())) {
            if (hand[2].getName().equals(hand[0].getName())) {
                    return true;
            }
            if (hand[2].getName().equals(hand[4].getName())) {
                swap(hand, 0, 3);
                swap(hand, 1, 4);
                return true;
            }
        }
        return false;
    }

    private static boolean isFourOfAKind(Card[] hand) {
        Card.CardNames curName = hand[1].getName();
        int startIndex = hand[0].getName().equals(curName) ? 0 : 1;
        for (int i = startIndex; i < hand.length - 2 + startIndex; i++) {
            if (!hand[i + 1].getName().equals(curName)) {
                return false;
            }
        }
        if (startIndex == 1) {
            swap(hand, 0, 4);
        }
        return true;
    }

    private static boolean isStraight(Card[] hand) {
        if (hand[0].getName().equals(Card.CardNames.ACE) && hand[1].getName().equals(Card.CardNames.FIVE)) {
            for (int i = 1; i < hand.length - 1; i++) {
                if (hand[i].getName().getCardWeight() != hand[i + 1].getName().getCardWeight() + 1) {
                    return false;
                }
            }
            swap(hand, 0, 4);
            return true;
        }
        for (int i = 0; i < hand.length - 1; i++) {
            if (hand[i].getName().getCardWeight() != hand[i + 1].getName().getCardWeight() + 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFlush(Card[] hand) {
        for (int i = 0; i < hand.length - 1; i++) {
            if (!hand[i].getSuit().equals(hand[i+1].getSuit())) {
                return false;
            }
        }
        return true;
    }



}
