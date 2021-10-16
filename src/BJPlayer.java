import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: BJPlayer
 * @description: class of black jack player
 * @author: You Peng
 **/
public class BJPlayer extends CardPlayer {

//    private double deposit;
//    private double betting;
//    private List<HandCard> handCardList;
    
    private String lastAction;
    
    public final static Map<String, CardGameAction> actionMap = new HashMap<>();
    static {
        actionMap.put(HitAction.ACTION_NAME, new HitAction(true));
        actionMap.put(StandAction.ACTION_NAME, new StandAction());
        actionMap.put(SplitAction.ACTION_NAME, new SplitAction());
        actionMap.put(DoubleUpAction.ACTION_NAME, new DoubleUpAction());
    }

    // TODO: only to pass compile
    public BJPlayer(int id) {
        // TODO:
        super(id);
        setBetting(0);
        getHandCardList().add(new HandCard(21));
    }

    public BJPlayer(int id, double deposit) {
        // TODO:
        this(id);
        setDeposit(deposit);
    }

    public String getLastAction() {
        return lastAction;
    }

    public double updateBet() {
        double bet = 0;
        for (HandCard handCard: getHandCardList()) {
            bet += handCard.getBetAmount();
        }
        double diff = bet - getBetting();
        setBetting(bet);
        return diff;
    }

    public double updateBet(double diff) {
        setBetting(getBetting() + diff);
        return diff;
    }

    public void updateDeposit(double diff) {
        setDeposit(getDeposit() - diff);
    }

    public List<Card> takeAction(BJDealer dealer, String actionName, int[] actOnIds) {
        lastAction = actionName;
        List<HandCard> actOn = new ArrayList<>();
        for (int i : actOnIds) {
            actOn.add(getHandCardList().get(i));
        }

        if (actionName.equals(SplitAction.ACTION_NAME)) {
            getHandCardList().removeAll(actOn);
            List<Card> result = actionMap.get(actionName).act(dealer, actOn);
            getHandCardList().addAll(actOn);
            return result;
        }
        else if (actionName.equals(DoubleUpAction.ACTION_NAME)) {
            List<Card> result = actionMap.get(actionName).act(dealer, actOn);
            updateDeposit(updateBet());
            return result;
        }
        return actionMap.get(actionName).act(dealer, actOn);
    }

}
