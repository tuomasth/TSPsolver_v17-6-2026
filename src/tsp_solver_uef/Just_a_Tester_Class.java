package tsp_solver_uef;
import java.util.*;

/**
 * TSP Solver by Tuomas Hyvönen, Java file 2 of 7 (unnecessary tester class) 
 * 
 * Extra Java main method for testing purposes. Does not create a new User Interface window. 
 * If used, disable the main method in "TSP_Solver_UEF.java" first. That creates 
 * a new UI every single time, unlike this Java file. 
 * 
 * Open source Java code, feel free to edit and try your own improvements. 
 * Tested with Windows 11 
 * Apache NetBeans 
 * Java JDK 17.0.12 64bit 
 * 
 * @author Tuomas Hyvönen 
 * @version 2.4
 */
public class Just_a_Tester_Class {
    
    /**
     * Possible main method when testing single details, rename to "main", 
     * call whatever methods you want and test them!
     * 
     * @param args String[]
     */
    public static void previousmain(String args[]) {
        // random testing, never mind 
        Point p1 = new Point(1.0, 1.1);  
        Point p2 = new Point(1.2, 9.1);  
        Point p3 = new Point(8.0, 9.1);  
        Point p4 = new Point(8.2, 1.1);  
        Point p5 = new Point(2.0, 3.1);  
        Point p6 = new Point(3.2, 6.1);  
        Point p7 = new Point(2.0, 4.1);  
        Point p8 = new Point(5.2, 5.1);  
        Point p9 = new Point(6.0, 3.1);  
        Point p10 = new Point(6.2, 7.1); 
        
        ArrayList<Double> a_list = new ArrayList<>(Arrays.asList(
                p1.getX(), p1.getY(), 
                p2.getX(), p2.getY(), p2.getX(), p2.getY(),
                p3.getX(), p3.getY(), p3.getX(), p3.getY(), 
                p4.getX(), p4.getY(), p4.getX(), p4.getY(),
                p5.getX(), p5.getY(), p5.getX(), p5.getY(), 
                p6.getX(), p6.getY(), p6.getX(), p6.getY(),
                p7.getX(), p7.getY(), p7.getX(), p7.getY(), 
                p8.getX(), p8.getY(), p8.getX(), p8.getY(),
                p9.getX(), p9.getY(), p9.getX(), p9.getY(),
                p10.getX(), p10.getY(), p10.getX(), p10.getY(),
                p1.getX(), p1.getY())
        );
        double euc = 0.0;
        for(int i = 0; i < a_list.size()-2; i+=2) {
            euc += Sub_algorithms.Euclidean_distance((double)a_list.get(i), 
                                         (double)a_list.get(i+1), 
                                         (double)a_list.get(i+2), 
                                         (double)a_list.get(i+3));
        }
        
        ArrayList newlist = Sub_algorithms.threeOptQuick(a_list, euc);
        System.out.println(newlist.toString());
        boolean b = Sub_algorithms.smaller_angle(1,2,3,4,5,6);
        System.out.println(b);
        
        System.out.println("\n-------------------\n");
        System.out.println(a_list.toString());
        Sub_algorithms.reverse(a_list, 4, 8);
        System.out.println(a_list.toString());
    }
} 