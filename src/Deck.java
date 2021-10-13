import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @className: Deck
 * @description: class of deck
 * @author: Yan Tong
 **/
public class Deck {
    private List<Card> cards;
    private long randomSeed;
    private int rightBound; // choose index from [0, rightBound], both inclusive

    public Deck() {
        this.cards = createCard();
        this.randomSeed = System.nanoTime();
        this.rightBound = this.cards.size() - 1;
    }

    public Card popCard() {
        if (isEmpty()) {
            return null;
        }
        // use random number to select pop card index
        Random random = new Random(randomSeed);
        int index = random.nextInt(this.rightBound + 1);
        Card chosenCard = this.cards.get(index);
        // after pop, we should swap chosen card with right bound card, and move right bound left
        swap(this.cards, index, rightBound--);
        return chosenCard;
    }

    public void shuffle() {
        // reset random seed and right bound
        this.randomSeed = System.nanoTime();
        this.rightBound = this.cards.size() - 1;
    }

    // to see whether there is valid card to pop
    public boolean isEmpty() {
        return this.rightBound < 0;
    }

    private void swap(List<Card> cards, int i, int j) {
        Card c = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, c);
    }

    private List<Card> createCard() {
        List<Card> res = new ArrayList<>();
        // 2-10, each num has 4 cards
        for (int i = 2; i <= 10; i++) {
            for (int j = 0; j < 4; j++) {
                res.add(new Card(String.valueOf(i)));
            }
        }
        // A, J, Q, K, each has 4 cards
        String[] symbols = new String[]{"A", "J", "Q", "K"};
        for (int i = 0; i < symbols.length; i++) {
            for (int j = 0; j < 4; j++) {
                res.add(new Card(symbols[i]));
            }
        }

        return res;
    }
}
