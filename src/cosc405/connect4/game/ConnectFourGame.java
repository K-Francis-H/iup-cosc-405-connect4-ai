package cosc405.connect4.game;

/**ConnectFourGame
 * 
 * This class contains the current game state as well
 * as information about who has won if the state is terminal.
 * The state is updated through this object by calling the
 * appropriate playerXMove() function with a column number
 * between 0 and 6 inclusive.
 * 
 * @author Kyle Harrity
 *
 */
public class ConnectFourGame
{
   public static int NUM_ROWS = 6;
   public static int NUM_COLS = 7;
   public static char player1Piece = 'X';
   public static char player2Piece = 'O';
   public static char emptySpace = '_';
   private String winner;
   private boolean gameOver;
   private boolean isStaleMate;
   private C4GameState gameState;
   
   public ConnectFourGame(){
	   this.winner = "N/A";
	   this.gameOver = false;
	   this.isStaleMate = false;
	   this.gameState = new C4GameState();
   }
   
   public boolean player1Move(int colNum){
	   if(gameState.canMove(colNum)){
		   gameState.move(ConnectFourGame.player1Piece, colNum);
		   return true;
	   }
	   //if we get here the column is full
	   System.out.println("Invalid move, Column "+colNum+" is full!");
	   return false;
   }
   
   public boolean player2Move(int colNum){
	   if(gameState.canMove(colNum)){
		   gameState.move(ConnectFourGame.player2Piece, colNum);
		   return true;
	   }
	   //if we get here the column is full
	   System.out.println("Invalid move, Column "+colNum+" is full!");
	   return false;
   }
   
   public boolean isStaleMate(){
	   return isStaleMate;
   }
   
   public boolean gameOver(){
	   return gameOver;
   }
   
   public String getWinner(){
	   return winner;
   }
   

   public void evalGameState(){
       if(gameState.isWinHorz(ConnectFourGame.player1Piece) 
    	  || gameState.isWinVert(ConnectFourGame.player1Piece)
    	  || gameState.isWinUpDiag(ConnectFourGame.player1Piece)
    	  || gameState.isWinDownDiag(ConnectFourGame.player1Piece)){
    	   gameOver=true;
    	   winner="Player1";
       }
       else if(gameState.isWinHorz(ConnectFourGame.player2Piece)
    		   || gameState.isWinVert(ConnectFourGame.player2Piece)
    		   || gameState.isWinUpDiag(ConnectFourGame.player2Piece)
    		   || gameState.isWinDownDiag(ConnectFourGame.player2Piece)){
    	   gameOver=true;
    	   winner="Player2";
       }
       else if(gameState.isStaleMate()){
    	   gameOver=true;
    	   isStaleMate=true;
    	   winner="STALEMATE";
       }
   }

   public void displayGameState(){
      gameState.printGameState();
   }
   
   public C4GameState getCurrentGameState(){
	   return gameState;
   }
    
}//end ConnectFourGame class
