package cosc405.connect4.players;

import java.util.Scanner;

/**HumanPlayer
 * 
 * This class is essentially the same as Player
 * but it implements the getMove() method by 
 * receiving input from the command line.
 * 
 * @author kyle
 *
 */
public class HumanPlayer extends Player
{
   private Scanner in;
   
   public HumanPlayer(int playerNum, Scanner in ){
	   super(playerNum);
	   this.in = in;
   }

   @Override
   public int getMove()
   {
	   boolean validMove = false;
	   int move = 0;
	   while(!validMove){
	      move = in.nextInt();
	      if( move >= 0 && move < 7 )
		      validMove = true;
	      else
		      System.out.println("\nInvalid move must be a column number in range [0-6].");
	   }
	   return move;
   }

}//end class HumanPlayer
