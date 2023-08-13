package solutions.exercise3;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.model.CapacityEdge;
/**
 * Creates Capacity Edges
 * @author Adrian Bohnstedt, Laurent Brune, Anton Herzog, Ammar Benazza
 * @version 1.0
 *
 * @param <V>
 */
abstract class Edge<V> implements CapacityEdge<V>, ExerciseSubmission{

	protected int capacity;
	protected V endNode;
	protected V startNode;
	
	/**
	 * Constructs a new Edge 
	 * 
	 * @param startNode
	 * @param endNode
	 * @param capacity
	 */
	public Edge(V startNode, V endNode, int capacity) {
		this.capacity = capacity;
		this.startNode = startNode;
		this.endNode = endNode;
	}
	/**
	 * Returns Team Identifier
	 * @return Team Identifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}
/**
 * Getter method for end node
 * 
 * @return end node
 */
	@Override
	public V getEnd() {
		// TODO Auto-generated method stub
		return endNode;
	}
/**
 * Getter method for start node
 * 
 * @return start node
 */
	@Override
	public V getStart() {
		// TODO Auto-generated method stub
		return startNode;
	}
/**
 * Getter method for capacity
 * 
 * @return capacity
 */
	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return capacity;
	}
/**
 * Sets the capacity
 * @param capacity
 * 
 * @throws Illegal Argument Exception if the capacity is negative
 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity<0)
			throw new IllegalArgumentException("capacity is negative");
		this.capacity = capacity;
		
	}

}
