package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

public class MinMaxTree implements Tree {
    MyNode root;
    Integer Alpha;


    public void MinMaxTree(Board.GameState state, Integer limit) {
        this.root = new MyNode(NodeType.ROOT,null,state,null,limit);
    }

    @Override
    public int scoreNode(Node leaf) {

        return 0;
    }

    @Override
    public Move prune() {
        return null;
    }
}
