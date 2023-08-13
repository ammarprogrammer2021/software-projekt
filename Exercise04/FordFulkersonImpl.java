package solutions.exercise4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise3.FlowEdge;
import org.sopra.api.exercises.exercise3.FlowGraph;
import org.sopra.api.exercises.exercise3.ResidualEdge;
import org.sopra.api.exercises.exercise3.ResidualGraph;
import org.sopra.api.exercises.exercise4.FordFulkerson;

import solutions.exercise3.ResidualGraphImpl;

/**
 * 
 * @author Anton Herzog, Adrian Bohnstedt, Ammar Benazza, Laurent Brune
 * @version 1.0
 *
 * @param <V> type of node
 */
public class FordFulkersonImpl<V> implements FordFulkerson<V>, ExerciseSubmission{

	/**
	 * This method determines the minimum capacity of all residualedges in the given path and assigns
	 * flow equal to the minimum capacity to each edge in the deque path
	 * 
	 * @param path path through a residualgraph
	 */
	@Override
	public void augmentPath(Deque<ResidualEdge<V>> path) {

		if (path==null) {
			throw new IllegalArgumentException ("Parameter is null");
		}
		
		//min_cap is initialized with the capacity of the first edge from the deque
		int min_cap = path.getFirst().getCapacity();
		
		//for-each loop to get the lowest capacity in the path
		for(ResidualEdge<V> edge:path) {
			if(edge.getCapacity() < min_cap) {
				min_cap = edge.getCapacity();
			}
		}
		
		//for-each loop to add flow to the edges of path according to max_cap
		for(ResidualEdge<V> edge:path) {
			edge.addFlow(min_cap);
		}
		
	}

	/**
	 * Computes the maximum flow in the given flownetwork from start to destination
	 * 
	 * @param start source node
	 * @param target sink node
	 * @param graph flowgraph
	 */
	@Override
	public void findMaxFlow(FlowGraph<V> graph, V start, V target) {

		if(graph==null) {
			throw new IllegalArgumentException("Null is an invalid value for the parameter graph!");
		}
		if(start==null) {
			throw new IllegalArgumentException("Null is an invalid value for the parameter start!");
		}
		if(target==null) {
			throw new IllegalArgumentException("Null is an invalid value for the parameter target!");
		}
		if(!graph.getNodes().contains(start)) {
			throw new NoSuchElementException("The given node start is not part of graph!");
		}
		if(!graph.getNodes().contains(target)) {
			throw new NoSuchElementException("The given node target is not part of graph!");
		}
		
		//Converting the given flowgraph into a residualgraph
		ResidualGraph<V> residualgraph = new ResidualGraphImpl(graph);
		
		Deque<ResidualEdge<V>> path;
		
		//While a path with remaining capacity from start to target exists do...
		while(findPath(start, target, residualgraph)!=null) {
			
			//assigning an existing path from start to target to path
			path = findPath(start, target, residualgraph);
			//Assigning the maximum flow possible to this path
			augmentPath(path);
		}
		
			for(ResidualEdge<V> edge:residualgraph.getEdges()) {
				V beginning = edge.getStart();
				V end = edge.getEnd();
				int res_capacity = edge.getCapacity();
				
				if(res_capacity >= graph.getEdge(beginning, end).getCapacity()) {
					
					graph.getEdge(beginning, end).setFlow(0);
					
				}
				if(res_capacity < graph.getEdge(beginning, end).getCapacity()) {
					
					int flow_capacity = graph.getEdge(beginning, end).getCapacity();
					graph.getEdge(beginning, end).setFlow(flow_capacity-res_capacity);
					
				}
		}
		
	}

	/**
	 * Finds the shortest path with remaining capacity in the given residualgraph
	 * 
	 * @param start startnode of the path
	 * @param end destinationnode of the path
	 * @param graph residualgraph to search in
	 * @return returns the shortest path from start to end
	 */
	@Override
	public Deque<ResidualEdge<V>> findPath(V start, V end, ResidualGraph<V> graph) {

		if(start==null) {
			System.out.println("exception");
			throw new IllegalArgumentException("Null is an invalid value for the startnode!");
		}
		if(end==graph) {
			System.out.println("exception");
			throw new IllegalArgumentException("Null is an invalid value for the destination node!");
		}
		if(graph==null) {
			System.out.println("exception");
			throw new IllegalArgumentException("Null is an invalid value for the residualgraph!");
		}
		if(!graph.getNodes().contains(start)) {
			System.out.println("exception");
			throw new IllegalArgumentException("The startnode is not part of the given residualgraph!");
		}
		if(!graph.getNodes().contains(end)) {
			System.out.println("exception");
			throw new IllegalArgumentException("The destinationnode is not part of the given residualgraph!");
		}
		if(start.equals(end)) {
			System.out.println("exception");
			throw new IllegalArgumentException("Start and end have to contain different values!");
		}
		
		//shortest path through the graph
		Deque<ResidualEdge<V>> output = new ArrayDeque<ResidualEdge<V>>();
		
		//List which contains already visited nodes
		ArrayList<V> visited = new ArrayList<V>();
		visited.add(start);
		
		//Accessibilitytree, Key contains the current node, value contains the previous node
		Map<V, V> tree = new HashMap<V, V>();
		tree.put(start, null); //adding the start node to the tree
		
		//Queue of nodes which are about to be edited
		Deque<V> queue = new ArrayDeque<V>();
		queue.add(start); //Adding the startnode
		
		//boolean value, which says whether we have already found the destination node, node2
		boolean found = false;
		
		//While-loop which completes the accessibilitytree
		while(!found) {
			V current_node = queue.poll();
			
			List<ResidualEdge<V>> edgesfrom = graph.edgesFrom(current_node);
			
			//for-each loop to iterate over all outgoing edges from the first node in the queue
			for(ResidualEdge<V> edges:edgesfrom) {
				//capacity of the edge between the two current nodes
				int capacity = graph.getEdge(current_node, edges.getEnd()).getCapacity();
				
				/*
				 * If the given node is NOT listed in visited yet and the capacity between the two current nodes is bigger 0,
				 * the node will be added to the list visited and to the queue
				 */
				if(!visited.contains(edges.getEnd()) && capacity>0 && !queue.contains(edges.getEnd())) {
					//adding the node to the accessibility tree
					tree.put(edges.getEnd(), current_node);
					
					//adding the node to the queue
					queue.add(edges.getEnd());
					
					//assigning the color grey to the node
					visited.add(edges.getEnd());
				}
			}
			if(tree.containsKey(end)) {
				found = true;
			}
			//return null, if node2 is not reachable
			if(queue.isEmpty()) {
				System.out.println("return null");
				return null;
			}
		}
		
		V current_node = end;
		V previous_node = tree.get(end);
		boolean stop = false;
		
		//While-loop to fill the parameter output with the needed Residualedges
		while(!stop) {
			
			//Residualedge between two nodes
			ResidualEdge<V> resedge = graph.getEdge(previous_node, current_node);
			
			//adding the Residualedge to output
			output.addLast(resedge);
			
			current_node = previous_node;
			previous_node = tree.get(previous_node);
			
			if(previous_node==null) {
				stop = true;
			}
		}
		
		return output;
	}

	/**
	 * Returns the Teamidentifier
	 * @return returns the TeamIdentifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}

}
