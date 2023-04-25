package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;


/**
 * Interface for the tree for the minmax
 * CURRENT WIP!!
 */
public interface Tree {


    /**
     * takes a scorer and a node to score it's value
     * @param leaf
     * @return nodes value
     */
    public int scoreNode (Node leaf);

    /**
     * prunes the tree and finds the best availible move
     * @return the move of the corresponding node of the next one to take
     */
    public Move prune ();
}
