import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A simple model of a zebra.
 * Zebras age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Zebra extends Animal
{
    // Characteristics shared by all zebras (class variables).

    // The age at which a zebra can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a zebra can live.
    private static final int MAX_AGE = 4000;
    // The likelihood of a zebra breeding.
    private static final double BREEDING_PROBABILITY = 0.05;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    private static final int WAKE_TIME = 11;//(wake at 5 AM)
    // The age to which a zebra can live.
    private static final int SLEEP_TIME = 14;//(Sleep at 7 AM)
    
    

    // Individual characteristics (instance fields).
    // The zebra's age.
    private int age;

    /**
     * Create a new zebra. A zebra may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the zebra will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Zebra(boolean randomAge, Field field, Location location)
    {
        super(field, location,"zebra",20);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            setFoodLevel(rand.nextInt(30));
        }
        else {
            age = 0;
            setFoodLevel(20);
        }
        setPrey();
    }
    
     public void live(List<Organism> newAnimals)
    {
        if ( Simulator.getTime() <= SLEEP_TIME && Simulator.getTime()>=WAKE_TIME)
        {
            act(newAnimals);
        }

    }
    

    public Zebra(boolean randomAge)
    {
        super("zebra");

        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

  

    /**
     * Increase the age.
     * This could result in the zebra's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this zebra is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newZebras A list to return newly born zebras.
     */
    protected void giveBirth(List<Organism> newZebras)
    {
        // New zebras are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Zebra young = new Zebra(false, field, loc);
            newZebras.add(young);
        }
    }
    
    
    
  

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A zebra can breed if it has reached the breeding age.
     * @return true if the zebra can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    
    
    

    /**
     * Adds animals to the list of "prey" for the current animal.
     */
    private void setPrey()
    {
       setEdible(new Grass());
    }
}

