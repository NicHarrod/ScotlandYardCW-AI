package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Move;

import javax.annotation.Nonnull;

/** PIRORITIES FOR THE DIFFERENT SCORE METHODS
 * Maybe if detective in adjacent node
 * Distance to the closest detective
 * No. of nodes at destination
 * No. of tickets availble vs No. used
 * If the ticket was a double or secret
 * Variety of transport at destination
 */


public interface MoveScorer {


    /**A method for scoring an individual  single move
     *@param move
     *@return a corresponding integer for the move
     */
    public int scoreMove(@Nonnull Move move);


    /** A method for finding the best move
     * will use a moves set as an attribute
     * @return a single move
     */
    public Move bestMove();

    /** the different scorer methods for the move:
     * @param dest of the move
     * @return A weighted int depending on how important the method is
     */
    // Scoring depending on the number of nodes @dest
    public int numNodes(@Nonnull int dest);
    // Scoring depending on the variety of nodes @dest
    public int numDiffNodes (@Nonnull int dest);
    // Scoring depending on the proximity of detectives @dest
    public int distToNextDetective(@Nonnull int dest);
    // Scoring depending on the value of the ticket used by the move
    public int ticketVal(@Nonnull Move move);

}
