package cosc405.connect4.game;

import java.util.ArrayList;
import java.util.List;

/**C4GameState
 * 
 * This class maintains the actual data representation of the game
 * state of the ConnectFourGame class. This class is also used by
 * AIPlayer to contain possible states that are evaluated to choose
 * the best move from a given state. This class also provides methods
 * to determine if the state is terminal and display the state.
 * 
 * @author Kyle Harrity
 *
 */
public class C4GameState
{
   private char[][] gameState;
   private int branchNum;
   private char turn;
   
   //used for initialization (creates root of game state space)
   public C4GameState(){
	   this.gameState = new char[ConnectFourGame.NUM_ROWS][ConnectFourGame.NUM_COLS];
	   //initialize to empty
	   for(char i=0; i < ConnectFourGame.NUM_ROWS; i++){
		   for(char j=0; j < ConnectFourGame.NUM_COLS; j++){
			   gameState[i][j] = ConnectFourGame.emptySpace;
		   }
	   }
	   turn = ConnectFourGame.player1Piece;
   }
   
   //used to make a move
   public C4GameState( C4GameState parent, char pieceType, int move){
	   this.gameState = new char[ConnectFourGame.NUM_ROWS][ConnectFourGame.NUM_COLS];
	   //initialize to parent
	   int pieceCount = 0;
	   for(char i=0; i < ConnectFourGame.NUM_ROWS; i++){
		   for(char j=0; j < ConnectFourGame.NUM_COLS; j++){
			   gameState[i][j] = parent.getValueAt(i,j);
			   if(gameState[i][j] != ConnectFourGame.emptySpace)
				   pieceCount++;
		   }
	   }
	   //place the move
	   move(pieceType, move);

	   this.branchNum = move;
	   if(pieceCount%2 == 0)
		   turn = ConnectFourGame.player1Piece;
	   else
		   turn = ConnectFourGame.player2Piece;
   }
   
   //returns a List of up to 7 children from the given state
   public List<C4GameState> getChildren(char pieceType){
	   List<C4GameState> children = new ArrayList<C4GameState>();
	   for(int i=0; i < ConnectFourGame.NUM_COLS; i++){
		   if(this.canMove(i))
			   children.add(new C4GameState(this, pieceType, i));
	   }
	   return children;
	   
   }
   
   public boolean canMove(int colNum){
	   if( gameState[0][colNum] == ConnectFourGame.emptySpace)
		   return true;
	   return false;
   }
   
   private void switchTurns(){
	   if(turn == ConnectFourGame.player1Piece)
		   turn = ConnectFourGame.player2Piece;
	   else
		   turn = ConnectFourGame.player1Piece;
   }
   
   public boolean move(char pieceType, int colNum){
	   switchTurns();
	   for( int i=ConnectFourGame.NUM_ROWS-1; i > -1; i--){//moving up from bottom to first blank piece
		   if( gameState[i][colNum] == ConnectFourGame.emptySpace ){
			   gameState[i][colNum] = pieceType;
			   return true; //exit were done here, otherwise itd fill the column
		   }
	   }	
	   return false;
   }
   
   public char getValueAt(int row, int col){
	   return gameState[row][col];
   }
   
   public int getBranchNum(){
	   return branchNum;
   }
   
   public char[][] getStateVals(){
	   return gameState;
   }
   
   public char getTurn(){
	   return turn;
   }
   
   public void printGameState(){
	  
	   //System.out.println("--------------");
	   for(int i=0; i < ConnectFourGame.NUM_ROWS; i++){
		   for(int j=0; j < ConnectFourGame.NUM_COLS; j++){
			   System.out.print("|"+gameState[i][j]);
		   }
		   System.out.println("|");
		   //System.out.println("--------------");
	   }	   
	   //System.out.println("--------------");
	   System.out.println("-0-1-2-3-4-5-6-");
   }
   
   public boolean isWin(){
	   return isWinHorz(ConnectFourGame.player1Piece)
			  || isWinHorz(ConnectFourGame.player2Piece)
			  || isWinVert(ConnectFourGame.player1Piece)
			  || isWinVert(ConnectFourGame.player2Piece)
              || isWinUpDiag(ConnectFourGame.player1Piece)
              || isWinUpDiag(ConnectFourGame.player2Piece)
              || isWinDownDiag(ConnectFourGame.player1Piece)
              || isWinDownDiag(ConnectFourGame.player2Piece);
	   
   }
   
   public boolean isWinHorz(char pieceType){
	   int count = 0;
	   for(int i=0; i<ConnectFourGame.NUM_ROWS; i++){
		   for(int j=0; j<ConnectFourGame.NUM_COLS; j++){
			   //if index is p1 piece and has at least 4 spaces to be a possible horizontal win
			   if(gameState[i][j] == pieceType && j <= ConnectFourGame.NUM_COLS-4){
				   count = 0;
				   for(int k = j; k < ConnectFourGame.NUM_COLS; k++){//while next piece is the correct type increase count
					   if(gameState[i][k] == pieceType)
						   count++;
					   else
						   break;
				   }
				   if(count == 4)
					   return true;
			   }//endif
		   }//endfor j
	   }//endfor i
	   return false;
   }
   
   public boolean isWinVert(char pieceType){
	   int count = 0;
	   for(int i=0; i<ConnectFourGame.NUM_ROWS; i++){
		   for(int j=0; j<ConnectFourGame.NUM_COLS; j++){
			   //if index is p1 piece and has at least 4 spaces to be a possible horizontal win
			   if(gameState[i][j] == pieceType && i <= ConnectFourGame.NUM_ROWS-4){
				   count = 0;
				   for(int k = i; k < ConnectFourGame.NUM_ROWS; k++){//while next piece is the correct type increase count
					   if(gameState[k][j] == pieceType)
						   count++;
					   else
						   break;
				   }
				   if(count == 4)
					   return true;
			   }//endif
		   }//endfor j
	   }//endfor i
	   return false;
   }
   
   public boolean isWinDownDiag(char pieceType){
	   for(int i=0; i < ConnectFourGame.NUM_ROWS-3; i++){
		   for(int j=0; j < ConnectFourGame.NUM_COLS-3; j++){
			   int count=0;
			   for(int k=0; k < 4; k++){
				  if(gameState[i+k][j+k] == pieceType)
					  count++;
				  else
					  break;
			   }
			   if(count == 4){
				   return true;
			   }
		   }
	   }
	   return false; 
   }
   
   public boolean isWinUpDiag(char pieceType){
	   for(int i=0; i < ConnectFourGame.NUM_ROWS-3; i++){
		   for(int j=3; j < ConnectFourGame.NUM_COLS; j++){
			   int count=0;
			   for(int k=0; k < 4; k++){
				  if(gameState[i+k][j-k] == pieceType)
					  count++;
				  else
					  break;
			   }
			   if(count == 4){
				   return true;
			   }
		   }
	   }
	   return false; 
   }
   
   //returns true if board is full, but no wins
   public boolean isStaleMate(){
	   for(int i=0; i < ConnectFourGame.NUM_ROWS; i++){
		   for(int j=0; j < ConnectFourGame.NUM_COLS; j++){
			   if(gameState[i][j] == ConnectFourGame.emptySpace){
				   return false;
			   }
				   
		   }
	   }
	   return true;
   }
}//end class C4GameState
