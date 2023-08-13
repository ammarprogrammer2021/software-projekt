package solutions.exercise1;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise1.AbstractScenarioUtilTest;
import org.sopra.api.model.PowerLine;
import org.sopra.api.model.PowerLineType;
import org.sopra.api.model.consumer.Consumer;
import org.sopra.api.model.consumer.ControllableConsumer;
import org.sopra.api.model.producer.ControllableProducer;
import org.sopra.api.model.producer.Producer;
/**
 * This Class tests ScenarionUtilImpl
 * @author Adrian Bohnstedt, Ammar Benazza, Anton Herzog, Laurent Brune
 * @version 1.0
 */
public class ScenarioUtilTest extends AbstractScenarioUtilTest implements ExerciseSubmission {

	@Override
	@Test
	public void testGetPowerLinesByType() {
		List<PowerLine> highVoltageLines = sut.getPowerLinesByType(graph1, PowerLineType.HIGH_VOLTAGE);
		assertThat("Number of high voltage lines", highVoltageLines.size(), is(11));

		List<PowerLine> lowVoltageLines = sut.getPowerLinesByType(graph1, PowerLineType.LOW_VOLTAGE);
		assertThat("Number of low voltage lines", lowVoltageLines.size(), is(3));

		List<PowerLine> mediumVoltageLines = sut.getPowerLinesByType(graph1, PowerLineType.MEDIUM_VOLTAGE);
		assertThat("Number of medium voltage lines", mediumVoltageLines.size(), is(4));
	}

	@Override
	@Test
	public void testGetPowerLinesByType_Parameters() {
		try {
			sut.getPowerLinesByType(null, null);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected exception
			System.out.println("IllegalArgumentException caught in testGetPowerLinesByType_Parameters");
		}

		try {
			sut.getPowerLinesByType(null, PowerLineType.HIGH_VOLTAGE);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected exception
			System.out.println("IllegalArgumentException caught in testGetPowerLinesByType_Parameters");
		}

		try {
			sut.getPowerLinesByType(graph1, null);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected exception
			System.out.println("IllegalArgumentException caught in testGetPowerLinesByType_Parameters");
		}
	}

	@Override
	/** 
	 * Returns Team Identifier
	 * @return The Team Identifier
	 */
	public String getTeamIdentifier() {
		return "G05T01";
	}

	@Override
	@Test
	/**
	 * Tests if getConsumer() gives back the right amount of consumers
	 */
	public void testGetConsumers() {
		//Test if the amount of consumers is correct
		List<Consumer> consumerList = sut.getConsumers(graph1);
		//The number of consumers should be 8 as seen in the scenario1 xml file
		assertThat("Number of Consumers", consumerList.size(), is(8));
	}

	@Override
	@Test
	/**
	 * Tests getConsumers() if it throws an Exception for input null 
	 */
	public void testGetConsumers_Parameters() {
		try {
			sut.getConsumers(null);
			fail();
		}
		catch(IllegalArgumentException e){
			System.out.println("IllegalArgumentException in testGetConsumers_Parameters");
			
		}
		
	}

	@Override
	@Test
	/**
	 * Tests if getControllableConsumers() gives back the right amount of controllable consumers
	 */
	public void testGetControllableConsumers() {
		List<ControllableConsumer> controllableConsumerList = sut.getControllableConsumers(graph1);
		//The number of controllable consumers should be 3 as seen in the scenario1 xml file
		assertThat("Number of controllable Consumers", controllableConsumerList.size(), is(3));
		
	}

	@Override
	@Test
	/**
	 * Tests if getControllableConsumers() throws an Exception for input null
	 */
	public void testGetControllableConsumers_Parameters() {
		try {
			sut.getControllableConsumers(null);
			fail();
		}
		catch(IllegalArgumentException e){
			System.out.println("IllegalArgumentException caught in testGetControllableConsumers_Parameters");
		}
		
	}

	@Override
	@Test
	/**
	 * Tests if getControllableProducers() gives back the right amount of controllable producers
	 */
	public void testGetControllableProducers() {
		List<ControllableProducer> controllableProducerList = sut.getControllableProducers(graph1);
		//The number of controllable producers should be 4 as seen in the scenario1 xml file
		assertThat("Number of controllable Producers", controllableProducerList.size(), is(4));
		
	}

	@Override
	@Test
	/**
	 * Tests getControllableProducers() if it throws an Exception for input null
	 */
	public void testGetControllableProducers_Parameters() {
		try {
			sut.getControllableProducers(null);
			fail();
		}
		catch(IllegalArgumentException e){
			System.out.println("IllegalArgumentException caught in testGetControllableProducers_Parameters");
		}
		
	}

	@Override
	@Test
	/**
	 * Tests if getProducers() gives back the right amount of producers
	 */
	public void testGetProducers() {
		List<Producer> producerList = sut.getProducers(graph1);
		//The number of producers should be 10 as seen in the scenario1 xml file
		assertThat("Number of Producers", producerList.size(), is(10));
		
	}

	@Override
	@Test
	/**
	 * Tests getProducers() if it throws an Exception for input null
	 */
	public void testGetProducers_Parameters() {
		try {
			sut.getProducers(null);
			fail();
		}
		catch(IllegalArgumentException e){
			System.out.println("IllegalArgumentException in testGetProducers_Parameters");
			
		}
		
	}

}
