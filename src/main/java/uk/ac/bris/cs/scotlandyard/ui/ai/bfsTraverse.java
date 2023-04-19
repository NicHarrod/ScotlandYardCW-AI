package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import javax.annotation.Nonnull;

public class bfsTraverse {
    public ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;

    public bfsTraverse(@Nonnull ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph){
        this.graph=graph;


    }

    public int findNextDetective (int Source){
        return 0;
    }
}
