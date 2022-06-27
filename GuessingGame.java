/**
 * @author Gijs Kruize
 * @id 1658662
 * @author Gijs Nolet
 * @id 1679570
 * @date 17-09-21
 */

import java.util.Random;
import java.util.Scanner;

class GuessingGame {
    Scanner sc = new Scanner(System.in);
    int code; //Integer that stores the code
    int[] guess = new int[7]; //The user gets a max of 7 guesses
    long arbitrary; //Stores seed for random number generator
    String enterCode; //Stores decision if the code is entered manually or randomly genereted
    //Table is used to print the guesses the 1st dimension is the row and the 2nd are the characters
    String[][] table; 

    void run() {
        System.out.println("Do you want to enter the secret code yourself?");

        //This makes sure that either a yes or no is entered
        enterCode = sc.nextLine();
        if (enterCode.equals("yes")) {
            System.out.println("Secretly type the code");
            code = sc.nextInt();
        } else {
            System.out.println("Type an arbitrary number");
            arbitrary = sc.nextInt();
            Random rand = new Random(arbitrary);
            code = rand.nextInt(100);
        }

        System.out.println("Start guessing!");
        
        //creates a table that stores the output
        table = new String [7][100];
        
        //Loop 7 times for each guess
        for (int i = 0 ; i < 7 ; i++) {
            guess[i] = sc.nextInt();
            
            //Stores the current guess itteration in the table
            for (int c = 0 ; c < 100; c++) {
                table[i][c] = ".";
            }
            table[i][code] = "|";
            table[i][guess[i]] = "X"; 

            //if statements that corrosponds to the current guess
            if (guess[i] == code) {
                System.out.println("Good guess! You won.");
            } else if (i == 6) {
                if (guess[i] > code) {
                    System.out.println("lower");
                } else if (guess[i] < code) {
                    System.out.println("higher");
                }
                System.out.println("No more guesses, you lost.");
            } else if (guess[i] > code) {
                System.out.println("lower");
            } else if (guess[i] < code) {
                System.out.println("higher");
            }

            if (guess[i] == code || i == 6) {
                System.out.println( (i + 1) + " guesses:");

                //These for loops store the contents of table into a string for each row
                for (int c = 0; c <= i; c++) {

                    String[] row = new String[i+1];
                    row[c] = "";

                    for (int d = 0; d < table[c].length; d++) {
                        row[c] = row[c] + table[c][d];
                    }

                    System.out.println(row[c]);
                }
                //After the code has been guessed, the loop can stop and the program is finished
                break;
            }

        }
                         
        sc.close(); //Close the scanner
    }

    public static void main(String[] args){
        (new GuessingGame()).run();
    }
}