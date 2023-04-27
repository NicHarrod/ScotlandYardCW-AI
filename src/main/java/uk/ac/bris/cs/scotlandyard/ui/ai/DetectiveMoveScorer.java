package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.LogEntry;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class DetectiveMoveScorer implements MoveScorer{
    Board.GameState state;
    ImmutableSet<Move> availableMoves;
    public DetectiveMoveScorer(ImmutableSet<Move> moves, Board.GameState state){
        this.availableMoves=moves;
        this.state=state;

    }
    @Override
    public int scoreMove (Move move){
        Integer dest = giveDest((Move.SingleMove) move);
        return numNodes(dest) + distToLastMrX((Move.SingleMove) move)*5;
    }
    private Integer giveDest (Move.SingleMove move){return move.destination;}

    @Override
    public Move bestMove() {
        HashMap<Move,Integer> moveMap = new HashMap<>();
        Map.Entry<Move,Integer> currentBest = null;
        for (Move m : availableMoves){
            moveMap.put(m,scoreMove(m));
        }

        for (Map.Entry<Move,Integer> mE : moveMap.entrySet()){
            if  (currentBest==null || mE.getValue() > currentBest.getValue()  ){
                currentBest=mE;
            }
        }
        return currentBest.getKey();
    }

    @Override
    public int numNodes(@Nonnull int dest) {
        return state.getSetup().graph.adjacentNodes(dest).size();
    }

    @Override
    public int numDiffNodes(@Nonnull int dest) {
        return 0;
    }

    @Override
    public int ticketVal(@Nonnull Move move) {
        return 0;
    }

    public Integer distToLastMrX (Move.SingleMove move){
        Integer lastSeen = getLastMrX();
        if (getLastMrX()==null) return 0;
        bfsTraverse traverser = new bfsTraverse(state.getSetup().graph,move.destination,lastSeen);
        return traverser.path.size();
    }

    public Integer getLastMrX (){
        for (LogEntry l : state.getMrXTravelLog().reverse()){
            if (l.location().isPresent()){
                return l.location().get();
            }
        }
        return null;
    }
}
