import java.util.ArrayList;
import java.util.List;

/**
 * @className: HitAction
 * @description: class of hit action in card game
 * @author: Yan Tong
 **/
public class HitAction implements CardGameAction {
    public static final String ACTION_NAME = "hit";

    private final boolean check;

    public HitAction () {
        this.check = false;
    }

    public HitAction (boolean check) {
        this.check = check;
    }
    /**
     * @Description: hit action of card game
     * @Param: Dealer dealer
     * @Param: List<HandCard> handCards: list of hand cards that going to be dealt
     * @return: List<Card>: list of cards that have been operated
     * @Author: Yan Tong
     */
    @Override
    public List<Card> act(Dealer dealer, List<HandCard> handCards) {
        List<Card> addedCard = new ArrayList<>();
        for (HandCard hc : handCards) {
            Card c = dealer.deal();
            c = hc.addCard(c, check);
            addedCard.add(c);
        }
        return addedCard;
    }
}
