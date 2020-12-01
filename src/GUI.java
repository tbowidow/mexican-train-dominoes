/**
 * Thomas Bowidowicz
 * CS351L - Project 2 - Mexican Train Dominoes
 *
 * This is the GUI version of the main Game class that runs the terminal
 * version of the game. The only changes between this class and the original
 * are the removal of the gameState(), makeChoice(), and round() classes from
 * this version in addition to removing a lot of my print statements that were
 * tied into the other version. This makes it more of a model that the controller
 * and FXML can manipulate.
 */

import java.util.*;
import java.util.Scanner;
import java.util.Iterator;

public class GUI {

    Scanner scan = new Scanner(System.in);

    /**
     * The variables names are self explanatory as to their role.
     */
    int numberOfPlayers;
    int numberOfComps;
    int totalPlayers;
    int initialDominoesDealt;
    int currentPlayer;
    int currentPlayerIndex;
    int whichDomino;
    int trainNumber;
    int winner;
    int finalScore;
    int compMoves = 0;
    int computerMatchIndex;
    int computerHighestPipIndex;
    int whichTrainNum;
    int openTrainIterator = 0;
    int openDoubleIndex;
    int gameOverCounter = 0;
    int lowestHighestScore = 99999;
    int setSize;

    String whichTrain;

    boolean roundOver = false;
    boolean playerHasHand = true;
    boolean dominoPlayed = false;
    boolean quitGame = false;
    boolean compTurn = false;
    boolean closeTrains = false;
    boolean needToFlip = false;

    //Initialize all players
    Player p1 = new Player();
    Player p2 = new Player();
    Player p3 = new Player();
    Player p4 = new Player();
    Player p5 = new Player();
    Player p6 = new Player();
    Player p7 = new Player();
    Player p8 = new Player();

    //Initialize all computers
    Computer c1 = new Computer();
    Computer c2 = new Computer();
    Computer c3 = new Computer();
    Computer c4 = new Computer();
    Computer c5 = new Computer();
    Computer c6 = new Computer();
    Computer c7 = new Computer();
    Computer c8 = new Computer();


    //Array of player objects
    ArrayList<Player> playerArray = new ArrayList<Player>();
    ArrayList<Integer> openDoubleTrains = new ArrayList<Integer>();

    // Iterator instance
    Iterator iterator = playerArray.iterator();

    Boneyard boneyard;

    public int getActivePlayer() {
        return currentPlayerIndex;
    }

    /**
     * The initialize method starts the game by taking the
     * size of the domino set players wish to use, the number
     * of human players, and the number of computer players. Once that is set,
     * it adds the required number of player objects to the playerArray list
     * and determines the number of starting dominoes to be dealt to each player.
     */
    public void initialize() {

        totalPlayers = numberOfPlayers + numberOfComps;

        // Adding players to the array
        if (numberOfPlayers == 1 ) {
            playerArray.add(p1);
        } else if (numberOfPlayers == 2) {
            playerArray.add(p1);
            playerArray.add(p2);
        } else if (numberOfPlayers == 3) {
            playerArray.add(p1);
            playerArray.add(p2);
            playerArray.add(p3);
        } else if (numberOfPlayers == 4) {
            playerArray.add(p1);
            playerArray.add(p2);
            playerArray.add(p3);
            playerArray.add(p4);
        } else if (numberOfPlayers == 5) {
            playerArray.add(p1);
            playerArray.add(p2);
            playerArray.add(p3);
            playerArray.add(p4);
            playerArray.add(p5);
        } else if (numberOfPlayers == 6) {
            playerArray.add(p1);
            playerArray.add(p2);
            playerArray.add(p3);
            playerArray.add(p4);
            playerArray.add(p5);
            playerArray.add(p6);
        } else if (numberOfPlayers == 7) {
            playerArray.add(p1);
            playerArray.add(p2);
            playerArray.add(p3);
            playerArray.add(p4);
            playerArray.add(p5);
            playerArray.add(p6);
            playerArray.add(p7);
        } else if (numberOfPlayers == 8) {
            playerArray.add(p1);
            playerArray.add(p2);
            playerArray.add(p3);
            playerArray.add(p4);
            playerArray.add(p5);
            playerArray.add(p6);
            playerArray.add(p7);
            playerArray.add(p8);
        }

        // Adding comps to the array
        if (numberOfComps == 1 ) {
            playerArray.add(c1);
        } else if (numberOfComps == 2) {
            playerArray.add(c1);
            playerArray.add(c2);
        } else if (numberOfComps == 3) {
            playerArray.add(c1);
            playerArray.add(c2);
            playerArray.add(c3);
        } else if (numberOfComps == 4) {
            playerArray.add(c1);
            playerArray.add(c2);
            playerArray.add(c3);
            playerArray.add(c4);
        } else if (numberOfComps == 5) {
            playerArray.add(c1);
            playerArray.add(c2);
            playerArray.add(c3);
            playerArray.add(c4);
            playerArray.add(c5);
        } else if (numberOfComps == 6) {
            playerArray.add(c1);
            playerArray.add(c2);
            playerArray.add(c3);
            playerArray.add(c4);
            playerArray.add(c5);
            playerArray.add(c6);
        } else if (numberOfComps == 7) {
            playerArray.add(c1);
            playerArray.add(c2);
            playerArray.add(c3);
            playerArray.add(c4);
            playerArray.add(c5);
            playerArray.add(c6);
            playerArray.add(c7);
        } else if (numberOfComps == 8) {
            playerArray.add(c1);
            playerArray.add(c2);
            playerArray.add(c3);
            playerArray.add(c4);
            playerArray.add(c5);
            playerArray.add(c6);
            playerArray.add(c7);
            playerArray.add(c8);
        }

        // Determining how many initial dominoes dealt
        // Check which domino double set
        if (setSize == 9) {
            if (totalPlayers == 2) {
                initialDominoesDealt = 15;
            } else if (totalPlayers == 3) {
                initialDominoesDealt = 13;
            } else {
                initialDominoesDealt = 10;
            }
        } else if (setSize == 12) {
            if (totalPlayers == 2 || totalPlayers == 3) {
                initialDominoesDealt = 16;
            } else if (totalPlayers == 4) {
                initialDominoesDealt = 15;
            } else if (totalPlayers == 5) {
                initialDominoesDealt = 14;
            } else if (totalPlayers == 6) {
                initialDominoesDealt = 12;
            } else if (totalPlayers == 7) {
                initialDominoesDealt = 10;
            } else {
                initialDominoesDealt = 9;
            }
        }
    }

    /**
     * The setUpData class sets up all of the game data and parameters needed
     * to launch the game. The int that is the double domino to be removed is
     * required as a parameter.
     */
    public void setUpData(int doubleDominoRemoved) {
        playerHasHand = true;
        currentPlayer = 1;
        currentPlayerIndex = 0;

        // Clear all player trains and reset playerOut
        for (int i = 0; i < playerArray.size(); i++) {
            playerArray.get(i).hand.clear();
            playerArray.get(i).trainPlayer.clear();
            playerArray.get(i).setPlayerOut(false);
        }

        // Eventually ask for 12 or 9 set
        boneyard = new Boneyard(setSize, doubleDominoRemoved);

        // Shuffle
        boneyard.shuffle();

        // Initialize player trains
        for (int i = 0; i < playerArray.size(); i ++) {
            playerArray.get(i).trainPlayer.add(boneyard.getCenter());
        }

        // Deal Dominoes
        dealDominoes();
    }

    /**
     * The dealDominoes method deals the initial amount of dominoes to each
     * player in the player array.
     */
    public void dealDominoes() {
        // Dealing dominoes
        for (int i = 0; i < totalPlayers &&
                iterator.hasNext(); i++) {
            for (int j = 0; j < initialDominoesDealt; j ++) {
                playerArray.get(i).hand.add(boneyard.dealDomino());
            }
        }
    }

    /**
     * The playDomino method takes in the values that the play button sets
     * and then actually checks to see if the requested move is valid. If
     * yes, it plays the domino.
     */
    public void playDomino() {

            switch(whichTrain) {
                case "center":
                    if (closeTrains == false) {
                        canPlayCenter();
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) ==
                                10) {
                            canPlayCenter();
                            openDoubleTrains.remove(openTrainIterator);
                        } else {
                            System.out.print("You need to play on");
                            System.out.println(" the open double train.");
                        }
                    }
                    break;
                case "mexican":
                    if (closeTrains == false) {
                        canPlayMexican();
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) ==
                                11) {
                            canPlayCenter();
                            openDoubleTrains.remove(openTrainIterator);
                        } else {
                            System.out.print("You need to play on");
                            System.out.println(" the open double train.");
                        }
                    }
                    break;
                case "player1":
                    trainNumber = 0;
                    if (closeTrains == false) {
                        canPlay(trainNumber);
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) ==
                                trainNumber) {
                            canPlay(trainNumber);
                            openDoubleTrains.remove(openTrainIterator);
                        } else {
                            System.out.print("You need to play on");
                            System.out.println(" the open double train.");
                        }
                    }
                    break;
                case "player2":
                    trainNumber = 1;
                    if (closeTrains == false) {
                        canPlay(trainNumber);
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) ==
                                trainNumber) {
                            canPlay(trainNumber);
                            openDoubleTrains.remove(openTrainIterator);
                        }
                    }
                    break;
                case "player3":
                    trainNumber = 2;
                    if (closeTrains == false) {
                        canPlay(trainNumber);
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) ==
                                trainNumber) {
                            canPlay(trainNumber);
                            openDoubleTrains.remove(openTrainIterator);
                        }
                    }
                    break;
                case "player4":
                    trainNumber = 3;
                    if (closeTrains == false) {
                        canPlay(trainNumber);
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) ==
                                trainNumber) {
                            canPlay(trainNumber);
                            openDoubleTrains.remove(openTrainIterator);
                        }
                    }
                    break;
            }

        dominoPlayed = false;
    }

    /**
     * The hasMove take an int of the index of the currentPlayer as a parameter
     * and checks if there is a valid move available for them to make. It is
     * used by the computer to check if they need to draw a domino or not. If
     * there is a move available, then it will return true. If not, false.
     */
    public boolean hasMove(int currentPlayer) {
        // Check player trains; iterates over all player trains
        for (int i = 0; i < playerArray.size(); i++) {
            // Iterates over current player's hand
            for (int j = 0; j < playerArray.get(currentPlayer).hand.size();
                 j++) {
                // Check both left and right values of the current domino
                if ((playerArray.get(i).trainPlayer.get(playerArray.get(i).
                        trainPlayer.size() - 1).getRightValue() ==
                        playerArray.get(currentPlayer).hand.get(j).
                                getLeftValue()) || (playerArray.get(i).
                        trainPlayer.get(playerArray.get(i).trainPlayer.
                        size() - 1).getRightValue() == playerArray.
                        get(currentPlayer).hand.get(j).getRightValue()) &&
                        ((currentPlayerIndex == i && playerArray.get(i).
                                getMarker() == false) ||
                                ((playerArray.get(i).getMarker() == true)) &&
                                        (closeTrains == false))) {
                    return true;
                }

                // Check center train
                if (((boneyard.trainCenter.get(boneyard.trainCenter.
                        size() - 1).getRightValue() == playerArray.
                        get(currentPlayer).hand.get(j).getLeftValue()) ||
                        (boneyard.trainCenter.get(boneyard.trainCenter.
                                size() - 1).getRightValue() == playerArray.
                                get(currentPlayer).hand.get(j).
                                getRightValue())) && closeTrains == false) {
                    return true;
                }

                // Check mexican train
                if (((boneyard.trainMexican.get(boneyard.trainMexican.
                        size() - 1).getRightValue() == playerArray.
                        get(currentPlayer).hand.get(j).getLeftValue()) ||
                        (boneyard.trainMexican.get(boneyard.trainMexican.
                                size() - 1).getRightValue() == playerArray.
                                get(currentPlayer).hand.get(j).
                                getRightValue())) && closeTrains == false) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * The canPlay method takes the int variable of the train the player wants
     * to play on and checks if they can actually play the domino there. If not,
     * it will print a statement to the console saying you cannot play it and
     * the player will go back to the playDomino method. If it is valid, then
     * the domino will be played and the dominoPlayed value will be set to true.
     */
    public void canPlay(int trainNumber) {
        // Check if it is the current player's train or another train
        // and if it has a marker on it
        if ((playerArray.get(trainNumber).trainPlayer.size() == 1 &&
                (((trainNumber != currentPlayerIndex) && (playerArray.
                        get(trainNumber).getMarker() == true)) ||
                        (trainNumber == currentPlayerIndex))) && (playerArray.
                get(trainNumber).trainPlayer.get(playerArray.get(trainNumber).
                trainPlayer.size() - 1).getRightValue() == playerArray.
                get(currentPlayerIndex).hand.get(whichDomino - 1).
                getLeftValue())) {
            playerArray.get(trainNumber).trainPlayer.add(
                    playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            // Check if double
            if (playerArray.get(currentPlayerIndex).
                    hand.get(whichDomino - 1).isDouble()) {
                playerArray.get(currentPlayerIndex).setPlayedDouble(true);
            }

            // Remove domino from player hand
            playerArray.get(currentPlayerIndex).hand.
                    remove(playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            dominoPlayed = true;
        } else if ((playerArray.get(trainNumber).trainPlayer.
                get(playerArray.get(trainNumber).trainPlayer.size() - 1).
                getRightValue() == playerArray.
                get(currentPlayerIndex).hand.get(whichDomino - 1).
                getLeftValue()) && (((trainNumber != currentPlayerIndex) &&
                (playerArray.get(trainNumber).getMarker() == true)) ||
                (trainNumber == currentPlayerIndex))) {
            playerArray.get(trainNumber).trainPlayer.add(
                    playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            // Check if double
            if (playerArray.get(currentPlayerIndex).
                    hand.get(whichDomino - 1).isDouble()) {
                playerArray.get(currentPlayerIndex).setPlayedDouble(true);

                playerArray.get(trainNumber).setOpenDouble(true);
            } else {
                playerArray.get(trainNumber).setOpenDouble(false);
            }

            // Remove marker
            if (trainNumber == currentPlayerIndex && playerArray.
                    get(currentPlayerIndex).getMarker() == true) {
                playerArray.get(currentPlayerIndex).setMarker(false);
            }

            // Remove domino from player hand
            playerArray.get(currentPlayerIndex).hand.
                    remove(playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));


            dominoPlayed = true;

        }
    }

    /**
     * The canPlayMexican method is similar to the method above. It checks to
     * see if the chosen domino can be played to the Mexican train. If it can,
     * then the it is played to the train, removed from the player's hand and
     * the dominoPlayed value is set to true.
     */
    public void canPlayMexican() {
        if (boneyard.trainMexican.size() == 1 && boneyard.trainMexican.
                get(boneyard.trainMexican.size() - 1).getRightValue() ==
                playerArray.get(currentPlayerIndex).
                        hand.get(whichDomino - 1).
                        getLeftValue()) {
            boneyard.trainMexican.add(
                    playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            // Check if double
            if (playerArray.get(currentPlayerIndex).
                    hand.get(whichDomino - 1).isDouble()) {
                playerArray.get(currentPlayerIndex).
                        setPlayedDouble(true);
            }

            // Remove domino from player hand
            playerArray.get(currentPlayerIndex).hand.
                    remove(playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            dominoPlayed = true;
        } else if (boneyard.trainMexican.get(boneyard.
                trainMexican.size() - 1).getRightValue() ==
                playerArray.get(currentPlayerIndex).
                        hand.get(whichDomino - 1).
                        getLeftValue()) {
            boneyard.trainMexican.add(
                    playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            // Check if double
            if (playerArray.get(currentPlayerIndex).
                    hand.get(whichDomino - 1).isDouble()) {
                playerArray.get(currentPlayerIndex).
                        setPlayedDouble(true);
                playerArray.get(trainNumber).setOpenDouble(true);
            } else {
                playerArray.get(trainNumber).setOpenDouble(false);
            }

            // Remove domino from player hand
            playerArray.get(currentPlayerIndex).hand.
                    remove(playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            dominoPlayed = true;

        }
    }

    /**
     * The canPlayCenter method is similar to the method above in that it checks
     * to see if the chosen domino can be played on the center train. If yes,
     * then it is added to the center train list and removed from the player's
     * list before the dominoPlayed value is set to true.
     */
    public void canPlayCenter() {
        if (boneyard.trainCenter.size() == 1 && boneyard.trainCenter.
                get(boneyard.trainCenter.size() - 1).getRightValue() ==
                playerArray.get(currentPlayerIndex).
                        hand.get(whichDomino - 1).
                        getLeftValue()) {
            boneyard.trainCenter.add(
                    playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            // Check if double
            if (playerArray.get(currentPlayerIndex).
                    hand.get(whichDomino - 1).isDouble()) {
                playerArray.get(currentPlayerIndex).
                        setPlayedDouble(true);

            }

            // Remove domino from player hand
            playerArray.get(currentPlayerIndex).hand.
                    remove(playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));


            dominoPlayed = true;
        } else if (boneyard.trainCenter.get(boneyard.
                trainCenter.size() - 1).getRightValue() ==
                playerArray.get(currentPlayerIndex).
                        hand.get(whichDomino - 1).
                        getLeftValue()) {
            boneyard.trainCenter.add(
                    playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            // Check if double
            if (playerArray.get(currentPlayerIndex).
                    hand.get(whichDomino - 1).isDouble()) {
                playerArray.get(currentPlayerIndex).
                        setPlayedDouble(true);
                playerArray.get(trainNumber).setOpenDouble(true);
            } else {
                playerArray.get(trainNumber).setOpenDouble(false);
            }

            // Remove domino from player hand
            playerArray.get(currentPlayerIndex).hand.
                    remove(playerArray.get(currentPlayerIndex).
                            hand.get(whichDomino - 1));

            dominoPlayed = true;

        }
    }

    /**
     * The doubleOpen method checks to see if there are any methods with open
     * double dominoes on them that haven't been played on yet. If so, it returns
     * true. If not, false.
     */
    public boolean doubleOpen() {
        int counter = 0;
        // Check through every train to see if there is an open double
        // Start at 1 to avoid the double center piece that starts each train
        for (int i = 0; i < playerArray.size(); i++) {
            // Checks if the last domino is an open double
            if ((playerArray.get(i).trainPlayer.get(playerArray.get(i).
                    trainPlayer.size() - 1).isDouble()) &&
                    (playerArray.get(i).trainPlayer.size()) != 1) {
                //Checks to see if value is already in openDouble Array
                for (int j = 0; j < openDoubleTrains.size(); j++) {
                    if (openDoubleTrains.get(j) != i) {
                        openDoubleTrains.add(i);
                        counter++;
                    }
                }
            }
        }

        // Check center train which is 10 in openDoubleTrains Arraylist
        if ((boneyard.trainCenter.size() != 1) &&
                boneyard.trainCenter.get(boneyard.trainCenter.size()-1).
                        isDouble()) {
            //Checks to see if value is already in openDouble Array
            for (int j = 0; j < openDoubleTrains.size(); j++) {
                if (openDoubleTrains.get(j) != 10) {
                    openDoubleTrains.add(10);
                    counter++;
                }
            }
        }

        // Check mexican train (Mexican train is 11)
        if ((boneyard.trainMexican.size() != 1) &&
                boneyard.trainMexican.get(boneyard.trainMexican.size()-1).
                        isDouble()) {
            //Checks to see if value is already in openDouble Array
            for (int j = 0; j < openDoubleTrains.size(); j++) {
                if (openDoubleTrains.get(j) != 11) {
                    openDoubleTrains.add(11);
                    counter++;
                }
            }
        }

        if (counter > 0) {
            return true;
        } else {
            return false;
        }
    }

    /** The computerMatch method is the main computer matching code that checks
     * to see if there is any match with the computer's highest valued domino.
     * If not, it moves to the next highest value and so on. If there's a match,
     * it returns true and sets the location of the match and the highestPip
     * domino if not, it returns false.
     */
    public boolean computerMatch() {
        for (int i = 0; i < ((Computer)playerArray.get(currentPlayerIndex)).
                highestPipChain.size(); i++) {
            for (int j = 0; j < playerArray.size(); j++) {
                // Check if player train's open domino is equal to the
                // highest pip chain first domino
                if (((playerArray.get(j).trainPlayer.get(playerArray.
                        get(j).trainPlayer.size() - 1).getRightValue()
                        == ((Computer)playerArray.
                        get(currentPlayerIndex)).highestPipChain.
                        get(i).getLeftValue())) &&
                        ((j != currentPlayerIndex) && playerArray.get(j).
                                getMarker() == true)) {
                    if (closeTrains == false) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = j;
                        return true;
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) == j) {
                            computerHighestPipIndex = i;
                            computerMatchIndex = j;
                            return true;
                        }
                    }
                } else if (((playerArray.get(j).trainPlayer.get(playerArray.
                        get(j).trainPlayer.size() - 1).getRightValue()
                        == ((Computer)playerArray.
                        get(currentPlayerIndex)).highestPipChain.
                        get(i).getLeftValue())) && (j == currentPlayerIndex)) {
                    if (closeTrains == false) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = j;
                        return true;
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) == j) {
                            computerHighestPipIndex = i;
                            computerMatchIndex = j;
                            return true;
                        }
                    }
                }

                // Check if left pip is a match and flip domino
                if ((playerArray.get(j).trainPlayer.get(playerArray.get(j).
                        trainPlayer.size() - 1).getRightValue() ==
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                highestPipChain.get(i).getRightValue()) &&
                        (playerArray.get(j).getMarker() == true)) {
                    if (closeTrains == false) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = j;
                        //Flip
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                highestPipChain.get(i).flipDomino();
                        needToFlip = true;
                        return true;
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) == j) {
                            computerHighestPipIndex = i;
                            computerMatchIndex = j;
                            //Flip
                            ((Computer)playerArray.get(currentPlayerIndex)).
                                    highestPipChain.get(i).flipDomino();
                            needToFlip = true;
                            return true;
                        }
                    }
                } else if ((playerArray.get(j).trainPlayer.
                        get(playerArray.get(j).trainPlayer.
                                size() - 1).getRightValue() ==
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                highestPipChain.get(i).getRightValue()) &&
                        ((j == currentPlayerIndex))) {
                    if (closeTrains == false) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = j;
                        //Flip
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                highestPipChain.get(i).flipDomino();
                        needToFlip = true;
                        return true;
                    } else if (closeTrains == true) {
                        if (openDoubleTrains.get(openTrainIterator) == j) {
                            computerHighestPipIndex = i;
                            computerMatchIndex = j;
                            //Flip
                            ((Computer)playerArray.get(currentPlayerIndex)).
                                    highestPipChain.get(i).flipDomino();
                            needToFlip = true;
                            return true;
                        }
                    }
                }
            }

            // Check center train return index 10
            if (((boneyard.trainCenter.get(boneyard.
                    trainCenter.size()-1)).getRightValue() ==
                    ((Computer)playerArray.get(currentPlayerIndex)).
                            highestPipChain.get(i).getLeftValue())) {
                if (closeTrains == false) {
                    computerHighestPipIndex = i;
                    computerMatchIndex = 10;
                    return true;
                } else if (closeTrains == true) {
                    if (openDoubleTrains.get(openTrainIterator) == 10) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = 10;
                        return true;
                    }
                }
            }

            // Check left pip and flip
            if ((boneyard.trainCenter.get(boneyard.trainCenter.size()-1).
                    getRightValue() == ((Computer)playerArray.
                    get(currentPlayerIndex)).highestPipChain.get(i).
                    getRightValue())) {
                if (closeTrains == false) {
                    computerHighestPipIndex = i;
                    computerMatchIndex = 10;
                    //Flip
                    ((Computer)playerArray.get(currentPlayerIndex)).
                            highestPipChain.get(i).flipDomino();
                    needToFlip = true;
                    return true;
                } else if (closeTrains == true) {
                    if (openDoubleTrains.get(openTrainIterator) == 10) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = 10;
                        //Flip
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                highestPipChain.get(i).flipDomino();
                        needToFlip = true;
                        return true;
                    }
                }
            }


            // Check mexican train
            if (((boneyard.trainMexican.get(boneyard.
                    trainMexican.size()-1)).getRightValue() ==
                    ((Computer)playerArray.get(currentPlayerIndex)).
                            highestPipChain.get(i).getLeftValue())) {
                if (closeTrains == false) {
                    computerHighestPipIndex = i;
                    computerMatchIndex = 11;
                    return true;
                } else if (closeTrains == true) {
                    if (openDoubleTrains.get(openTrainIterator) == 11) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = 11;
                        return true;
                    }
                }
            }

            // Check left and flip
            if ((boneyard.trainMexican.get(boneyard.trainMexican.
                    size()-1).getRightValue() == ((Computer)playerArray.
                    get(currentPlayerIndex)).highestPipChain.get(i).
                    getRightValue())) {
                if (closeTrains == false) {
                    computerHighestPipIndex = i;
                    computerMatchIndex = 11;
                    //Flip
                    ((Computer)playerArray.get(currentPlayerIndex)).
                            highestPipChain.get(i).flipDomino();
                    needToFlip = true;
                    return true;
                } else if (closeTrains == true) {
                    if (openDoubleTrains.get(openTrainIterator) == 11) {
                        computerHighestPipIndex = i;
                        computerMatchIndex = 11;
                        //Flip
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                highestPipChain.get(i).flipDomino();
                        needToFlip = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * The finalScore method tallies the final score for each player and
     * determines the winner. The winner's index for the player are is returned.
     */
    public int finalScore() {
        for (int i = 0; i < playerArray.size(); i++) {
            finalScore = playerArray.get(i).totalRoundScores();
            if (finalScore < lowestHighestScore) {
                lowestHighestScore = finalScore;
                winner = i + 1;
            }
        }
        return winner;
    }

}