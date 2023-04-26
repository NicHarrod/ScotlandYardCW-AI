package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.esotericsoftware.kryo.util.Null;
import com.google.common.collect.ImmutableSet;
import org.commonmark.node.Visitor;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;

import java.util.HashSet;
import java.util.Set;

public class MinMaxTree implements Tree {
    MyNode root;
    Double Alpha, Beta;
    public MyNode pruned;


    public MinMaxTree(Board.GameState state, Integer limit) {
        this.root = new MyNode(NodeType.ROOT,null,state,null,limit);
        this.Alpha = Double.NEGATIVE_INFINITY;
        this.Beta = Double.POSITIVE_INFINITY;
        this.pruned = prune(this.root.state, this.root.limit, this.root, this.root.Maxxing, this.Alpha, this.Beta);
    }

    @Override
    public Double scoreNode(MyNode leaf, MoveScorer scorer) {
        return (double) scorer.scoreMove(leaf.move);
    }

    @Override
    public MyNode prune(Board.GameState state, Integer limit, MyNode node, Boolean Maxxing,Double alpha,Double beta) {
        MrXMoveScorer leafScorer = new MrXMoveScorer ( null,node.state);
        if (node.type==NodeType.LEAF || limit == node.generation) return node;

        if (Maxxing) {
            Double MaxEval = Double.NEGATIVE_INFINITY;
            MyNode chosenNode = node.children.get(0);
            for (MyNode c:node.children) {
                Double Eval = scoreNode (prune(state,limit -1, c, false, alpha, beta),leafScorer);
                //MaxEval = Math.max(MaxEval, Eval);
                if (MaxEval<Eval){
                    chosenNode = c;
                    System.out.println(chosenNode.move);
                    if (alpha<beta) return c;
                }
                alpha = Math.max(alpha, Eval);
                //if (alpha<beta) return MaxEval;
            }
            return chosenNode;
        }
        else {
            Double MiniEval = Double.POSITIVE_INFINITY;
            MyNode chosenNode = node.children.get(0);
            for (MyNode c : node.children) {
                Double Eval = scoreNode (prune(state,limit -1, c, false, alpha, beta),leafScorer);
                //MiniEval = Math.min(MiniEval, Eval);
                if (MiniEval>Eval){
                    chosenNode = c;
                    System.out.println(chosenNode.move);
                    if (alpha<beta) return c;
                }
                beta = Math.min(beta, Eval);
                //if (alpha<beta) return MiniEval;
            }
            return chosenNode;
        }
    }
}
