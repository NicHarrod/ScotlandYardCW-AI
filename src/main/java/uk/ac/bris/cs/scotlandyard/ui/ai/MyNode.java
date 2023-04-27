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

/**
 * A class that implments the node interface
 * some attributes are public in order for the tester to have access to them
 */
public class MyNode implements Node {
    public MyNode parent;
    Board.GameState state;
    public Move move;
    public ArrayList<MyNode> children;
    Integer generation;
    Integer limit;
    NodeType type;
    boolean Maxxing;


    /**
     * The constructor for the node
     * Children - the children of the node, created in MakeChildren(),
     * Maxxing - flag used to indicate if it's maxxing or minning,
     * generation - the current generation of the node, used to determine it's level in the tree,
     * @param type - the nodetype from enums,
     * @param parent - the parent of node, handed to the nodes in makechildren using this,
     * @param state - the stored state that the node creates,
     * @param move - the move that gets the node to that point,
     * @param limit - the max depth allowed for the node
     */
    public MyNode(NodeType type, MyNode parent, Board.GameState state, Move move, Integer limit) {
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
            this.children = MakeChildren();

        }
        if(generation % 2==0) this.Maxxing = true;
        else this.Maxxing=false;
    }
    @Override  public ArrayList<MyNode> MakeChildren() {
        this.children=new ArrayList<MyNode>();
        NodeType childType = NodeType.NORMAL;
        if (type == NodeType.LEAF){return children;}
        if (generation+1 == limit){childType = NodeType.LEAF;}
        if (generation==0){childType=NodeType.MIDDLE;}
        if (generation==0 || generation%2==0) {
            for (Move m : state.getAvailableMoves()) {
                children.add(new MyNode(childType, this, state.advance(m), m, limit));
            }
        }
        else{
            Board.GameState currentState = this.state;
            ImmutableSet<Piece> pieces= currentState.getPlayers();
            for (Piece p : pieces){
                if (!p.isMrX()){
                    DetectiveMoveScorer scorer = new DetectiveMoveScorer(

                            ImmutableSet.copyOf(getAvailibleMovesForPiece(p,currentState)),currentState);
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

    /**
     * a function for a node to score itself, only runs on leaf nodes
     * @return the score of the node
     */
    public int scoreSelf(){
        if (this.type==NodeType.LEAF) {
            MoveScorer scorer;
            if (generation==0 || generation%2==0) {
                scorer = new MrXMoveScorer(null, state);
            } else {
                scorer = new DetectiveMoveScorer(null, state);
            }
            return scorer.scoreMove(this.move);
        }
        return 0;}

    @Override  public String toString() {
        if (this.parent==null){
            return ("root" + "\n children: \n" + this.children);
        }
        if (this.move==null){
            return "detective move \n children: " + this.children + "\n";
        }
        return (this.move.toString() + "\n children: " + this.children + "\n");
    }

    @Override public Integer generation() {return generation;}
    @Override public Integer limit() {return limit;}
    public Board.GameState state() {return state;}
}
