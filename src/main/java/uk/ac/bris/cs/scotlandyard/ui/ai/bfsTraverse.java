package uk.ac.bris.cs.scotlandyard.ui.ai;
import java.util.*;

import com.esotericsoftware.kryo.util.Null;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import javafx.css.Size;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Piece;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;


import javax.annotation.Nonnull;

public class bfsTraverse {
    public ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph;
    public ArrayList<Integer> path;
    public HashMap<Integer, Integer> prev;

    public bfsTraverse(@Nonnull ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Integer start, Integer end, ImmutableSet players){
        this.prev = solve(graph, start);

        this.path = PathFromEndToStart(start, end, this.prev);
    }

    public HashMap<Integer, Integer> solve (@Nonnull ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, Integer start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        HashMap<Integer, Boolean> visited = new HashMap<>(graph.nodes().size());
        visited.put(start, false);
        HashMap<Integer, Integer> prev = new HashMap<>(graph.nodes().size());
        for (Integer node : graph.nodes()) {
            visited.put(node, false);
        }
        visited.put(start, true);
        List<Integer> neighbours;
        while (!queue.isEmpty()) {
            Integer parent = queue.remove();
            neighbours =  graph.adjacentNodes(parent).stream().toList();
            for (Integer next : neighbours) {
                if (visited.get(next) == false) {
                    queue.add(next);
                    visited.put(next, true);
                    prev.put(next, parent);
                }
            }
        }
        return prev;
    }

    public ArrayList<Integer> PathFromEndToStart(Integer start,Integer end,HashMap<Integer, Integer> prev){
        ArrayList<Integer> path = new ArrayList<>();
        for (Integer at = end; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        System.out.print("path"+path);
        if (path.get(0) == start) {
            return path;
        }
        return null;
    }

}
