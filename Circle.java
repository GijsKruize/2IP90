/**
 * Circle
 * @GijsKruize 
 * @1658662 
 */
import java.util.Locale;
import java.util.Scanner;

class Circle {
    Scanner sc = new Scanner(System.in).useLocale(Locale.US); //Imports US keyboard layout so errors with a dot in a double wont occur

    public void run() {

	    double x1; //X-coördinate of the midpoint of the 1st circle
        double y1; //y-coördinate of the midpoint of the 1st circle
        double r1; //Radius of the 1st circle
        double x2; //X-coördinate of the midpoint of the 2nd circle
        double y2; //y-coördinate of the midpoint of the 2nd circle
        double r2; //Radius of the 2nd circle
        double xt; //X-coördinate of the testing point
        double yt; //y-coördinate of the testing point
        double d1; //Distance between testing point and coördinates of the midpoint of the 1st circle
        double d2; //Distance between testing point and coördinates of the midpoint of the 2nd circle
        boolean c1; //Boolean for testing of point is within the 1st circle
        boolean c2; //Boolean for testing of point is within the 2nd circle
        boolean r; //Boolean for testing if the entered radii are valid

            //Scanner objects that assign the correct value to the doubles
        x1 = sc.nextDouble();
        y1 = sc.nextDouble();
        r1 = sc.nextDouble();
        x2 = sc.nextDouble();
        y2 = sc.nextDouble();
        r2 = sc.nextDouble();
        xt = sc.nextDouble();
        yt = sc.nextDouble();

        d1 = Math.sqrt( (y1 - yt) * (y1 - yt) + (x1 - xt) * (x1 - xt) ); //Calculates the distance between the midpoint of the first circle and the testing point
        d2 = Math.sqrt( (y2 - yt) * (y2 - yt) + (x2 - xt) * (x2 - xt) ); //Calculates the distance between the midpoint of the second circle and the testing point
        c1 = d1 <= r1; //Tests if the distance between the testing point and the midpoint of the first circle is less or equal then the radius of the first circle thus laying in the first circle
        c2 = d2 <= r2; //Tests if the distance between the testing point and the midpoint of the second circle is less or equal then the radius of the second circle thus laying in the second circle
        r = (r1 > 0) && (r2 > 0); //Tests if the entered radii are positive thus being valid

            //If statements that assign the correct output to the outcome of the boolean tests
        if (r == false) {
             System.out.println("input error");
        } 
        else if ( (c1 == true) && (c2 == true) ) {
            System.out.println("The point lies in both circles");
        } 
        else if (c1 == true) {
            System.out.println("The point lies in the first circle");
        } 
        else if (c2 == true) {
            System.out.println("The point lies in the second circle");
        } 
        else {
            System.out.println("The point does not lie in either circle");
        }

        sc.close();

    }

    public static void main(String[] args) {
        (new Circle()).run();
    }
}
