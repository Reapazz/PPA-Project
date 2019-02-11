import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Organism
{
    private boolean isMale; 
    private Random sex;       
    private int foodLevel;

    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location,String name, int food)
    {
        super(field, location,name,food);
        sex = new Random();
        this.isMale = sex.nextBoolean();

    }

    public Animal(String name)
    {
        super(name);
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    
    abstract public void live(List<Organism> newOrganism);
    
    
    
    public void act(List<Organism> newAnimals)
    {

        incrementAge();
        incrementHunger();
        if(isAlive()) {
            checkForPartner(newAnimals);
            //giveBirth(newLions);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

   
    protected abstract void incrementAge();
  
    protected void checkForPartner(List<Organism> newAnimals){
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);

            if(animal instanceof Animal){
                Animal mate = (Animal) animal;

                if(mate.getClass().equals(this.getClass())) {
                    if(this.getSex() ==! mate.getSex()){

                        giveBirth(newAnimals);

                    }

                }
            }
        }
    }

    protected abstract void giveBirth(List<Organism> newAnimals);

    /**
     * Look for animals adjacent to the current location.
     * If the animal is prey and is alive, only the first one is eaten (killed).
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object organism = field.getObjectAt(where);
            if(organism instanceof Organism) {
                 Organism prey = (Organism) organism;

                for(int i=0; i<getEdibleSize(); i++){
                    if(getEdible(i).getName().equals(prey.getName()) && prey.isAlive()){
                        prey.setDead();
                        setFoodLevel(prey.getFoodValue());
                        return where;
                    }
                }                            
            }
        }
        return null;
    }
   
    

    protected void incrementHunger()
    {
        setFoodLevel(getFoodLevel())GETcHNAGEHUNGER());
        if(getFoodLevel() <= 0) {
            setDead();
        }
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */

    protected boolean getSex()
    {
        return isMale;
    }

    protected int getFoodLevel()
    { 
        return foodLevel;  
    }

    protected void setFoodLevel(int level)
    { 
        foodLevel = level; 
    }

}
