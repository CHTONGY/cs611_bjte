import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @className: Dealer
 * @description: class of dealer
 * @author: Yan Tong
 **/
public class Dealer {
    private List<Deck> deckList;
    private HandCard handCard;

    private CardIterator cardIterator;

    // init Dealer with DeckNum deck
    public Dealer(int deckNum) {
        List<Deck> deckList = new ArrayList<>();
        for (int i = 0; i < deckNum; i++) {
            deckList.add(new Deck());
        }
        this.deckList = deckList;
        this.handCard = null;

        this.cardIterator = new CardIterator();
    }

    // initialize with 1 deck if no argument
    public Dealer() {
        this(1);
    }

    public void shuffle() {
        for (Deck deck : deckList) {
            deck.shuffle();
        }
    }

    /**
     * @Description: turn private handcard into public, return this card
     * @Description: if there are multiple private cards, reveal any one of them
     * @return: card that was revealed
     * @Author: Yan Tong
     */
    public Card reveal() {
        List<Card> handCardList = handCard.getHandCardList();
        for (Card card : handCardList) {
            if (!card.isPublic()) {
                card.setPublic(true);
                return card;
            }
        }
        return null;
    }

    /**
     * @Description: reveal all private cards in hand card
     * @return: the list of all revealed cards
     * @Author: Yan Tong
     */
    public List<Card> revealCardList() {
        List<Card> revealCards = new ArrayList<>();
        List<Card> handCardList = handCard.getHandCardList();
        for (Card card : handCardList) {
            if (!card.isPublic()) {
                card.setPublic(true);
                revealCards.add(card);
            }
        }
        return revealCards;
    }

    /**
     * @Description: get a card into hand card
     * @return: card that received
     * @Author: Yan Tong
     */
    public Card hit() {
        HitAction hitAction = new HitAction();
        List<HandCard> handCardList = new ArrayList<>();
        handCardList.add(this.handCard);
        List<Card> addedCard = hitAction.act(this, handCardList);
        return addedCard.get(0);
    }

    /**
     * @Description: to give out a card from a deck
     * @return: the card that gives out
     * @Author: Yan Tong
     */
    public Card deal() {
        // if deck list has cards remain, then use them directly
        if (cardIterator.hasNext()) {
            return cardIterator.next();
        }
        // if all decks are empty, then shuffle and reset iterator
        shuffle();
        cardIterator.reset();
        if (cardIterator.hasNext()) {
            return cardIterator.next();
        }
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

    /**
     * @Description: iterate cards in deck list, use in deal()
     * @Author: Yan Tong
     */
    private class CardIterator implements Iterator<Card> {
        private int deckIndex;

        public CardIterator() {
            this.deckIndex = 0;
        }

        @Override
        public boolean hasNext() {
            Deck curDeck = deckList.get(this.deckIndex);
            // curDeck is not empty, then we have next card
            if (!curDeck.isEmpty()) {
                return true;
            }
            // if we have another deck to use, then we still have next
            if (this.deckIndex < deckList.size() - 1) {
                return true;
            }

            return false;
        }

        @Override
        public Card next() {
            Deck curDeck = deckList.get(this.deckIndex);
            if (!curDeck.isEmpty()) {
                return curDeck.popCard();
            }
            this.deckIndex += 1;
            curDeck = deckList.get(this.deckIndex);
            return curDeck.popCard();
        }

        public void reset() {
            this.deckIndex = 0;
        }
    }
}
