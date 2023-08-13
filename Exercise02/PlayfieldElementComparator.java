package solutions.exercise2;

import java.util.Comparator;

import org.sopra.api.ConstructionCostCalculator;
import org.sopra.api.ConstructionCostCalculatorImpl;
import org.sopra.api.Scenario;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.model.PlayfieldElement;
import org.sopra.api.model.producer.ProducerType;
/**
 * Compares Playfield Elements considering which are better (cheaper) to build on
 * @author Anton Herzog, Adrian Bohnstedt, Laurent Brune, Ammar Benazza
 * @version 1.0
 */
public class PlayfieldElementComparator implements Comparator<PlayfieldElement>, ExerciseSubmission{

	private ProducerType producertype;
	private ConstructionCostCalculator ccc;
	/**
	 * Creates new Instance of ConstructionCostCalculatorImpl and saves Object variables
	 * @param ccc Construction Cost Calculator
	 * @param producerType Producer Type
	 * @param scenario Scenario
	 */
	public PlayfieldElementComparator(ProducerType producertype, Scenario scenario) {
		
		if(producertype==null || scenario==null) {
			throw new NullPointerException("producertype and scenario can't be null!");
		}
		
		ConstructionCostCalculatorImpl ccc = new ConstructionCostCalculatorImpl(scenario);
		this.ccc = ccc;
		
		this.producertype = producertype;
		
	}
	/**
	 * Compares to PlayFieldElements. The Playfield with higher cost is ranked lower.
	 * 
	 * @param e1 first PlayfieldElement
	 * @param e2 second PlayfieldElement
	 * @return returns int bigger 0 if its ranked higher or int smaller 0 if its ranked lower or 0 for the same rank
	 */
	@Override
	public int compare(PlayfieldElement e1, PlayfieldElement e2) {
		
		if(e1 == null | e2 == null) {
			throw new NullPointerException("PlayfieldElement e1 and e2 can't be null!");
		}
		
		//cce = ConstructionCostElement
		double cce1 = ccc.calculateCost(e1, producertype);
		double cce2 = ccc.calculateCost(e2, producertype);
		
		return ((int) (cce2-cce1));
	}
	/**
	 * Gets the Team Identifier
	 * @return returns Team Identifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}

}
