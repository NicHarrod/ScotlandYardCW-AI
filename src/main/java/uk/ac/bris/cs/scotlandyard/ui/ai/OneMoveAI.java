package uk.ac.bris.cs.scotlandyard.ui.ai;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;


import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
public class OneMoveAI implements Ai{

    private final class MyMoveScorer implements MoveScorer{

        @Override
        public int scoreMove(@Nonnull Move move) {
            return 0;
        }

        @Override
        public Move bestMove() {
            return null;
        }



    }

    @Nonnull @Override public String name() { return "RANDOM"; }

    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            Pair<Long, TimeUnit> timeoutPair) {
        // returns a random move, replace with your own implementation
        var moves = board.getAvailableMoves().asList();
        return moves.get(new Random().nextInt(moves.size()));
    }
}
