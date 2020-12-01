/**
 * Thomas Bowidowicz
 * CS351L - Project 2 - Mexican Train Dominoes
 * 
 * The Player class defines a player as having an ArrayList of a hand,
 * a train, and roundScores along with various states such as markers,
 * roundOver, openDouble on the player's train, and playerOut which get
 * toggled true or false depending on the state of the game. Beyond setter
 * and getter methods, there's a showHand method that prints out the hand of
 * the player, a showTrain method that prints out the player's train, and a
 * tallyScore method that gets the score for the entire hand. 
 */

import java.util.*;

public class Player {

    private int totalScore;
    private int totalRoundScores;
    private int roundScore;
    private boolean marker;
    private boolean roundOver;
    private boolean playedDouble = false;
    private boolean openDouble;
    private boolean playerOut;
    ArrayList<Domino> hand = new ArrayList<Domino>();
    ArrayList<Domino> trainPlayer = new ArrayList<Domino>();
    ArrayList<Integer> roundScores = new ArrayList<Integer>();

    public Player() {

        this.totalScore = totalScore;
        this.roundScore = roundScore;
        this.roundOver = roundOver;
        this.playedDouble = playedDouble;
        this.marker = marker;
        this.hand = hand;
        this.trainPlayer = trainPlayer;

    }

    public void showHand() {

        int i = 0;
        while (i < hand.size()) {
            if (i < hand.size() - 1) {
                System.out.print(hand.get(i) + ", ");
            } else {
                System.out.println(hand.get(i) + "]");
            }
            i++;
        }

    }

    public void showTrain() {
        if (trainPlayer.isEmpty()) {
            System.out.println("Train is empty");
        } else {
            for(int i = 0; i < trainPlayer.size(); i+=2) {
                System.out.print(trainPlayer.get(i) + " ");
            }

            System.out.println();
            System.out.print("                    ");

            for(int i = 1; i < trainPlayer.size(); i+=2) {
                System.out.print(trainPlayer.get(i) + " ");
            }
            System.out.println();
        }
    }

    public int tallyScore() {
        totalScore = 0;
        for (int i = 0; i < hand.size(); i++) {
            totalScore += hand.get(i).getLeftValue() + hand.get(i).getRightValue();
        }
        return totalScore;
    }

    public void setPlayedDouble(boolean playedDouble) {
        this.playedDouble = playedDouble;
    }

    public boolean getPlayedDouble() {
        return playedDouble;
    }

    public void setPlayerOut(boolean playerOut) {
        this.playerOut = playerOut;
    }

    public boolean getPlayerOut() {
        return playerOut;
    }

    public boolean getMarker() {
        return marker;
    }

    public void setMarker(boolean marker) {
        this.marker = marker;
    }

    public void setOpenDouble(boolean openDouble) {
        this.openDouble = openDouble;
    }

    public boolean getOpenDouble() {
        return openDouble;
    }

    public int totalRoundScores() {
        for (int i = 0; i < roundScores.size(); i++) {
            totalRoundScores += roundScores.get(i);
        }
        return totalRoundScores;
    }

}