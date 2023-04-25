package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;

import java.util.HashSet;
import java.util.Set;

public class MyNode implements Node {
    Node parent;
    Board.GameState state;
    Move move;
    Set<Node> children;
    Integer generation;
    Integer limit;
    NodeType type;
    public MyNode(NodeType type, Node parent, Board.GameState state, Move move, Integer limit) {
        this.type=type;
        this.state=state;
        if (type==NodeType.ROOT){
            this.limit=limit;
            this.move=null;
            this.parent=null;
            this.generation=0;
            this.children=MakeChildren();
        }
        else {
            this.parent = parent;
            this.move = move;
            this.generation = parent.generation() +1;
            this.limit = parent.limit();
            System.out.println(generation);
            this.children = MakeChildren();
        }
    }
    @Override  public Set<Node> MakeChildren() {
        this.children=new HashSet<Node>();
        NodeType childType = NodeType.NORMAL;
        if (type == NodeType.LEAF){
            return children;
        }
        if (generation+1 == limit){
            childType = NodeType.LEAF;
        }
        for (Move m: state.getAvailableMoves()){
            children.add(new MyNode(childType,this,state.advance(m),m,limit));

        }
        return children;
    }
    @Override public Integer generation() {return generation;}
    @Override public Integer limit() {return limit;}
    @Override  public String toString() {
        if (this.parent==null){
            return ("root" + "\n children: \n" + this.children);
        }
        return (this.move.toString() + "\n children: \n" + this.children);
    }
}
