package uk.ac.bris.cs.scotlandyard.ui.ai;

public class NodeEvalPair {
    public MyNode node;
    public Double Eval;
    public NodeEvalPair(MyNode node,Double Eval){
        this.node=node;
        this.Eval=Eval;
    }
    public MyNode giveTopNode(MyNode node){
        if (node.generation==1){
            return node;
        }
        else return giveTopNode(node.parent);
    }

    @Override
    public String toString() {
        return "\nvalue "+Eval+"\n node: " + node ;
    }
}
