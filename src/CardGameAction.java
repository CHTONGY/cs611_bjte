import java.util.List;

/**
 * @interfaceName: CardGameAction
 * @description: interface of card game actions
 * @author: Fangxu Zhou, You Peng, Yan Tong
 **/
public interface CardGameAction {
    /**
     * @Description: action of card game
     * @Param: Dealer dealer
     * @Param: List<HandCard> handCards: list of hand cards that going to be dealt
     * @return: List<Card>: list of cards that have been operated
     * @Author: Fangxu Zhou, You Peng, Yan Tong
     */
    List<Card> act(Dealer dealer, List<HandCard> handCards);
}
