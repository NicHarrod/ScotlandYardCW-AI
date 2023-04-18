package uk.ac.bris.cs.scotlandyard.ui.ai;


import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;


import com.google.common.collect.ImmutableList;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
public class OneMoveAI implements Ai{

    private final class MyMoveScorer implements MoveScorer{

        public ImmutableList<Move> availableMoves;
        public MyMoveScorer (ImmutableList<Move> moves){
            this.availableMoves=moves;
        }
        @Override
        public int scoreMove(@Nonnull Move move) {
            return 0;
        }

        @Override
        public Move bestMove() {
            return null;
        }

        @Override
        public int numNodes(@Nonnull Move move) {
            return 0;
        }

        @Override
        public int numDiffNodes(@Nonnull Move move) {
            return 0;
        }

        @Override
        public int distToNextDetective(@Nonnull Move move) {
            return 0;
        }

        @Override
        public int ticketVal(@Nonnull Move move) {
            return 0;
        }


    }

    @Nonnull @Override public String name() { return "RANDOM"; }

    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            Pair<Long, TimeUnit> timeoutPair) {
        // returns a random move, replace with your own implementation
        var moves = board.getAvailableMoves().asList();
        return new MyMoveScorer(moves).bestMove();
    }
}
