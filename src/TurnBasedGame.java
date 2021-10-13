/**
 * @interfaceName: TurnBasedGame
 * @description: interface of turn based game
 * @author: Fangxu Zhou, You Peng, Yan Tong
 **/
public interface TurnBasedGame<T extends Player> {
    /**
     * @Description: change to next player
     * @Param: T curPlayer: current player
     * @return: T: next player
     * @Author: Fangxu Zhou, You Peng, Yan Tong
     */
    T nextTurn(T curPlayer);

    /**
     * @Description: whether the game has next new round
     * @return: boolean: true if there is next new round
     * @Author: Fangxu Zhou, You Peng, Yan Tong
     */
    boolean hasNextRound();

    /**
     * @Description: play next round
     * @Author: Fangxu Zhou, You Peng, Yan Tong
     */
    void nextRound();
}
