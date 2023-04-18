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
    /**A method for scoring an individual move
    *@param move
    *@return a corresponding integer for the move
    */
    public int scoreMove(@Nonnull Move move);

    /** A method for finding the best move
     * will use a moves set as an attribute
     * @return a single move
     */
    public Move bestMove();
}
