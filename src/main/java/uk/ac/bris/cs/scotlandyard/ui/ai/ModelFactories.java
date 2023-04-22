package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableList;
import uk.ac.bris.cs.scotlandyard.model.Board.GameState;
import uk.ac.bris.cs.scotlandyard.model.Model;
import uk.ac.bris.cs.scotlandyard.model.MyGameStateFactory;
import uk.ac.bris.cs.scotlandyard.model.MyModelFactory;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard.Factory;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.function.Supplier;

/**
 * Not part of the coursework, required for the tests to find your factory implementations.
 */
public class ModelFactories {

	/**
	 * @return factories that will be used throughout the parameterised tests.
	 */
	public static ImmutableList<
			Entry<
					Supplier<Factory<GameState>>,
					Supplier<Factory<Model>>
					>
			> factories() {
		return ImmutableList.of(
				new SimpleImmutableEntry<>(MyGameStateFactory::new, MyModelFactory::new));
	}


}
