package uk.ac.bris.cs.scotlandyard.ui.ai;

import uk.ac.bris.cs.scotlandyard.model.Board;

import java.util.*;
//package uk.ac.bris.cs.scotlandyard.ui.ai;

public class MinMaxTree {
    MyNode root;
    Double Alpha, Beta;
    public int pruned;
    Double MaxEval;
    Double MiniEval;
    public HashMap<Integer, Integer> treeMap;
    MyNode bestNode;




    public MinMaxTree(Board.GameState state, Integer limit) {
        this.root = new MyNode(NodeType.ROOT,null,state,null,limit);
        this.Alpha = Double.NEGATIVE_INFINITY;
        this.Beta = Double.POSITIVE_INFINITY;
        this.treeMap = new HashMap<>();
        assignGreatParent();
        this.pruned = prune (this.root.state, this.root.limit, this.root, this.root.Maxxing, this.Alpha, this.Beta, -1);
        this.MaxEval = Double.NEGATIVE_INFINITY;
        this.MiniEval = Double.POSITIVE_INFINITY;
        this.Alpha = Double.NEGATIVE_INFINITY;
        this.Beta = Double.POSITIVE_INFINITY;
        this.bestNode=chooseBest();



    }
    public MyNode chooseBest(){
        Map.Entry<Integer,Integer> currentBest=null;
        System.out.println("map"+treeMap);
        for (Map.Entry<Integer,Integer> mE : treeMap.entrySet()){
            //System.out.println(mE);
            if(currentBest==null|| mE.getKey()>currentBest.getKey()) currentBest=mE;
        }
        System.out.println(currentBest.getValue());
        System.out.println(currentBest);
        return root.children.get(currentBest.getValue() );
    }


    public int scoreNode(MyNode leaf, MrXMoveScorer scorer) {
        //System.out.println("scored "+scorer.scoreMove(leaf.move));
        return scorer.scoreMove(leaf.move);
    }
    public void assignGreatParent(){
        for (MyNode n : root.children){
            n.Greatparent=root.children.indexOf(n);
        }
    }



    public int prune(Board.GameState state, Integer limit, MyNode node, Boolean Maxxing,Double alpha,Double beta,  Integer greatparent) {
        MrXMoveScorer leafScorer = new MrXMoveScorer ( null,node.state);
        //System.out.println("greatpeatnet"+node.Greatparent);
        if (node.type==NodeType.LEAF || limit == 0) {
            this.treeMap.put(scoreNode(node, leafScorer), node.Greatparent);
            //System.out.println("term"+scoreNode(node, leafScorer));
            return scoreNode(node, leafScorer);}
        if (node.Greatparent != -1) {
            node.Greatparent = greatparent;
        }
        int i=0;
        if (node.Maxxing == true) {
            int MaxEval = Integer.MIN_VALUE;
            for (MyNode c:node.children) {
                if (node.type==NodeType.MIDDLE) {
                    c.Greatparent = node.children.indexOf(c);
                    //System.out.println("middleparent max"+ node.children.get(0).children);

                }

                i++;

                int Eval = prune(state,limit -1, c, false, alpha, beta, c.Greatparent );
                MaxEval = Math.max(MaxEval, Eval);
                alpha = Math.max(alpha, Eval);
                //if (alpha<beta) return MaxEval;
            }
            return MaxEval;
        }
        else {

            int MiniEval = Integer.MAX_VALUE;
            for (MyNode c : node.children) {
                //System.out.println("min "+node.children.indexOf(c));
                if (node.type==NodeType.MIDDLE) {
                    c.Greatparent = node.children.indexOf(c);
                    //System.out.println("middleparent mini"+ node.children.get(0).children);
                }


                int Eval = prune(state,limit -1, c, true, alpha, beta,c.Greatparent);
                MiniEval = Math.min(MiniEval, Eval);
                beta = Math.min(beta, Eval);
                //if (alpha<beta) return MiniEval;

            }
            return MiniEval;
        }
    }
}
/*
@Override
public HashMap<MyNode, Double> prune(Board.GameState state, Integer limit, MyNode node,
                                     Boolean Maxxing, Double alpha, Double beta, Integer greatparent,HashMap<MyNode, Double> NodeVal ) {
    //if (node == this.root) firstNodes.addAll(this.root.children);
    if (node.Greatparent != -1) {
        node.Greatparent = greatparent;
        NodeVal.put(node, Double.NEGATIVE_INFINITY);
    }
    //MrXMoveScorer XleafScorer = new MrXMoveScorer ( null,node.state);
    //DetectiveMoveScorer DleafScorer = new DetectiveMoveScorer(null, node.state);
    if (node.type==NodeType.LEAF || limit == node.generation) {
        if (Maxxing) this.MaxEval= node.scoreSelf();
        else this.MiniEval= node.scoreSelf();
        return NodeVal;
    }

    int i= 0;
    if (Maxxing) {
        for (MyNode c:node.children) {
            if (node.Greatparent == -1) c.Greatparent = i;
            //Double Eval = node.scoreSelf();
            NodeVal.put(node, node.scoreSelf());
            NodeVal.putAll(prune(state,limit -1, c, false, alpha, beta,c.Greatparent, NodeVal));
            this.MaxEval = Math.max(MaxEval, Eval);
                alpha = Math.max(alpha, Eval);
                //chosenNode = c;
                if (alpha<beta) return NodeVal;
            i++;
        }
    }
    else {
        for (MyNode c : node.children) {
            if (node.Greatparent == -1) c.Greatparent = i;
            Double Eval = node.scoreSelf();
            NodeVal.put(node, node.scoreSelf());
            NodeVal.putAll(prune(state,limit -1, c, false, alpha, beta,c.Greatparent, NodeVal));
            //Double Eval = scoreNode (prune(state,limit -1, c, true, alpha, beta, c.Greatparent);
            this.MiniEval = Math.min(MiniEval, Eval);
            //chosenNode = c;
            beta = Math.min(beta, Eval);
            if (alpha<beta) return NodeVal;
            i++;
        }
    }
    return NodeVal;
}*/
