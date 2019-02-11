import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a lion.
 * Lions age, move, eat zebras, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Lion extends Animal
{
    // Characteristics shared by all lions (class variables).

    // The age at which a lion can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a lion can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.1
    ;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The lion's age.
    private int age;
    // The lion's food level, which is increased by eating .
   private static final int WAKE_TIME = 11;//(wake at 5 AM)
    // The age to which a zebra can live.
    private static final int SLEEP_TIME = 15;//(Sleep at 7 AM)

    /**
     * Create a lion. A lion can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the lion will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lion(boolean randomAge, Field field, Location location)
    {
        super(field, location,"lion",5);
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
    public Lion(boolean randomAge)
    {
        super("lion");

        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * This is what the lion does most of the time: 
     */
    public void live(List<Organism> newAnimals)
    {
        if (Simulator.getTime() <= SLEEP_TIME && Simulator.getTime()>=WAKE_TIME)
        {
            act(newAnimals);
        }

    }
    
    
    
    
    

    /**
     * Increase the age. This could result in the lion's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this lion more hungry. This could result in the lion's death.
     */
    

   
    /**
     * Check whether or not this lion is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born lions.
     */

    protected void giveBirth(List<Organism> newLions)
    {

        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Lion  young = new Lion(false, field, loc);
            newLions.add(young);
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
     * A lion can breed if it has reached the breeding age.
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
        setEdible(new Zebra(false));
        //setEdible(new Grass());
    }
}
