
/**
 * @author Gijs Kruize
 * @ID 1658662
 * @Date 2-11-2021
 */

import java.util.ArrayList;
import java.util.Map;

public class Speeding {

    double TOLERANCE = 0.001;

    boolean noBackward(double[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                return false;
            }
        }
        return true;
    }

    double[] speeds(double[] a) {
        double[] speeds = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            if (i == 0 || i == a.length - 1) {
                speeds[i] = 0.0;
            } else {
                speeds[i] = (a[i + 1] - a[i - 1]) / 2;
            }
        }
        return speeds;
    }

    ArrayList<Integer> stops(double[] a) {
        ArrayList<Integer> stops = new ArrayList<>();
        for (int i = 1; i < a.length; i++) {
            if ((a[i] - a[i - 1]) < TOLERANCE) {
                stops.add(i);
            }
        }
        return stops;
    }

    Map<Integer, Double> exceedings(double a[], double limit){
        Map<Integer, Double> exceedings;
        for (int i = 0; i < a.length; i++) {
            if(speeds(a)[i] > limit ){
                exceedings.put(i, speeds(a)[i]);
            }
        }
        return exceedings;
    }

}
