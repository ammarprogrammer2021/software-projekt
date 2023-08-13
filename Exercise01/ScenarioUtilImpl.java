package solutions.exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.sopra.api.Scenario;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise1.ScenarioUtil;
import org.sopra.api.model.EnergyNode;
import org.sopra.api.model.Graph;
import org.sopra.api.model.PlayfieldElement;
import org.sopra.api.model.PlayfieldElement.ElementType;
import org.sopra.api.model.PowerLine;
import org.sopra.api.model.PowerLineType;
import org.sopra.api.model.consumer.Consumer;
import org.sopra.api.model.consumer.ControllableConsumer;
import org.sopra.api.model.producer.ControllableProducer;
import org.sopra.api.model.producer.Producer;
/**
 * This Class gets information about the playing field
 * @author Adrian Bohnstedt, Ammar Benazza, Anton Herzog, Laurent Brune
 * @version 1.0
 */
public class ScenarioUtilImpl implements ExerciseSubmission, ScenarioUtil {

    @Override
    public List<PlayfieldElement> getPlayfieldElementsByType(Scenario scenario, ElementType type) {
	if (scenario == null || type == null) {
	    throw new IllegalArgumentException("Parameter is not allowed to be null.");
	}

	List<PlayfieldElement> ret = new ArrayList<>();
	for (int x = 0; x < scenario.getPlayfield().getHorizontalSize(); x++) {
	    for (int y = 0; y < scenario.getPlayfield().getVerticalSize(); y++) {
		PlayfieldElement element = scenario.getPlayfield().getPlayfieldElement(x, y);
		if (element.getElementType() == type) {
		    ret.add(element);
		}
	    }
	}
	return ret;
    }
    @Override
    /**
     * Takes a graph and a type and eturns a List of all powerLine objects of a given type of the graph
     * @param graph The EnergyNode PowerLine graph
     * @param type The type of the Powerline
     * @param powerLinesOfType ArrayList of all PowerLines of the given Type
     * @param powerLines List of all PowerLines
     * @return The List with the PowerLines of the specific type
     */
    public List<PowerLine> getPowerLinesByType(Graph<EnergyNode, PowerLine> graph, PowerLineType type){
    	if(graph == null || type == null)
    		throw new IllegalArgumentException("Graph or type is null!");
    	//List of all powerLines of the given type
    	List<PowerLine> powerLinesOfType = new ArrayList<PowerLine>();
    	//List of all edges to iterate over
    	List<PowerLine> powerLines = graph.getEdges();
    	for(PowerLine Line: powerLines) {
    		if(Line.getType().equals(type))
    			powerLinesOfType.add(Line);	
    	}
    	return powerLinesOfType;
    }
    @Override
    /**
     * Takes a graph and returns a List of all Energynodes that are an instance of ControllableProducer
     * @param graph The EnergyNode PowerLine graph
     * @param ControllableProducers List of all controllable Producers
     * @param itNodes Set of all Nodes
     * @return List of ControllableProducers
     */
    public List<ControllableProducer> getControllableProducers(Graph<EnergyNode, PowerLine> graph){
    	if(graph == null)
    		throw new IllegalArgumentException("Graph is null!");
    	List<ControllableProducer> ControllableProducers = new ArrayList<ControllableProducer>();
    	//Set to iterate over
    	Set<EnergyNode> itNodes = graph.getNodes();
    	for(EnergyNode node: itNodes) {
    		if(node instanceof ControllableProducer)
    			ControllableProducers.add((ControllableProducer) node);
    	}
    	return ControllableProducers;
    }
    @Override
    /**
     *  Takes a graph and returns a List of all EnergyNodes that are an instance of ControllableConsumer
     * @param graph The EnergyNode PowerLine graph
     * @param ControllableConsumers List of all controllable Consumers
     * @param itNodes Set of all Nodes
     * @return List of ControllableConsumers
     */
    public List<ControllableConsumer> getControllableConsumers(Graph<EnergyNode, PowerLine> graph){
    	if(graph == null)
    		throw new IllegalArgumentException("Graph is null");
    	List<ControllableConsumer> ControllableConsumers = new ArrayList<ControllableConsumer>();
    	//Set to iterate over
    	Set<EnergyNode> itNodes = graph.getNodes();
    	for(EnergyNode node: itNodes) {
    		if(node instanceof ControllableConsumer)
    			ControllableConsumers.add((ControllableConsumer) node);
    	}
    	return ControllableConsumers;
    }
    @Override
    /**
     * Takes a graph and returns a List of all EnergyNodes that are an instance of Producer
     * @param graph The EnergyNode PowerLine graph
     * @param producerList List of all Producers
     * @param itNodes Set of all Nodes
     * @return List of Producers
     */
    public List<Producer> getProducers(Graph<EnergyNode, PowerLine> graph){
    	if(graph == null)
    		throw new IllegalArgumentException("Graph is null");
    	List<Producer> producerList = new ArrayList<Producer>();
    	//Set to iterate over
    	Set<EnergyNode> itNodes = graph.getNodes();
    	for(EnergyNode node: itNodes) {
    		if(node instanceof Producer)
    			producerList.add((Producer) node);
    	}
    	return producerList;
    }
    @Override
    /**
     * Takes a graph and returns a List of all EnergyNodes that are an instance of Consumer
     * @param graph The EnergyNode PowerLine graph
     * @param consumerList List of all Consumers
     * @param itNodes Set of all Nodes
     * @return List of Consumers
     */
    public List<Consumer> getConsumers(Graph<EnergyNode, PowerLine> graph){
    	if(graph == null)
    		throw new IllegalArgumentException("Graph is null");
    	List<Consumer> consumerList = new ArrayList<Consumer>();
    	//Set to iterate over
    	Set<EnergyNode> itNodes = graph.getNodes();
    	for(EnergyNode node: itNodes) {
    		if(node instanceof Consumer)
    			consumerList.add((Consumer) node);
    	}
    	return consumerList;
    }
	@Override
	/**
	 * Returns Team Identifier
	 * @Return Team Identifier
	 */
	public String getTeamIdentifier() {
		return "G05T01";
	}

}
