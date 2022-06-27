/**
 * @author Gijs Kruize
 * @id 1658662
 * @author Gijs Nolet
 * @id 1679570
 * @date 17-09-21
 */
import java.util.Scanner;

class GuessingGameAdvanced{
    
    void run(){
        Scanner sc = new Scanner(System.in);
        int upperbound = 1000; 
        int lowerbound = 0;
        int guess; //The guess the computer makes based on user input
        String answer; //The user input
        //Boolean used to break out of the for loop if a correct guess is made
        boolean guessed = false; 

        System.out.println("Think of a scered number not smaller than 0 and not greater than 1000."
        + " Type 'go' if you have one");
        
        //Wait for the user to type go
        if (!sc.nextLine().equals("go")) {
            System.out.println("please type 'go' to start");

            sc.close();
            return;
        }

        //The maximum amount of guesses the computer has is 10
        for (int i = 0; i <= 10; i++) {

            //Calculate the middle number between the lowerbound and upperbound;
            guess =  lowerbound + ((upperbound - lowerbound) / 2);
            System.out.println(guess);

            //Loop untill the user has made a correct input
            answer = sc.nextLine();

            if (answer.equals("guessed")) {
                guessed = true; //Set guess to true so the for loop can be broken
                break;
            } else if (answer.equals("higher") || answer.equals("h")) {
                lowerbound = guess; 
                break;
            } else if (answer.equals("lower") || answer.equals("l")) {
                upperbound = guess;
                break;
            } else {
                System.out.println("Please answer with higher, lower, h or l");
            }

            if (guessed == true) {
                break;
            }

        }

        sc.close(); //Close the scanner
    }

    public static void main(String[] args){
        (new GuessingGameAdvanced()).run();
    }
}