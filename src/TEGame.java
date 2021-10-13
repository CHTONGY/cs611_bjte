/**
 * @className: TEGame
 * @description: class of trianta ena
 * @author: Yan Tong, Fangxu Zhou
 **/
public class TEGame implements Game, CardGame, TurnBasedGame<TEPlayer>, Winnable {
    /**
     * @Description: constructor
     * @Author: Yan Tong
     */
    public TEGame(int playerNum) {
        // TODO:
    }

    /**
     * @Description: whether it's a card game
     * @return: true if it is a card game
     * @Author: Fangxu Zhou, Yan Tong
     */
    @Override
    public boolean isCardGame() {
        // TODO:
        return false;
    }

    /**
     * @Description: start game
     * @Author: Yan Tong
     */
    @Override
    public void play() {
        // TODO:
    }

    /**
     * @Description: welcome sentences of game
     * @Author: Yan Tong
     */
    @Override
    public void welcome() {
        // TODO:
    }

    /**
     * @Description: init process of game
     * @Author: Yan Tong
     */
    @Override
    public void init() {
        // TODO:
    }

    /**
     * @param curPlayer
     * @Description: change to next player
     * @Param: T curPlayer: current player
     * @return: T: next player
     * @Author: Yan Tong
     */
    @Override
    public TEPlayer nextTurn(TEPlayer curPlayer) {
        // TODO:
        return null;
    }

    /**
     * @Description: whether the game has next new round
     * @return: boolean: true if there is next new round
     * @Author: Yan Tong
     */
    @Override
    public boolean hasNextRound() {
        // TODO:
        return false;
    }

    /**
     * @Description: play next round
     * @Author: Yan Tong
     */
    @Override
    public void nextRound() {
        // TODO:
    }

    /**
     * @Description: check whether a game has winner(s), and do correspond logic
     * @Author: Fangxu Zhou
     */
    @Override
    public void checkWin() {
        // TODO:
    }

    /**
     * @Description: transfer dealer into player
     * @return: true if it is a card game
     * @Author: Fangxu Zhou
     */
    private TEPlayer transferDealer2Player(TEDealer dealer) {
        // TODO:
        return null;
    }

    /**
     * @Description: transfer player into dealer
     * @Author: Fangxu Zhou
     */
    private TEDealer transferPlayer2Dealer(TEPlayer player) {
        // TODO:
        return null;
    }
}
