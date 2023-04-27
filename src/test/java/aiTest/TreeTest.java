package aiTest;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Player;
import uk.ac.bris.cs.scotlandyard.ui.ai.MinMaxTree;
import uk.ac.bris.cs.scotlandyard.ui.ai.MrXMoveScorer;
import uk.ac.bris.cs.scotlandyard.ui.ai.MyNode;
import uk.ac.bris.cs.scotlandyard.ui.ai.NodeType;

import java.util.Random;

import static uk.ac.bris.cs.scotlandyard.model.Piece.Detective.*;
import static uk.ac.bris.cs.scotlandyard.model.Piece.MrX.MRX;
import static uk.ac.bris.cs.scotlandyard.model.ScotlandYard.defaultDetectiveTickets;
import static uk.ac.bris.cs.scotlandyard.model.ScotlandYard.defaultMrXTickets;

public class TreeTest extends ParameterisedModelTestBase{

    @Test public void nodeCreationTest(){
        var mrX = new Player(MRX, defaultMrXTickets(), 106);
        var red = new Player(RED, defaultDetectiveTickets(), 100);
        var blue = new Player(BLUE,defaultDetectiveTickets(), 62);

        Board.GameState state = gameStateFactory.build(standard24MoveSetup(),
                mrX, red,blue);
        MyNode root = new MyNode(NodeType.ROOT,null,state,null,2);
        Assert.assertEquals(root.children.get(0).children.size(),1);
    }
    @Test public void scoreSelfOnlyWorksOnLeaf(){
        var mrX = new Player(MRX, defaultMrXTickets(), 106);
        var red = new Player(RED, defaultDetectiveTickets(), 100);
        var blue = new Player(BLUE,defaultDetectiveTickets(), 62);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(),
                mrX, red,blue);
        MyNode root = new MyNode(NodeType.ROOT,null,state,null,2);
        Assert.assertEquals(root.children.get(0).scoreSelf(),0);
    }



    @Test public void minMaxPruneTest(){
        var mrX = new Player(MRX, makeTickets(10,10,10,10,0), 116);
        var red = new Player(RED, defaultDetectiveTickets(), 3);
        var blue = new Player(BLUE,defaultDetectiveTickets(), 62);
        var green = new Player(GREEN,defaultDetectiveTickets(), 4);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(),
                mrX, red,green,blue);
        MinMaxTree testTree = new MinMaxTree(state,3);
        MyNode chosenNode = testTree.bestNode;
        MrXMoveScorer scorer = new MrXMoveScorer(null,chosenNode.state());
        System.out.println(state.getAvailableMoves());
        System.out.print(testTree.bestNode.move);
        System.out.println("PRUNED\n" + chosenNode.move + " generation " + chosenNode.generation() + " score " + scorer.scoreMove(chosenNode.move));
    }
    /*@Test public void playoutTest(){
        var mrX = new Player(MRX, defaultMrXTickets(), 116);
        var red = new Player(RED, defaultDetectiveTickets(), 3);
        Board.GameState state = gameStateFactory.build(standard24MoveSetup(),
                mrX, red);
        while (true){
            MinMaxTree testTree = new MinMaxTree(state,3);
            MyNode chosenNode = testTree.bestNode;
            state = state.advance(chosenNode.move);
            var moves=state.getAvailableMoves();
            state = state.advance(new Random().nextInt(moves.size()));

        }
    }*/



}
