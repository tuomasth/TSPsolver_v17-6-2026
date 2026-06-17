package tsp_solver_uef;

/**
 * TSP Solver by Tuomas Hyvönen, Java file 4 of 7 
 * 
 * A class for creating a Cartesian x-y-point instance. Not used much if arrays[] and ArrayLists are used instead.
 * 
 * Open source Java code, feel free to edit and try your own improvements. 
 * Tested with Windows 11 
 * Apache NetBeans 
 * Java JDK 17.0.12 64bit 
 * 
 * @author Tuomas Hyvönen 
 * @version 2.3
 */
public class Point {
    private double x;
    private double y;
    private double weight;
    // (Euclidean distances or "vector" lengths can be calculated anytime for any 2 points, check the Sub_algorithms.java) 
    
    /**
     * The constructor.
     * 
     * @param x double
     * @param y double
     */
    public Point(double x, double y) {
        if(((x < 5000000) && (y < 5000000)) && 
           ((x > -1) && (y > -1))) {
            this.x = x;
            this.y = y;
            this.weight = 1.0;
        }
        else {
            System.out.println("Coordinates should be less than 5 000 000 and more than -1");
        }
    } 
    
    /**
     * The getter method for X.
     * 
     * @return x double
     */
    public double getX() {
        //printPoint(p);
        return this.x;
    }
    
    /**
     * The getter method for Y.
     * 
     * @return y double
     */
    public double getY() {
        //printPoint(p);
        return this.y;
    }
    
    /**
     * The setter method for point object.
     * 
     * @param x double
     * @param y double
     */
    public void setPoint(double x, double y) {
        if(((x < 5000000) && (y < 5000000)) && 
           ((x > -1) && (y > -1))) {
                this.x = x;
                this.y = y;
        }
        else {
            System.out.println("Coordinates should be less than 5 000 000 and more than -1");
        }
    }
    
    /**
     * Print a point's information.
     * 
     * @param p Point
     */
    public void printPoint(Point p) {
        System.out.println("POINT WEIGHT AND X&Y ARE " + p.weight + "; " 
                + p.x + ", " + p.y + "\n-- " + p.toString() + "\n");
    }
    
    /**
     * If a point needs a priority or a special price, this setter method will set a weight.
     * 
     * @param weight double
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    /**
     * Returns the weight of a point, has nothing to do with coordinates.
     * 
     * @return weight double
     */
    public double getWeight() {
        return this.weight;
    }
} 