package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

public class RootNode extends Node{

    public RootNode(Board.GameState state, Integer limit) {
        super(null,null);
        this.limit=limit;
        this.state=state;
        this.move=null;
        this.parent=null;
        this.generation=0;
        this.children=MakeChildren();

    }
}
