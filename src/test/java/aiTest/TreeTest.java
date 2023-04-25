package aiTest;

import org.junit.Test;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.MyNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.NodeType;

import static uk.ac.bris.cs.scotlandyard.model.Piece.Detective.*;
import static uk.ac.bris.cs.scotlandyard.model.Piece.MrX.MRX;
import static uk.ac.bris.cs.scotlandyard.model.ScotlandYard.defaultDetectiveTickets;
import static uk.ac.bris.cs.scotlandyard.model.ScotlandYard.defaultMrXTickets;

public class TreeTest extends ParameterisedModelTestBase{

    @Test public void nodeCreationTest(){
        var mrX = new Player(MRX, defaultMrXTickets(), 106);
        var red = new Player(RED, defaultDetectiveTickets(), 91);

        Board.GameState state = gameStateFactory.build(standard24MoveSetup(),
                mrX, red);

        MyNode root = new MyNode(NodeType.ROOT,null,state,null,2);

        System.out.print(root);
    }

}
