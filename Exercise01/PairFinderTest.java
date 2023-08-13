package solutions.exercise1;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise1.AbstractPairFinderTest;
import org.sopra.api.model.consumer.Consumer;
import org.sopra.api.model.producer.Producer;
import static org.junit.Assert.assertEquals;

/**
 * This Class tests the PairFindeImpl Class
 * @author Adrian Bohnstedt, Ammar Benazza, Anton Herzog, Laurent Brune
 * @version 1.0
 *
 */
public class PairFinderTest extends AbstractPairFinderTest implements ExerciseSubmission{
	public PairFinderImpl sut;
	/**
	 * 
	 */
	@Before
	public void setup() {
		sut = new PairFinderImpl();
	}
	@Test
	public void testFindConsumerProducerPairs() {
		setup();
		Map<Consumer, Producer> map = sut.findConsumerProducerPairs(graph1);
		System.out.println(map);
		assertEquals(map.size(), 7);
	}

	@Override
	/**
	 * Returns Team Identifier
	 * @Return The Team Identifier
	 */
	public String getTeamIdentifier() {
		return "G05T01";
	}

}

