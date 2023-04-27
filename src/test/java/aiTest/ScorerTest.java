package aiTest;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.ui.ai.DetectiveMoveScorer;
import uk.ac.bris.cs.scotlandyard.ui.ai.MoveScorer;
import uk.ac.bris.cs.scotlandyard.ui.ai.MrXMoveScorer;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.bris.cs.scotlandyard.model.Piece.Detective.BLUE;
import static uk.ac.bris.cs.scotlandyard.model.Piece.MrX.MRX;
import static uk.ac.bris.cs.scotlandyard.model.ScotlandYard.defaultMrXTickets;

/**
 * tests the scorer class and if it returns the correct values along with the correct move
 */
public class ScorerTest extends ParameterisedModelTestBase {
    @Test
    public void mrXMoveScoredCorrectly() {
        var mrX = new Player(MRX, defaultMrXTickets(), 104);
        var blue = new Player(BLUE, makeTickets(11, 8, 4, 0, 0), 118);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(), mrX, blue);
        MoveScorer Scorer = new MrXMoveScorer(state.getAvailableMoves(), state);
        Assert.assertEquals(Scorer.scoreMove(new Move.SingleMove(MRX, 104, ScotlandYard.Ticket.TAXI, 116)), 19);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveNotScoredIfInvalid() {
        var mrX = new Player(MRX, defaultMrXTickets(), 104);
        var blue = new Player(BLUE, makeTickets(11, 8, 4, 0, 0), 118);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(), mrX, blue);
        MoveScorer Scorer = new MrXMoveScorer(state.getAvailableMoves(), state);
        Scorer.scoreMove(new Move.SingleMove(MRX, 95, ScotlandYard.Ticket.TAXI, 116));
    }

    @Test
    public void detectiveMoveScoredCorrectly() {
        var mrX = new Player(MRX, defaultMrXTickets(), 104);
        var blue = new Player(BLUE, makeTickets(11, 8, 4, 0, 0), 118);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(), mrX, blue);
        MoveScorer Scorer = new DetectiveMoveScorer(state.getAvailableMoves(), state);
        Assert.assertEquals(Scorer.scoreMove(new Move.SingleMove(BLUE,118, ScotlandYard.Ticket.BUS,142)), 9);
    }

}