package uk.ac.bris.cs.scotlandyard.ui.ai;


import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;


import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;

public class BestMoveAI implements Ai{



    @Nonnull @Override public String name() { return "BEST"; }

    @Nonnull @Override public Move pickMove(
            @Nonnull Board board,
            Pair<Long, TimeUnit> timeoutPair) {
        // returns a random move, replace with your own implementation
        var moves = board.getAvailableMoves();
        return new MrXMoveScorer(moves, (Board.GameState) board).bestMove();
    }
}
