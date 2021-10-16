import java.util.*;

/**
 * @className: HandCard
 * @description: class of player's hand cards
 * @author: You Peng
 **/

public class HandCard {

    private List<Card> handCardList;
    private boolean isBusted;
    private double betAmount;
    private int totalPoints;
    private int winStatus;  // other: none, -1: lose, 0: tie, 1: win
    private final int maxPoint; // For BlackJack it's 21, for Trianta Ena it's 31.

    /**
     * Construct a HandCard with a specific max point such as 21 and 31.
     *
     * @param betAmount The amount of money to bet on this hand.
     * @param maxPoint The specific max point. Exceeding it will be considered busted.
     */
    public HandCard(double betAmount, int maxPoint) {
        this(maxPoint);

        if (betAmount < 0)  throw new IllegalArgumentException("Error: betAmount is invalid!");
        this.betAmount      = betAmount;
    }

    public HandCard(int maxPoint) {
        if (maxPoint < 1)  throw new IllegalArgumentException("Error: maxPoint is invalid!");

        this.maxPoint      = maxPoint;
        this.handCardList   = new ArrayList<>();
        this.totalPoints    = 0;
        this.isBusted       = false;
        this.winStatus      = Integer.MIN_VALUE;
    }

    /**
     * Add a card into the list.
     *
     * @param card The card to be added into the list.
     */
    public void addCard(Card card) {
        // TODO:
        if (card != null) {
            handCardList.add(card);
            totalPoints += card.getFaceValue();
            updateBusted();
        }
    }

    public Card addCard(Card card, boolean check) {
        if (check) {
            card = checkCard(this, card);
        }
        addCard(card);
        return card;
    }

    /**
     * Remove card by index.
     *
     * @param index The index of the card to be removed.
     * @return The card that is removed.
     */
    public Card removeCard(int index) {
        // TODO:
        Card card = handCardList.get(index);
        handCardList.remove(index);
        totalPoints -= card.getFaceValue();
        updateBusted();
        return card;
    }

    /**
     * Randomly remove a card.
     *
     * @return The card that is removed.
     */
    public Card randomRemoveCard() {
        // TODO: rename the function to randomRemoveCard
        // create instance of Random class
        Random rand = new Random(System.nanoTime());
        // Generate random integers in range 0 to list size
        int randId = rand.nextInt(handCardList.size());
        return removeCard(randId);
    }

    /**
     * Remove a specific card in the handCardList.
     *
     * @param card The card to be removed.
     * @return The card that is removed. "null" if the card doesn't exist.
     * @throws NoSuchElementException When the card does not exist.
     *
     */

    public Card removeCard(Card card) {
        // TODO:
        int index = handCardList.indexOf(card);
        if (index < 0) {
            throw new NoSuchElementException("No such card in this hand.");
        }
        return removeCard(index);
    }

    /**
     * Automatically choose a faceValue of Card that is less possible to be busted.
     *
     * @param handCard The hand to add the card into.
     * @param card The card to be added into a hand.
     * @return The Card after being checked.
     * @author You Peng
     */
    public Card checkCard(HandCard handCard, Card card) {
        if (card.getFaceValue() > 10) {
            card.setFaceValue(10);
        }
        else if (card.getSymbol().equals("A")) {
            handCard.calTotalPoints();
            if (handCard.getTotalPoints() + card.getFaceValue() <= handCard.getMaxPoint()) {
                card.setFaceValue(11);
            }
            else {
                card.setFaceValue(1);
            }
        }
        return card;
    }

    public List<Card> getHandCardList() {
        return handCardList;
    }

    public void setHandCardList(List<Card> handCardList) {
        this.handCardList = handCardList;
        calTotalPoints();
        updateBusted();
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void calTotalPoints() {
        int points = 0;
        for (Card card: handCardList) {
            points += card.getFaceValue();
        }
        this.totalPoints = points;
    }

    public boolean isBusted() {
        return isBusted;
    }

    public void setBusted(boolean busted) {
        isBusted = busted;
    }

    public void updateBusted() {
        isBusted = (totalPoints > maxPoint);
    }

    public int getWinStatus() {
        return winStatus;
    }

    public void setWinStatus(int winStatus) {
        this.winStatus = winStatus;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        handCardList.sort(new HandCardComparator());
        sb.append("\nCurrent cards: ");
        for (Card card: handCardList) {
            sb.append(card.getFaceValue());
            sb.append(", ");
        }
        sb.append(String.format("\nBet Amount: $%.2f\nBusted: %s", betAmount, isBusted()));
        return sb.toString();
    }

    private static class HandCardComparator implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            return o1.getFaceValue() - o2.getFaceValue();
        }
    }
}
