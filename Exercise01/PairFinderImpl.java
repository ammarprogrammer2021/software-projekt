package solutions.exercise1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sopra.api.model.consumer.Consumer;
import org.sopra.api.model.producer.Producer;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise1.AbstractPairFinder;
import org.sopra.api.exercises.exercise1.PairFinder;
import org.sopra.api.model.EnergyNode;
import org.sopra.api.model.Graph;
import org.sopra.api.model.PowerLine;

/**
 * 	This class provides a method to make pairs of consumers and their nearest producer.
 * 
 *	@author  Softwarepraktikum
 *	@version 1.5.2
 */
public class PairFinderImpl extends AbstractPairFinder implements ExerciseSubmission, PairFinder {

/**
 * Returns pairs of all existing consumers and the nearest producer that is not already assigned to another consumer.
 *
 * @param	graph  a graph object which contains all nodes and edges of a scenario
 * @return	map of all consumers and their nearest not already assigned producer
 */
	public Map<Consumer, Producer> findConsumerProducerPairs(Graph<EnergyNode, PowerLine> graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Parameter is not allowed to be null.");
		}
		
		List<Consumer> consumers = this.util.getConsumers(graph);		// List of all consumers
		List<Producer> assigned = new ArrayList<>();					// List of all already assigned producers
		List<Consumer> orderedConsumers = new ArrayList<>();			// List of consumers in correct order
		Map<Consumer, Producer> consumerProducerPair = new HashMap<>();	// Map of consumers and producers
		
		for (int y=0;y<9;y++) { //starts from 0
			for(int x=0;x<9;x++) { //starts form 0
				if(getNodeAtPosition(graph, x, y) != null) { //null check
					
					if (consumers.contains(getNodeAtPosition(graph, x, y))) {
						orderedConsumers.add((Consumer) getNodeAtPosition(graph, x, y));
					}
				}
			}
		}
		
		for (Consumer consumer : orderedConsumers) {
			consumerProducerPair.put(consumer, findNextProducer(graph, assigned, consumer));
			assigned.add(consumerProducerPair.get(consumer));
		}
		return consumerProducerPair;
	}

	
	/**
	 * Returns the nearest Producer to the EnergyNode start. <br>
	 * The method searches for the Producer in circles around the EnergyNode start. It starts at the PlayFieldElement diagonally 
	 * left below the start and then searches in a clockwise circle around start in bigger circles each round. Stops when finding a producer or after 9 circles.<p> 
	 *
	 * @param  graph  a graph object which contains all nodes and edges of a scenario
	 * @param  assigned	a list of all Producers that are already assigned to consumers
	 * @param  start  the EnergyNode around which the closest Producer should be found
	 * @return        the nearest Producer to start or null if there are no unassigned Producers in the graph.
	 */	
	private Producer findNextProducer(Graph<EnergyNode, PowerLine> graph, List<Producer> assigned, EnergyNode start) {
		if (graph == null || assigned == null || start == null) {
			throw new IllegalArgumentException("Parameter is not allowed to be null.");
		}

		int currentX = start.getXPos() - 1; //-1 instead of +1
		int currentY = start.getYPos() + 1;

		for (int round = 0; round < 9; round++) {
			// up
			for (int i = 1; i <= 2 * round; i++) {
				currentY = currentY + 1;
				if(getNodeAtPosition(graph, currentX, currentY) != null) { // null check
					if (isProducerAtPosition(graph, assigned, currentX, currentY)) {
						return (Producer) getNodeAtPosition(graph, currentX, currentY);
					}
				}
			}
			// right
			for (int i = 1; i <= 2 * round; i++) {
				currentX = currentX + 1; //sollte +1 sein
				if(getNodeAtPosition(graph, currentX, currentY) != null) { // null check
					if (isProducerAtPosition(graph, assigned, currentX, currentY)) {
						return (Producer) getNodeAtPosition(graph, currentX, currentY);
					}
				}
			}
			// down
			for (int i = 1; i <= 2 * round; i++) {
				currentY = currentY - 1;
				if(getNodeAtPosition(graph, currentX, currentY) != null) { // null check
					if (isProducerAtPosition(graph, assigned, currentX, currentY)) {
						return (Producer) getNodeAtPosition(graph, currentX, currentY);
					}
				}
			}
			// left
			for (int i = 1; i <= 2 * round; i++) { //2 instead of 1
				currentX = currentX - 1;
				if(getNodeAtPosition(graph, currentX, currentY) != null) { // null check
					if (isProducerAtPosition(graph, assigned, currentX, currentY)) {
						return (Producer) getNodeAtPosition(graph, currentX, currentY);
					}
				}
			}
			// new round
			currentX = currentX - 1; // - instead of +
			currentY = currentY + 1;
		}
		return null;
	}
	

	/**
	 * Checks whether there is an unassigned Producer at a specified position.
	 *
	 * @param  graph  a graph object which contains all nodes and edges of a scenario
	 * @param  assigned	a list of all Producers that are already assigned to consumers
	 * @param  x	coordinate of the position that should be checked
	 * @param  y	coordinate of the position that should be checked
	 * @return        true if there is an unassigned Producer at the position or false if not
	 */	
	private Boolean isProducerAtPosition(Graph<EnergyNode, PowerLine> graph, List<Producer> assigned, int x, int y) {
		if (graph == null || assigned == null) {
			throw new IllegalArgumentException("Parameter is not allowed to be null.");
		}

		if (x < 0 || y < 0 || x > 9 || y > 9) { //  x<0 y<0 instead of x<1 y<1  and x> 9 y>9 instead of >=9
			return false;
		}

		List<Producer> producers = this.util.getProducers(graph);

		for (EnergyNode node : graph.getNodes()) {
			if (node.getXPos() == x && node.getYPos() == y && producers.contains(node) && !assigned.contains(node)) { // and not assigned instead of  or assigned
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Checks whether there is an unassigned Producer at a specified position.
	 *
	 * @param  graph  a graph object which contains all nodes and edges of a scenario
	 * @param  assigned	a list of all Producers that are already assigned to consumers
	 * @param  x	coordinate of the position that should be checked
	 * @param  y	coordinate of the position that should be checked
	 * @return        true if there is an unassigned Producer at the position or false if not
	 */	
	private EnergyNode getNodeAtPosition(Graph<EnergyNode, PowerLine> graph, int x, int y) {
		if (graph == null) {
			throw new IllegalArgumentException("Parameter is not allowed to be null.");
		}
		
		for (EnergyNode node : graph.getNodes()) {
			if (node.getXPos() == x && node.getYPos() == y) {
				return node;
			}
		}
		return null;
	}

	@Override
	/**
	 * Returns the Team Identifier
	 * @Return The Team Identifier
	 */
	public String getTeamIdentifier() {
		return "G05T01";
	}

}
