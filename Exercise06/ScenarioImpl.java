package solutions.exercise6;

import java.util.ArrayList;
import java.util.List;
import org.sopra.api.Game;
import org.sopra.api.Scenario;
import org.sopra.api.exercises.ExerciseSubmission;
import org.sopra.api.exercises.exercise1.ScenarioUtil;
import org.sopra.api.model.PlantLocation;
import org.sopra.api.model.PlayfieldElement.ElementType;
import org.sopra.api.model.PowerLine;
import org.sopra.api.model.PowerLineType;
import org.sopra.api.model.consumer.Consumer;
import org.sopra.api.model.consumer.ControllableConsumer;
import org.sopra.api.model.producer.BioGasFiredPowerPlant;
import org.sopra.api.model.producer.CoalFiredPowerPlant;
import org.sopra.api.model.producer.ControllableProducer;
import org.sopra.api.model.producer.GasFiredPowerPlant;
import org.sopra.api.model.producer.HydroPowerPlant;
import org.sopra.api.model.producer.NuclearPowerPlant;
import org.sopra.api.model.producer.Producer;
import org.sopra.api.model.producer.ProducerType;
import org.sopra.api.model.producer.SolarPowerPlant;
import org.sopra.api.model.producer.WindPowerPlant;
import org.sopra.exceptions.CannotAssignCommandException;
import org.sopra.exceptions.CannotExecuteCommandException;
import solutions.exercise1.ScenarioUtilImpl;

/**
 * Implements a scenario. Builds and Controls to get the most points over 1000 rounds.
 * @author Anton Herzog, Adrian Bohnstedt, Ammar Benazza, Laurent Brune
 * @version 1.0
 */
public class ScenarioImpl implements Game, ExerciseSubmission{
	
	/**
	 * Analyses scenario and can build new producers or power lines.
	 * @param scenario The given scenario
	 */
	@Override
	public void buildPhase(Scenario scenario) {
		//max last durch gas abdecken
		//hydro bauen auf flüssen maximal bis wir industrial parks abdecken
		//solar bauen auf Bergen max 100% restliche last abdecken
		//wind bauen neben beach, mountain, river
		
		ScenarioUtilImpl scenarioutilimpl = new ScenarioUtilImpl();
		
		//Lists of consumers, producers and plantlocations
		List<Consumer> consumers = scenarioutilimpl.getConsumers(scenario.getGraph());
		List<Producer> producers = scenarioutilimpl.getProducers(scenario.getGraph());
		List<PlantLocation> plantlocations = scenario.getPlantLocations();
		//Calculating the maximal possible powerconsumption
		int max_consumption = 0;
		for(Consumer c:consumers) {
			max_consumption += c.getMaximumEnergyLevel();
		}
		//Calculating the maximal energyproduction
		int max_production = 0;
		for(Producer p:producers) {
			max_production += p.getMaximumEnergyLevel();
		}
			
		//Calculating the maximal energy production for renewable Energysources
		int max_production_renewable = 0;
		for(Producer p:producers) {
			if(p instanceof SolarPowerPlant) {
				max_production_renewable += p.getMaximumEnergyLevel();
			}
			if(p instanceof WindPowerPlant) {
				max_production_renewable += p.getMaximumEnergyLevel();
			}
			if(p instanceof HydroPowerPlant) {
				max_production_renewable += p.getMaximumEnergyLevel();
			}
		}
		
		//Calculating the maximal energy production for fossil Energysources
		int max_production_fossil = 0;
		for(Producer p:producers) {
			if(p instanceof GasFiredPowerPlant) {
				max_production_fossil += p.getMaximumEnergyLevel();
			}
			if(p instanceof BioGasFiredPowerPlant) {
				max_production_fossil += p.getMaximumEnergyLevel();
			}
			if(p instanceof CoalFiredPowerPlant) {
				max_production_fossil += p.getMaximumEnergyLevel();
			}
			if(p instanceof NuclearPowerPlant) {
				max_production_fossil += p.getMaximumEnergyLevel();
			}
		}
		
		//Relation between renewablenergyproduction and fossilenergyproduction
		//we want the coefficent co to have a value of 1.5
		double co = (double)max_production_renewable/(double)max_production_fossil;
		
		//Building new Powerplants
		for(PlantLocation pl:plantlocations) {
			
			//plantlocation is free and we want to build renewable energyproducers
			if(!(pl.isBuilt()) && co<1.5) {
				if(pl.getPlayfieldElement().getElementType() == ElementType.RIVER) {
					try {
						scenario.getCommandFactory().createBuildPlantCommand(pl, ProducerType.HYDRO_POWER_PLANT).execute();
					} catch (CannotExecuteCommandException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(pl.getPlayfieldElement().getElementType() == ElementType.SEA) {
					try {
						scenario.getCommandFactory().createBuildPlantCommand(pl, ProducerType.WIND_POWER_PLANT).execute();
					} catch (CannotExecuteCommandException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(pl.getPlayfieldElement().getElementType() == ElementType.MOUNTAIN || pl.getPlayfieldElement().getElementType() == ElementType.BEACH || pl.getPlayfieldElement().getElementType() == ElementType.GRASSLAND) {
					try {
						scenario.getCommandFactory().createBuildPlantCommand(pl, ProducerType.SOLAR_POWER_PLANT).execute();
					} catch (CannotExecuteCommandException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			//plantlocation is free and according to co we want to build fossil energysources
			if(!(pl.isBuilt()) && co>1.5) {
				if(pl.getPlayfieldElement().getElementType() == ElementType.GRASSLAND) {
					try {
						scenario.getCommandFactory().createBuildPlantCommand(pl, ProducerType.GASFIRED_POWER_PLANT).execute();
					} catch (CannotExecuteCommandException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(pl.getPlayfieldElement().getElementType() == ElementType.RIVER) {
					try {
						scenario.getCommandFactory().createBuildPlantCommand(pl, ProducerType.COALFIRED_POWER_PLANT).execute();
					} catch (CannotExecuteCommandException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
		//Checking whether the current maximal energyproduction is high enough
		List<Producer> pro = scenarioutilimpl.getProducers(scenario.getGraph());
		int maxenergyproduction_new = 0;
		for(Producer p:pro) {
			maxenergyproduction_new += p.getMaximumEnergyLevel();
		}
		
		//if the current maximum energyproduction is too low, the remaining Plantocations will be filled with Solarppwerplants
		if(maxenergyproduction_new < max_consumption) {
			for(PlantLocation pl:plantlocations) {
				if(!(pl.isBuilt())) {
					try {
						scenario.getCommandFactory().createBuildPlantCommand(pl, ProducerType.SOLAR_POWER_PLANT).execute();
					} catch (CannotExecuteCommandException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
		//upgrading all powerlines to Highvoltage powerlines
		for(PowerLine pl:scenario.getGraph().getEdges()) {
			if(pl.getType()!= PowerLineType.HIGH_VOLTAGE)
				try {
					scenario.getCommandFactory().createUpgradeLineCommand(pl, PowerLineType.HIGH_VOLTAGE).execute();
				} catch(CannotExecuteCommandException e2) {
					throw new RuntimeException("problem in execution of this upgrade command");
				}
		 }
		
		//upgrade all of the powerlines to high voltage lines
		for(PowerLine p1:scenario.getGraph().getEdges()) {
			if(p1.getType() != PowerLineType.HIGH_VOLTAGE)
				try {
					scenario.getCommandFactory().createUpgradeLineCommand(p1, PowerLineType.HIGH_VOLTAGE).execute();
				} catch (CannotExecuteCommandException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	/**
	 * Executes the scenario and controls production and consumption
	 * @param scenario The given Scenario
	 * @param round The round that lasts one hour simulation time
	 */
	@Override
	public void executionPhase(Scenario scenario, int round) {
		//setup lists
		ScenarioUtil scenarioUtil = new ScenarioUtilImpl();
		List<Consumer> consumers = scenarioUtil.getConsumers(scenario.getGraph());
		List<ControllableConsumer> controllableConsumers = scenarioUtil.getControllableConsumers(scenario.getGraph());
		List<Producer> producers = scenarioUtil.getProducers(scenario.getGraph());
		List<ControllableProducer> controllableProducers = scenarioUtil.getControllableProducers(scenario.getGraph());
		List<ControllableProducer> gasPlants = new ArrayList<>();
		List<ControllableProducer> bioGasPlants = new ArrayList<>();;
		List<ControllableProducer> coalPlants = new ArrayList<>();;
		List<ControllableProducer> nuclearPlants = new ArrayList<>();
		//create Lists for gas, bioGas, coal and nuclear plants
		for(ControllableProducer cp: controllableProducers) {
			if(cp instanceof GasFiredPowerPlant) {
				gasPlants.add(cp);
			}
			if(cp instanceof BioGasFiredPowerPlant) {
				bioGasPlants.add(cp);
			}
			if(cp instanceof CoalFiredPowerPlant) {
				coalPlants.add(cp);
			}
			if(cp instanceof NuclearPowerPlant) {
				nuclearPlants.add(cp);
			}
		}
		//calculate total required power
		int requiredPower = 0;
		for(Consumer c:consumers) {
			requiredPower += c.getRequiredPower();
		}
		//calculate total production
		int providedPower = 0;
		for(Producer p:producers) {
			providedPower += p.getProvidedPower();
		}
		//difference
		int powerDiff = providedPower - requiredPower;
		int maxConsumedPower = 0;
		for(Consumer c:consumers) {
			maxConsumedPower += c.getMaximumEnergyLevel();
		}
		System.out.println("Max Consumed Power: " + maxConsumedPower);
		//calculate maximum solar power
		int maxSolarPower = 0;
		for(Producer p:producers) {
			if(p instanceof SolarPowerPlant) {
				maxSolarPower += p.getMaximumEnergyLevel();
			}
		}
		System.out.println("Max Solar Power: " + maxSolarPower);
		//time
		int time = round % 24;
		System.out.println("The Time is : " + time + ":00");
		//determine if turning off coal or nuclear during the day is needed
		boolean noticableSolar = true;
		boolean muchSolar = true;
		if(maxSolarPower*4 < maxConsumedPower) {
			muchSolar = false;
		}
		if(maxSolarPower*15 < maxConsumedPower) {
			noticableSolar = false;
		}
		System.out.println("noticeable solar: " + noticableSolar);
		System.out.println("much solar: " + muchSolar);
		//***************   nuclear day/ night cycle *********************
		for(ControllableProducer cp: nuclearPlants) {
			System.out.print("");
			int production = cp.getMaximumEnergyLevel();
			//turn on at 19:00
			if(time == 10  && cp.getEnergyLevel() == 0) {
				System.out.println("Name: " + cp.getName() + "    Current: " + cp.getEnergyLevel() + "    Max: " + cp.getMaximumEnergyLevel());
				try {
					scenario.getCommandFactory().createAdjustProducerCommand(cp, production).assign();
				} catch (CannotAssignCommandException e) {
					System.out.println("Turning on nuclear failed");
				}
			}
			//turn off at 9:00
			if(time == 23 && cp.getEnergyLevel() > 0 && muchSolar) {
				System.out.println("Name: " + cp.getName() + "    Current: " + cp.getEnergyLevel() + "    Max: " + cp.getMaximumEnergyLevel());
				try {
					scenario.getCommandFactory().createAdjustProducerCommand(cp, -production).assign();
				} catch (CannotAssignCommandException e) {
					System.out.println("Turning off nuclear failed");
				}
			}
		}
		//******************   coal cycle    ***********************
		for(ControllableProducer cp: coalPlants) {
			//turn on at 5:00
			if(time == 2  && cp.getEnergyLevel() == 0) {
				System.out.println("Name: " + cp.getName() + "    Current: " + cp.getEnergyLevel() + "    Max: " + cp.getMaximumEnergyLevel());
				try {
					scenario.getCommandFactory().createAdjustProducerCommand(cp, cp.getMaximumEnergyLevel()).assign();
				} catch (CannotAssignCommandException e) {
					System.out.println("Turning on coal failed");
				}
			}
			//turn off at 10:00
			if(time == 7  && cp.getEnergyLevel() > 0 && noticableSolar) {
				System.out.println("Name: " + cp.getName() + "    Current: " + cp.getEnergyLevel() + "    Max: " + cp.getMaximumEnergyLevel());
				try {
					scenario.getCommandFactory().createAdjustProducerCommand(cp, -cp.getMaximumEnergyLevel()).assign();
				} catch (CannotAssignCommandException e) {
					System.out.println("Turning off coal failed");
				}
			}
			//turn on at 16:00
			if(time == 13  && cp.getEnergyLevel() == 0) {
				System.out.println("Name: " + cp.getName() + "    Current: " + cp.getEnergyLevel() + "    Max: " + cp.getMaximumEnergyLevel());
				try {
					scenario.getCommandFactory().createAdjustProducerCommand(cp, cp.getMaximumEnergyLevel()).assign();
				} catch (CannotAssignCommandException e) {
					System.out.println("Turning on coal failed");
					e.printStackTrace();
				}
			}
			//turn off at 20:00
			if(time == 17  && cp.getEnergyLevel() > 0) {
				System.out.println("Name: " + cp.getName() + "    Current: " + cp.getEnergyLevel() + "    Max: " + cp.getMaximumEnergyLevel());
				try {
					scenario.getCommandFactory().createAdjustProducerCommand(cp, -cp.getMaximumEnergyLevel()).assign();
				} catch (CannotAssignCommandException e) {
					System.out.println("Turning off coal failed");
				}
			}
		}
		//-----------------------------------------------------------------------
		//TOO MUCH ENERGY -> first turn on consumers, second turn off producers
		//-----------------------------------------------------------------------
		if(powerDiff > 0) {
			//To Do :  maybe sort by cost
			//turn on Consumers
			for(ControllableConsumer cc: controllableConsumers) {
				if(powerDiff > 0 && !(cc.isAdjusting())) {
					int possibleAdditionalPower = cc.getMaximumEnergyLevel() - cc.getEnergyLevel();
					//shouldnt be higher than powerDiff
					if(possibleAdditionalPower > powerDiff) {
						possibleAdditionalPower = powerDiff;
					}
					try {
						scenario.getCommandFactory().createAdjustConsumerCommand(cc, possibleAdditionalPower).assign();
					} catch (CannotAssignCommandException e) {
						System.out.println("ERROR in turning on controllable Consumers");
					}
					//substract new consumption
					powerDiff = powerDiff-possibleAdditionalPower;
				}
				
			}
		}
		//turn off producers
		if(powerDiff > 0) {
			//turn off gas
			for(ControllableProducer cp: gasPlants) {
				if(powerDiff > 0 && !(cp.isAdjusting())) {
					int currentPower = cp.getEnergyLevel();
					int removePower = 0; //negative
					if(currentPower > powerDiff) {
						removePower = -powerDiff;
					}
					else {
						removePower = -currentPower;
					}
					try {
						scenario.getCommandFactory().createAdjustProducerCommand(cp, removePower).assign();
					} catch (CannotAssignCommandException e) {
						System.out.println("ERROR in turning off gas plant");
					}
					//dont add new negative Power to powerDiff calculation
					//powerDiff = powerDiff+removePower;
				}
			}
			//turn off bioGas
			for(ControllableProducer cp: bioGasPlants) {
				if(powerDiff > 0 && !(cp.isAdjusting())) {
					int currentPower = cp.getEnergyLevel();
					int removePower = 0; //negative
					if(currentPower > powerDiff) {
						removePower = -powerDiff;
					}
					else {
						removePower = -currentPower;
					}
					try {
						scenario.getCommandFactory().createAdjustProducerCommand(cp, removePower).assign();
					} catch (CannotAssignCommandException e) {
						System.out.println("ERROR in turning off bio gas plant");
					}
					//add new negative Power
					powerDiff = powerDiff+removePower;
				}
			}
		}
		//------------------------------------------------------------------------
		//NOT ENOUGH ENERGY -> first turn on producers, second turn off consumers
		//------------------------------------------------------------------------
		else if(powerDiff < 0) {
			//turn on producers
			if(powerDiff < 0) {
				//turn on gas
				for(ControllableProducer cp: gasPlants) {
					if(powerDiff < 0 && !(cp.isAdjusting())) {
						int possibleAdditionalPower = cp.getMaximumEnergyLevel() - cp.getEnergyLevel();
						//shouldnt be higher than the absolute value of powerDiff
						if(possibleAdditionalPower > -powerDiff) {
							possibleAdditionalPower = -powerDiff;
						}
						try {
							scenario.getCommandFactory().createAdjustProducerCommand(cp, possibleAdditionalPower).assign();
						} catch (CannotAssignCommandException e) {
							System.out.println("ERROR in turning on gas plant");
						}
						//dont add new consumption to powerDiff calculation
						powerDiff = powerDiff+possibleAdditionalPower;
					}
				}
				//turn on bioGas
				for(ControllableProducer cp: bioGasPlants) {
					if(powerDiff < 0 && !(cp.isAdjusting())) {
						int possibleAdditionalPower = cp.getMaximumEnergyLevel() - cp.getEnergyLevel();
						//shouldnt be higher than the absolute value of powerDiff
						if(possibleAdditionalPower > -powerDiff) {
							possibleAdditionalPower = -powerDiff;
						}
						try {
							scenario.getCommandFactory().createAdjustProducerCommand(cp, possibleAdditionalPower).assign();
						} catch (CannotAssignCommandException e) {
							System.out.println("ERROR in turning on bio gas plant");
						}
						//add new consumption
						powerDiff = powerDiff+possibleAdditionalPower;
					}
				}
			}
			//turn off controllable consumers
			if(powerDiff < 0) {
				for(ControllableConsumer cc: controllableConsumers) {
					if(powerDiff < 0 && !(cc.isAdjusting())) {
						int currentPower = cc.getEnergyLevel();
						int removePower = 0;
						if(currentPower > -powerDiff) {
							//negative
							removePower = powerDiff;
						}
						else {
							removePower = -currentPower;
						}
						try {
							scenario.getCommandFactory().createAdjustConsumerCommand(cc, removePower).assign();
						} catch (CannotAssignCommandException e) {
							System.out.println("ERROR in turning off consumers");
						}
						//substract new negative power
						powerDiff = powerDiff-removePower;
					}
				}
			}
		}
	}
	
	/**
	 * Returns the Team Identifier
	 * @return Team Identifier
	 */
	@Override
	public String getTeamIdentifier() {
		return "G05T01";
	}
	
}
