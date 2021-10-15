import java.util.List;

/**
 * @className: TEDealer
 * @description: class of trianta ena dealer
 * @author: Yan Tong
 **/
public class TEDealer extends Dealer {
    private double deposit;

    /**
     * @Description: get a card into hand card
     * @return: card that received
     * @Author: Yan Tong
     */
    @Override
    public Card hit() {
        return hit(true);
    }

    public Card hit(boolean isPublicCard) {
        Card hitCard = super.hit();
        hitCard.setPublic(isPublicCard);
        dealFaceValue(hitCard);
        return hitCard;
    }

    public boolean isBankrupt() {
        return this.deposit <= 0;
    }

    public TEDealer() {
        super(2);
    }

    public void payOut(double money) {
        this.deposit -= money;
    }

    public void accumulate(double money) {
        this.deposit += money;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    private void dealFaceValue(Card card) {
        String cardSymbol = card.getSymbol();
        if (cardSymbol.equalsIgnoreCase("A")) {
            boolean hasA = false;
            List<Card> cards = this.getHandCard().getHandCardList();
            for (Card c : cards) {
                if (c.getSymbol().equalsIgnoreCase("A")) {
                    hasA = true;
                    break;
                }
            }
            if (hasA) {
                card.setFaceValue(11);
            } else if (this.getHandCard().getTotalPoints() + 11 <= 31) {
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
        this.getHandCard().calTotalPoints();
        this.getHandCard().updateBusted();
    }
}
