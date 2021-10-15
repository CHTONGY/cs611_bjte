import java.util.ArrayList;
import java.util.List;

/**
 * @className: DoubleUpAction
 * @description: class of double up action in card game
 * @author: You Peng
 **/
public class DoubleUpAction implements CardGameAction {

    public static final String ACTION_NAME = "doubleUp";

    /**
     * @param dealer
     * @param handCards
     * @Description: action of card game
     * @Param: Dealer dealer
     * @Param: List<HandCard> handCards: list of hand cards that going to be dealt
     * @return: List<HandCard>
     * @Author: You Peng
     */
    @Override
    public List<Card> act(Dealer dealer, List<HandCard> handCards) {
        // TODO:
        for (HandCard handCard: handCards) {
            handCard.setBetAmount(handCard.getBetAmount() * 2);
        }
        return new ArrayList<>();
    }
}
