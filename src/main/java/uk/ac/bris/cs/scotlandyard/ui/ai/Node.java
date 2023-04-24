package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Node {
    Node parent;
    Board.GameState state;
    Move move;
    Set<Node> children;
    Integer generation;
    Integer limit;
    public  Node (Node parent,Move move){
        if (!(parent == null)) {

            this.parent = parent;
            this.move = move;
            this.state = parent.state.advance(move);
            this.generation = parent.generation + 1;
            this.limit = parent.limit;
            System.out.println(generation);
            this.children = MakeChildren();
        }
    };

     Set<Node> MakeChildren(){
         children = new HashSet<Node>();
         if (limit == generation){
             return children;
         }
         if (limit == generation+1) {
             for (Move m : state.getAvailableMoves()) {
                 children.add(new LeafNode(this, m));
             }
         }
         else {
             for (Move m : state.getAvailableMoves()) {
                 children.add(new NormalNode(this, m));
             }
         }
         return children;
    }

    @Override
    public String toString() {
         if (this.parent==null){
             return ("root" + "\n children: \n" + this.children);
         }
        return (this.move.toString() + "\n children: \n" + this.children);
    }
}
