/**
 * @className: BJGame
 * @description: black jack game logic
 * @author: You Peng, Fangxu Zhou
 **/
public class BJGame implements Game, CardGame, TurnBasedGame<BJPlayer>, Winnable {

    /**
     * @Description: constructor
     * @Author: You Peng
     */
    public BJGame(int playerNum) {
        // TODO:
    }

    /**
     * @Description: whether it's a card game
     * @return: true if it is a card game
     * @Author: Fangxu Zhou, You Peng
     */
    @Override
    public boolean isCardGame() {
        // TODO:
        return false;
    }

    /**
     * @Description: start game
     * @Author: You Peng
     */
    @Override
    public void play() {
        // TODO:
    }

    /**
     * @Description: welcome sentences of game
     * @Author: You Peng
     */
    @Override
    public void welcome() {
        // TODO:
    }

    /**
     * @Description: init process of game
     * @Author: You Peng
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
     * @Author: You Peng
     */
    @Override
    public BJPlayer nextTurn(BJPlayer curPlayer) {
        // TODO:
        return null;
    }

    /**
     * @Description: whether the game has next new round
     * @return: boolean: true if there is next new round
     * @Author: You Peng
     */
    @Override
    public boolean hasNextRound() {
        // TODO:
        return false;
    }

    /**
     * @Description: play next round
     * @Author: You Peng
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
}
