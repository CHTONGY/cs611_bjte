import java.util.ArrayList;
import java.util.List;

/**
 * @className: StandAction
 * @description: class of stand action in card game
 * @author: Fangxu Zhou, You Peng, Yan Tong
 **/
public class StandAction implements CardGameAction {
    public static final String ACTION_NAME = "stand";
    /**
     * @Description: stand action of card game
     * @Param: Dealer dealer
     * @Param: List<HandCard> handCards: list of hand cards that going to be dealt
     * @return: empty list because no
     * @Author: Fangxu Zhou, You Peng, Yan Tong
     */
    @Override
    public List<Card> act(Dealer dealer, List<HandCard> handCards) {
        return new ArrayList<>();
    }
}
