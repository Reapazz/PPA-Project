import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
/**
 * Abstract class Organism - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Organism
{
    // instance variables - replace the example below with your own

    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;

    private List<Organism> edible;

    private String name;

    private int foodValue;

    public Organism(Field field, Location location, String name, int food)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        edible = new ArrayList<>();
        this.name = name;
        this.foodValue = food;  

    }

    public Organism(String name)
    {
        alive = true;       
        edible = new ArrayList<>();
        this.name = name;

    }

    abstract public void act(List<Organism> newOrganism);

    abstract protected void live(List<Organism> newOrganism);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    protected Organism getEdible(int index)
    {
        return edible.get(index);
    }

    protected void setEdible(Organism organism)
    {
        edible.add(organism);
    }

    protected int getEdibleSize()
    {
        return edible.size();
    }

    protected String getName()
    {
        return name;
    }

    protected int getFoodValue()
    {
        return foodValue;
    }
}
