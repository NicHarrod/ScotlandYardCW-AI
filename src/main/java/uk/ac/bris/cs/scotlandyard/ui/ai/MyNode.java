package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MyNode implements Node {
    Node parent;
    public Board.GameState state;
    public Move move;
    public ArrayList<MyNode> children;
    Integer generation;
    Integer limit;
    NodeType type;
    Integer score;
    boolean Maxxing;

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
            //System.out.println(generation);
            this.children = MakeChildren();
        }
        if(generation%2==0){
            this.Maxxing = true;
        }
        else{
            this.Maxxing=false;
        }
    }
    @Override  public ArrayList<MyNode> MakeChildren() {
        this.children=new ArrayList<MyNode>();
        NodeType childType = NodeType.NORMAL;
        if (type == NodeType.LEAF){return children;}
        if (generation+1 == limit){childType = NodeType.LEAF;}
        if (!Maxxing) {
            for (Move m : state.getAvailableMoves()) {
                children.add(new MyNode(childType, this, state.advance(m), m, limit));
            }
        }
        if(Maxxing){
            Board.GameState currentState =this.state;
            for (Piece p : currentState.getPlayers()){
                DetectiveMoveScorer scorer = new DetectiveMoveScorer(
                        ImmutableSet.copyOf(getAvailibleMovesForPiece(p,currentState)),currentState);
                if (!p.isMrX()){
                    currentState = currentState.advance(scorer.bestMove());
                }
            }
            children.add(new MyNode(childType,this,currentState,null,limit));

        }
        return children;
    }
    private HashSet<Move> getAvailibleMovesForPiece (Piece piece, Board.GameState tempstate){
        HashSet<Move> moves = new HashSet<>();
        for (Move m : tempstate.getAvailableMoves()){
            if (m.commencedBy()==piece){
                moves.add(m);
            }
        }
        return moves;
    }
    @Override public Integer generation() {return generation;}
    @Override public Integer limit() {return limit;}

    public void scoreSelf(){
        if (this.type==NodeType.LEAF) {
            MoveScorer scorer;
            if (Maxxing) {
                scorer = new MrXMoveScorer(null, state);
            } else {
                scorer = new DetectiveMoveScorer(null, state);
            }
            this.score = scorer.scoreMove(this.move);
            return;
        }
        this.score =0;
        return;
    }
    @Override  public String toString() {
        if (this.parent==null){
            return ("root" + "\n children: \n" + this.children);
        }
        return (this.move.toString() + "\n children: " + this.children + "\n");
    }


}
