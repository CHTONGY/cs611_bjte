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

    private void resetCurPlayer() {
        curPlayerIndex = 0;
        curPlayer = players.get(curPlayerIndex);
    }

    private void resetHands() {
        for (BJPlayer player: players) {
            player.getHandCardList().clear();
            player.getHandCardList().add(new HandCard(21));
        }
        bjDealer.setHandCard(new HandCard(21));
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
            System.out.println("\n==================================New Round!==================================\n");
            resetHands();
            nextRound();
        }
        System.out.println("\n=====================Somebody was bankrupt! Black Jack ends!=====================\n");
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

    private int[] chooseActOn(BJPlayer player) {
        System.out.printf("\nPlayer[#%d] now chooses which hands you want to act on:\n", player.getId());
        List<HandCard> handCards = player.getHandCardList();
        System.out.println("Your hands are as following:\n");
        for (int i = 0; i < handCards.size(); i++) {
            System.out.printf("Hand[#%d]: ", i);
            System.out.println(handCards.get(i));
        }


        boolean valid;
        int[] res;
        do {
            valid = true;
            System.out.print("(input eg: 0 2 3) >>> ");
            String input = ScanUtils.scanString();
            String[] ids = input.split(" ");

            res = new int[ids.length];
            for (int i = 0; i < res.length; i++) {
                res[i] = Integer.parseInt(ids[i]);
                if (res[i] < 0 || res[i] >= handCards.size()) {
                    System.out.println("Invalid input, please enter again.");
                    valid = false;
                    break;
                }
            }

        }while (!valid);
        return res;
//        return null;
    }

    private void askBet(BJPlayer player) {
        double minBet = 1;

        int nHands = player.getHandCardList().size();

        for (int i = 0; i < nHands; i++) {
            if (!player.isBankrupt()) {
                HandCard handCard = player.getHandCardList().get(i);
                // already has a bet
                if (handCard.getBetAmount() > 0) continue;
                
                System.out.printf("\nPlayer[#%d] now enters the bet:\n", player.getId());
                System.out.printf("\nPlayer[#%d] HandCard[#%d]: %s\n\nInput bet for this hand >>> ", player.getId(), i, handCard);
                double bet = ScanUtils.scanDouble(minBet, player.getDeposit() - (nHands - i - 1) * minBet);
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
            printHandsOfCurPlayer();
            askBet(player);
        }
        
        resetCurPlayer();
        int countStand = 0;
        while (countStand < players.size()) {
            if (curPlayerIndex == 0)    countStand = 0;

            String choice = chooseAction(curPlayer);
            if (choice.equals(StandAction.ACTION_NAME)) {
                countStand++;
                printHandsOfCurPlayer();
                nextTurn(curPlayer);
                continue;
            }

            int[] actOnIds = chooseActOn(curPlayer);

            curPlayer.takeAction(bjDealer, choice, actOnIds);
            askBet(curPlayer);
            printHandsOfCurPlayer();

            if (curPlayer.getLastAction().equals(StandAction.ACTION_NAME)
                    || choice.equals(SplitAction.ACTION_NAME)
                    || choice.equals(DoubleUpAction.ACTION_NAME)) {
                countStand++;
                nextTurn(curPlayer);
            }
            //else curPlayer keep hitting
        }

        // all players stand, dealer's turn
        bjDealer.reveal();
        while (bjDealer.getHandCard().getTotalPoints() <= 17) {
            bjDealer.getHandCard().addCard(bjDealer.deal(faceUp), true);
        }
        System.out.printf("Dealer's Hand Cards are as following: %s\n", bjDealer.getHandCard());

        checkWin();
    }


    /**
     * @Description: check whether a game has winner(s), and do correspond logic
     * @Author: Fangxu Zhou
     */
    @Override
    public void checkWin() {
        // loop in players
        for(BJPlayer player : players) {
//        	System.out.println("This player's deposit is "+player.getDeposit());
            if(!player.isBankrupt()) {
                // if the player'not bankrupt, loop in all of his hand card lists
                for(HandCard playerHandCard: player.getHandCardList()) {
                    // get data of player
                    int playerPts = playerHandCard.getTotalPoints();
                    double playerDeposit = player.getDeposit();
                    double playerBetting =  player.getBetting();
                    // get data of dealer
                    HandCard dealerHandCard = bjDealer.getHandCard();
                    int dealerPts = dealerHandCard.getTotalPoints();
                    // win or lose cases of a player's hand card
                    boolean playerWon = (!playerHandCard.isBusted() && ((playerPts > dealerPts) || bjDealer.getHandCard().isBusted()))
                            || (playerPts == dealerPts && isNaturalBlackJack(playerHandCard) && !isNaturalBlackJack(dealerHandCard));
                    boolean playerLosed = (playerHandCard.isBusted() || playerPts < dealerPts)
                            || (playerPts == dealerPts && !isNaturalBlackJack(playerHandCard) && isNaturalBlackJack(dealerHandCard));
                    System.out.println("This hand card of Player No." + player.getId() + " has total points as " + playerPts);
                    System.out.println("Dealer's hand card has total points as " + dealerPts);
                    if(playerWon) {
                        System.out.println("Player No." + player.getId() + "wins! ");
                        if (isNaturalBlackJack(playerHandCard))
                            System.out.println("Winning with a natural BlackJack");
                        player.setDeposit(playerDeposit + playerBetting * 2);
                        System.out.println("Winning with a bet amount as " + player.getBetting());
                        System.out.println("Player No." + player.getId() + " now has deposit as " + player.getDeposit());
                        playerHandCard.setWinStatus(1);
                    }else if(playerLosed) {
                        System.out.println("Player No." + player.getId() + " loses.. Dealer wins..");
//            			System.out.println("The player's deposit is " + playerDeposit);
//            			System.out.println("The player's deposit is " + playerBetting);
                        if (isNaturalBlackJack(dealerHandCard))
                            System.out.println("Losing by a natural BlackJack of Dealer");
//            			if (playerDeposit > playerBetting)
//            				player.setDeposit(playerDeposit - playerBetting);
//            			else
//            				player.setDeposit(0);
                        System.out.println("Losing with a bet amount as " + player.getBetting());
                        System.out.println("Player No." + player.getId() + " now has deposit as " + player.getDeposit());
                        playerHandCard.setWinStatus(-1);
                    }else {
                        playerHandCard.setWinStatus(0);
                        if (isNaturalBlackJack(playerHandCard) && isNaturalBlackJack(dealerHandCard))
                            System.out.println("Both Player and Dealer hold a natural BlackJack");
                        System.out.println("This round ends in a tie");
                        System.out.println("The bet with amount as " + player.getBetting() + " of Player No." + player.getId() + "returns");
                        System.out.println("Player No." + player.getId() + " now has deposit as " + player.getDeposit());
                    }
                }
            }

        }
    }
    
    /**
     * @Description: check whether a game has winner(s), and do correspond logic
     * @return: true if this handcard is a natural BlackJack
     * @Author: Fangxu Zhou
     */
    private boolean isNaturalBlackJack(HandCard handCard) {
    	return handCard.getHandCardList().size() == 2 && handCard.getTotalPoints() == 21;
    }
    
    private void printHandsOfCurPlayer() {
        List<HandCard> handCards = curPlayer.getHandCardList();
        int nHands = handCards.size();

        for (int i = 0; i < nHands; i++) {
            HandCard handCard = handCards.get(i);
            System.out.printf("\nPlayer[#%d] HandCard[#%d]: %s\n", curPlayer.getId(), i, handCard);
        }
    }
}
