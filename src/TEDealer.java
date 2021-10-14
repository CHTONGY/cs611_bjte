/**
 * @className: TEDealer
 * @description: class of trianta ena dealer
 * @author: Yan Tong
 **/
public class TEDealer extends Dealer {
    private double deposit;

    public boolean isBankrupt() {
        return this.deposit < 0;
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
}
