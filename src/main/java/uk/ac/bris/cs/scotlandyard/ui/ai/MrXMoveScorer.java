package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import uk.ac.bris.cs.scotlandyard.model.*;

import javax.annotation.Nonnull;
import java.util.*;

public final class MrXMoveScorer implements MoveScorer{

    //using this to get the values for the desitnation from the diffferent possible moves (single or double)
    private final class ScoreMoveVisitor implements Move.Visitor<Integer>{

        public void ScoreMoveVisitor (){}
        @Override
        public Integer visit(Move.SingleMove move) {
            return move.destination;
        }

        @Override
        public Integer visit(Move.DoubleMove move) {
            return move.destination2;
        }
    }

    public Board.GameState state;
    public ImmutableSet<Move> availableMoves;
    public Integer mrXOrigin;
    public MrXMoveScorer (ImmutableSet<Move> moves, Board.GameState state){
        this.availableMoves=moves;
        this.state=state;
    }



    @Override
    public int scoreMove(@Nonnull Move move) {
        int dest = move.accept(new ScoreMoveVisitor());
        int moveTotal = numNodes(dest) + numDiffNodes(dest) + distToNextDetective(dest) + ticketVal(move);
        System.out.println("numNodes"+numNodes(dest));
        System.out.println("numDiffNodes"+numDiffNodes(dest));
        System.out.println("distToNextDetective"+distToNextDetective(dest));
        System.out.println("ticketVal"+ticketVal(move));
        return moveTotal;
    }

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
        Set<Integer> adjNodes= state.getSetup().graph.adjacentNodes(dest);
        Set<ScotlandYard.Transport> nodeOptions= new HashSet<>();
        for (int n : adjNodes){
            Optional<ImmutableSet<ScotlandYard.Transport>> transportSet = state.getSetup().graph.edgeValue(dest,n);
            nodeOptions.addAll(transportSet.get());
        }
        return nodeOptions.size();
    }

    @Override
    public HashMap<Piece,Integer> distToDetectives(@Nonnull int dest) {
        HashMap<Piece,Integer> distToDetects = new HashMap<>();
        for (Piece d : state.getPlayers()) {
            if (!d.isMrX()){
                bfsTraverse bfs = new bfsTraverse(state.getSetup().graph, dest, state.getDetectiveLocation((Piece.Detective) d).get());
                ArrayList<Integer> FromMrXpath = bfs.path;
                distToDetects.put(d, FromMrXpath.size());

            }
        }
        return distToDetects;
    }

    public Integer distToNextDetective(@Nonnull int dest) {
        HashMap<Piece,Integer> distToDetects = distToDetectives(dest);
        System.out.print(distToDetectives(dest));
        Integer distToClosest = 1000;
        for (Piece d : state.getPlayers()) {
            if (!d.isMrX()) {
                Integer dist = distToDetectives(dest).get(d);
                if (dist < distToClosest) {
                    distToClosest = dist;
                }
            }
        }
        return distToClosest-1;
    }
    @Override
    public int ticketVal(@Nonnull Move move) {
        //Train takes the furthest
        //would rather use a ticket that is more likely to have the most of
        //Double and secret are most valuable
        int totalVal = 0;
        for (ScotlandYard.Ticket t : move.tickets()){
            switch (t){
                case TAXI -> totalVal += 3;
                case BUS -> totalVal += 2;
                case UNDERGROUND -> totalVal += 1;
                case DOUBLE -> totalVal += -5;
                case SECRET -> totalVal += -4;

            }
        }
        return totalVal;
    }


}
