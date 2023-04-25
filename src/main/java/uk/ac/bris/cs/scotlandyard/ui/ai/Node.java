package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.ArrayList;
import java.util.Set;

public interface Node {
    /**
     * creates a set of child nodes
     * @return children
     */
    public ArrayList<MyNode> MakeChildren();

    /**
     * returns the nodes generation
     * @return Integer
     */
    public Integer generation();

    /**
     * returns the nodes limit
     * @return Integer
     */
    public Integer limit();
}
