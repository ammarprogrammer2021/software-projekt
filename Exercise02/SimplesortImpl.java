package solutions.exercise2;

import java.util.Comparator;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise2.Simplesort;
/**
 * Implements a simple sorting algorithm, that sorts an array using a Comparator
 * @author Adrian Bohnstedt, Laurent Brune, Anton Herzog, Ammar Benazza
 * @version 1.0
 * @param <T> generic Type parameter
 */
public class SimplesortImpl<T> implements Simplesort<T>, ExerciseSubmission{

	private Comparator<T> comp;
	/**
	 * The constructor saves the comparator as object variable
	 * @param c Comparator
	 */
	public SimplesortImpl (Comparator<T> comp) {
		
		if(comp == null) {
			throw new IllegalArgumentException("Null is an invalid value for comp!");
		}
		this.comp = comp;
	}
	/*
	 * Sorts an array in the given Intervall
	 * 
	 * @param arr Array which should be sorted
	 * @param left left intervalllimit
	 * @param right right intervallimit
	 */
	@Override
	public void simplesort(T[] arr, int left, int right) {
		//Null-Test
		if(arr == null || right<left || left < 0 || right >= arr.length) {
			throw new IllegalArgumentException("Parameters can't be null. Right limit must be higher than left limit!");	
		}
		
		for(int i=left; i<right; i++) {
			for(int j=i+1; j<=right; j++) {
				//only change the elements if...
				if(comp.compare(arr[i], arr[j]) > 0) {
					//Creating two help variables for the swap
					T swap1 = arr[i];
					T swap2 = arr[j];
					arr[i] = swap2;
					arr[j] = swap1;
				}
			}
		}
		
	}
	@Override
	/**
	 * Gets the Team Identifier
	 * @return Team Identifier
	 */
	public String getTeamIdentifier() {
		return "G05T01";
	}

}
