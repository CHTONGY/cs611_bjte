import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: TEPlayer
 * @description: class of trianta ena player
 * @author: Yan Tong
 **/
public class TEPlayer extends CardPlayer {
    // TODO:
    private Map<String, CardGameAction> actionMap;
    private boolean isCashOut;

    public TEPlayer(int id) {
        super(id);
        this.actionMap = new HashMap<>();
        this.actionMap.put("hit", new HitAction());
        this.actionMap.put("stand", new StandAction());
    }

    public Card hit(TEDealer teDealer, boolean isPublicCard) {
        // te player only has one handcard, so we can just pass the handcard list to act method
        Card hitCard = this.actionMap.get("hit").act(teDealer, this.getHandCardList()).get(0);
        hitCard.setPublic(isPublicCard);
        dealFaceValue(hitCard);
        return hitCard;
    }

    public Card hit(TEDealer teDealer) {
        return hit(teDealer, true);
    }

    public void stand(TEDealer teDealer) {
        this.actionMap.get("stand").act(teDealer, this.getHandCardList());
    }

    public void cashOut() {
        this.isCashOut = true;
    }

    private void dealFaceValue(Card card) {
        String cardSymbol = card.getSymbol();
        if (cardSymbol.equalsIgnoreCase("A")) {
            boolean hasA = false;
            List<Card> cards = this.getHandCardList().get(0).getHandCardList();
            for (Card c : cards) {
                if (c.getSymbol().equalsIgnoreCase("A")) {
                    hasA = true;
                    break;
                }
            }
            if (hasA) {
                card.setFaceValue(11);
            } else if (this.getHandCardList().get(0).getTotalPoints() + 11 <= 31) {
                card.setFaceValue(11);
            } else {
                card.setFaceValue(1);
            }
        } else if (cardSymbol.equalsIgnoreCase("J") ||
                cardSymbol.equalsIgnoreCase("Q") ||
                cardSymbol.equalsIgnoreCase("K")) {
            card.setFaceValue(10);
        } else {
            card.setFaceValue(Integer.valueOf(cardSymbol));
        }
        this.getHandCardList().get(0).calTotalPoints();
        this.getHandCardList().get(0).updateBusted();
    }
}
