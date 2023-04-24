package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.HashSet;

public class LeafNode extends Node {
    public LeafNode(Node parent, Move move) {
        super(parent, move);
        this.children = null;
    }
}
