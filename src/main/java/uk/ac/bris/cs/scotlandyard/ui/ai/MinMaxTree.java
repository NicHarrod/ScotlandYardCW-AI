package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.esotericsoftware.kryo.util.Null;
import org.commonmark.node.Visitor;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;

public class MinMaxTree implements Tree {
    MyNode root;
    Double Alpha, Beta;


    public void MinMaxTree(Board.GameState state, Integer limit) {
        this.root = new MyNode(NodeType.ROOT,null,state,null,limit);
        this.Alpha = Double.NEGATIVE_INFINITY;
        this.Beta = Double.POSITIVE_INFINITY;
        Double prune = prune(this.root.state, this.root.limit, this.root, this.root.Maxxing, this.Alpha, this.Beta);
    }

    @Override
    public Double scoreNode(Node leaf) {

        return 0.0;
    }

    @Override
    public Double prune(Board.GameState state, Integer limit, MyNode node, Boolean Maxxing,Double alpha,Double beta) {
        if (node.children.isEmpty() || limit == 0) return scoreNode(node);

        if (Maxxing == true) {
            Double MaxEval = Double.NEGATIVE_INFINITY;
            int child = 0;
            while (child < node.children.size() && alpha<beta){
                child++;
                Double Eval = prune(state,limit -1, node.children.get(child), false, alpha, beta);
                MaxEval = Math.max(MaxEval, Eval);
                alpha = Math.max(alpha, Eval);
            }
            return MaxEval;
        }
        else {
            Double MiniEval = Double.POSITIVE_INFINITY;
            int child = 0;
            while (child < node.children.size() && alpha<beta){
                child++;
                Double Eval = prune(state,limit -1, node.children.get(child), true, alpha, beta);
                MiniEval = Math.max(MiniEval, Eval);
                alpha = Math.max(alpha, Eval);
            }
            return MiniEval;
        }
    }
}
