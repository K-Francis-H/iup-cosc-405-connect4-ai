package cosc405.connect4.players;

import java.util.Random;

import cosc405.connect4.game.ConnectFourGame;

/**Player
 * 
 * This is the parent class of HumanPlayer and
 * AIPlayer it contains some bookkeeping data
 * about player number and piece type. The Player
 * class may also be instantiated for a 'dumb'
 * player. getMove() returns a random move.
 * 
 * 
 * @author kyle
 *
 */
public class Player implements Playable
{
	   protected int playerNum;
	   protected int opponentPlayerNum;
	   protected char pieceType;
	   protected char opponentPieceType;
	   
	   public Player(int playerNum){
		   this.playerNum = playerNum;
		   if(playerNum == 1){
			   this.opponentPlayerNum = 2;
			   this.pieceType = ConnectFourGame.player1Piece;
			   this.opponentPieceType = ConnectFourGame.player2Piece;
		   }
		   else{
			   this.opponentPlayerNum = 1;
			   this.pieceType = ConnectFourGame.player2Piece;
			   this.opponentPieceType = ConnectFourGame.player1Piece;
		   }
	   }
	   
	   public int getMove(){
		   return new Random().nextInt(ConnectFourGame.NUM_COLS-1);
	   }
	   
	   public int getPlayerNum(){
		   return playerNum;
	   }
	   
	   public char getPieceType(){
		   return pieceType;
	   }
	   
}
