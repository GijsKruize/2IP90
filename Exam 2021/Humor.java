
/**
 * @author Gijs Kruize
 * @ID 1658662
 * @date 2-11-21
 */
import java.util.ArrayList;

class Joke {
    String jokeName;
    String jokeDescription;
    int jokeSpace;
    double jokeFunFactor;
    String jokeInsulted;

    Joke(String name, String description, int space, double funFactor) {
        jokeName = name;
        jokeDescription = description;
        jokeSpace = space;
        jokeFunFactor = funFactor;
        jokeInsulted = null;
    }

    Joke() {
        jokeName = "envelop";
        jokeDescription = "Ik ken een goeie mop, twee tieten in een envellop";
        jokeSpace = 30;
        jokeFunFactor = 1.0;
        jokeInsulted = null;
    }

    public String toString() {
        String text = jokeName + ", " + jokeDescription + ", " + jokeSpace + ", " + jokeFunFactor;
        return text;
    }
}

class InsultingJoke extends Joke {

    InsultingJoke(String name, String description, int space, double funFactor, String insulted) {
        jokeName = name;
        jokeDescription = description;
        jokeSpace = space;
        jokeFunFactor = funFactor;
        jokeInsulted = insulted;
    }

    public String toString() {
        String text = jokeName + ", " + jokeDescription + ", " + jokeSpace + ", " + jokeFunFactor + ", " + jokeInsulted;
        return text;
    }

}

class Issue {
    ArrayList<Joke> jokes = new ArrayList<>();

    void add(Joke j) {
        jokes.add(j);
    }

    void overview() {
        int totalLength = 0;
        for (int i = 0; i < jokes.size(); i++) {
            totalLength += jokes.get(i).jokeSpace;
        }
        System.out.println(totalLength);
    }

    double calculateFunniness() {
        double funniness = 0;
        for (int i = 0; i < jokes.size(); i++) {
            if (jokes.get(i).jokeInsulted == null) {
                funniness += jokes.get(i).jokeFunFactor / jokes.get(i).jokeSpace;
            } else if (jokes.get(i).jokeInsulted == "Limburgians") {
                funniness += jokes.get(i).jokeFunFactor * 2 / jokes.get(i).jokeSpace;
            } else if (jokes.get(i).jokeInsulted != null) {
                funniness += jokes.get(i).jokeFunFactor * 1.5 / jokes.get(i).jokeSpace;
            }
        }
        return funniness;
    }
}

class Humor {

    void createIssue() {
        Issue issue = new Issue();
        Joke j = new Joke("Palindrome", "A nerdy guy holds a black sign with the text 'Madam, I'm Adam'", 30, 25.0);
        issue.add(j);

        Joke k = new InsultingJoke("Little Pastor", "Gus and Theo met in a bar in the Stratum End, and ...", 120, 10.0,
                "Brabanders");
        issue.add(k);

        issue.overview();

    }

    public static void main(String[] args) {
        new Humor().createIssue();
    }
}