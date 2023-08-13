package solutions.exercise3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise3.FlowEdge;
import org.sopra.api.exercises.exercise3.FlowGraph;
/**
 * Creates a flow graph
 * @author Adrian Bohnstedt, Laurent Brune, Anton Herzog, Ammar Benazza
 * @param graph Graph of all nodes and edges
 * @param <V>
 */
public class FlowGraphImpl<V> implements FlowGraph<V>, ExerciseSubmission {
	
	//Graph of start node, map of end node and the edge between them
	private Map<V, Map<V, FlowEdge<V>>> graph = new HashMap<V, Map<V, FlowEdge<V>>>();
	/**
	 * Adds flow edge with flow of 0. Returns existing edge if edge already exists.
	 * @param start start
	 * @param dest destination
	 * @param flow flow
	 * @param flowEdge the new FlowEdge
	 * @throws NoSuchELementException If start or dest node does not exist in the graph
	 * @return new FlowEdge
	 */
	@Override
	public FlowEdge<V> addEdge(V start, V dest, int capacity) {
		if(!containsNode(start))
			throw new NoSuchElementException("start does not exist");
		if(!containsNode(dest))
			throw new NoSuchElementException("dest does not exist");
		//get Edge if Edge is not null
		if(getEdge(start, dest) != null)
			return getEdge(start, dest);
		//flow is automaticly 0;
		FlowEdge<V> flowEdge = new FlowEdgeImpl<V>(start, dest, capacity);
		//adds edge
		graph.get(start).put(dest, flowEdge);
		return flowEdge;
	}
	/**
	 * Adds new node to graph if its not contained in the graph
	 * @param node new Node
	 * @return true if node was added
	 */
	@Override
	public boolean addNode(V node) {
	if(node != null) {
		//Map<V, FlowEdge<V>> edgeMap = new HashMap<V, FlowEdge<V>>(); not needed
		if(!containsNode(node)){
			graph.put(node, null);
			return true;
		}
	}
		return false;
	}
	/**
	 * Returns true if graph contains node
	 * @param node node
	 * @returns if node is contained
	 */
	@Override
	public boolean containsNode(V node) {
		if(graph.containsKey(node))
				return true;
		return false;
	}
	/**
	 * Returns all edges from node. If node does not exist throws NoSuchElementException.
	 * @param node node
	 * @param edges Collection of the edges of the node
	 * @return edges of the node
	 */
	@Override
	public Collection<FlowEdge<V>> edgesFrom(V node) {
		if(!containsNode(node))
			throw new NoSuchElementException("Node does not exist");
		Collection<FlowEdge<V>> edges = graph.get(node).values();
		return edges;
	}
	/**
	 * Returns a flow edge with given parameters if it is contained in the Graph
	 * @param start start node
	 * @param end end node
	 * @param edge the flowEdge of the Graph between the given nodes
	 * @return the flow edge
	 */
	@Override
	public FlowEdge<V> getEdge(V start, V end) {
		if(start == null || end == null)
			return null;
		if(graph.get(start) == null || graph.get(start).get(end) == null)
			return null;
		FlowEdge<V> edge = graph.get(start).get(end);
		return edge;
	}
	/**
	 * Returns list of all edges of the graph
	 * @param allEdges List of all edges
	 * @return all edges 
	 */
	@Override
	public List<FlowEdge<V>> getEdges() {
		List<FlowEdge<V>> allEdges = new ArrayList<FlowEdge<V>>();
		for(V node: getNodes()) {
			for(FlowEdge<V> flowEdge: edgesFrom(node)) {
				allEdges.add(flowEdge);
			}
		}
		return allEdges;
	}
	/**
	 * Returns a set of all nodes of the graph
	 * @return set of all nodes
	 */
	@Override
	public Set<V> getNodes() {
		return graph.keySet();
	}
	/**
	 * Returns Team Identifier
	 * @return Team Identifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}
	
}
