package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;


/**
 * Interface for the tree for the minmax
 * CURRENT WIP!!
 */
public interface Tree {
    /**
     * takes a state and limit and starts creating the nodes
     * @param state
     * @param limit
     */
    public void Tree (Board.GameState state, Integer limit);

    /**
     * takes a scorer and a node to score it's value
     * @param leaf
     * @return nodes value
     */
    public int scoreNode (LeafNode leaf);

    /**
     * prunes the tree and finds the best availible move
     * @return the move of the corresponding node of the next one to take
     */
    public Move prune ();
}
