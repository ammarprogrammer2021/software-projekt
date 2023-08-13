package solutions.exercise3;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise3.FlowEdge;
/**
 * Creates flow Edges
 * @author Adrian Bohnstedt, Ammar Benazza, Anton Herzog, Laurent Brune
 * @version 1.0
 * @param <V>
 */
public class FlowEdgeImpl<V> extends Edge<V> implements FlowEdge<V>, ExerciseSubmission{

	private int flow;
	/**
	 * Constructs a new FlowEdge
	 * @param flow
	 * @param startNode
	 * @param endNode
	 * @param capacity
	 */
	public FlowEdgeImpl(V startNode, V endNode, int capacity) {
		super(startNode, endNode, capacity);
		this.flow = 0;
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
	 * Gets the flow
	 * @return flow
	 */
	@Override
	public int getFlow() {
		return flow;
	}
	/**
	 * Sets the flow
	 * @throws IllegalArgumentExeption if flow is negative
	 * @param flow
	 */
	@Override
	public void setFlow(int flow) {
		if(flow<0)
			throw new IllegalArgumentException("Flow is negative");
		this.flow = flow;
		
	}

}
