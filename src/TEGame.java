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
            tePlayerList.add(new TEPlayer(i));
        }
        this.tePlayerList = tePlayerList;
        this.teDealer = new TEDealer();
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
        // TODO:
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
        // TODO:
        while (this.curPlayer != null) {
            // TODO: ask player to do action. hit or stand
        }
        // TODO: teDealer pick card


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
}
