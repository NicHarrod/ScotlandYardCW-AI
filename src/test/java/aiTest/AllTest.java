package aiTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BfsTest.class,
        ScorerTest.class,
        TreeTest.class
})
public class AllTest {}
