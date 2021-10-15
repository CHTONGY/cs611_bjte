import java.util.ArrayList;
import java.util.List;

/**
 * @className: BJGame
 * @description: black jack game logic
 * @author: You Peng, Fangxu Zhou
 **/
public class BJGame implements Game, CardGame, TurnBasedGame<BJPlayer>, Winnable {

    List<BJPlayer> players;
    BJDealer bjDealer;
    BJPlayer curPlayer;
    int curPlayerIndex;


    /**
     * @Description: constructor
     * @Author: You Peng
     */
    public BJGame(int playerNum) {
        // TODO:
        players = new ArrayList<>();
        bjDealer = new BJDealer();
        // playerNum - 1, one for dealer
        for (int i = 0; i < playerNum - 1; i++) {
            players.add(new BJPlayer(i));
        }
        curPlayerIndex = -1;
        curPlayer = null;
    }

    /**
     * @Description: whether it's a card game
     * @return: true if it is a card game
     * @Author: Fangxu Zhou, You Peng
     */
    @Override
    public boolean isCardGame() {
        // TODO:
        return true;
    }

    /**
     * @Description: welcome sentences of game
     * @Author: You Peng
     */
    @Override
    public void welcome() {
        // TODO:
        String welcomeMsg = "\n==========================================================================\n"
                + "Welcome to Black Jack!\n"
                + "This program is to design and implement the game of Black Jack (in Java).\n"
                + "The game should allow for two players to play against each other.\n"
                + "The players should continue to take turns until there is a winner or until there is a stalemate.\n"
                + "==========================================================================\n\n";
        System.out.println(welcomeMsg);
    }

    /**
     * @Description: init process of game
     * @Author: You Peng
     */
    @Override
    public void init() {
        // TODO:
        welcome();
        double deposit;
        for (BJPlayer player: players) {
            deposit = 1000;
            System.out.printf("\nPlease enter the initial money for player[%d] >>> ", player.getId());
            deposit = ScanUtils.scanDouble(1000, 1000000);
            player.setDeposit(deposit);
        }
        resetCurPlayer();
    }
    
    public void resetCurPlayer() {
        curPlayerIndex = 0;
        curPlayer = players.get(curPlayerIndex);
    }

    /**
     * @Description: start game
     * @Author: You Peng
     */
    @Override
    public void play() {
        // TODO:

        // game begins
        init();
        while (hasNextRound()) {
            nextRound();
        }

    }

    private String chooseAction(BJPlayer player) {
        System.out.printf("\nPlayer[#%d] now chooses the action:\n", player.getId());
        for (String k: BJPlayer.actionMap.keySet()) {
            System.out.printf("\t%s\n", k);
        }
        do {
            System.out.print("Your choice >>> ");
            String choice = ScanUtils.scanString();
            if (!BJPlayer.actionMap.containsKey(choice)) {
                System.out.println("\nInvalid choice, please input again.");
            }
            else return choice;
        }while (true);
    }

    private void askBet(BJPlayer player) {
        double minBet = 1;

        int nHands = player.getHandCardList().size();
        System.out.printf("\nPlayer[#%d] now enters the bet:\n", player.getId());

        for (int i = 0; i < nHands; i++) {
            if (!player.isBankrupt()) {
                HandCard handCard = player.getHandCardList().get(i);
                // already has a bet
                if (handCard.getBetAmount() >= 0) continue;

                System.out.printf("\nPlayer[#%d] HandCard[#%d]: %s\nInput bet >>> ", player.getId(), i, handCard);
                double bet = ScanUtils.scanDouble(minBet, player.getDeposit() - (nHands - i) * minBet);
                handCard.setBetAmount(bet);
                player.updateDeposit(player.updateBet(bet));
            }
        }
    }

    /**
     * @param curPlayer The player of this turn.
     * @Description: change to next player
     * @Param: T curPlayer: current player
     * @return: T: next player
     * @Author: You Peng
     */
    @Override
    public BJPlayer nextTurn(BJPlayer curPlayer) {
        // TODO:
        curPlayerIndex = (curPlayerIndex + 1) % players.size();
        curPlayer = players.get(curPlayerIndex);
        return curPlayer;
    }

    /**
     * @Description: whether the game has next new round
     * @return: boolean: true if there is next new round
     * @Author: You Peng
     */
    @Override
    public boolean hasNextRound() {
        // TODO:

        for (BJPlayer player: players) {
            if (player.isBankrupt()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Description: play next round
     * @Author: You Peng
     */
    @Override
    public void nextRound() {
        // TODO:

        boolean faceUp = true;
        boolean faceDown = false;

        bjDealer.getHandCard().addCard(bjDealer.deal(faceUp), true);
        bjDealer.getHandCard().addCard(bjDealer.deal(faceDown), true);

        for (BJPlayer player: players) {
            player.getHandCardList().get(0).addCard(bjDealer.deal(faceUp), true);
            player.getHandCardList().get(0).addCard(bjDealer.deal(faceDown), true);

            askBet(player);
        }
        
        resetCurPlayer();
        int countStand = 0;
        while (countStand < players.size()) {
            if (curPlayerIndex == 0)    countStand = 0;
            
            String choice = chooseAction(curPlayer);
            curPlayer.takeAction(bjDealer, choice);
            // keep hitting
            while (choice.equals(HitAction.ACTION_NAME)) {
                choice = chooseAction(curPlayer);
                curPlayer.takeAction(bjDealer, choice);
            }
            askBet(curPlayer);
            if (curPlayer.getLastAction().equals(StandAction.ACTION_NAME)) {
                countStand++;
            }
            if (choice.equals(SplitAction.ACTION_NAME) || choice.equals(DoubleUpAction.ACTION_NAME)) {
                curPlayer.updateDeposit(curPlayer.updateBet());
                countStand++;
            }
            nextTurn(curPlayer);
        }

        // all players stand, dealer's turn
        bjDealer.reveal();
        while (bjDealer.getHandCard().getTotalPoints() <= 17) {
            bjDealer.getHandCard().addCard(bjDealer.deal(faceUp), true);
        }
        checkWin();
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
