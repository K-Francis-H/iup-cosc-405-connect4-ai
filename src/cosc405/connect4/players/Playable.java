package cosc405.connect4.players;

/**Playable
 * 
 * This interface exists for the sole purpose of allowing
 * the HumanPlayer and AIPlayer classes which inherit from
 * the Player class to have a getMove() method with different
 * internal functionality.
 * 
 * @author kyle
 *
 */
public interface Playable
{
   int getMove();
}