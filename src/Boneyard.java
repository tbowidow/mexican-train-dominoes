/**
 * Thomas Bowidowicz
 * CS351L - Project 2
 * 
 * The Boneyard class defines the set of dominoes along with the center
 * and mexican trains. The dominoSet is an ArrayList that is created
 * by the Boneyard constructor which takes in the set size and the current
 * double domino that is starting at the center. There is a shuffle method
 * that uses a randomly generated number from the dominoSet.size() base to
 * mix the order of the dominoes up. A switch statement in the constructor
 * determines which double is removed. There is also a deal domino method
 * that pops the top domino from the boneyard and gives it to a player.
 * Finally, there are show methods for all of the trains, the set, and the
 * boneyard itself.
 */

import java.util.*;
import java.security.SecureRandom;
import java.util.Iterator;

public class Boneyard {

    private static final SecureRandom randomNumbers = new SecureRandom();

    ArrayList<Domino> trainCenter = new ArrayList<Domino>();
    ArrayList<Domino> trainMexican = new ArrayList<Domino>();

    private ArrayList<Domino> dominoSet = new ArrayList<Domino>();
    private Domino center;
    private int currentDomino = 0;
    private int set;

    Iterator iterator = dominoSet.iterator();

    public Boneyard(int set, int dominoToRemove) {

        this.set = set;

        //Populate the domino set except the double nine
        for (int i = 0; i <= set; i++) {
            for(int j = i; j <= set; j++) {
                dominoSet.add(new Domino(i, j));
            }
        }

        if (set == 9) {
            // Remove the double domino from set and set it to center
            switch (dominoToRemove) {
                case 9:
                    dominoSet.remove(54);
                    center = new Domino(9, 9);
                    break;
                case 8:
                    dominoSet.remove(52);
                    center = new Domino(8, 8);
                    break;
                case 7:
                    dominoSet.remove(49);
                    center = new Domino(7, 7);
                    break;
                case 6:
                    dominoSet.remove(45);
                    center = new Domino(6, 6);
                    break;
                case 5:
                    dominoSet.remove(40);
                    center = new Domino(5, 5);
                    break;
                case 4:
                    dominoSet.remove(34);
                    center = new Domino(4, 4);
                    break;
                case 3:
                    dominoSet.remove(27);
                    center = new Domino(3, 3);
                    break;
                case 2:
                    dominoSet.remove(19);
                    center = new Domino(2, 2);
                    break;
                case 1:
                    dominoSet.remove(10);
                    center = new Domino(1, 1);
                    break;
                case 0:
                    dominoSet.remove(0);
                    center = new Domino(0, 0);
                    break;
            }
        } else if (set == 12) {
            switch (dominoToRemove) {
                case 12:
                    dominoSet.remove(90);
                    center = new Domino(9, 9);
                    break;
                case 11:
                    dominoSet.remove(88);
                    center = new Domino(9, 9);
                    break;
                case 10:
                    dominoSet.remove(85);
                    center = new Domino(9, 9);
                    break;
                case 9:
                    dominoSet.remove(81);
                    center = new Domino(9, 9);
                    break;
                case 8:
                    dominoSet.remove(76);
                    center = new Domino(8, 8);
                    break;
                case 7:
                    dominoSet.remove(70);
                    center = new Domino(7, 7);
                    break;
                case 6:
                    dominoSet.remove(63);
                    center = new Domino(6, 6);
                    break;
                case 5:
                    dominoSet.remove(55);
                    center = new Domino(5, 5);
                    break;
                case 4:
                    dominoSet.remove(46);
                    center = new Domino(4, 4);
                    break;
                case 3:
                    dominoSet.remove(36);
                    center = new Domino(3, 3);
                    break;
                case 2:
                    dominoSet.remove(25);
                    center = new Domino(2, 2);
                    break;
                case 1:
                    dominoSet.remove(13);
                    center = new Domino(1, 1);
                    break;
                case 0:
                    dominoSet.remove(0);
                    center = new Domino(0, 0);
                    break;
            }
        }

        trainCenter.add(center);
        trainMexican.add(center);
    }


    public void shuffle() {

        currentDomino = 0;

        // For each Domino, pick another random Domino (0-54) - the double nine
        // or (0-90) - the double twelve, and swap them
        for (int first = 0; first < dominoSet.size()-1; first++) {
            // select a random number between 0 and 54 or 0 and 90
            int second = randomNumbers.nextInt(dominoSet.size()-1);

            // swap current Domino with randomly selected Domino
            Domino temp = dominoSet.get(first);
            dominoSet.set(first, dominoSet.get(second));
            dominoSet.set(second, temp);
        }

    }

    public void showCenter() {
        for (int i = 0; i < trainCenter.size(); i+=2) {
            System.out.print(trainCenter.get(i) + " ");
        }

        System.out.println();
        System.out.print("            ");

        for (int i = 1; i < trainCenter.size(); i+=2) {
            System.out.print(trainCenter.get(i) + " ");
        }

        System.out.println();
    }

    public void showMexicanTrain() {
        for (int i = 0; i < trainMexican.size(); i+=2) {
            System.out.print(trainMexican.get(i) + " ");
        }

        System.out.println();
        System.out.print("                   ");

        for (int i = 1; i < trainMexican.size(); i+=2) {
            System.out.print(trainMexican.get(i) + " ");
        }

        System.out.println();
    }

    public void showDominoes() {

        for(int i = 0; i < dominoSet.size(); i+=2) {
            System.out.print(dominoSet.get(i) + " ");
        }

        System.out.println();
        System.out.print("    ");

        for(int i = 1; i < dominoSet.size(); i+=2) {
            System.out.print(dominoSet.get(i) + " ");
        }
        System.out.println();

    }

    // Deal one Domino
    public Domino dealDomino() {
        // Determine whether dominoes remain to be dealt
        if (iterator.hasNext()) {
            Domino temp = new Domino(0,0);
            temp = dominoSet.get(currentDomino);
            dominoSet.remove(currentDomino);
            return temp; // return current Domino in array
        }
        else {
            System.out.println ( "Ran out of dominoes" );
            return null; // return null to indicate that all Dominoes were dealt
        }
    }

    public Domino getCenter() {
        return center;
    }

    public boolean dominoSetEmpty() {
        if (dominoSet.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}