/**
 * @className: Controller
 * @description: class of program logic controller
 * @author: Yan Tong
 **/
public class Controller {
    public static void run() {
        // TODO:
        System.out.println("Welcome to card games!");
        while (true) {
            System.out.println("Enter 'b' for playing black jack. " +
                    "Enter 't' for playing trianta ena. " +
                    "Enter 'e' for exit: ");
            String enter = ScanUtils.scanString();
            while (!enter.equalsIgnoreCase("b") &&
                    !enter.equalsIgnoreCase("t") &&
                    !enter.equalsIgnoreCase("e")) {
                System.out.println("invalid input, please enter 'b' for black jack, " +
                        "'t' for trianta ena, or 'e' for exit.");
                enter = ScanUtils.scanString();
            }
            if (enter.equalsIgnoreCase("e")) {
                System.out.println("Thank you for your playing!");
                return;
            }
            if (enter.equalsIgnoreCase("b")) {
                BJGame bjGame = createBJGame();
                bjGame.play();
            } else if (enter.equalsIgnoreCase("t")) {
                TEGame teGame = createTEGame();
                teGame.play();
            }
        }
    }

    private static BJGame createBJGame() {
        // BJGame only allows 2 people to play
        BJGame bjGame = new BJGame(2);
        return bjGame;
    }

    private static TEGame createTEGame() {
        System.out.println("Enter number of players going to attend:");
        int playerNum = ScanUtils.scanInt(2, Integer.MAX_VALUE);
        TEGame teGame = new TEGame(playerNum);
        return teGame;
    }
}
