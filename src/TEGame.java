import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @className: TEGame
 * @description: class of trianta ena
 * @author: Yan Tong, Fangxu Zhou
 **/
public class TEGame implements Game, CardGame, TurnBasedGame<TEPlayer>, Winnable {
    public final int INITIAL_DEPOSIT = 1000;
    private TEDealer teDealer;
    private List<TEPlayer> tePlayerList;
    private TEPlayer curPlayer;

    private int curPlayerIndex; // the index of curPlayer in tePlayerList

    /**
     * @Description: constructor
     * @Author: Yan Tong
     */
    public TEGame(int playerNum) {
        // initialize 1 dealer, num-1 player
        List<TEPlayer> tePlayerList = new ArrayList<>();
        for (int i = 1; i < playerNum; i++) {
            TEPlayer newPlayer = new TEPlayer(i);
            newPlayer.setDeposit(INITIAL_DEPOSIT);
            newPlayer.getHandCardList().add(new HandCard(0, 31));
            tePlayerList.add(newPlayer);
        }
        this.tePlayerList = tePlayerList;
        // init dealer
        this.teDealer = new TEDealer();
        this.teDealer.setDeposit(3 * INITIAL_DEPOSIT);
        this.teDealer.setHandCard(new HandCard(0, 31));

        this.curPlayerIndex = 0;
        this.curPlayer = this.tePlayerList.get(0);
    }

    /**
     * @Description: whether it's a card game
     * @return: true if it is a card game
     * @Author: Fangxu Zhou, Yan Tong
     */
    @Override
    public boolean isCardGame() {
        return true;
    }
    
    public TEDealer getTEDealer() {
    	return this.teDealer;
    }
    
    public void setTEDealer(TEDealer teDealer) {
    	this.teDealer = teDealer;
    }

    /**
     * @Description: start game
     * @Author: Yan Tong
     */
    @Override
    public void play() {
        welcome();
        while (true) {
            if (hasNextRound()) {
                nextRound();
            }
            if (!wantToContinue()) {
                break;
            }
        }
    }

    /**
     * @Description: welcome sentences of game
     * @Author: Yan Tong
     */
    @Override
    public void welcome() {
        System.out.printf("Welcome to Trianta Ena. There are %d player(s) and 1 dealer. " +
                "Enjoy yourselves!\n", this.tePlayerList.size());
    }

    /**
     * @Description: init process of game
     * @Author: Yan Tong
     */
    @Override
    public void init() {
        // do nothing on init process
        return;
    }

    /**
     * @param curPlayer
     * @Description: change to next player. if all players have done, then return null
     * @Param: T curPlayer: current player
     * @return: T: next player. if no next player, return null
     * @Author: Yan Tong
     */
    @Override
    public TEPlayer nextTurn(TEPlayer curPlayer) {
        if (this.curPlayerIndex + 1 == this.tePlayerList.size()) {
            return null;
        }
        return this.tePlayerList.get(++this.curPlayerIndex);
    }

    /**
     * @Description: whether the game has next new round
     * @return: boolean: true if there is next new round
     * @Author: Yan Tong
     */
    @Override
    public boolean hasNextRound() {
        for (TEPlayer tePlayer : this.tePlayerList) {
            if (tePlayer.isBankrupt()) {
                return false;
            }
        }
        if (this.teDealer.isBankrupt()) {
            return false;
        }
        return true;
    }

    /**
     * @Description: play next round
     * @Author: Yan Tong
     */
    @Override
    public void nextRound() {
        dealInitialCard();
        setPlayerBetting();
        dealCards2Player(2, true);
        dealCards2Dealer(2, false);
        while (this.curPlayer != null) {
            // ask player to do action. hit or stand
            System.out.printf("Player %d, your hand cards: %s\n",
                    this.curPlayer.getId(),
                    playerHandCardInfo(this.curPlayer));
            System.out.println("enter 'h' for hit, or enter 's' for stand");
            String choice = ScanUtils.scanString();
            while (!choice.equalsIgnoreCase("s")) {
                if (!choice.equalsIgnoreCase("h")) {
                    System.out.println("invalid input. please enter 'h' for hit, or enter 's' for stand:");
                    continue;
                }
                Card hitCard = this.curPlayer.hit(this.teDealer);
                System.out.printf("the card you get is %s\n", hitCard.getSymbol());
                if (this.curPlayer.getHandCardList().get(0).isBusted()) {
                    System.out.printf("total face value: %d. busted!\n",
                            this.curPlayer.getHandCardList().get(0).getTotalPoints());
                    break;
                }
                System.out.println("enter 'h' for hit, or enter 's' for stand");
                choice = ScanUtils.scanString();
            }
            System.out.printf("Player %d, your hand cards: %s\n",
                    this.curPlayer.getId(),
                    playerHandCardInfo(this.curPlayer));
            System.out.println("next player.");

            this.curPlayer = nextTurn(this.curPlayer);
        }
        // teDealer pick card
        handleDealer();
        // at the end of a round, check win
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

    /**
     * @Description: ask if player want to be de
     * @return: true if it is a card game
     * @Author: Fangxu Zhou
     */
    private void sortPlayersByDeposit(){
    	Collections.sort(this.tePlayerList, new Comparator<TEPlayer>() {
			@Override
			public int compare(TEPlayer p1, TEPlayer p2) {
				// TODO Auto-generated method stub
				double diff = p1.getDeposit() - p2.getDeposit();
				if(diff<0) {
					return -1;
				}else if(diff>0) {
					return 1;
				}
				return 0;
			}
    	});
    }
    
    
    /**
     * @Description: ask if player want to be dealer
     * @return: true if it is a card game
     * @Author: Fangxu Zhou
     */
    private TEPlayer askToBeDealer() {
        this.sortPlayersByDeposit(); 
        TEPlayer player = null;
        for (TEPlayer p : this.tePlayerList){
        	System.out.println("Player No." +  p.getId() + ", do you want to be the dealer for the next round?");
        	System.out.println("Press y for a yes, press any button else for a no.");
        	String answer = ScanUtils.scanString();
        	if (answer.equalsIgnoreCase("y")) {
        		player  =  p;
        		break;
        	}
        }	
        return player;
    }
    
    
    /**
     * @Description: exchange the roles of dealer and player
     * @return: true if it is a card game
     * @Author: Fangxu Zhou
     */
    private void exchangeRoles(TEPlayer playerTobeDealer) {
    	// transfer the dealer to a new player
    	TEPlayer newPlayer = new TEPlayer(playerTobeDealer.getId());
    	newPlayer.setDeposit(this.teDealer.getDeposit());
    	this.tePlayerList.remove(playerTobeDealer);
    	this.tePlayerList.add(newPlayer);
    	
    	// transfer the player to a deaterr
    	TEDealer newDealer = new TEDealer();
    	newDealer.setDeposit(playerTobeDealer.getDeposit());
    	this.setTEDealer(newDealer);	
    }
    

    private boolean wantToContinue() {
        System.out.println("do you want to continue? enter y(es) or n(o):");
        String choice = ScanUtils.scanString();
        while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
            System.out.println("please enter 'y' for yes, or 'n' for no:");
            choice = ScanUtils.scanString();
        }
        if (choice.equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    private void dealInitialCard() {
        // deal card(s) to players and dealer at the beginning of game
        dealCards2Dealer(1, true);
        dealCards2Player(1, false);
    }

    private void dealCards2Dealer(int cardNum, boolean isPublicCard) {
        for (int i = 0; i < cardNum; i++) {
            this.teDealer.getHandCard().addCard(this.teDealer.deal(isPublicCard));
        }
    }

    private void dealCards2Player(int cardNum, boolean isPublicCard) {
        for (TEPlayer tePlayer : this.tePlayerList) {
            for (int i = 0; i < cardNum; i++) {
                tePlayer.getHandCardList().get(0).addCard(this.teDealer.deal(isPublicCard));
            }
        }
    }

    /**
     * @Description: set betting after getting the first card
     * @Author: Yan Tong
     */
    private void setPlayerBetting() {
        for (TEPlayer tePlayer : this.tePlayerList) {
            while (true) {
                System.out.printf("Player%d, your private card is: %s. " +
                                "Enter 'b' for betting, or 'f' for fold:",
                        tePlayer.getId(),
                        tePlayer.getHandCardList().get(0).getHandCardList().get(0).getSymbol());
                String choice = ScanUtils.scanString();
                while (!choice.equalsIgnoreCase("b") && !choice.equalsIgnoreCase("f")) {
                    System.out.println("please enter 'b' for betting, or 'f' for fold:");
                    choice = ScanUtils.scanString();
                }

                if (choice.equalsIgnoreCase("b")) {
                    break;
                }

                // fold
                tePlayer.getHandCardList().get(0).removeCard(0);
                tePlayer.getHandCardList().get(0).addCard(this.teDealer.deal(false));
                System.out.println("You choose to fold. Change a new card for you.");
            }

            // betting
            System.out.printf("Your deposit is: %f, enter the money you want to bet:\n", tePlayer.getDeposit());
            double betting = ScanUtils.scanDouble(0, tePlayer.getDeposit());
            while (betting == 0) {
                System.out.println("Betting has to be larger than 0. Please enter again:");
                betting = ScanUtils.scanDouble(0, tePlayer.getDeposit());
            }
            tePlayer.setBetting(betting);
            System.out.println("Done");
        }
    }

    /**
     * @Description: get all hand cards' symbols of te player
     * @Param: player
     * @return: card symbol string
     * @Author: Yan Tong
     */
    private String playerHandCardInfo(TEPlayer tePlayer) {
        return printHandCardInfo(tePlayer.getHandCardList().get(0));
    }

    private String printHandCardInfo(HandCard handCard) {
        StringBuilder publicSb = new StringBuilder();
        StringBuilder privateSb = new StringBuilder();
        publicSb.append("public card(s): ");
        privateSb.append("private card(s): ");
        List<Card> cards = handCard.getHandCardList();
        for (Card card : cards) {
            if (card.isPublic()) {
                publicSb.append(card.getSymbol()).
                        append("(").append(card.getFaceValue()).append(")").
                        append(" ");
            } else {
                privateSb.append(card.getSymbol()).
                        append("(").append(card.getFaceValue()).append(")").
                        append(" ");
            }
        }
        publicSb.append("; ").append(privateSb);
        return publicSb.toString().trim();
    }

    private void handleDealer() {
        this.teDealer.revealAllPrivateCard();
        while (this.teDealer.getHandCard().getTotalPoints() < 27) {
            this.teDealer.hit();
        }
        System.out.println("Dealer cards info:");
        printHandCardInfo(this.teDealer.getHandCard());
    }
}
