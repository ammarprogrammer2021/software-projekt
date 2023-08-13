package solutions.exercise2;
import static org.junit.Assert.assertArrayEquals;


import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise2.AbstractTestRunTimeSorting;

import com.google.common.base.Stopwatch;

/**
 * This class compares the runtimes of the implemented sort algorithms
 * when sorting large arrays.
 * @author Adrian Bohnstedt, Ammar Benazza, Anton Herzog, Laurent Brune
 * @version 1.0
 */

public class TestRunTimeSorting extends AbstractTestRunTimeSorting implements ExerciseSubmission{
	private Integer [] sortedArray;
	private Integer [] unorderedArray;
	private SimplesortImpl<Integer> SsI;
	private QuicksortImpl<Integer> QsI;
	private Stopwatch stopwatch;
	
	
/**
 * This method uses inherited methods to create sorted and mixed arrays of the same length
 * and saves them as object variables
 */
	@Override
	@Before
	public void setup() {
		this.sortedArray = createOrderedIntArray(1000);
		this.unorderedArray = createShuffledIntArray(1000);
		this.SsI = new SimplesortImpl<Integer>(comparator);
		this.QsI = new QuicksortImpl<Integer>(comparator);
		this.stopwatch = Stopwatch.createUnstarted();
	}
	
	/** This test method tests whether the implemented quicksort method 
	 *  correctly sorts the array. 
	 *  It also starts a stopwatch to measure the runtime of the sorting process
	 */
	@Test
	@Override
	public void simplesortRunTimeTest() {
		stopwatch.start();
		SsI.simplesort(unorderedArray, 0,unorderedArray.length-1);
		stopwatch.stop();
		long timeElapsed = stopwatch.elapsed(TimeUnit.MICROSECONDS);
		System.out.println("Time elapsed for sorting shuffled array with SimpleSort = " + timeElapsed +"µ");
		assertArrayEquals(sortedArray,unorderedArray);
	}
	
	/** This test method tests whether the implemented simplesort method 
	 *  correctly sorts the array. 
	 *  It also starts a stopwatch to measure the runtime of the sorting process
	 */
	@Test
	@Override
	public void quicksortRunTimeTest() {
		stopwatch.start();
		QsI.quicksort(unorderedArray, 0,unorderedArray.length-1);
		stopwatch.stop();
		long timeElapsed = stopwatch.elapsed(TimeUnit.MICROSECONDS);
		System.out.println("Time elapsed for sorting shuffled array with QuickSort = " + timeElapsed +"µ");
		assertArrayEquals(sortedArray,unorderedArray);
	}

	/*
	 * Returns teamidentifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}
		
	}