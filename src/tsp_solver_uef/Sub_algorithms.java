package tsp_solver_uef;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * TSP Solver by Tuomas Hyvönen, Java file 5 of 7
 * 
 * The sub algorithm class with lots of tools such as the Euclidean distance, 
 * the minimum spanning tree and the convex hull. 
 * 
 * Open source Java code, feel free to edit and try your own improvements. 
 * Tested with Windows 11 
 * Apache NetBeans 
 * Java JDK 17.0.12 64bit 
 * 
 * @author Tuomas Hyvönen 
 * @version 2.3
 */
public class Sub_algorithms {
    
    /**
     * Calculate real Euclidean distance. 
     * 
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     * @return d double
     */
    public static double Euclidean_distance(
            double x1, double y1, double x2, double y2) {
        double d = Math.sqrt(((x2 - x1)*(x2 - x1))+((y2 - y1)*(y2 - y1)));
        return d;
    }
    
    /**
     * Calculate Euclidean distance without square root. A bit less processing time. 
     * 
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     * @return d double
     */
    public static double Euclidean_distance_squared(
            double x1, double y1, double x2, double y2) {
        double d = ((x2 - x1)*(x2 - x1))+((y2 - y1)*(y2 - y1));
        return d;
    }
    
    /**
     * Generate the convex hull with Graham scan. 
     * 
     * PhD Ronald Lewis Graham (Oct 31, 1935 – Jul 6, 2020) - Your scientific career will be remembered. 
     * It was an honour to re-implement this algorithm (as well as for example the Kohonen SOM to another class).
     * 
     * @param min integer
     * @param max integer
     * @param x_coordinates ArrayList
     * @param y_coordinates ArrayList
     * @return res String
     */
    public static String ConvexHull(int min, int max, 
            ArrayList x_coordinates, ArrayList y_coordinates) {
        double tempX;
        double tempY;
        for(int i = 0; i < y_coordinates.size()-1; i++) {
            for(int j = i+1; j < y_coordinates.size(); j++) {
                if((double)y_coordinates.get(i) > (double)y_coordinates.get(j)) {
                    tempX = (double)x_coordinates.get(j);
                    tempY = (double)y_coordinates.get(j);
                    x_coordinates.set(j, (double)x_coordinates.get(i));
                    y_coordinates.set(j, (double)y_coordinates.get(i));
                    x_coordinates.set(i, tempX);
                    y_coordinates.set(i, tempY);
                }
                if((double)x_coordinates.get(i) < (double)x_coordinates.get(j)) {
                    tempX = (double)x_coordinates.get(j);
                    tempY = (double)y_coordinates.get(j);
                    x_coordinates.set(j, (double)x_coordinates.get(i));
                    y_coordinates.set(j, (double)y_coordinates.get(i));
                    x_coordinates.set(i, tempX);
                    y_coordinates.set(i, tempY);
                }
            }
        }
        
        double[][][] result = new double[max][2][2];  // pointer, isY?, isEnd? 
        String[][][] result2;
        double[][] handleVerticesInThisOrder = new double[max][2];
        double temp_max_x = Double.MIN_VALUE;
        for(int i = 0; i < max; i++) {  // search for the rightmost vertice
            if((double)x_coordinates.get(i) > temp_max_x){
                temp_max_x = (double)x_coordinates.get(i);
            }
        }
        double temp_min_y = Double.MAX_VALUE;
        int index_of_downmost_of_rightmosts = -1;
        for(int i = 0; i < max; i++) {
            if((double)x_coordinates.get(i) == temp_max_x) {
                if((double)y_coordinates.get(i) < temp_min_y) {
                    temp_min_y = (double)y_coordinates.get(i);
                    index_of_downmost_of_rightmosts = i;
                }
            }
        }
        
        // calculate the slopes by comparing every other point 
        // with the first ("rightmostdownmost") point 
        // (y2 - y1) / (x2 - x1) from smallest to biggest 
        //                       but first the ones with same x coordinates 
        //                       and from min y to max y 
        
        for(int j = 0; j < max-1; j++) {
            int tempminindex = j;
            for(int k = j+1; k < max; k++) {
                if((double)y_coordinates.get(k) < (double)y_coordinates.get(tempminindex)) {
                    if((double)x_coordinates.get(k) == temp_max_x) {
                        tempminindex = k;
                    }
                }
            }
            if(tempminindex != j && (double)x_coordinates.get(j) == temp_max_x) {   
            // if a change to tempminindex was made, swap 
                if((double)y_coordinates.get(tempminindex) < 
                   (double)y_coordinates.get(index_of_downmost_of_rightmosts)) {
                    double pivot;

                    pivot = (double)y_coordinates.get(tempminindex);
                    y_coordinates.set(tempminindex, y_coordinates.get(j));
                    y_coordinates.set(j, pivot);

                    pivot = (double)x_coordinates.get(tempminindex);
                    x_coordinates.set(tempminindex, x_coordinates.get(j));
                    x_coordinates.set(j, pivot);
                    
                    index_of_downmost_of_rightmosts = tempminindex;
                }
            }
        } 
        //System.out.println("\n\n\nDownmost of rightmosts is " + 
        //   (double) x_coordinates.get(index_of_downmost_of_rightmosts) + 
        //   ", " + (double) y_coordinates.get(index_of_downmost_of_rightmosts));
        
        handleVerticesInThisOrder[0][0] = 
                (double) x_coordinates.get(index_of_downmost_of_rightmosts);
        handleVerticesInThisOrder[0][1] = 
                (double) y_coordinates.get(index_of_downmost_of_rightmosts);
        
        // take the max Xs coordinates and after that,
        // calculate slopes and take them also
        int index_with_handleVerticesInThisOrder = 0;
        for(int i = 0; i < max; i++) {
            if((double)x_coordinates.get(i) == temp_max_x){
                handleVerticesInThisOrder[index_with_handleVerticesInThisOrder]
                        [0] = (double)x_coordinates.get(i);
                handleVerticesInThisOrder[index_with_handleVerticesInThisOrder]
                        [1] = (double)y_coordinates.get(i);
                index_with_handleVerticesInThisOrder++;
            }
        }
        boolean no_slope = false;
        for(int j = 0; j < max-1; j++) {
            if((double)x_coordinates.get(j) != temp_max_x) {
                
                double slope_min = ((double)y_coordinates.get(j) - 
                        (double)y_coordinates.get(index_of_downmost_of_rightmosts)) / 
                                   ((double)x_coordinates.get(j) - 
                        (double)x_coordinates.get(index_of_downmost_of_rightmosts));
                int temp_min_index = j;
                for(int k = j+1; k < max; k++) {
                    double slope = ((double)y_coordinates.get(k) - 
                            (double)y_coordinates.get(index_of_downmost_of_rightmosts)) / 
                                   ((double)x_coordinates.get(k) - 
                            (double)x_coordinates.get(index_of_downmost_of_rightmosts));
                    if(slope < slope_min && no_slope == false) {
                        if((double)x_coordinates.get(k) != temp_max_x) {
                            slope_min = slope;
                            temp_min_index = k;
                            //System.out.println("slope_min = " + 
                            //slope_min + " between (" + 
                            //        (double)x_coordinates.get(temp_min_index) 
                            //+ ", " + 
                            //        (double)y_coordinates.get(temp_min_index) 
                            //+ ") and Downmost of rightmost");
                        }
                    }
                    no_slope = false;
                }
                if(temp_min_index != j && 
                        (double)x_coordinates.get(j) != temp_max_x ) {
                    double temp;
                    temp = (double)y_coordinates.get(temp_min_index);
                    y_coordinates.set(temp_min_index, y_coordinates.get(j));
                    y_coordinates.set(j, temp);
                    temp = (double)x_coordinates.get(temp_min_index);
                    x_coordinates.set(temp_min_index, x_coordinates.get(j));
                    x_coordinates.set(j, temp);
                    //System.out.println("swapped " + x_coordinates.get(temp_min_index) + 
                    //", " + y_coordinates.get(temp_min_index) + 
                    //        " AND " + x_coordinates.get(j) + ", " + y_coordinates.get(j));
                    //System.out.println("\n\n\ndownmost of rightmosts is " + 
                    //(double) x_coordinates.get(index_of_downmost_of_rightmosts) + 
                    //", " + (double) y_coordinates.get(index_of_downmost_of_rightmosts));
                }
            }
        }
        for(int i = 0; i < max; i++) {
            if((double)x_coordinates.get(i) != temp_max_x ) {
                handleVerticesInThisOrder[index_with_handleVerticesInThisOrder][0] = 
                        (double)x_coordinates.get(i);
                handleVerticesInThisOrder[index_with_handleVerticesInThisOrder][1] = 
                        (double)y_coordinates.get(i);
                index_with_handleVerticesInThisOrder++;
            }
        }
        for(int i = 0; i < handleVerticesInThisOrder.length -1; i++) {
            for(int j = i; j < handleVerticesInThisOrder.length; j++) {
                // if some slopes are equal (or there was no slope), sort by distance 
                // (y2 - y1) / (x2 - x1) 
                double slope1 = (handleVerticesInThisOrder[i][1] - 
                        (double)y_coordinates.get(index_of_downmost_of_rightmosts)) / 
                                (handleVerticesInThisOrder[i][0] - 
                        (double)x_coordinates.get(index_of_downmost_of_rightmosts)); 
                double slope2 = (handleVerticesInThisOrder[j][1] - 
                        (double)y_coordinates.get(index_of_downmost_of_rightmosts)) / 
                                (handleVerticesInThisOrder[j][0] - 
                        (double)x_coordinates.get(index_of_downmost_of_rightmosts));
                if(slope1 == slope2) {
                    double distance1 = Euclidean_distance_squared( 
                        // x1, y1, x2, y2 
                            (double)x_coordinates.get(index_of_downmost_of_rightmosts), 
                            (double)y_coordinates.get(index_of_downmost_of_rightmosts), 
                            handleVerticesInThisOrder[i][0], 
                            handleVerticesInThisOrder[i][1]);
                    double distance2 = Euclidean_distance_squared( 
                            (double)x_coordinates.get(index_of_downmost_of_rightmosts), 
                            (double)y_coordinates.get(index_of_downmost_of_rightmosts), 
                            handleVerticesInThisOrder[j][0], 
                            handleVerticesInThisOrder[j][1]);
                    //System.out.println("slope1: " + slope1 + ", slope2: " + slope2);
                    if((distance1 < distance2) &&   // vai > ? 
                        (handleVerticesInThisOrder[i][0] != temp_max_x) && 
                        (handleVerticesInThisOrder[j][0] != temp_max_x)) {
                        // swap "handleVerticesInThisOrder[i][0], 
                        //       handleVerticesInThisOrder[i][1]" 
                        //      and "handleVerticesInThisOrder[j][0], 
                        //           handleVerticesInThisOrder[j][1]" 
                        double temp;
                        temp = handleVerticesInThisOrder[i][0];
                        handleVerticesInThisOrder[i][0] = handleVerticesInThisOrder[j][0];
                        handleVerticesInThisOrder[j][0] = temp;
                        temp = handleVerticesInThisOrder[i][1];
                        handleVerticesInThisOrder[i][1] = handleVerticesInThisOrder[j][1];
                        handleVerticesInThisOrder[j][1] = temp;
                    }
                }
            }
        }
        /*for (double[] handleVerticesInThisOrder1 : handleVerticesInThisOrder){
            System.out.println("handleVerticesInThisOrder: " 
                    + handleVerticesInThisOrder1[0] + ", " + 
                    handleVerticesInThisOrder1[1]);
        }*/
        // the sorting part ends here
    
        // handle vertices and if not a counter-clockwise turn or 
        // 0 (straight line), repair connections 
        DoubleStack hulls_x_coordinates = new DoubleStack();
        DoubleStack hulls_y_coordinates = new DoubleStack();
        hulls_x_coordinates.push(handleVerticesInThisOrder[0][0]); // index, isY? 
        hulls_y_coordinates.push(handleVerticesInThisOrder[0][1]);
        hulls_x_coordinates.push(handleVerticesInThisOrder[1][0]);
        hulls_y_coordinates.push(handleVerticesInThisOrder[1][1]);
        
        for(int i = 2; i < max; i++) {
            double top_x = hulls_x_coordinates.top();
            hulls_x_coordinates.pop();
            double top_y = hulls_y_coordinates.top();
            hulls_y_coordinates.pop();
            
            while ((counterClockwiseTurn(hulls_x_coordinates.top(), 
                    hulls_y_coordinates.top(), 
                    top_x, top_y, handleVerticesInThisOrder[i][0], // discard if clockwise (-1) 
                    handleVerticesInThisOrder[i][1]) <= 0 && 
                    hulls_y_coordinates.isEmpty() == false)
                    && (top_x != handleVerticesInThisOrder[0][0] && top_y != handleVerticesInThisOrder[0][1])
                    ){ 
                        top_x = hulls_x_coordinates.top();
                        hulls_x_coordinates.pop();
                        top_y = hulls_y_coordinates.top();
                        hulls_y_coordinates.pop();
            }
            hulls_x_coordinates.push(top_x);
            hulls_y_coordinates.push(top_y);
            hulls_x_coordinates.push(handleVerticesInThisOrder[i][0]);
            hulls_y_coordinates.push(handleVerticesInThisOrder[i][1]);
        }
        for(int i = 0; i < max; i++) {
            result[i][0][0] = hulls_x_coordinates.top();
            hulls_x_coordinates.pop();
            result[i][1][0] = hulls_y_coordinates.top(); 
            hulls_y_coordinates.pop();
        }
        // generate a cycle of the vertices 
        int lastIndex = 0;
        for(int i = 0; i < max -1; i++) {
            if(result[i+1][0][0] < Double.MAX_VALUE && 
                    result[i+1][1][0] < Double.MAX_VALUE){
                lastIndex = i+1;
                result[i][0][1] = result[i+1][0][0]; // index, isY, isEnd 
                result[i][1][1] = result[i+1][1][0];
            }
        }
        // complete the cycle:
        result[lastIndex][0][1] = result[0][0][0];
        result[lastIndex][1][1] = result[0][1][0];
        
        result2 = new String[lastIndex+1][2][2];
        
        for(int i = 0; i < max; i++) {
            if(result[i][0][1] < Double.MAX_VALUE && 
                    result[i][1][1] < Double.MAX_VALUE && 
                    result[i][0][0] < Double.MAX_VALUE && 
                    result[i][1][0] < Double.MAX_VALUE){
                if(i < lastIndex+1) {
                    result2[i][0][0] = String.valueOf(result[i][0][0]);
                    result2[i][0][1] = String.valueOf(result[i][1][0]);
                    result2[i][1][0] = String.valueOf(result[i][0][1]);
                    result2[i][1][1] = String.valueOf(result[i][1][1]);
                }
            }
        }
        String res = Arrays.deepToString(result2);
        //System.out.println(Arrays.deepToString(result2) + ", lastIndex is " + lastIndex);
        return res;
    }
    
    /**
     * This method checks if a turn with 3 points is 
     * counterclockwise ( more than 0, return +1 ), 
     * clockwise ( less than 0, return -1 ) or 
     * collinear ( =0, return 0 ),
     * called by Graham Convex Hull.
     * 
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     * @param x3 double
     * @param y3 double
     * @return ret_value integer
     */
    public static int counterClockwiseTurn(double x1, double y1, 
                                           double x2, double y2, 
                                           double x3, double y3) {
        int ret_value = -2;
        try {
            int area2 = (int) (((x2 - x1)*(y3 - y1)) - ((y2 - y1)*(x3 - x1)));
            if(area2 < 0) {
                ret_value =  -1;    //cw
            }
            else {
                if(area2 > 0) {
                    ret_value =  1; //ccw
                }
                else {
                    ret_value = 0;
                }
            }
        }
        catch(Exception e) {
            System.err.println(e);
        }
        return ret_value;
    }
    
    /**
     * Checks if the first point has a smaller "angle" than the second point, 
     * from Skiena and Revilla (2003) page 318.Programming Challenges, The Programming Contest Training Manual. 
     * Unused.
     * 
     * @param d1 double
     * @param d2 double
     * @param d3 double
     * @param d4 double
     * @param d5 double
     * @param d6 double
     * @return 
     */
    public static boolean smaller_angle(double d1, double d2, 
            double d3, double d4, double d5, double d6) {
        if(counterClockwiseTurn(d1, d2, d3, d4, d5, d6) == 0) {
            return Euclidean_distance_squared(d1, d2, d3, d4) > 
                   Euclidean_distance_squared(d1, d2, d5, d6);
        }
        return counterClockwiseTurn(d1, d2, d3, d4, d5, d6) != 1;
    }
    
    /**
     * Create a minimal spanning tree with Prim's algorithm.
     * Prim adds edges to the existing tree, Kruskal would just add anywhere until the result is MST.
     * 
     * @param min integer
     * @param max integer
     * @param x_coordinates ArrayList
     * @param y_coordinates ArrayList
     * @return result double[][][]
     */
    public static double[][][] MST_Prim(int min, int max, 
            ArrayList x_coordinates, ArrayList y_coordinates) {
        double[][][] result = new double[20000][2][2];  // pointer, isY?, isEnd? 
        Random rand = new Random();                     // though this is not the best way to store edges 
        int random = rand.nextInt((max - min) + 1) + min;
        boolean[] booltable = new boolean[max];
        result[0][0][0] = (double) x_coordinates.get(random -1);
        result[0][1][0] = (double) y_coordinates.get(random -1);
        int pointer = 0; 
        //int pointer_start = random -1;
        double X1 = 0;
        double Y1 = 0;
        double X2 = 0;
        double Y2 = 0;
        for(int i = 1; i < max; i++) {
            // check every connected node and find a min distance to 
            // the next unconnected node 
            booltable[random - 1] = true;
            double min_distance = Double.MAX_VALUE;
            double temp_distance;
            for(int h = 0; h < booltable.length; h++) {
                for(int j = 0; j < booltable.length; j++) {
                    if(booltable[j] == false && booltable[h] == true) {
                        double x1 = (double) x_coordinates.get(h);
                        double y1 = (double) y_coordinates.get(h);
                        double x2 = (double) x_coordinates.get(j);
                        double y2 = (double) y_coordinates.get(j);
                        temp_distance = Sub_algorithms.
                                Euclidean_distance_squared(x1, y1, x2, y2);
                        if(temp_distance < min_distance) {
                            min_distance = temp_distance;
                            pointer = j;
                            X1 = x1; Y1 = y1; X2 = x2; Y2 = y2; 
                        }
                    }
                }
            }
            booltable[pointer] = true; 
            result[i-1][0][0] = X1; 
            result[i-1][1][0] = Y1; 
            result[i-1][0][1] = X2; 
            result[i-1][1][1] = Y2; 
        }
        return result; 
    }
    
    /**
     * Do an Eulerian walk and embed the tour.
     * 
     * @param edges double[][][]
     * @param edges_in_tsp_solution int
     * @param max int
     * @return result String[]
     */
    public static String[] Euler_and_embedded_tour(double edges[][][], 
            int edges_in_tsp_solution, int max) { // the last is max +1 or edges +1
        
        //System.out.println("Euler_and_embedded_tour, edges in TSP solution is " + 
        //        edges_in_tsp_solution + " and max is " + max);
        
        String[] result = new String[edges_in_tsp_solution +1];
        String[] sub_result = new String[max];
        boolean[] isTaken = new boolean[max -1];
        boolean isTaken_has_all_true = false;
        int pointer = 0;
        int previousPointer;// = 0;

        isTaken[pointer] = true;
        sub_result[pointer] = String.valueOf(edges[pointer][0][1]) + " " + 
                String.valueOf(edges[pointer][1][1]); 
        String last_value = sub_result[pointer];
        String start_value = "";
        double last_x = edges[pointer][0][1]; 
        double last_y = edges[pointer][1][1]; 

        // the first edge is ok, now connect the others: 
        while (isTaken_has_all_true == false) {
            previousPointer = pointer;
            
            for(int i = 1; i < max -1; i++) {
                if((((edges[i][0][0] == last_x) && 
                      (edges[i][1][0] == last_y))) && (isTaken[i] == false)) {
                    //check that a connection is made so that the line doesn't cut 
                    isTaken[i] = true;
                    sub_result[pointer] = String.valueOf(edges[i][0][1]) + 
                            " " + String.valueOf(edges[i][1][1]); 
                    last_x = edges[i][0][1]; 
                    last_y = edges[i][1][1]; 
                    
                    pointer++;
                    
                }
                
                if(i == 1) {
                        start_value = String.valueOf(edges[i][0][0]) + 
                                " " + String.valueOf(edges[i][1][0]); 
                }
            }
            isTaken_has_all_true = true;
            for(int i = 0; i < max -1; i++) {
                if(isTaken[i] == false) {
                    isTaken_has_all_true = false;
                }
            }
            
            if(pointer == previousPointer) {
                for(int i = 1; i < isTaken.length; i++) {
                    if(isTaken[i] == false && 
                            ((edges[i][0][0] == last_x) &&
                             (edges[i][1][0] == last_y))) {
                        isTaken[i] = true;
                        sub_result[pointer] = String.valueOf(edges[i][0][1]) + 
                                " " + String.valueOf(edges[i][1][1]); 
                        last_x = edges[i][0][1]; 
                        last_y = edges[i][1][1]; 

                        pointer++;
                        i = isTaken.length;
                    }
                    if(isTaken[i] == false && 
                            ((edges[i][0][1] == last_x) &&
                             (edges[i][1][1] == last_y))) {
                        // swap
                        double helpvarX = edges[i][0][1];
                        double helpvarY = edges[i][1][1];
                        edges[i][0][1] = edges[i][0][0];
                        edges[i][1][1] = edges[i][1][0];
                        edges[i][0][0] = helpvarX;
                        edges[i][1][0] = helpvarY;
                        
                        isTaken[i] = true;
                        sub_result[pointer] = String.valueOf(edges[i][0][1]) + 
                                " " + String.valueOf(edges[i][1][1]); 
                        last_x = edges[i][0][1]; 
                        last_y = edges[i][1][1]; 

                        pointer++;
                        i = isTaken.length;
                    }
                }
                if(pointer == previousPointer) {
                    for(int i = 0; i < isTaken.length; i++) {
                        if(isTaken[i] == false) {
                            isTaken[i] = true;
                            sub_result[pointer] = String.valueOf(edges[i][0][1]) + 
                                    " " + String.valueOf(edges[i][1][1]); 
                            last_x = edges[i][0][1]; 
                            last_y = edges[i][1][1]; 

                            pointer++;
                            i = isTaken.length;
                        }
                    }
                }
            }
            //System.out.println(Arrays.toString(isTaken));
        }
        
        // adding the last value and the first value + swapping the table 
        sub_result[max - 2] = last_value; 
        sub_result[max - 1] = start_value;
        //System.out.println(Arrays.toString(sub_result)  + " ignore last");
        String help_var;
        String first_val = "";
        for(int i = sub_result.length -2; i > -1; i--) { 
            if(i == sub_result.length -2) {
                first_val = sub_result[i];
            }
            help_var = sub_result[i+1];
            sub_result[i+1] = sub_result[i];
            sub_result[i] = help_var;
        }
        sub_result[0] = first_val;
        
        // embedding the tour:
        for(int i = 0; i < sub_result.length; i++) {
            for(int j = i+1; j < sub_result.length; j++) {
                if(sub_result[i].equals(sub_result[j]) && 
                   !sub_result[i].equals("del")) {
                    if(sub_result[j].equals(
                            sub_result[0]) && j == sub_result.length -1) {
                        // do nothing
                    }
                    else {
                        sub_result[j] = "del";
                        //System.out.println("Deleted " + j);
                    }
                }
            }
        }
        int k = 0;
        for (String sub_result1 : sub_result) {
            if (sub_result1.equals("del") == false) {
                try {
                    result[k] = sub_result1;
                    k++;
                }
                catch(Exception e) {
                    //System.out.println(e);
                }
            }
        }
        return result;
    }

    /**
     * Try to perform a 2-opt move that tries to remove some crossings for instance.
     * 
     * Should be tested if real improvement is surely made always (0 profit not wanted).
     * 
     * ArrayList coordinates should have edges stored this way:
     * (beginX, beginY, endX, endY) (beginX, beginY, endX, endY) (beginX, beginY, endX, endY) ... 
     * 
     * Uses the Squared Euclidean distance call to avoid worthless square root computing.
     * 
     * @param coordinates ArrayList
     * @param eucDistOld double
     * @return ArrayList
     */
    public static ArrayList twoOpt(ArrayList coordinates, double eucDistOld) {
        ArrayList originals = (ArrayList<Object>)coordinates.clone();
        
        for(int i = 4; i < coordinates.size()-8; i+=4) {
            for(int j = i+4; j < coordinates.size()-4; j+=4) {
                
                double ifConnect1 = Euclidean_distance_squared(
                        (double)coordinates.get(i), (double)coordinates.get(i+1), 
                        (double)coordinates.get(j), (double)coordinates.get(j+1));
                double ifConnect2 = Euclidean_distance_squared(
                        (double)coordinates.get(i+2), (double)coordinates.get(i+3), 
                        (double)coordinates.get(j+2), (double)coordinates.get(j+3));
                double iNormal = Euclidean_distance_squared(
                        (double)coordinates.get(i), (double)coordinates.get(i+1), 
                        (double)coordinates.get(i+2), (double)coordinates.get(i+3));
                double jNormal = Euclidean_distance_squared(
                        (double)coordinates.get(j), (double)coordinates.get(j+1), 
                        (double)coordinates.get(j+2), (double)coordinates.get(j+3));
                
                double profitNeg = (ifConnect1 + ifConnect2) - (iNormal + jNormal);
                
                //System.out.println("Tour profit (if negative, good) would be " + profitNeg);
                if(profitNeg < 0) {
                    
                    // swapping, 
                    // could be improved:
                    double helpVar1 = (double)coordinates.get(j+2);
                    double helpVar2 = (double)coordinates.get(j+3);
                    double helpVar3 = (double)coordinates.get(i);
                    double helpVar4 = (double)coordinates.get(i+1);
                    coordinates.set(i, coordinates.get(j));
                    coordinates.set(i+1, coordinates.get(j+1));
                    coordinates.set(j+2, coordinates.get(i+2));
                    coordinates.set(j+3, coordinates.get(i+3));
                    coordinates.set(i+2, helpVar1);
                    coordinates.set(i+3, helpVar2);
                    coordinates.set(j, helpVar3);
                    coordinates.set(j+1, helpVar4);
                    
                    //System.out.println("SWAP!! 2opt swap with indexes " + i + ", " + j);
                    //System.out.println("XXXXXXXXXXXXXXX" +coordinates.toString() + "\n");
                    
                    if(i > coordinates.size()/2) {
                        for(int k = 2; k < coordinates.size()-2; k+=2) {
                            for(int l = 2; l < coordinates.size()-2; l+=2) {
                                if(((double)coordinates.get(k) == (double)coordinates.get(l)) && 
                                   ((double)coordinates.get(k+1) == (double)coordinates.get(l+1))) {
                                        coordinates.remove(l+1);
                                        coordinates.remove(l);
                                        break;
                                }
                            }
                        }
                        //System.out.println("AFTER " + coordinates.toString());
                    }
                    else {
                        for(int k = coordinates.size()-4; k > 2; k-=2) {
                            for(int l = coordinates.size()-4; l > 2; l-=2) {
                                if(((double)coordinates.get(k) == (double)coordinates.get(l)) && 
                                   ((double)coordinates.get(k+1) == (double)coordinates.get(l+1))) {
                                        coordinates.remove(l+1);
                                        coordinates.remove(l);
                                        k-=2;
                                        break;
                                }
                            }
                        }
                        //System.out.println("AFTER XXXXXXXXX" + coordinates.toString());
                    }
                    
                    int random1 = 2+(int)(Math.random()*((((coordinates.size()/2)-6) -2) +2));
                    for(int k = random1 *2; k < (random1 *2)+1; k+=2) {
                        for(int l = k+2; l < k+3; l+=2) {
                            helpVar1 = (double)coordinates.get(k);
                            helpVar2 = (double)coordinates.get(k+1);
                            coordinates.set(k, coordinates.get(l));
                            coordinates.set(k+1, coordinates.get(l+1));
                            coordinates.set(l, helpVar1);
                            coordinates.set(l+1, helpVar2);
                        }
                    }
                    
                    i = coordinates.size();
                    //j = coordinates.size(); // ending the i and j for loops 
                    break;
                    //System.out.println("After          " + coordinates.toString());
                }
            }
        }

        double eucNew = 0.0;
        for(int i = 0; i < coordinates.size()-2; i+=2) {
            eucNew += Euclidean_distance((double)coordinates.get(i), 
                                         (double)coordinates.get(i+1), 
                                         (double)coordinates.get(i+2), 
                                         (double)coordinates.get(i+3));
        }
        for(int k = 2; k < coordinates.size()-2; k+=2) {
            coordinates.add(k+1, coordinates.get(k+1));
            coordinates.add(k, coordinates.get(k));
                double helpVar1 = (double)coordinates.get(k+1);
                coordinates.set(k+1, coordinates.get(k+2));
                coordinates.set(k+2, helpVar1);
            k+=2;
        }
        
        //System.out.println("NEW dist: " + eucNew + ", OLD dist: " + eucDistOld);
        if(eucNew < eucDistOld) {
            System.out.println("IMPROVED RETURNED: " + coordinates);
            return coordinates;
        }
        else {
            //System.out.println("ORIGINALS RETURNED: " + originals);
            return originals;
        }
    }
    
    /**
     * Removing 3 edges for some sets, try reconnecting.
     * 
     * If improvement is found, then return the better/best route.
     * 
     * Important: this could be improved by adding for loops and making the search ranges wider!
     * 
     * ArrayList coordinates should have edges stored this way at first:
     * (beginX, beginY, endX, endY) (beginX, beginY, endX, endY) (beginX, beginY, endX, endY) ... 
     * 
     * @param coordinates ArrayList
     * @param eucDistOld double
     * @return ArrayList
     */
    public static ArrayList threeOpt(ArrayList <Double>coordinates, double eucDistOld) {
        ArrayList <Double>originals = (ArrayList)coordinates.clone();
        
        // delete duplicates, add them back later: 
        for(int i = 2; i < coordinates.size()-4; i+=2) {
            coordinates.remove(i);
            coordinates.remove(i);
        }
        
        for(int i = 0; i < coordinates.size()-12; i+=2) {
            ArrayList coordinatesCopy = (ArrayList<Object>)coordinates.clone();
            for(int j = 0; j < 8; j++) {
                if(j == 0) { // swap 2 and 3
                    Collections.swap(coordinates, i+4, i+6); Collections.swap(coordinates, i+5, i+7);
                }
                if(j == 1) { // swap 1 and 4, 2 and 3
                    Collections.swap(coordinates, i+2, i+8); Collections.swap(coordinates, i+3, i+9);
                    Collections.swap(coordinates, i+4, i+6); Collections.swap(coordinates, i+5, i+7);
                }
                if(j == 2) { // swap 3 and 4
                    Collections.swap(coordinates, i+6, i+8); Collections.swap(coordinates, i+7, i+9);
                }
                if(j == 3) { // swap 1 and 2
                    Collections.swap(coordinates, i+2, i+4); Collections.swap(coordinates, i+3, i+5);
                }
                if(j == 4) { // swap 1 and 2, 3 and 4
                    Collections.swap(coordinates, i+2, i+4); Collections.swap(coordinates, i+3, i+5);
                    Collections.swap(coordinates, i+6, i+8); Collections.swap(coordinates, i+7, i+9);
                }
                if(j == 5) { // swap 1 and 3, 2 and 4, 1 and 2
                    Collections.swap(coordinates, i+2, i+6); Collections.swap(coordinates, i+3, i+7);
                    Collections.swap(coordinates, i+4, i+8); Collections.swap(coordinates, i+5, i+9);
                    Collections.swap(coordinates, i+2, i+4); Collections.swap(coordinates, i+3, i+5);
                }
                if(j == 6) { // swap 1 and 4, 2 and 3, 1 and 2
                    Collections.swap(coordinates, i+2, i+8); Collections.swap(coordinates, i+3, i+9);
                    Collections.swap(coordinates, i+4, i+6); Collections.swap(coordinates, i+5, i+7);
                    Collections.swap(coordinates, i+2, i+4); Collections.swap(coordinates, i+3, i+5);
                }
                if(j == 7) { // swap 1 and 3, 2 and 4
                    Collections.swap(coordinates, i+2, i+6); Collections.swap(coordinates, i+3, i+7);
                    Collections.swap(coordinates, i+4, i+8); Collections.swap(coordinates, i+5, i+9);
                }

                double eucNew = 0.0;
                for(int k = 0; k < coordinates.size()-2; k+=2) {
                    eucNew += Euclidean_distance((double)coordinates.get(k), 
                                                 (double)coordinates.get(k+1), 
                                                 (double)coordinates.get(k+2), 
                                                 (double)coordinates.get(k+3));
                }
                if(eucNew < eucDistOld) {
                    //change all back: 
                    for(int k = 2; k < coordinates.size()-2; k+=2) {
                        coordinates.add(k+1, coordinates.get(k+1));
                        coordinates.add(k, coordinates.get(k));
                            double helpVar1 = (double)coordinates.get(k+1);
                            coordinates.set(k+1, coordinates.get(k+2));
                            coordinates.set(k+2, helpVar1);
                        k+=2;
                    }
                    System.out.println("IMPROVED RETURNED FROM 3OPT: " + coordinates);
                    return coordinates;
                }
                coordinates = coordinatesCopy;
            }
        }
        //change all back, no improvements found: 
        for(int k = 2; k < coordinates.size()-2; k+=2) {
            coordinates.add(k+1, coordinates.get(k+1));
            coordinates.add(k, coordinates.get(k));
                double helpVar1 = (double)coordinates.get(k+1);
                coordinates.set(k+1, coordinates.get(k+2));
                coordinates.set(k+2, helpVar1);
            k+=2;
        }
        return originals;
    }
    
    /**
     * Basic idea: take an already existing Hamiltonian cycle (TSP tour).
     * Make opt moves and try to find out improvements (randomly in this implementation).
     * If stuck at a local minimum, possibly try to steer the computing to another direction.
     * Uses the double stack like the convex hull.
     * 
     * Could be improved a lot, does not use a tree, does not "delete bad gains".
     * 
     * @param max int
     * @param eucDistOld double
     * @param coordinates ArrayList
     * @return edges double[][]
     */
    public static double[][] MakeOptMoves(int max, double eucDistOld, ArrayList coordinates) {
        // max = amount of vertices, then the best known distance, then the coordinates xy xy xy...
        //System.out.println("OPT at the start: " + coordinates.toString() + "\nOLD Euc. distance is " + eucDistOld);
        ArrayList coordinatesTry1 = new ArrayList<>();
        double wanted_distance_limit = 0; // or kind of gain control, turned out unnecessary so 0 is set 
        DoubleStack x_coordinatesSt = new DoubleStack();
        DoubleStack y_coordinatesSt = new DoubleStack();
        
        for(int i = 0; i < coordinates.size(); i+=2) {
            x_coordinatesSt.push((double)coordinates.get(i));
            y_coordinatesSt.push((double)coordinates.get(i+1));
            //System.out.println("pushed " + (double)coordinates.get(i) + 
            //        " and " + (double)coordinates.get(i+1));
        }
        
        for(int i = 0; i < 2000000; i++) { // for loop can adjust how many tries 
            double dxStartEnd = x_coordinatesSt.top(); 
                                x_coordinatesSt.pop();
            double dyStartEnd = y_coordinatesSt.top(); 
                                y_coordinatesSt.pop();
            coordinatesTry1.add(dxStartEnd);
            coordinatesTry1.add(dyStartEnd);
            
            boolean switc;// = false; // switch is an illegal variable name in Java 
            double d1; 
            double d2; 
            double d3; 
            double d4; 
            double d5; 
            double d6;
            while(!x_coordinatesSt.isEmpty() && !y_coordinatesSt.isEmpty()) {
                // System.gc(); // if Java memory issues occur 
                boolean done = false;
                double eucdist = 0.0;
                
                d1 = x_coordinatesSt.top();     // 3 nodes/vertices
                     x_coordinatesSt.pop();

                d2 = y_coordinatesSt.top();
                     y_coordinatesSt.pop();
                
                //if((d1 != dxStartEnd) && (d2 != dyStartEnd) && 
                //    d1 != Double.MAX_VALUE && d2 != Double.MAX_VALUE) {
                        d3 = x_coordinatesSt.top();
                             x_coordinatesSt.pop();

                        d4 = y_coordinatesSt.top();
                             y_coordinatesSt.pop();
                                
                //    if((d3 != dxStartEnd) && (d4 != dyStartEnd) && 
                //       (d3 != Double.MAX_VALUE && d4 != Double.MAX_VALUE)) {
                            d5 = x_coordinatesSt.top();
                                 x_coordinatesSt.pop();

                            d6 = y_coordinatesSt.top();
                                 y_coordinatesSt.pop();
                //    }
                //}
                
                if( //((d1 >= Double.MAX_VALUE && d2 >= Double.MAX_VALUE) || 
                    // (d3 >= Double.MAX_VALUE && d4 >= Double.MAX_VALUE) || 
                    // (d5 >= Double.MAX_VALUE && d6 >= Double.MAX_VALUE)) || 
                   ((d1 == dxStartEnd && d2 == dyStartEnd) || 
                    (d3 == dxStartEnd && d4 == dyStartEnd) || 
                    (d5 == dxStartEnd && d6 == dyStartEnd))) {
                        done = true;
                        x_coordinatesSt.empty();
                        y_coordinatesSt.empty();
                        
                        //System.out.println(d1 + ", " + d2 + ", " +  d3 + 
                        //           ", " +  d4 + ", " + d5 + ", " +  d6);
                    
                        if(d1 == dxStartEnd && d2 == dyStartEnd) {
                            coordinatesTry1.add(d1);
                            coordinatesTry1.add(d2);
                        }
                        if(d3 == dxStartEnd && d4 == dyStartEnd) {
                            coordinatesTry1.add(d1);
                            coordinatesTry1.add(d2);
                            coordinatesTry1.add(d3);
                            coordinatesTry1.add(d4);
                        }
                        if(d5 == dxStartEnd && d6 == dyStartEnd) {
                            coordinatesTry1.add(d1);
                            coordinatesTry1.add(d2);
                            coordinatesTry1.add(d3);
                            coordinatesTry1.add(d4);
                            coordinatesTry1.add(d5);
                            coordinatesTry1.add(d6);
                        }
                }
                
                if(!done) {
                    switc = Math.random() < 0.5;
                    double testingEucdist1 = Euclidean_distance_squared(d1, d2, d3, d4);
                    double testingEucdist2 = Euclidean_distance_squared(d5, d6, d3, d4);
                    
                    //System.out.println("Trying with " + d1 + ", " + d2 + ", " +  
                    //        d3 + ", " +  d4 + ", " + d5 + ", " +  d6);
                    
                    if(wanted_distance_limit < (testingEucdist1 - testingEucdist2)) {
                        if(switc) {
                            if(testingEucdist1 < testingEucdist2) {
                                x_coordinatesSt.push(d5);
                                y_coordinatesSt.push(d6);
                                coordinatesTry1.add(d1);
                                coordinatesTry1.add(d2);
                                coordinatesTry1.add(d3);
                                coordinatesTry1.add(d4);
                            }
                            else {
                                x_coordinatesSt.push(d1);
                                y_coordinatesSt.push(d2);
                                coordinatesTry1.add(d3);
                                coordinatesTry1.add(d4);
                                coordinatesTry1.add(d5);
                                coordinatesTry1.add(d6);
                            }
                        }
                        else {
                            if(testingEucdist1 < testingEucdist2) {
                                x_coordinatesSt.push(d5);
                                y_coordinatesSt.push(d6);
                                coordinatesTry1.add(d3);
                                coordinatesTry1.add(d4);
                                coordinatesTry1.add(d1);
                                coordinatesTry1.add(d2);
                            }
                            else {
                                x_coordinatesSt.push(d1);
                                y_coordinatesSt.push(d2);
                                coordinatesTry1.add(d5);
                                coordinatesTry1.add(d6);
                                coordinatesTry1.add(d3);
                                coordinatesTry1.add(d4);
                            }
                        }
                        if((wanted_distance_limit < (testingEucdist1 - testingEucdist2))) {
                            wanted_distance_limit *= 1.2;
                        }
                        else {
                            if(wanted_distance_limit > testingEucdist1 *1.5) {
                                wanted_distance_limit -= testingEucdist1;
                            }   // adjusting with programmer's own will with this if-else part 
                            if(wanted_distance_limit > testingEucdist2 *1.5) {
                                wanted_distance_limit -= testingEucdist2;
                            }
                        }
                    }
                    else {
                        coordinatesTry1.add(d1);
                        coordinatesTry1.add(d2);
                        coordinatesTry1.add(d3);
                        coordinatesTry1.add(d4);
                        coordinatesTry1.add(d5);
                        coordinatesTry1.add(d6);
                        wanted_distance_limit /=1.5;
                    }
                }
                
                if(done) {
                    for(int j = 0; j < coordinatesTry1.size()-2; j+=2) {
                        eucdist += Euclidean_distance((double)coordinatesTry1.get(j), 
                                                      (double)coordinatesTry1.get(j+1), 
                                                      (double)coordinatesTry1.get(j+2), 
                                                      (double)coordinatesTry1.get(j+3));
                        // be sure to add also the end node which is the same as start node
                    }
                    
                    x_coordinatesSt.empty();
                    y_coordinatesSt.empty();
                    if((eucdist < (eucDistOld)) && x_coordinatesSt.isEmpty() && y_coordinatesSt.isEmpty()) {
                        System.out.println("\tImprovement found! "
                                + eucdist + " --- " + eucDistOld + "\n");
                        //improvement =true;
                        eucDistOld = eucdist;
                        coordinates = (ArrayList<Object>)coordinatesTry1.clone();
                        // coordinates (ArrayList) should have an improved solution, if found 
                    }
                    else {
                        //System.out.println("\tNo improvements this time. " + 
                        //        eucdist + ", old distance: " + eucDistOld + "\n");
                        // quit, leave the stacks empty if they 
                        // are empty - so the while loop can end 
                    }
                }
            }
        }
        //System.out.println("And now: " + coordinates + "\nIMPROVEMENTS YET?: " + improvement);
        
        double[][] edges = new double[max+2][2]; // value, isY? 
        for(int i = 0; i < max+1; i++) {
            edges[i][0] = Double.MAX_VALUE;
            edges[i][1] = Double.MAX_VALUE;
        }
        
        for(int i = 2; i < coordinates.size()-2; i+=2) {
            coordinates.add(i+2, coordinates.get(i));
            coordinates.add(i+3, coordinates.get(i+1));
            i+=2;
        }
        
        // random opt trying:
        ArrayList coordinatesBest;
        coordinatesBest = new ArrayList<>(coordinates);
        ArrayList coordinatesTry = coordinates;
        double eucdBestNew = eucDistOld;
        
        for(int i = 0; i < 50; i++) {    // can adjust how many times etc.
            coordinatesTry = twoOpt(coordinatesTry, eucdBestNew);
            coordinatesTry = threeOpt(coordinatesTry, eucdBestNew);
            
            double eucd = 0.0;
            for(int j = 0; j < coordinatesTry.size(); j+=4) {
                eucd += Euclidean_distance((double)coordinatesTry.get(j), 
                                           (double)coordinatesTry.get(j+1), 
                                           (double)coordinatesTry.get(j+2), 
                                           (double)coordinatesTry.get(j+3));
            }
            if((eucd <= eucdBestNew) && (eucdBestNew <= eucDistOld)) {
                //System.out.println("---NEW BEST: " + eucd);
                eucdBestNew = eucd;
                coordinatesBest = (ArrayList<Object>)coordinatesTry.clone();
            }
        }
        
        for(int i = 2; i < coordinatesBest.size()-1; i+=2) {
            for(int j = 2; j < coordinatesBest.size()-1; j+=2) {
                if(coordinatesBest.get(i).equals(coordinatesBest.get(j)) && 
                   coordinatesBest.get(i+1).equals(coordinatesBest.get(j+1))) {
                        coordinatesBest.remove(i);
                        coordinatesBest.remove(i);
                }
            }
        }
        coordinatesBest.add(coordinatesBest.get(0));
        coordinatesBest.add(coordinatesBest.get(1));
        
        int insertIndex = 0;
        for(int i = 0; i < coordinatesBest.size()-2; i+=2) {
            if(insertIndex < edges.length) {
                edges[insertIndex][0] = (double)coordinatesBest.get(i);
                edges[insertIndex][1] = (double)coordinatesBest.get(i+1);
                insertIndex++;
            }
        }
        if(insertIndex < edges.length) {
            edges[insertIndex][0] = edges[0][0];
            edges[insertIndex][1] = edges[0][1];
        }
        return edges;
    }
} 