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
    public Double pruned;


    public MinMaxTree(Board.GameState state, Integer limit) {
        this.root = new MyNode(NodeType.ROOT,null,state,null,limit);
        this.Alpha = Double.NEGATIVE_INFINITY;
        this.Beta = Double.POSITIVE_INFINITY;
        this.pruned = prune(this.root.state, this.root.limit, this.root, this.root.Maxxing, this.Alpha, this.Beta);
    }

    @Override
    public Double scoreNode(MyNode leaf, MrXMoveScorer scorer) {
        return (double) scorer.scoreMove(leaf.move);
    }

    @Override
    public Double prune(Board.GameState state, Integer limit, MyNode node, Boolean Maxxing,Double alpha,Double beta) {
        MrXMoveScorer leafScorer = new MrXMoveScorer ( null,node.state);
        if (node.type==NodeType.LEAF || limit == 0) return scoreNode(node, leafScorer);

        if (Maxxing) {
            Double MaxEval = Double.NEGATIVE_INFINITY;
            for (MyNode c:node.children) {
                Double Eval = prune(state,limit -1, c, false, alpha, beta);
                MaxEval = Math.max(MaxEval, Eval);
                alpha = Math.max(alpha, Eval);
                if (alpha<beta) return MaxEval;
            }
            return MaxEval;
        }
        else {
            Double MiniEval = Double.POSITIVE_INFINITY;
            for (MyNode c : node.children) {
                Double Eval = prune(state,limit -1, c, true, alpha, beta);
                MiniEval = Math.min(MiniEval, Eval);
                beta = Math.min(beta, Eval);
                if (alpha<beta) return MiniEval;
            }
            return MiniEval;
        }
    }
}
