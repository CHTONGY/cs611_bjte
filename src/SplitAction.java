import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: SplitAction
 * @description: class of split action in card game
 * @author: You Peng
 **/

public class SplitAction implements CardGameAction {

    public static final String ACTION_NAME = "split";

    /**
     * The split action of card game. Splitting is allowed when a HandCard has two cards of the same rank.
     *
     * @param dealer The dealer.
     * @param handCards: list of hand cards that going to be dealt.
     * @return list of cards that have been operated.
     */
    @Override
    public List<Card> act(Dealer dealer, List<HandCard> handCards) {
        // TODO:
        for (HandCard handCard: handCards) {
            if (!canSplit(handCard)) {
                try {
                    throw new OperationNotSupportedException("There's at least one handcard that is not allowed to split!");
                } catch (OperationNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }

        int oldSize = handCards.size();
        for (int i = 0; i < oldSize; i++) {
            HandCard oldHandCard = handCards.get(i);
            if (canSplit(oldHandCard)) {
                HandCard newHandCard = new HandCard(oldHandCard.getBetAmount(), oldHandCard.getMaxPoint());
                // only two same cards, remove the last one
                newHandCard.addCard(oldHandCard.removeCard(1), true);
                handCards.add(newHandCard);
            }
        }
        HitAction hitAction = new HitAction();
        return new ArrayList<>(hitAction.act(dealer, handCards));
    }

    /**
     * Check whether the specific HandCard is allowed to split.
     *
     * @param handCard to be split.
     * @return true if handcard is allowed to be split, else return false.
     */
    public boolean canSplit(HandCard handCard) {
        List<Card> cards = handCard.getHandCardList();
        if (cards.size() != 2) {
            return false;
        }

        return cards.get(0).equals(cards.get(1));

    }

}
