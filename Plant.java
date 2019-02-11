import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
/**
 * Write a description of class Plant here.
 *
 * @author (your name)
 * @version (a version number or a date)
 * 
 * 
 * 
 * 
 */

public abstract class Plant extends Organism
{

    /**
     * Constructor for objects of class Plant
     */
    public Plant(Field field, Location location,String name, int food)
    {
        super(field,location, name ,food);

    }

    public Plant(String name)
    {
        super(name);
    }

    
    protected abstract void grow(List<Organism> newPlant);
    
    protected abstract void live(List<Organism> newOrganism);
    
    public void act(List<Organism> newPlants) 
    {
        grow(newPlants);

    }

    
    
}
    
    
