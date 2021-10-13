import java.util.List;

/**
 * @className: Dealer
 * @description: class of dealer
 * @author: Yan Tong
 **/
public class Dealer {
    private List<Deck> deckList;
    private HandCard handCard;

    public Dealer() {
        // TODO: init deck list
    }

    public void shuffle() {
        // TODO:
    }

    public Card reveal() {
        // TODO:
        return null;
    }

    public Card hit() {
        // TODO:
        return null;
    }

    public Card deal() {
        // TODO:
        return null;
    }

    public List<Deck> getDeckList() {
        return deckList;
    }

    public void setDeckList(List<Deck> deckList) {
        this.deckList = deckList;
    }

    public HandCard getHandCard() {
        return handCard;
    }

    public void setHandCard(HandCard handCard) {
        this.handCard = handCard;
    }
}
