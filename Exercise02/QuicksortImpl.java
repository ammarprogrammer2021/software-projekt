package solutions.exercise2;

import java.util.Comparator;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise2.Quicksort;
/**
 * Sorts elements quickly using pivotelement
 * 
 * @author Anton Herzog, Adrian Bohnstedt, Laurent Brune, Ammar Benazza
 * @version 1.0
 * @param <T>
 */
public class QuicksortImpl<T> implements Quicksort<T>, ExerciseSubmission{

	private Comparator<T> comp1;
	
	/**
	 * The Constructor checks if Comparator comp1 is 0 and then saves it as object variable
	 * @param comp1 generic Comparator
	 */
	public QuicksortImpl(Comparator<T> comp1)	{
		
		if(comp1 == null) {
			throw new IllegalArgumentException("Null is an invalid value for compare!");
		}
		
		this.comp1 = comp1;
	}
	
	/**
	 * Gets Team Identifier
	 * @return returns the team identifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}

	/**
	 * Sorts given array and returns an index. The  element left of this index and every element until i-1 belongs to the first partition
	 * and the rest to the second partition
	 * 
	 * @param arr Array which should be sorted
	 * @param left left pointer
	 * @param right right pointer
	 * @return returns the new partitionelement after sorting the array 
	 */
	@Override
	public int partition(T[] arr, int left, int right) {
		
		if(arr == null || left < 0 || right >= arr.length || right < left) {
			throw new IllegalArgumentException("One parameter contains an invalid value!");
		}
		
		int pivotposition = (left+right)/2;
		T pivotelement = arr[pivotposition];
		
		//While the left and right pointer didn't cross ways do...
		while(left <= right) {
			
			//while the value at left pointer is lower than value of pivotelement increase left by 1
			while(comp1.compare(arr[left], pivotelement) < 0) {
				left++;
			}
			
			//While the value at right pointer is higher than value of pivotelement decrease right by 1
			while(comp1.compare(arr[right], pivotelement) > 0) {
				right--;
			}
			
			if(left <= right) {
				T swap1 = arr[left];
				T swap2 = arr[right];
				arr[left] = swap2;
				arr[right] = swap1;
				
				left++;
				right--;
			}
		}
		
		return left;
		
	}

	/**
	 * This Method sorts an array in the given intervall using the partition method.
	 * 
	 * @param arr array which should be sorted
	 * @param left left border of the intervall
	 * @param right right border of the intervall
	 * 
	 * @return
	 */
	@Override
	public void quicksort(T[] arr, int left, int right) {
		//checking parameters
		if(arr == null || left < 0 || right >= arr.length || right < left) {
			throw new IllegalArgumentException("None of the paramters is allowed to be null!");
		}
		
		if(left < right) {
			
			int partitionposition = partition(arr, left, right);
			
			quicksort(arr, left, partitionposition-1);
			quicksort(arr, partitionposition, right);
			
		}
	}

}


