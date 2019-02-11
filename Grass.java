import java.util.List;
import java.util.Random;
import java.util.Iterator;
/**
 * Write a description of class Grass here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Grass extends Plant
{
    // instance variables - replace the example below with your own
    // instance variables - replace the example below with your own
    private static final Random rand = Randomizer.getRandom();

    private static final double BREEDING_PROBABILITY = 0.008;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;

    private static final int WAKE_TIME = 1;//(Start growth at 1 AM)
    // The age to which a zebra can live.
    private static final int SLEEP_TIME = 10;//(Sleep at 7 AM)

    /**
     * Constructor for objects of class Grass
     */

    public Grass(Field field, Location location)
    {
        super(field, location,"grass",15);

    }

    public Grass()
    {
        super("grass");
    }

    protected void live(List<Organism> newPlants)
    {
        if ( Simulator.getTime() <= SLEEP_TIME && Simulator.getTime()>=WAKE_TIME)
        {

            act(newPlants);
        }

    }

    protected void grow(List<Organism> newPlants)
    {
        Field field = getField();        
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grass young = new Grass(field,loc);
            newPlants.add(young);
        }
    
    }

    private int breed()
    {
        int births = 0;
        if(rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
}
