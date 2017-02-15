package cosc405.connect4.players;

import java.util.List;

import cosc405.connect4.game.C4GameState;
import cosc405.connect4.game.ConnectFourGame;

public class AIPlayer extends Player
{
   private int maxDepth;
   private ConnectFourGame c4g;
   
   private static final int INFINITY = 999999999;
   
   public AIPlayer(int playerNum, int maxDepth, ConnectFourGame c4g){
	   super(playerNum);

	   this.maxDepth = maxDepth;
	   this.c4g = c4g;	   
   }
   
   public void setMaxDepth(int maxDepth){
	   this.maxDepth = maxDepth;
   }
   
   public int getMaxDepth(){
	   return maxDepth;
   }
   
   
   @Override
   public int getMove(){
	   return minimax(c4g.getCurrentGameState(), 0, pieceType);
   }
   
   /**minimax()
    * 
    * @param state : C4GameState - current game state
    * @param level : int         - depth in state space
    * @param turn  : char        - player piece (used to determine if min level or max
    * 
    * This function recursively explores the state space to the AIPlayer's specified depth
    * bound to find the best move.
    * 
    * @return score  : int - if level > 0
    * @return choice : int - if level == 0 on range [0-6]
    */ 
   private int minimax(C4GameState state, int level, char turn){
	   if(level == maxDepth || state.isWin()){
		   if(level == 0)
			   return state.getBranchNum();
		   else
		      return score(state, level);
	   }
	   else{
		   if( turn == pieceType ){//maximizing level

			  List<C4GameState> children = state.getChildren(pieceType);
		      int maxBranch = 0;
		      int max = -INFINITY;
		      int tmp = 0;
		      for(int i=0; i < children.size(); i++){
		    	  tmp = minimax(children.get(i), level+1, opponentPieceType);
		    	  //if(level == 0) DEBUG StUFF
		    		//  System.out.println("choice: "+children.get(i).getBranchNum()+" score: "+tmp);
		    	  if(tmp >= max){
		    		  max = tmp;
		    		  maxBranch = children.get(i).getBranchNum();
		    	  }
		      }
		      if(level == 0)//top of tree return choice not max
		    	  return maxBranch;
		      else //return max score
		    	  return max;
		   }
		   else{// minimizing level
			   
			  List<C4GameState> children = state.getChildren(opponentPieceType);		   
		      int min = INFINITY;
		      int tmp = 0;
		      for(int i=0; i < children.size(); i++){
		    	  tmp = minimax(children.get(i), level+1, pieceType);
		    	  if(tmp <= min){
		    		  min = tmp;
		    	  }
		      }
		      //System.out.println("min: "+min+" choice: "+minBranch);
		      return min;
		   }
	   }//end else
   }//end minimax()
   
   
   /**score()
    * 
    * @param state : C4GameState - the object containing the current game state and other pertinent info
    * 
    * This function computes and returns the sum of the heuristic values for the move that led to this state
    * 
    * @return score : int - the overall score for this state based on the heuristics
    */
   public int score(C4GameState state, int level){
	   char[][] s = state.getStateVals();
	   //get position of latest move
	   int col = state.getBranchNum();
	   int row = 0; //find row based on col position (always last occupied row)
	   for(int i=0; i < ConnectFourGame.NUM_ROWS; i++){
		   if(s[i][col] != ConnectFourGame.emptySpace){
			   row = i;
			   break;
		   }
	   }
	   
	   char turn = state.getTurn();	   
       
	   if(state.isWin()){//return large value and short circuit heuristic eval
	      if(turn == pieceType)
	    	  //divide by level so closer win is picked over later win
		      return INFINITY/level;
		  else
			   return -INFINITY/level;
	   }
	   
	   //otherwise just return the heuristic score sum
	   return   numLinesHeuristic(s, row, col, turn) 
               +adjacencyHeuristic(s, row, col, turn)
               +blockingHeuristic(s, row, col, turn);
   }
   
   /**numLinesHeuristic()
    * @param state : char[][] - game state as 2D char array
    * @param row   : int      - the row that the move that led to this state is in. [0-5]
    * @param col   : int      - the column that the move to this state is in. [0-6]
    * @param turn  : char     - the type of piece (X, O) that was used to get to this state
    * 
    * This heuristic determines the number of vertical, horizontal, and diagonal lines 
    * (wins) from the move leading to this state. 
    * 
    * @return numLines : int - the number of lines possible from the specified move.  
    */
   private int numLinesHeuristic(char[][] state, int row, int col, char turn){
	   //determine who we are computing the heuristic for 
	   char opponent;
	   if(turn == ConnectFourGame.player1Piece)
		   opponent = ConnectFourGame.player2Piece;
	   else
		   opponent = ConnectFourGame.player1Piece;

	   int numHorz = 0;
	   int numVert = 0;
	   int numDiag = 0;

	   /*HORIZONTAL
	   if(row < ConnectFourGame.NUM_ROWS-1){//if row is not along bottom
			boolean unimpeded = true;
			for(int i=Math.max(col-3, 0); i < col+1; i++){
			   if(i < ConnectFourGame.NUM_COLS-3){//if still enough space to have a line
			      for(int j=i; j < i+3; j++){
			    	  //if next piece is opponents or empty space
				      if(state[row][j] == opponent || state[row+1][j] == ConnectFourGame.emptySpace)
					      unimpeded = false;
			      }
			      if(unimpeded)
				      numHorz++;
			      unimpeded = true;
			   }
			   else//no chance of a line -> exit
				   break;
			}		   
	   }
	   else{//row is along bottom*/
			boolean unimpeded = true;
			for(int i=Math.max(col-3, 0); i < col+1; i++){
			   if(i < ConnectFourGame.NUM_COLS-3){//if still enough space to have a line
			      for(int j=i; j < i+3; j++){
				      if(state[row][j] == opponent)
					      unimpeded = false;
			      }
			      if(unimpeded)
				      numHorz++;
			      unimpeded = true;
			   }
			   else//no chance of a line -> exit
				   break;
			} 
	   //}
	   /*END HORIZONTAL*/
	   
	   //Left these out they seem to distract the AI from better moves...
	   /*VERTICAL
	   if( row > 2 )//if row > 2 ==> there are no pieces above it and enough space for a win
		   numVert = 1;
	   else{//we have to check
		   int pieceCount = 0;
		   for(int i=row; i<row+3; i++){
			   if(state[i][col] != opponent)
				   pieceCount++;
			   else
				   break;
		   }
		   if(pieceCount+(row+1) >= 4)//if there are a total of 4+ spaces/player pieces
			   numVert = 1;
	   }
	   /*END VERTICAL*/
	   
	   /*DIAGONAL
	   if(row > 2 && col < ConnectFourGame.NUM_COLS-3){//forward diag is possible /
		   boolean forwardDiag = true;
		   for(int i=0; i < 4; i++){
			   if(state[row-i][col+i] == opponent)
				   forwardDiag = false;
		   }
		   if(forwardDiag)
			   numDiag++;
	   }
	   if(row > 2 && col > 2){//back diag possible \
		   boolean backDiag = true;
		   for(int i=0; i < 4; i++){
			   if(state[row-i][col-i] == opponent)
				   backDiag = false;
		   }
		   if(backDiag)
			   numDiag++;
	   }
	   /*END DIAGONAL*/
	   
	   
	   
	   if(turn == pieceType)//if it is evaluated for the AI return positive value
	      return numHorz+numVert+numDiag;
	   else //return negative value
		   return -(numHorz+numVert+numDiag);
   }
   
   /**adjacencyHeuristic()
    * 
    * @param state : char[][] - game state as 2D char array
    * @param row   : int      - the row that the move that led to this state is in. [0-5]
    * @param col   : int      - the column that the move to this state is in. [0-6]
    * @param turn  : char     - the type of piece (X, O) that was used to get to this state
    * 
    * This heuristic determines how much connectivity is achieved by the specified move.
    * It only considers the horizontal and vertical directions, the diagonal case was left
    * unimplemented because the overall AI performs reasonably well without it and other
    * heuristics such as numLinesHeuristic() and the win check in the minimax() provide
    * good coverage of the possibilities of a diagonal win.
    * 
    * Possibilities for adjacency include:
    *     2 in a row
    *     3 in a row
    *     4 in a row
    * The possibilities are weighted, summed and then returned
    *  
    * @return score : int - the weighted sum of the found connectivity created by the
    * specified move
    */
   private int adjacencyHeuristic(char[][] state, int row, int col, char turn){
	   char opponent;
	   if(turn == ConnectFourGame.player1Piece)
		   opponent = ConnectFourGame.player2Piece;
	   else
		   opponent = ConnectFourGame.player1Piece;
	   
	   int num2s = 0; //2 in a lines
	   int num3s = 0; //3 in a lines
	   int num4s = 0; //4 in a lines
	   
	   /*HORIZONTAL & VERTICAL*/
	   int hl=0, hr=0, v=0;
	   boolean canHl=true, canHr=true, canV=true;
	   for(int i=1; i < 4; i++){
		   if(col+i < ConnectFourGame.NUM_COLS && canHr)//horz right
			   if(state[row][col+i] != opponent)
				   hr++;
			   else
				   canHr=false;
		   if(col-i > -1 && canHl)//horz left
			   if(state[row][col-i] != opponent)
				   hl++;
			   else
				   canHl = false;
		   if(row+i < ConnectFourGame.NUM_ROWS && canV)
			   if(state[row+i][col] != opponent)
				   v++;
			   else
				   canV = false;
	   }
	   if(hr == 1)
		   num2s++;
	   else if(hr > 1)
		   num3s++;
	   if(hl == 1)
		   num2s++;
	   else if(hl > 1)
		   num3s++;
	   if(v == 1)
		   num2s++;
	   else if(v > 1)
		   num3s++;
	   /*END HORIZONTAL & VERTICAL*/
	   
	   /*DIAGONAL*/
	   int dr = 0, dl = 0, ur = 0, ul = 0;
	   boolean canDr=true, canDl=true, canUr=true, canUl=true;
	   for(int i=1; i < 4; i++){
		   if(row+i < ConnectFourGame.NUM_ROWS && col+i < ConnectFourGame.NUM_COLS && canDr)//down right
			   if(state[row+i][col+i] != opponent)
				   dr++;
			   else
				   canDr = false;
		   if(row-i > -1 && col+i < ConnectFourGame.NUM_COLS && canUr)//up right
			   if(state[row-i][col+i] != opponent)
				   ur++;
			   else
				   canUr = false;
		   if(row+i < ConnectFourGame.NUM_ROWS && col-i > -1 && canDl)//down left
			   if(state[row+i][col-i] != opponent)
				   dl++;
			   else
				   canDl = false;
		   if(row-i > -1 && col-i > -1 && canUl)//up left
			   if(state[row-i][col-i] != opponent)
				   ul++;
			   else
				   canUl = false;
	   }
	   if(dr == 1)
		   num2s++;
	   else if(dr > 1)
		   num3s++;
	   if(dl == 1)
		   num2s++;
	   else if(dl > 1)
		   num3s++;
	   if(ur == 1)
		   num2s++;
	   else if(ur > 1)
		   num3s++;
	   if(ul == 1)
		   num2s++;
	   else if(ul > 1)
		   num3s++;
	   /*END DIAGONAL*/
	   
	   if(turn == pieceType)
	      return num2s+num3s*6+num4s*1000;
	   else{
		   return -(num2s+num3s*6+num4s*1000);
	   }
		   
   }
   
   /**blockingHeuristic()
    * 
    * @param state : char[][] - game state as 2D char array
    * @param row   : int      - the row that the move that led to this state is in. [0-5]
    * @param col   : int      - the column that the move to this state is in. [0-6]
    * @param turn  : char     - the type of piece (X, O) that was used to get to this state
    * 
    * This heuristic considers how many possible wins are blocked by the specified move. Blocks
    * are checked in the horizontal, vertical, and diagonal directions. This is the only defensive
    * heuristic employed by the AIPlayer, the rest are of an offensive nature.
    * There are only 2 blocking possibilities that are considered:
    *    blocks 2 in a row
    *    blocks 3 in a row
    * Blocking a single piece is not really necessary, and blocking 4 cannot happen during
    * normal play (the other player has won at that point).
    * 
    * @return score : int - the weighted sum of the number of blocks created by the specified move
    */
   private int blockingHeuristic(char[][] state, int row, int col, char turn){
	   //determine who we're computing the heuristic for
	   char opponent;
	   if(turn == ConnectFourGame.player1Piece)
		   opponent = ConnectFourGame.player2Piece;
	   else
		   opponent = ConnectFourGame.player1Piece;
	   
	   int blocks2 = 0;
	   int blocks3 = 0;
	   
	   /*HORIZONTAL*/
	   //left
	   int numPieces = 0;
	   for(int i=col+1; i< ConnectFourGame.NUM_COLS; i++){
		   //System.out.println("Left: pos: "+i+" piece: "+state[row][i]);
		   if(state[row][i] == opponent)
			   numPieces++;
		   else
			   break;
	   }
	   //right
	   for(int i=col-1; i > -1; i--){
		   if(state[row][i] == opponent)
			   numPieces++;
		   else
			   break;
	   }
	   if(numPieces > 2){
		   blocks3++;
	   }
	   else if(numPieces == 2){
		   blocks2++;
	   }
	   /*END HORIZONTAL*/
		   
	   
	   /*VERTICAL*/
	   numPieces=0;
	   for(int i=row+1; i < ConnectFourGame.NUM_ROWS; i++){
		   if(state[i][col] == opponent)
			   numPieces++;
		   else
			   break;
	   }
	   if(numPieces > 2){
		   blocks3++;
	   }
	   else if(numPieces == 2){
		   blocks2++;
	   }
	   /*END VERTICAL*/
	   
	   
	   /*DIAGONAL*/
	   numPieces=0;
	   //o
	   // x
	   //  x
	   //   x
	   for(int i=1; i < 4; i++ ){//down forward diag
		   if(row+i < ConnectFourGame.NUM_ROWS && col+i < ConnectFourGame.NUM_COLS){
			   if(state[row+i][col+i] == opponent)
				   numPieces++;
			   else
				   break;
		   }
		   else
			   break;
	   }
	   if(numPieces > 2)
		   blocks3++;
	   else if(numPieces == 2)
		   blocks2++;
	   numPieces = 0;
	   //   x
	   //  x
	   // x
	   //o
	   for(int i=1; i < 4; i++){//up forward diag
		   if(row-i > 0 && col+i < ConnectFourGame.NUM_COLS){
			   if(state[row-i][col+i] == opponent)
				   numPieces++;
			   else
				   break;
		   }
		   else
			   break;
	   }
	   if(numPieces > 2)
		   blocks3++;
	   else if(numPieces == 2)
		   blocks2++;
	   numPieces = 0;
	   //   o
	   //  x
	   // x
	   //x
	   for(int i=1; i<4; i++){//down back diag
		   if(row+i < ConnectFourGame.NUM_ROWS && col-i > 0){
			   if(state[row+i][col-i] == opponent)
				   numPieces++;
			   else
				   break;
		   }
		   else
			   break;
	   }
	   if(numPieces > 2)
		   blocks3++;
	   else if(numPieces == 2)
		   blocks2++;
	   numPieces = 0;
	   //x
	   // x
	   //  x
	   //   o
	   for(int i=0; i < 4; i++){//up back diag
		   if(row-i > 0 && col-i > 0){
			   if(state[row-i][col-i] == opponent)
				   numPieces++;
			   else
				   break;
		   }
		   else
			   break;
	   }
	   if(numPieces > 2)
		   blocks3++;
	   else if(numPieces == 2)
		   blocks2++;
	   /*END DIAGONAL*/
	   
	   if(turn == pieceType)
	      return blocks2*10 + 100*blocks3;
	   else
		   return -(blocks2*10 + 100*blocks3);
   }
  
}//end AIPlayer class