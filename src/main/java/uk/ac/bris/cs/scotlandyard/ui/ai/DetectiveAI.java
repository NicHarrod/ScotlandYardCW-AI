package uk.ac.bris.cs.scotlandyard.ui.ai;

import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DetectiveAI implements Ai {

    @Nonnull
    @Override public String name() { return "DETECTIVEBEST"; }
    private HashSet<Move> getAvailibleMovesForPiece (Piece piece, Board.GameState tempstate){
        HashSet<Move> moves = new HashSet<>();
        for (Move m : tempstate.getAvailableMoves()){
            if (m.commencedBy()==piece){
                moves.add(m);
            }
        }
        return moves;

    }
    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            Pair<Long, TimeUnit> timeoutPair) {
        board.
        return (new DetectiveMoveScorer(board.getAvailableMoves(), (Board.GameState) board).bestMove());
    }
}
