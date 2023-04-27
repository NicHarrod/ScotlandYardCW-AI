package aiTest;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;
import uk.ac.bris.cs.scotlandyard.ui.ai.bfsTraverse;

public class BfsTest extends  ParameterisedModelTestBase{
        @Test public void PathFormsForLongest(){
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = standardGraph();
            bfsTraverse traverser = new bfsTraverse(graph,190,7);
            //route is: [190, 180, 153, 140, 89, 105, 72, 42, 7]
            Assert.assertEquals(traverser.path.size(),9);
        }

        @Test public void ReturnsValueWhenGivenItself(){
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = standardGraph();
            bfsTraverse traverser = new bfsTraverse(graph,190,190);
            //route is: [190]
            Assert.assertEquals(traverser.path.size(),1);
        }

        @Test public void SameRouteLengthBothWays(){
            ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph = standardGraph();
            bfsTraverse traverser1 = new bfsTraverse(graph,190,111);
            bfsTraverse traverser2 = new bfsTraverse(graph,111,190);
            Assert.assertEquals(traverser1.path.size(),traverser2.path.size());
        }


}
