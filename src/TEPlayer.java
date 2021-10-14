import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: TEPlayer
 * @description: class of trianta ena player
 * @author: Yan Tong
 **/
public class TEPlayer extends CardPlayer {
    // TODO:
    private Map<String, CardGameAction> actionMap;
    private boolean isCashOut;

    public TEPlayer(int id) {
        super(id);
        this.actionMap = new HashMap<>();
        this.actionMap.put("hit", new HitAction());
        this.actionMap.put("stand", new StandAction());
    }

    public List<Card> hit(TEDealer teDealer) {
        // te player only has one handcard, so we can just pass the handcard list to act method
        return this.actionMap.get("hit").act(teDealer, this.getHandCardList());
    }

    public void stand(TEDealer teDealer) {
        this.actionMap.get("stand").act(teDealer, this.getHandCardList());
    }

    public void cashOut() {
        this.isCashOut = true;
    }
}
