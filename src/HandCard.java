import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @className: HandCard
 * @description: class of player's hand cards
 * @author: You Peng
 **/


public class HandCard {
    private List<Card> handCardList;
    private boolean isBusted;
    private int totalPoints;
    private int winStatus;  // other: none, -1: lose, 0: tie, 1: win
    private final int MAX_POINT; // For BlackJack it's 21, for Trianta Ena it's 31.

    /**
     *
     * @param max_point
     */
    public HandCard(int max_point) {
        if (max_point < 1)  throw new IllegalArgumentException("Error: Max point is invalid!");

        this.MAX_POINT      = max_point;
        this.handCardList   = new ArrayList<>();
        this.totalPoints    = 0;
        this.isBusted       = false;
        this.winStatus      = Integer.MIN_VALUE;
    }

    public void addCard(Card card) {
        // TODO:
        if (card != null) {
            handCardList.add(card);
            totalPoints += card.getFaceValue();
        }
    }

    public Card randomRemoveCard() {
        // TODO: rename the function to randomRemoveCard
        // create instance of Random class
        Random rand = new Random();
        // Generate random integers in range 0 to list size
        int randId = rand.nextInt(handCardList.size());
        return handCardList.remove(randId);
    }

    /**
     *
     * @param   card
     * @return  The card that is removed. "null" if the card doesn't exist.
     */
    public Card removeCard(Card card) {
        // TODO:
        return handCardList.remove(card) ? card : null;
    }

    public List<Card> getHandCardList() {
        return handCardList;
    }

    public void setHandCardList(List<Card> handCardList) {
        this.handCardList = handCardList;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isBusted() {
        return isBusted;
    }

    public void setBusted(boolean busted) {
        isBusted = busted;
    }

    public int getWinStatus() {
        return winStatus;
    }

    public void setWinStatus(int winStatus) {
        this.winStatus = winStatus;
    }
}
