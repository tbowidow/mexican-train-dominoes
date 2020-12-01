/**
 * Thomas Bowidowicz
 * CS351L - Project 2 - Mexican Train Dominoes
 *
 * This program runs the Mexican Train dominoes game as listed in the handout
 * in the terminal and serves as the basis for the GUI version of the game.
 * This game class serves as the main class for the game from which the
 * program is run. It uses a game method to managae all of the method calls
 * which start the game such as initialize. The initialize mehod asks players
 * for the size domino set they wish to use (double 9 or double 12), the
 * number of human players, and the number of computer players (there can
 * be any combination including only computers). These players are stored into
 * an ArrayList. Once this information is gathered, the game method calls the
 * round method which starts by instantiating the Boneyard class (which houses
 * the domino set, center train, and the mexican train) and then enters the
 * main game loop. This loop checks to see if the player is a human or computer
 * and either runs the AI logic or the human options. Both players will see
 * a current state of the board from the gameState method.
 *
 * The game will check for whether plays are legal or not by checking if there
 * are any open double dominoes unaddressed or by seeing if player's have a
 * marker on their train so others can play on it. After running the computer
 * against itself many times, I found that it is possible to stalemate and so
 * I added a playerOut method in the Player class that activates if players
 * have no option to play. The game method will loop through the rounds after
 * each round ends and keep a running tally of the score. Once all rounds are
 * over, the total scores are checked and the lowest score is declared the
 * winner.
 *
 * It is suggested to play computers against each other or else the end state
 * will take forever to get to!
 */

import java.util.*;
import java.util.Scanner;
import java.util.Iterator;

public class Game {

    Scanner scan = new Scanner(System.in);

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

    public void initialize() {

        // Welcome and player composition inputs
        System.out.println("Welcome to Mexican Train Dominoes!");

        // Input size of domino set
        System.out.println(
                "Would you like to play with a 9 or a 12 set? [9/12]");
        String set = scan.nextLine();
        setSize = Integer.valueOf(set);

        while (numberOfComps + numberOfPlayers == 0 ||
                (numberOfComps + numberOfPlayers) > 8) {
                System.out.println(
                        "Please enter the number of human players: ");
                String humanPlayers = scan.nextLine();
                numberOfPlayers = Integer.valueOf(humanPlayers);


                System.out.println(
                        "Please enter the number of computer players: ");
                String compPlayers = scan.nextLine();
                numberOfComps = Integer.valueOf(compPlayers);

                if (numberOfComps > 8 ||
                        (numberOfComps + numberOfPlayers) > 8) {
                    System.out.println(
                            "The limit of players is 8, please reinput: ");
                }

        }

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

        // Composition of players and intro to game
        System.out.print("There are " + numberOfPlayers + " human players ");
        System.out.println("and " + numberOfComps + " computer players.");
        System.out.println("Let's play Mexican Train Dominoes!");

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

    public void round(int doubleDominoRemoved) {

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

        // Main game loop
        while(roundOver == false) {
            // Start of game
            System.out.println("Each player is being dealt " +
                                initialDominoesDealt + " dominoes.");

            dealDominoes();

            // Loop for mid round to determine if keep playing
            while (playerHasHand == true) {
                // Check if computer
                if (playerArray.get(currentPlayerIndex) instanceof Computer) {
                    // run comp AI logic
                    while (compMoves <= 1) {

                        ((Computer)playerArray.get(currentPlayerIndex)).
                                clearOrderChain();
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                orderChain();
                        ((Computer)playerArray.get(currentPlayerIndex)).
                                printOrderChain();
                        gameState();
                        if (computerMatch()) {
                            if (computerMatch()) {
                                // Create combo list
                                // Play domino match
                                // iterate through hand, find and match played
                                // domino, remove from hand

                                if (computerMatchIndex == 10) {
                                    if (closeTrains == false) {
                                        whichDomino = ((Computer)playerArray.
                                                get(currentPlayerIndex))
                                                .findHandMatch(
                                                        (computerHighestPipIndex));
                                        whichDomino++;
                                        canPlayCenter();
                                        whichDomino--;
                                        dominoPlayed = false;
                                        needToFlip = false;
                                    } else if (closeTrains == true) {
                                        if (openDoubleTrains.
                                                get(openTrainIterator)
                                                == computerMatchIndex) {
                                            whichDomino =
                                                    ((Computer)playerArray.
                                                            get(currentPlayerIndex))
                                                    .findHandMatch(
                                                            (computerHighestPipIndex));
                                            whichDomino++;
                                            canPlayCenter();
                                            whichDomino--;
                                            dominoPlayed = false;
                                            needToFlip = false;
                                            openDoubleTrains.
                                                    remove(openTrainIterator);
                                        } else {
                                            System.out.print(
                                                    "You need to play on");
                                            System.out.println(
                                                    " the open double train.");
                                        }
                                    }

                                } else if (computerMatchIndex == 11) {
                                    if (closeTrains == false) {
                                        whichDomino = ((Computer)playerArray.
                                                get(currentPlayerIndex))
                                                .findHandMatch(
                                                        (computerHighestPipIndex));
                                        whichDomino++;
                                        canPlayMexican();
                                        whichDomino--;
                                        dominoPlayed = false;
                                        needToFlip = false;
                                    } else if (closeTrains == true) {
                                        if (openDoubleTrains.
                                                get(openTrainIterator) == 11) {
                                            whichDomino =
                                                    ((Computer)playerArray.
                                                            get(currentPlayerIndex))
                                                    .findHandMatch(
                                                            (computerHighestPipIndex));
                                            whichDomino++;
                                            canPlayMexican();
                                            whichDomino--;
                                            dominoPlayed = false;
                                            needToFlip = false;
                                            openDoubleTrains.remove(
                                                    openTrainIterator);
                                        } else {
                                            System.out.print(
                                                    "You need to play on");
                                            System.out.println(
                                                    " the open double train.");
                                        }
                                    }

                                } else  {
                                    if (closeTrains == false) {
                                        whichDomino = ((Computer)playerArray.
                                                get(currentPlayerIndex))
                                                .findHandMatch(
                                                        (computerHighestPipIndex)
                                                );
                                        whichDomino++;
                                        canPlay(computerMatchIndex);
                                        whichDomino--;
                                        dominoPlayed = false;
                                        needToFlip = false;
                                    } else if (closeTrains == true) {
                                        if (openDoubleTrains.get(
                                                openTrainIterator) ==
                                                computerMatchIndex) {
                                            whichDomino =
                                                    ((Computer)playerArray.get(
                                                            currentPlayerIndex)
                                                    )
                                                    .findHandMatch(
                                                            (computerHighestPipIndex));
                                            whichDomino++;
                                            canPlay(computerMatchIndex);
                                            whichDomino--;
                                            dominoPlayed = false;
                                            needToFlip = false;
                                            openDoubleTrains.
                                                    remove(openTrainIterator);
                                        } else {
                                            System.out.print(
                                                    "You need to play on");
                                            System.out.println(
                                                    " the open double train.");
                                        }
                                    }

                                }

                                if (playerArray.get(currentPlayerIndex).
                                        getMarker() == true) {
                                    playerArray.get(currentPlayerIndex).
                                            setMarker(false);
                                }
                            } else {
                                System.out.println(
                                        "Computer cannot play this turn.");
                                playerArray.get(currentPlayerIndex).
                                        setMarker(true);
                            }
                            compMoves += 2;

                        } else {
                            System.out.println(
                                    "Computer has no match. Drawing domino.");
                            // Check if boneyard is empty
                            if (boneyard.dominoSetEmpty()) {
                                System.out.println("Boneyard is empty.");
                                playerArray.get(currentPlayerIndex).
                                        setMarker(true);
                                playerArray.get(currentPlayerIndex).
                                        setPlayerOut(true);
                            } else {
                                playerArray.get(currentPlayerIndex).hand.
                                        add(boneyard.dealDomino());
                                System.out.println("Computer was dealt: " +
                                        playerArray.get(currentPlayerIndex).
                                                hand.get(playerArray.
                                                get(currentPlayerIndex).hand.
                                                size() - 1));
                            }
                            compMoves++;
                        }

                    }
                    compMoves = 0;
                } else {
                    //Call gamestate to run menu, options, and logic
                    gameState();
                }

                // Check current player if they have a hand
                if ((playerArray.get(currentPlayerIndex).
                        hand.isEmpty()) == true) {
                    playerHasHand = false;
                }

                gameOverCounter = 0;

                // Check all players to see if they are out
                for (int i = 0; i < playerArray.size(); i++) {
                    if (playerArray.get(i).getPlayerOut() == true) {
                        gameOverCounter++;
                    }
                }

                if (gameOverCounter == playerArray.size()) {
                    playerHasHand = false;
                }

                // Move to next player and back to first if everyone has gone
                // Also check if the last domino played was a double
                if (currentPlayer < totalPlayers && (playerArray.
                        get(currentPlayerIndex).getPlayedDouble() == false)) {
                    currentPlayer++;
                    currentPlayerIndex++;
                    if(doubleOpen()) {
                        closeTrains = true;
                    } else {
                        closeTrains = false;
                    }
                } else if (currentPlayer >= totalPlayers &&
                        (playerArray.get(currentPlayerIndex).
                                getPlayedDouble() == false)) {
                    currentPlayer = 1;
                    currentPlayerIndex = 0;
                    if(doubleOpen()) {
                        closeTrains = true;
                    } else {
                        closeTrains = false;
                    }
                } else if (playerArray.get(currentPlayerIndex).
                        getPlayedDouble() == true) {
                    playerArray.get(currentPlayerIndex).
                            setPlayedDouble(false);
                    currentPlayer = currentPlayer;
                    currentPlayerIndex = currentPlayerIndex;
                }
            }

            //Exit loop
            roundOver = true;

            System.out.println("Round over!");
            System.out.println("Here are the current scores: ");


            // Add round score to each player
            for (int i = 0; i < playerArray.size(); i++) {
                playerArray.get(i).roundScores.add(playerArray.get(i).
                        tallyScore());
                System.out.println("Player " + i + ": ");
                for (int j = 0; j < playerArray.get(i).roundScores.size();
                     j++) {
                    System.out.print(playerArray.get(i).roundScores.
                            get(j) + " ");
                }
                System.out.println();
            }

        }

        roundOver = false;

    }

    public void dealDominoes() {
        // Dealing dominoes
        for (int i = 0; i < totalPlayers &&
                iterator.hasNext(); i++) {
            for (int j = 0; j < initialDominoesDealt; j ++) {
                playerArray.get(i).hand.add(boneyard.dealDomino());
            }
        }
    }

    // Prints out the gamestate
    public void gameState() {
        System.out.println();
        System.out.println("Gamestate:");

        if (!(openDoubleTrains.isEmpty())) {
            System.out.println("****** There is " + openDoubleTrains.size() +
                    " open double on train that need to be played on ******");
            System.out.println("Open Doubles: ");
            for (int i = 0; i < openDoubleTrains.size(); i++) {
                if (openDoubleTrains.get(i) == 10) {
                    System.out.println("Center train");
                } else if (openDoubleTrains.get(i) == 11) {
                    System.out.println("Mexican train");
                } else {
                    System.out.println("Player " + openDoubleTrains.
                            get(i) + "'s train");
                }
            }
        }

        // Print hand for players
        for (int i = 0; i < totalPlayers; i++) {
            System.out.print("Player " + (i + 1) + "'s hand: [");
            playerArray.get(i).showHand();
        }

        // Print out center
        System.out.print("Center: ");
        boneyard.showCenter();

        // Print out Mexican train
        System.out.print("Mexican train: ");
        boneyard.showMexicanTrain();

        // Print out trains
        for (int i = 0; i < totalPlayers; i++) {
            // Check if marker is on train for others to play
            if (playerArray.get(i).getMarker() == true) {
                System.out.print("*");
            }
                System.out.print("Player " + (i + 1) + " train: ");
                playerArray.get(i).showTrain();
        }

        // Print boneyard
        System.out.println("Boneyard: ");
        boneyard.showDominoes();

        // Current player
        System.out.println("Current player: Player " + currentPlayer);

        // Menu
        System.out.println("Player " + currentPlayer + "'s turn: ");
        System.out.println("[p] Play domino");
        System.out.println("[d] Draw domino from boneyard");
        System.out.println("[q] Quit");

        if (!(playerArray.get(currentPlayerIndex) instanceof Computer)) {
            makeChoice();
        }
    }

    public void makeChoice() {
        // Scan input
        String command = scan.nextLine();

        // Switch statement
        switch(command) {
            case "p":
                playDomino();
                break;
            case "d":
                if (boneyard.dominoSetEmpty()) {
                    System.out.println("Boneyard is empty.");
                    playerArray.get(currentPlayerIndex).setMarker(true);
                } else {
                    playerArray.get(currentPlayerIndex).hand.
                            add(boneyard.dealDomino());
                    System.out.println("You were dealt: " + playerArray.
                            get(currentPlayerIndex).hand.get(playerArray.
                            get(currentPlayerIndex).hand.size()-1));
                    playDomino();
                }
                break;
            case "q":
                System.out.println("Quitting game. Thanks for playing!");
                playerHasHand = false;
                roundOver = true;
                quitGame = true;
                break;
            default:
                System.out.println("Invalid command, please try again.");
        }
    }

    // Most of the game logic
    public void playDomino() {

            while (dominoPlayed == false) {
                System.out.println("Can you play a domino? [y/n]");
                String canPlay = scan.nextLine();

                if (canPlay.equals("y")) {
                    dominoPlayed = false;
                } else {
                    dominoPlayed = true;
                    playerArray.get(currentPlayerIndex).setMarker(true);
                    break;
                }

                System.out.println("Which domino would you like to play?");

                String nextCommand = scan.nextLine();
                whichDomino = Integer.valueOf(nextCommand);

                if (whichDomino == 1) {
                    System.out.println("You picked the " +
                            whichDomino + "st domino: " +
                            playerArray.get(currentPlayerIndex).
                                    hand.get(whichDomino - 1));
                } else {
                    System.out.println("You picked the " +
                            whichDomino + "th domino: " + playerArray.
                            get(currentPlayerIndex).hand.
                            get(whichDomino - 1));
                }

                // Train choice
                System.out.print("Which train would you like to play on? ");
                System.out.println("[center/player1/mexican...]");
                String nextInput = scan.nextLine();
                whichTrain = nextInput;

                // Flip or not
                System.out.println("Would you like to flip the domino? [y/n]");
                nextInput = scan.nextLine();
                if (nextInput.equals("y")) {
                    playerArray.get(currentPlayerIndex).hand.
                            get(whichDomino - 1).flipDomino();
                }

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
                            } else {
                                System.out.print("You need to play on");
                                System.out.println(" the open double train.");
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
                            } else {
                                System.out.print("You need to play on");
                                System.out.println(" the open double train.");
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
                            } else {
                                System.out.print("You need to play on");
                                System.out.println(" the open double train.");
                            }
                        }
                        break;
                }
            }
            dominoPlayed = false;
    }

    // Checks if there is a move available
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

    // Checks to see if you can play the domino listed on the train requested
    // and plays it
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

        } else {
            System.out.println("You cannot play that domino there.");
        }
    }

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

        } else {
            System.out.println("You cannot play that domino there.");
        }
    }

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

        } else {
            System.out.println("You cannot play that domino there.");
        }
    }

    // Checks to see if there are any open doubles
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

    // Main computer matching code that checks to see if there is any
    // match with the computer's highest valued domino. If not, it moves
    // to the next highest value and so on. If there's a match, it returns
    // true and sets the location of the match and the highestPip domino
    // if not, it returns false
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

    // Tallies the final score
    public int finalScore() {
        System.out.println("The final scores for the game is: ");
        for (int i = 0; i < playerArray.size(); i++) {
            finalScore = playerArray.get(i).totalRoundScores();
            if (finalScore < lowestHighestScore) {
                lowestHighestScore = finalScore;
                winner = i + 1;
            }
            System.out.println("Player " + (i + 1) + "'s total score: " +
                    finalScore);
        }
        return winner;
    }

    public Game() {
        initialize();
        for (int i = setSize; i >= 0; i--) {
            if (quitGame == false) {
                System.out.println("Starting round with the double " +
                        i + " domino in the center.");
                round(i);
            }
        }
        System.out.println("Congratulations Player " + finalScore() +
                "! You are the winner!");
        System.out.println("Thank you for playing.");
    }

    public static void main(String[] args ) {
        Game game = new Game();
    }
}