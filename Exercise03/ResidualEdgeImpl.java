package solutions.exercise3;

import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise3.ResidualEdge;

public class ResidualEdgeImpl<V> extends Edge<V> implements ResidualEdge<V>,ExerciseSubmission {
   private ResidualEdge<V> reverse ;
   
public ResidualEdgeImpl(V startNode,V endNode,int capacity) {
	   super(startNode,endNode,capacity);
   }

@Override
public void addFlow(int amount) {
	if(amount>capacity ) {
		throw new IllegalArgumentException("The amount of the added flow exceeded the maximum capacity");
	}
    if(amount>=0) {
	capacity -= amount;
	reverse.setCapacity(amount+reverse.getCapacity());
    }
    if(amount<0) {
    	reverse.addFlow(-amount);
    }
}

@Override
public ResidualEdge<V> getReverse() {
	return reverse;
}

@Override
public void setReverse(ResidualEdge<V> reverse) {
	if(reverse == null) {
		throw new IllegalArgumentException("This parameter cannot be null");
	}
	this.reverse = reverse;
}
}
