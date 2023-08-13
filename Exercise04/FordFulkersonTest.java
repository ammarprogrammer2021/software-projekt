package solutions.exercise4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise3.FlowGraph;
import org.sopra.api.exercises.exercise3.ResidualEdge;
import org.sopra.api.exercises.exercise3.ResidualGraph;
import org.sopra.api.exercises.exercise4.AbstractFordFulkersonTest;

import solutions.exercise3.FlowGraphImpl;
import solutions.exercise3.ResidualEdgeImpl;
import solutions.exercise3.ResidualGraphImpl;
/**
 * Tests the FordFulkerson algorithm of the FordFulkersonImpl class
 * 
 * @author Anton Herzog, Adrian Bohnstedt, Ammar Benazza, Laurent Brune
 * @version 1.0
 * 
 */
public class FordFulkersonTest extends AbstractFordFulkersonTest implements ExerciseSubmission{
	/**
	 * Tests the augmentPath method for path1
	 */
	@Override
	@Test
	public void test_augmentPath1() {
	//Setting up the expected output deque
	Deque<ResidualEdge<String>> expected = new ArrayDeque<ResidualEdge<String>>();
	ResidualEdge<String> s_b_expected = new ResidualEdgeImpl<String>("s", "b", 4);
	ResidualEdge<String> b_a_expected = new ResidualEdgeImpl<String>("b", "a", 0);
	ResidualEdge<String> a_d_expected = new ResidualEdgeImpl<String>("a", "d", 5);
	ResidualEdge<String> d_t_expected = new ResidualEdgeImpl<String>("d", "t", 3);
	expected.add(d_t_expected);
	expected.add(a_d_expected);
	expected.add(b_a_expected);
	expected.add(s_b_expected);
				
	ResidualGraph<String> graph1 = new ResidualGraphImpl<String>(flowGraph1);
	//Creating the path which should be tested
	Deque<ResidualEdge<String>> test = new ArrayDeque<ResidualEdge<String>>();
	ResidualEdge<String> s_b_test = graph1.getEdge("s", "b");
	ResidualEdge<String> b_a_test = graph1.getEdge("b", "a");
	ResidualEdge<String> a_d_test = graph1.getEdge("a", "d");
	ResidualEdge<String> d_t_test = graph1.getEdge("d", "t");
	test.add(d_t_test);
	test.add(a_d_test);
	test.add(b_a_test);
	test.add(s_b_test);
				
	sut.augmentPath(test);
				
	for(int i=0; i<4; i++) {
		assertEquals(expected.getFirst().getStart(), test.getFirst().getStart());
		assertEquals(expected.getFirst().getEnd(), test.getFirst().getEnd());
		assertEquals(expected.getFirst().getCapacity(), test.getFirst().getCapacity());
		expected.removeFirst();
		test.removeFirst();
	}	
	}
	/**
	 * Tests the augmentPath method for path2
	 */
	@Override
	@Test
	public void test_augmentPath2() {
		//Setting up the expected output deque
		Deque<ResidualEdge<String>> expected = new ArrayDeque<ResidualEdge<String>>();
		expected.add(new ResidualEdgeImpl<String>("e", "t", 2));
		expected.add(new ResidualEdgeImpl<String>("b", "e", 4));
		expected.add(new ResidualEdgeImpl<String>("c", "b", 0));
		expected.add(new ResidualEdgeImpl<String>("a", "c", 2));
		expected.add(new ResidualEdgeImpl<String>("s", "a", 7));
				
		ResidualGraph<String> graph2 = new ResidualGraphImpl<String>(flowGraph2);
		Deque<ResidualEdge<String>> test = new ArrayDeque<ResidualEdge<String>>();
		test.add(graph2.getEdge("e", "t"));
		test.add(graph2.getEdge("b", "e"));
		test.add(graph2.getEdge("c", "b"));
		test.add(graph2.getEdge("a", "c"));
		test.add(graph2.getEdge("s", "a"));
		sut.augmentPath(test);
				
		for(int i=0; i<5; i++) {
			assertEquals(expected.getFirst().getStart(), test.getFirst().getStart());
			assertEquals(expected.getFirst().getEnd(), test.getFirst().getEnd());
			assertEquals(expected.getFirst().getCapacity(), test.getFirst().getCapacity());
			expected.removeFirst();
			test.removeFirst();
		}
		
	}
	/**
	 * Tests if augmentPath method throws the correct exception for parameter null
	 */
	@Override
	@Test
	public void test_augmentPath_ParameterNull() {
		try {
			sut.augmentPath(null);
			fail();
		}
		catch(IllegalArgumentException e) {
			
		}
	}
	/**
	 * Tests if findMaxFlow method throws the correct exception if parameter graph is null
	 */
	@Override
	@Test
	public void test_findMaxFlow_ParameterGraphIsNull() {
		try {
			sut.findMaxFlow(null, "s", "t");
			fail();
		}
		catch(IllegalArgumentException e) {
			
		}
		catch(NoSuchElementException e) {
			fail();
		}
		
	}
	/**
	 * Tests if findMaxFlow method throws the correct exception if parameter start is null
	 */
	@Override
	@Test
	public void test_findMaxFlow_ParameterStartIsNull() {
		try {
			sut.findMaxFlow(flowGraph1, null, "t");
			fail();
		}
		catch(IllegalArgumentException e) {
			
		}
		catch(NoSuchElementException e) {
			fail();
		}
		
	}
	/**
	 * Tests if findMaxFlow method throws the correct exception if parameter end is null
	 */
	@Override
	@Test
	public void test_findMaxFlow_ParameterTargetIsNull() {
		try {
			sut.findMaxFlow(flowGraph1, "s", null);
			fail();
		}
		catch(IllegalArgumentException e) {
			
		}
		catch(NoSuchElementException e) {
			fail();
		}
		
	}
	/**
	 * Tests if findMaxFlow method throws the correct exception if parameter target is not in graph
	 */
	@Override
	@Test
	public void test_findMaxFlow_ParameterTargetNotInGraph() {
		try {
			sut.findMaxFlow(flowGraph1, "s", "e");
			fail();
		}
		catch(NoSuchElementException e) {
			
		}
		
	}
	/**
	 * Tests findMaxFlow method for flowGraphA
	 */
	@Override
	@Test
	public void test_findMaxFlow_flowGraphA() {
		sut.findMaxFlow(flowGraphA,"s","t");
		
		assertEquals(17, flowGraphA.getEdge("s", "a").getFlow() + flowGraphA.getEdge("s", "b").getFlow());
		assertEquals(17, flowGraphA.getEdge("d", "t").getFlow() + flowGraphA.getEdge("e", "t").getFlow());
		
	}
	/**
	 * Tests findMaxFlow method for flowGraphB
	 */
	@Override
	@Test
	public void test_findMaxFlow_flowGraphB() {
		sut.findMaxFlow(flowGraphB, "s", "t");
		
		assertEquals(24, flowGraphB.getEdge("s", "a").getFlow() + flowGraphB.getEdge("s", "b").getFlow());
		assertEquals(24, flowGraphB.getEdge("d", "t").getFlow() + flowGraphB.getEdge("e", "t").getFlow());
	}
	/**
	 * Tests findMaxFlow method for flowGraphC
	 */
	@Override
	@Test
	public void test_findMaxFlow_flowGraphC() {
		sut.findMaxFlow(flowGraphC, "s", "t");
		
		assertEquals(10, flowGraphC.getEdge("s", "a").getFlow() + flowGraphC.getEdge("s", "b").getFlow());
		assertEquals(10, flowGraphC.getEdge("d", "t").getFlow() + flowGraphC.getEdge("e", "t").getFlow());
		
	}
	/**
	 * Tests findPath method for path1
	 */
	@Override
	@Test
	public void test_findPath1() {
		Deque<ResidualEdge<String>> deque = sut.findPath("s", "t", new ResidualGraphImpl<String>(flowGraph1));
		Deque<ResidualEdge<String>> deque2 = new ArrayDeque<>();
		//adding Nodes
		deque2.add(new ResidualEdgeImpl<String>("d","t", 6));
		deque2.add(new ResidualEdgeImpl<String>("a","d", 8));
		deque2.add(new ResidualEdgeImpl<String>("s", "a", 10));
		//comparing the nodes, edges and capacity of each edge
		assertNotNull(deque);
		assertTrue(deque.size() == 3);
		for(int i = 0; i<3; i++) {
			assertNotNull(deque.getFirst());
			
			assertEquals(deque.getFirst().getStart(), deque2.getFirst().getStart());
			assertEquals(deque.getFirst().getEnd(), deque2.getFirst().getEnd());
			assertEquals(deque.getFirst().getCapacity(), deque2.getFirst().getCapacity());
			System.out.println(deque2.getFirst().getStart() + " - " +deque2.getFirst().getEnd() + " : " + deque2.getFirst().getCapacity());
			if(!deque.isEmpty())
				deque.poll();
			if(!deque2.isEmpty())
				deque2.poll();
		}
		
	}
	/**
	 * Tests findPath method for path2
	 */
	@Override
	@Test
	public void test_findPath2() {
		Deque<ResidualEdge<String>> deque = sut.findPath("s", "t", new ResidualGraphImpl<String>(flowGraph2));
		Deque<ResidualEdge<String>> deque2 = new ArrayDeque<>();
		//adding Nodes
		deque2.add(new ResidualEdgeImpl<String>("e","t", 7));
		deque2.add(new ResidualEdgeImpl<String>("b","e", 9));
		deque2.add(new ResidualEdgeImpl<String>("s", "b", 11));
		//comparing the nodes, edges and capacity of each edge
		assertNotNull(deque);
		assertTrue(deque.size() == 3);
		for(int i = 0; i<3; i++) {
			assertNotNull(deque.getFirst());
	
			assertEquals(deque.getFirst().getStart(), deque2.getFirst().getStart());
			assertEquals(deque.getFirst().getEnd(), deque2.getFirst().getEnd());
			assertEquals(deque.getFirst().getCapacity(), deque2.getFirst().getCapacity());
			System.out.println(deque2.getFirst().getStart() + " - " +deque2.getFirst().getEnd() + " : " + deque2.getFirst().getCapacity());
			if(!deque.isEmpty())
				deque.poll();
			if(!deque2.isEmpty())
				deque2.poll();
		}
	}
	/**
	 * Tests findPath method for with max capacity null
	 */
	@Override
	@Test
	public void test_findPath_IsNull() {
		Deque<ResidualEdge<String>> deque = sut.findPath("s", "t", new ResidualGraphImpl<String>(flowGraph3));
		//creating flowGraph
		FlowGraph<String> flowGraph = new FlowGraphImpl<String>();
		flowGraph.addNode("t");
		flowGraph.addNode("b");
		flowGraph.addNode("e");
		flowGraph.addNode("s");
		flowGraph.addEdge("e", "t", 0);
		flowGraph.addEdge("b", "e", 0);
		flowGraph.addEdge("s", "b", 0);
		
//		System.out.println(deque.getFirst().getStart() + " - " +deque.getFirst().getEnd() + " : " + deque.getFirst().getCapacity());
		//comparing
		assertNull(deque);
		assertNull(sut.findPath("s","t", new ResidualGraphImpl<String>(flowGraph)));
	}
	/**
	 * Tests if findPath method throws the correct exception when parameter graph is null
	 */
	@Override
	@Test
	public void test_findPath_ParameterGraphIsNull() {
		try {
			sut.findPath("s", "t", null);
			fail();
		}
		catch(IllegalArgumentException e){}
		
	}
	/**
	 * Tests if findPath throws the correct exception when parameter start is null
	 */
	@Override
	@Test
	public void test_findPath_ParameterStartIsNull() {
		try {
			sut.findPath(null, "t", new ResidualGraphImpl<String>(flowGraph1));
			fail();
		}
		catch(IllegalArgumentException e){}
		
	}
	/**
	 * Tests if findPath throws the correct exception when target parameter is null
	 */
	@Override
	@Test
	public void test_findPath_ParameterTargetIsNull() {
		try {
			sut.findPath("s", null, new ResidualGraphImpl<String>(flowGraph1));
			fail();
		}
		catch(IllegalArgumentException e){}
		
	}
	/**
	 * Returns the Teamidentifier
	 * @return the TeamIdentifier
	 */
	@Override
	public String getTeamIdentifier() {
		//
		return "G05T01";
	}

}

