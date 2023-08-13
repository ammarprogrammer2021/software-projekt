package solutions.exercise2;

import static org.junit.Assert.assertArrayEquals;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise2.AbstractSimplesortTest;
/**
 * Tests correct implementation of QuickSortTest
 * 
 * @author Anton Herzog, Adrian Bohnstedt, Laurent Brune, Ammar Benazza
 * @version 1.0
 */
public class SimplesortTest extends AbstractSimplesortTest implements ExerciseSubmission {

	/**
	 * Gets the Team Identifier
	 * @return returns the team identifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}

	/**
	 * Testcase, where the method receives valid parameters
	 * @param test array under test
	 * @param result array to compare test result
	 * @param Arr array under test
	 */
	@Override
	@Test
	public void testSimplesort() {
		
		//Array which should be sorted
		Integer[] test = {3, 5, -4, -100, 12, 7, 1, 25, 17, 0};
		//Expected resulting array
		Integer[] result = {-100, -4, 0, 1, 3, 5, 7, 12, 17, 25};
		sut.simplesort(test, 0, 9);
		
		assertArrayEquals(test, result);
		
		Integer[] Arr = {-2,11,0,-1,-4,8,1,3,-7};//we choose an unsorted Example of an Array
		sut.simplesort(Arr, 1, 7);
		Integer expected [] = new Integer[] {-2,-4,-1,0,1,3,8,11,-7}; // we sort this Array between the two limits left and right
        	assertArrayEquals(expected,Arr);// we compare our Array after sorting it 
	
	}

	/**
	 * Testcase, where the method receives invalid parameters
	 * We expect an exception
	 * 
	 * @param test array under test
	 */
	@Override
	@Test
	public void testSimplesort_Parameters() {
		
		Integer[] test = {3, 5, -4, -100, 12, 7, 1, 25, 17, 0};
		
		//left border is lower than 0
		try {
			sut.simplesort(test, -2, 6);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//Method receives an null for the first argument
		try {
			sut.simplesort(null, 2, 6);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//Right border is >= test.length
		try {
			sut.simplesort(test, 2, 10);
		} catch(IllegalArgumentException e) {
			//Expected Exception
		}
		
		//Right border is lower than the left border
		try {
			sut.simplesort(test, 4, 2);
		} catch(IllegalArgumentException e) {
			//Expected Exception
		}
		
		
	}

}
