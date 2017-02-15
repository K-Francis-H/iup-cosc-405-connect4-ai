package cosc405.connect4.gameloop;

import java.util.Scanner;

import cosc405.connect4.game.ConnectFourGame;
import cosc405.connect4.players.AIPlayer;
import cosc405.connect4.players.HumanPlayer;
import cosc405.connect4.players.Player;

/**GameLoop
 * This class contains the main function which runs
 * the Connect Four Game.
 * 
 * @author Kyle Harrity
 *
 */
public class GameLoop
{

	/**main()
	 * 
	 * This function instantiates the ConnectFourGame and Player objects
	 * then enters a game loop that receives input from the players, updates
	 * the game state and then displays it. After a terminal state is reached the loop
	 * exits and the winner, if any, is declared.
	 */
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Connect4\nIs player1 \"X\" a human or computer (h/c)");
        String p1Response = in.nextLine();
        System.out.println("Is player2 \"O\" a human or computer (h/c)"); 
        String p2Response = in.nextLine();
        int depthBound = 7;
        
        ConnectFourGame c4g = new ConnectFourGame();
        Player player1;
        Player player2;
        if(p1Response.equals("h"))
        	player1 = new HumanPlayer(1, in);
        else
        	player1 = new AIPlayer(1, depthBound, c4g);//player 1, max Depth = 5
        if(p2Response.equals("h"))
        	player2 = new HumanPlayer(2, in);
        else
        	player2 = new AIPlayer(2, depthBound, c4g);
        
        
        while(!c4g.gameOver()){

        	System.out.print("Player1's ("+c4g.getCurrentGameState().getTurn()+") move: ");
        	int p1move = player1.getMove();
        	System.out.println(p1move);
        	if(!c4g.player1Move(p1move))
        		while(!c4g.player1Move(player1.getMove()));
        	c4g.displayGameState();
        	
        	c4g.evalGameState();
        	if(c4g.gameOver())
        		break;
        	
        	System.out.print("Player2's ("+c4g.getCurrentGameState().getTurn()+") move: ");
        	int p2move = player2.getMove();
        	System.out.println(p2move);
        	if(!c4g.player2Move(p2move))
        		while(!c4g.player1Move(player2.getMove()));
        	c4g.displayGameState();
        	
        	c4g.evalGameState();
        }
        if(c4g.isStaleMate())
        	System.out.println("Stalemate!");
        else
            System.out.println(c4g.getWinner()+" Wins!");
	}

}
