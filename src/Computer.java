/**
 * Thomas Bowidowicz
 * CS351L - Project 2 - Mexican Train Dominoes
 * 
 * The Computer class extends the Player class, but adds some unique
 * methods that help the AI determine it's move. The orderChain method
 * copies the computer's hand into another ArrayList highestPipChain
 * that sorts the total pip value of the dominoes from highest to lowest
 * so that the computer can use that to determine which domino has the
 * highest priority to play. The findHandMatch method checks to see where
 * the highestPipChain domino that is played is located in the hand ArrayList.
 */

import java.util.*;

public class Computer extends Player {
    ArrayList<Domino> highestPipChain = new ArrayList<Domino>();

    public Computer() {
        super();
        this.highestPipChain = highestPipChain;
    }

    public void orderChain() {
        Domino temp = new Domino(0,0);

        // Copy hand to highestPipChain
        for (int i = 0; i < hand.size(); i++) {
            highestPipChain.add(hand.get(i));
        }

        // Sort largest total value to smallest
        for (int i = 0; i < highestPipChain.size(); i++) {
            for (int j = 0; j < highestPipChain.size(); j++) {
                if (highestPipChain.get(i).getTotalPips() <
                        highestPipChain.get(j).getTotalPips()) {
                    temp = highestPipChain.get(i);
                    highestPipChain.set(i, highestPipChain.get(j));
                    highestPipChain.set(j, temp);
                }
            }
        }

        Collections.reverse(highestPipChain);
    }

    public void printOrderChain() {
        for (int i = 0; i < highestPipChain.size(); i++) {
            System.out.print(highestPipChain.get(i) + ", ");
        }
        System.out.println();
    }

    public boolean hasDouble() {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).isDouble()) {
                return true;
            }
        }
        return false;
    }

    public int findHandMatch(int dominoPlayed) {
        int match = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (highestPipChain.get(dominoPlayed) == hand.get(i)) {
                match = i;
            }
        }
        return match;
    }

    public void clearOrderChain() {
        highestPipChain.clear();
    }
}