import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing zebras and lions.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a lion will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.01;
    // The probability that a zebra will be created in any given grid position.
    private static final double ZEBRA_CREATION_PROBABILITY = 0.06;    
    private static final double GRASS_CREATION_PROBABILITY = 0.04;  

    //List of all possible weather patterns

    private List<String> weather;    
    private static String currentWeather;
    private static int HUNGER_MODIFIER;
    private static  double FERTILITY_MODIFIER;
    private static int SLEEP_MODIFIER;

    // List of animals in the field.
    private List<Organism> organisms;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    private static long time= 0 ;
    private boolean isDay;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
        addWeather();

    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        organisms = new ArrayList<>();
        field = new Field(depth, width);
        addWeather();
        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Zebra.class, Color.ORANGE);
        view.setColor(Lion.class, Color.BLUE);
        view.setColor(Grass.class, Color.GREEN);

        // Setup a valid starting point.
        reset();

    }

    public static void main(String args[])
    {
        Simulator s = new Simulator();
        s.populate();
        s.runLongSimulation();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(10); // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * lion and zebra.
     */
    public void simulateOneStep()
    {
        step++;
        time++;
        time = time % 24;
        if (time == 0 )
        {
            chooseWeather();
        }
        // Provide space for newborn animals.
        List<Organism> newOrganisms = new ArrayList<Organism>();        
        // Let all zebras act.
        for(Iterator<Organism> it = organisms.iterator(); it.hasNext(); ) {
            Organism organisms = it.next();
            organisms.live(newOrganisms);
            if(! organisms.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born lions and zebras to the main lists.
        organisms.addAll(newOrganisms);

        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        organisms.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    public Field getSimField()
    {
        return field;
    }

    /**
     * Randomly populate the field with lions and zebras.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lion lion = new Lion(true, field, location);
                    organisms.add(lion);
                }
                else if(rand.nextDouble() <= ZEBRA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zebra zebra = new Zebra(true, field, location);
                    organisms.add(zebra);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(field, location);
                    organisms.add(grass);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public static long getTime() 
    {
        return time;

    }

    public static String timeToString()
    {String output;
        if (time < 10){
            output ="0"+time+":00";
        }
        else{
            output = getTime() + ":00";}
        return output;

    }

    public void addWeather (){
        weather.add("fog");
        weather.add("cold");
        weather.add("sun");
        weather.add("snow");

    }

    public String chooseWeather()
    {
        addWeather();
        Random rand = new Random(3);
        int i = rand.nextInt() ;
        currentWeather = weather.get(i);
        return currentWeather;

    }

    public void changeWeatherConditions()
    {
        if(currentWeather.equals("sun")){
            resetWeather();        
        }
        else if(currentWeather.equals("fog")){
            resetWeather();
            SLEEP_MODIFIER=1;
        }
        else if(currentWeather.equals("cold")){
            resetWeather();
            HUNGER_MODIFIER =3;
            FERTILITY_MODIFIER=1.0;
        }
        else if(currentWeather.equals("snow")){
            resetWeather();
            HUNGER_MODIFIER =3;
            FERTILITY_MODIFIER=1.0;
            SLEEP_MODIFIER=1;
        }
    }

    public void resetWeather()
    {
        HUNGER_MODIFIER =0;
        FERTILITY_MODIFIER=0.0;
        SLEEP_MODIFIER=0;
    }
    
    public static int getHungerModifier()
    {
        return HUNGER_MODIFIER;
    }
    
    public static double getFertilityModifier()
    {
        return FERTILITY_MODIFIER;
    }
    
    public static int getSleepModifier()
    {
        return SLEEP_MODIFIER;
    } 
    
    public static String getCurrentWeather()
    {
        return currentWeather;
    }
}
