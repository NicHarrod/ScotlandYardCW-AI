package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.*;
//package uk.ac.bris.cs.scotlandyard.ui.ai;

public class MinMaxTree {
    MyNode root;
    //public HashMap<Integer, Integer> treeMap;
    public MyNode bestNode;

    public MinMaxTree(Board.GameState state, Integer limit) {
        this.root = new MyNode(NodeType.ROOT, null, state, null, limit);
        //this.treeMap = new HashMap<>();
        NodeEvalPair result = minMax(root,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
        System.out.println("endnode" + result);
        System.out.println("result parent" + result.node.parent);
        System.out.println("result parent parent" + result.node.parent.parent);

        this.bestNode=result.giveTopNode(result.node);
        //this.bestNode = prune(this.root.state, this.root.limit, this.root, this.root.Maxxing, this.Alpha, this.Beta).node;

    }

    public int scoreNode(MyNode leaf, MoveScorer scorer) {
        //System.out.println("scored "+scorer.scoreMove(leaf.move));
        return scorer.scoreMove(leaf.move);
    }

    public NodeEvalPair minMax(MyNode node,Double alpha,Double beta){
        MoveScorer scorer = new MrXMoveScorer(null,node.state);
        if (node.limit==0 || node.type==NodeType.LEAF){
            return new NodeEvalPair(node, (double) scoreNode(node,scorer));
        }
        if(node.Maxxing){
            NodeEvalPair MaxEval =new NodeEvalPair(node.children.get(0),Double.NEGATIVE_INFINITY);
            for (MyNode c : node.children){
                NodeEvalPair eval = minMax(c,alpha, beta);
                if (eval.Eval>MaxEval.Eval) MaxEval=eval;
                alpha = Math.max(alpha, eval.Eval);
                if (beta <= alpha) return MaxEval;
            }
            return MaxEval;
        }
        else{
            NodeEvalPair MinEval =new NodeEvalPair(node.children.get(0),Double.POSITIVE_INFINITY);
            for (MyNode c : node.children){
                NodeEvalPair eval = minMax(c,alpha, beta);
                if (eval.Eval<MinEval.Eval) MinEval=eval;
                beta = Math.max(beta, eval.Eval);
                if (beta <= alpha) return MinEval;
            }
            return MinEval;
        }
    }
}
