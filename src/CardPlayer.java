import java.util.List;

/**
 * @className: CardPlayer
 * @description: class of card player
 * @author: Fangxu Zhou, You Peng, Yan Tong
 **/
public class CardPlayer extends Player {
    private double deposit;
    private double betting;
    private List<HandCard> handCardList;

    public CardPlayer(int id) {
        super(id);
    }

    public double getDeposit() {
        return deposit;
    }

    public boolean isBankrupt() {
        return this.deposit < 0;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getBetting() {
        return betting;
    }

    public void setBetting(double betting) {
        this.betting = betting;
    }

    public List<HandCard> getHandCardList() {
        return handCardList;
    }

    public void setHandCardList(List<HandCard> handCardList) {
        this.handCardList = handCardList;
    }
}
