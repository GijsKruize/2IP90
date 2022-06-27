/**
 * Assignment 6 -- Prisoner's Dilemma -- 2ip90
 * part Patch
 * 
 * @author Gijs Kruize
 * @author Gijs Noler
 * assignment group 64
 * 
 * assignment copyright Kees Huizing
 */
import java.awt.Color;

class Patch {
    
    private boolean cooperating;

    private double score;
    
    private Color color;
    
    // returns true if and only if patch is cooperating
    boolean isCooperating() {
        return cooperating;
    }
    
    // set strategy to C if isC is true and to D if false
    void setCooperating(boolean isC) {
        cooperating = isC;
    }
    
    // change strategy from C to D and vice versa
    void toggleStrategy() {
        if(cooperating){
            cooperating = false;
        }else{
            cooperating = true;
        }
    }
    
    void setScore(double score){
        this.score = score;
    }

    // return score of this patch in current round
    double getScore() {
        return score;
    }

    Color getColor(){
        return color;
    }
    
    void setColor(Color c){
        color = c;
    }
}
