package aiTest;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.MoveScorer;
import uk.ac.bris.cs.scotlandyard.ui.ai.MrXMoveScorer;
import uk.ac.bris.cs.scotlandyard.ui.ai.OneMoveAI;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.bris.cs.scotlandyard.model.Piece.Detective.BLUE;
import static uk.ac.bris.cs.scotlandyard.model.Piece.MrX.MRX;
import static uk.ac.bris.cs.scotlandyard.model.ScotlandYard.defaultMrXTickets;

/**
 * tests the scorer class and if it returns the correct values along with the correct move
 */
public class ScorerTest extends ParameterisedModelTestBase {
    @Test public void moveScoredCorrectly(){
        var mrX = new Player(MRX, defaultMrXTickets(), 104);
        var blue = new Player(BLUE, makeTickets(11, 8, 4, 0, 0), 116);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(), mrX, blue);
        MoveScorer Scorer = new MrXMoveScorer(state.getAvailableMoves(),state);
        Assert.assertEquals(Scorer.scoreMove(new Move.SingleMove(MRX,104, ScotlandYard.Ticket.UNDERGROUND,88)),11);
    }
}
