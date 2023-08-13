package solutions.exercise3;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise3.AbstractFlowGraphTest;
import org.sopra.api.exercises.exercise3.FlowEdge;

public class FlowGraphTest extends AbstractFlowGraphTest implements ExerciseSubmission{
	{
}
    @Test
	@Override
	public void test_addEdge() {
		for(String node:nodes) {
			sut.addNode(node);
		}
		try {
			sut.addEdge("x","v",0);
			fail("NoSuchElementException was not detected.");
		}catch(Exception e) {
			assertThat("Expected NoSuchElementException",e,instanceOf(NoSuchElementException.class));
		}
		try {
			sut.addEdge("u","z",0);
			fail("NoSuchElementException was not detected.");
		}catch(Exception e) {
			assertThat("Expected NoSuchElementException",e,instanceOf(NoSuchElementException.class));
		}
		try {
			sut.addEdge("a","b",0);
			fail("NoSuchElementException was not detected.");
		}catch(Exception e) {
			assertThat("Expected NoSuchElementException",e,instanceOf(NoSuchElementException.class));
		}
		FlowEdge<String>edge_sv=sut.addEdge("s","v",7);
		assertNotNull(edge_sv);
		assertEquals(edge_sv.getStart(),"s");
		assertEquals(edge_sv.getEnd(),"v");
		assertEquals(edge_sv.getCapacity(),7);
		assertEquals(edge_sv.getFlow(),0);
		FlowEdge<String>edge_sv2=sut.addEdge("s","v",7);
		assertEquals(edge_sv2,edge_sv);
		assertEquals(sut.getEdges().size(),1);
		assertTrue(sut.getEdges().contains(edge_sv));
	
	}
    @Test
	@Override
	public void test_addNode() {
     for(String node:nodes) {
    	 assertNotNull(node);
    	 assertFalse(sut.containsNode(node));
    	assertTrue(sut.addNode(node));
     }
    	assertFalse(sut.addNode(null));
        assertFalse(sut.addNode("s"));
        assertFalse(sut.addNode("u"));
        assertFalse(sut.addNode("v"));
        assertFalse(sut.addNode("t"));
        }
    @Test
	@Override
	public void test_containsNode() {
		for(String node:nodes) {
			sut.addNode(node);
		}
		for(FlowEdge<String> edge:edges) {
			sut.addEdge(edge.getStart(),edge.getEnd(),edge.getCapacity());	
			}
		for(String node:nodes) {
			assertTrue(sut.containsNode(node));
			System.out.println(" Der Graph enthält die Knote "+node+" :"+sut.containsNode(node));
			assertFalse(sut.containsNode(null));
			assertFalse(sut.containsNode("K"));
		}
		
	}

    @Test
	@Override
	public void test_edgesFrom() {
		for(String node:nodes) {// we create the flow graph
			sut.addNode(node);
		}
		for(FlowEdge<String> edge:edges) {
			sut.addEdge(edge.getStart(),edge.getEnd(),edge.getCapacity());	
			}
		try {
			sut.edgesFrom(null);
			fail("NoSuchElementException was not detected");
		}catch(NoSuchElementException e) {
			assertThat(e,instanceOf(NoSuchElementException.class));
		}
		try {
			sut.edgesFrom("r");
			fail("NoSuchElementException was not detected");
		} catch (NoSuchElementException e) {
			assertThat(e,instanceOf(NoSuchElementException.class));
		}
	Collection<FlowEdge<String>> edges_s = sut.edgesFrom("s");
	    assertEquals(edges_s.size(),2);
	    assertTrue(edges_s.contains(sut.getEdge("s", "u")));
	    assertTrue(edges_s.contains(sut.getEdge("s","v")));
	    for(FlowEdge<String> edge:edges_s) {
	    	assertEquals(edge.getStart(),"s");
	    	assertTrue(edge.getEnd()=="u"||edge.getEnd()=="v");
	    }
		for(String node:nodes) {
		 System.out.println("Die aus der Knote "+node+" abgehenden Kanten sind"+sut.edgesFrom(node));
		}
		
    }
	
    @Test
	@Override
	public void test_getEdge() {
		for(String node:nodes) {//creating the graph
			sut.addNode(node);
		}
		for(FlowEdge<String> edge:edges) {
			sut.addEdge(edge.getStart(),edge.getEnd(),edge.getCapacity());	
			}
		assertNull(sut.getEdge("u",null));
		assertNull(sut.getEdge(null,"v"));
		assertNull(sut.getEdge(null,null));
		assertNull(sut.getEdge("K","t"));
		assertNull(sut.getEdge("u","L"));
		System.out.println("Die Kante von s nach u is "+sut.getEdge("s","u"));
		FlowEdge<String> edge_st = sut.getEdge("u","t");
		assertNotNull(edge_st);
		assertEquals(edge_st.getStart(),"u");
		assertEquals(edge_st.getEnd(),"t");
		assertEquals(edge_st,sut.addEdge("u", "t", 4));
		
	}
    @Test
	@Override
	public void test_getEdges() {
    	assertNotNull(sut.getEdges());
    	assertTrue(sut.getEdges().isEmpty());
		for(String node:nodes) {
			sut.addNode(node);
		}
		for(FlowEdge<String> edge:edges) {
			sut.addEdge(edge.getStart(),edge.getEnd(),edge.getCapacity());	
			}
		 assertNotNull(sut.getEdges());
		 assertEquals(sut.getEdges().size(),10);
		 assertTrue(sut.getEdges().contains(sut.getEdge("u","v")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("v","u")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("u","s")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("s","u")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("s","v")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("v","s")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("u","t")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("t","u")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("t","v")));
		 assertTrue(sut.getEdges().contains(sut.getEdge("v","t")));
			System.out.println("Die Kanten des Graphs sind "+sut.getEdges());
	}
    @Test
	@Override
	public void test_getNodes() {
    	assertNotNull(sut.getNodes());
    	assertTrue(sut.getNodes().isEmpty());
		for(String node:nodes) {
			sut.addNode(node);
		}
		for(FlowEdge<String> edge:edges) {
			sut.addEdge(edge.getStart(),edge.getEnd(),edge.getCapacity());	
			}
		assertEquals(sut.getNodes().size(),4);
		assertTrue(sut.getNodes().contains("u"));
		assertTrue(sut.getNodes().contains("v"));
		assertTrue(sut.getNodes().contains("s"));
		assertTrue(sut.getNodes().contains("t"));
		
		
			System.out.println("Die Knoten des Graphs sind "+sut.getNodes());
	}
	
	@Override
	public String getTeamIdentifier() {
		
		return "G05T01";
	}

}
