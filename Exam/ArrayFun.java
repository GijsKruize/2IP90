
/** 
 * @author Gijs Kruize
 * @id 1658662
 */

import java.util.Arrays;

class ArrayFun {
    double sum(double[] a) {
        double sum = 0;
        if (a != null) {
            for (int i = 0; i < a.length; i++) {
                sum = sum + a[i];
            }
        }
        return sum;
    }

    double[] colsum(double[][] m) {
        double[] columnsum = new double[m[0].length];
        for (int i = 0; i < m[i].length; i++) {
            for (int j = 0; j < m.length; j++) {
                columnsum[i] = columnsum[i] + m[j][i];
            }
        }
        return columnsum; // change this
    }

    int segsum(int[] a, int from, int upto) {
        int sum = 0;
        if (a != null) {
            for (int i = from; i < upto; i++) {
                sum = sum + a[i];
            }
        }
        return sum;
    }

    void demo() {
        // double[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { -1, -2, -3 }, { 2, 4, 6 } };
        // double[] result = colsum(matrix);
        // System.out.println(Arrays.toString(result));
        int[] a = {1,2,3,4};
        System.out.println(segsum(a, 0, 4));
        System.out.println(segsum(a, 1, 3));
        System.out.println(segsum(a, 0, 0));
    }

    public static void main(String[] a) {
        (new ArrayFun()).demo();
    }
}