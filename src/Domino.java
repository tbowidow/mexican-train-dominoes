/**
 *  Thomas Bowidowicz
 *  CS351L - Project 2 - Mexican Train Dominoes
 *  This class defines a Domino object with a left and right pip
 *  value. There are getter and setter methods as well as a toString
 *  method, a getTotalPips method for the AI to determine the highest
 *  value domino, an isDouble method that checks if it is a double, and a
 *  flip domino method that flips the left and right values.
 */

public class Domino {

    private int leftValue;
    private int rightValue;
    private int totalPips;

    public Domino(int leftValue, int rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        totalPips = leftValue + rightValue;
    }

    public int getLeftValue() {
        return leftValue;
    }

    public int getRightValue() {
        return rightValue;
    }

    public int getTotalPips() {
        totalPips = leftValue + rightValue;
        return totalPips;
    }

    public boolean goodMatch(Domino first, Domino second) {
        if(first.rightValue == second.leftValue) {
            return true;
        } else {
            return false;
        }
    }

    public void flipDomino() {
        int tempValue = leftValue;
        leftValue = rightValue;
        rightValue = tempValue;
    }

    public boolean isDouble() {
        if(leftValue == rightValue) {
            return true;
        } else {
            return false;
        }
    }

    // Return String representation of domino
    public String toString() {
        return String.format("[%s | %s]", leftValue, rightValue);
    }

}