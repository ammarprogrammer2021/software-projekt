package solutions.exercise2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise2.AbstractQuicksortTest;

public class QuicksortTest extends AbstractQuicksortTest implements ExerciseSubmission{

	/*
	 * @return returns teamidentifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}

	/*
	 * Testcase where the method receives valid parameters
	 */
	@Override
	@Test
	public void testPartition() {
		
		Integer[] test = {1, 12, 5, 26, 7, 14, 3, 7, 2};
		Integer[] result = {1, 2, 5, 7, 3, 14, 7, 26, 12};
		assertEquals(sut.partition(test, 0, 8), 3);
		sut.partition(test, 0, 8);
		assertArrayEquals(test, result);
		
	}

	/*
	 * Testcases where the method partition() receives invalid Parameters
	 * Exceptions expected
	 */
	@Override
	@Test
	public void testPartition_Parameters() {
		
		Integer[] test = {3, 5, -4, -100, 12, 7, 1};
		
		//receives an array which has the value null
		try {
			sut.quicksort(null, 0, 6);
		} catch(IllegalArgumentException e) {
			//Expected Exception
		}
		
		//receives left border lower than 0
		try {
			sut.quicksort(test, -2, 6);
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//receives right border higher than array.length
		try {
			sut.quicksort(test, 0, 8);
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//receives right limit lower than left limit
		try {
			sut.quicksort(test, 2, 1);
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//receives two negativ boundaries, right<left
		try {
			sut.quicksort(test, -2, -5);
		} catch (IllegalArgumentException e) {
			//Expected exception
		}
		
		//receives two negativ boundaries, right>left
		try {
			sut.quicksort(test, -4, -2);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected exception
		}
		
	}
	
	/*
	 * Testcases where the method receives valid values for its parameters
	 */
	@Override
	@Test
	public void testQuicksort() {
		
		Integer[] test = {3, 5, -4, -100, 12, 13, 0};
		Integer[] result = {-100, -4, 0, 3, 5, 12, 13};
		sut.quicksort(test, 0, 6);
		assertArrayEquals(test, result);
		
		Integer[] test2 = {5, 17, -3, 59, -42, 6, 19};
		Integer[] result2 = {-42, -3, 5, 6, 17, 19, 59};
		sut.quicksort(test2, 0, 6);
		assertArrayEquals(test2, result2);
		
	}

	/*
	 * Testcases where the method receives invalid values for its parameters
	 * Exceptions expected
	 */
	@Override
	@Test
	public void testQuicksort_Parameters() {
		
		Integer[] test = {3, 5, -4, -100, 12, 7, 1};
		
		//Method receives an empty arrary
		try {
			sut.quicksort(null, 0, 6);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//Method receives a left limit lower than 0
		try {
			sut.quicksort(test, -1, 6);
			fail();
		} catch(IllegalArgumentException e) {
			//Expected Exception
		}
		
		//Method receives a right limit higher than the arrays length
		try {
			sut.quicksort(test, 0, 66);
			fail();
		} catch (IllegalArgumentException e) {
			//Expected Exception
		}
		
		//Method receives a right border which is lower than the left border
		try {
			sut.quicksort(test, 2, 1);
			fail();
		} catch(IllegalArgumentException e) {
			//Expected Exception
		}
		
	}

}
