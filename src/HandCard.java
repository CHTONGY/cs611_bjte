import java.util.List;

/**
 * @className: HandCard
 * @description: class of player's hand cards
 * @author: You Peng
 **/
public class HandCard {
    private List<Card> handCardList;
    private int totalPoints;
    private boolean isBusted;
    private int winStatus;  // -1: lose, 0: tie, 1: win

    public HandCard() {
    }

    public void addCard(Card card) {
        // TODO:
    }

    public Card removeCard() {
        // TODO:
        return null;
    }

    public Card removeCard(Card card) {
        // TODO:
        return null;
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
