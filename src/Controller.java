/**
 * Author - Thomas Bowidowicz
 * CS351L - Project 2 - Mexican Train Dominoes
 *
 * This is the controller class for my GUI version of the game. It manipulates
 * the FXML document and provides the event listener for the game in
 * addition to adding a few methods such as gameState which update the game's
 * visual state for the user. The main game loop's logic was moved over to this
 * class so that the game would work. This version only works for 4 players and
 * a set of double 9. USING A VALUE OF DOUBLE 12 OR MORE THAN 4 PLAYERS WILL
 * CRASH THE PROGRAM.
 *
 * There is no error checking in this. The user must input values as expected
 * and in the right order. The order in which to play it is:
 * 1) Enter the domino set size as 9, human player as desired, and computers
 * as desired so that the total number of players is 4 or less.
 * 2) Hit the start button
 * 3) If it is only computer, then the simulation will run to completion and
 * and print out the final results.
 * 4) If there is a human involved, it will start as their turn.
 * 5) The human should enter the number value of their domino (start at one
 * for the first domino in their hand), the train the wish to play on (player1,
 * player2, mexican, center...), and if they need to flip their domino.
 * 6) Click play for your round to be completed. The computer will immediately
 * follow.
 * 7) If the player wants to draw a domino, they can by pressing the draw
 * button.
 * 8) If the player can't play, press the can't play button.
 */

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Labeled;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import java.lang.Integer;

/**
 * The Controller class is the class that controls the FXML file and is the
 * interlocutor for the game logic and the visuals. Some of the main game
 * loop logic from the model was moved over to this to allow it to work.
 * This class handles all of the action listeners for the GUI.
 */
public class Controller implements Initializable {

    int width = 40;
    int height = 20;
    int i = 0;
    int j = 0;
    int drawCounter = 0;
    int totalPlayers;
    int humans;
    int computers;
    boolean cannotPlay;
    boolean roundOver;

    GUI gui;

   //@FXML public Pane board;
    @FXML public Button play;
    @FXML public Button draw;
    @FXML public Button quit;
    @FXML public Button start;
    @FXML public Button cantPlay;
    @FXML public TextField setSize;
    @FXML public TextField numHumans;
    @FXML public TextField numComps;
    @FXML public TextField whichDomino;
    @FXML public TextField whichTrain;
    @FXML public TextField flipDomino;
    @FXML public Label currentPlayerLabel;
    @FXML public HBox centerTrain;
    @FXML public Text text;
    @FXML public Label centerTrainLabel;
    @FXML public Label inGameText;
    @FXML public HBox boneyardField;
    @FXML public HBox boneyardTop;
    @FXML public HBox boneyardBottom;
    @FXML public HBox boneyardLabel;
    @FXML public HBox playerOneHand;
    @FXML public HBox playerTwoHand;
    @FXML public HBox playerThreeHand;
    @FXML public HBox playerFourHand;
    @FXML public HBox mexicanTrain;
    @FXML public HBox playerOneTrain;
    @FXML public HBox playerTwoTrain;
    @FXML public HBox playerThreeTrain;
    @FXML public HBox playerFourTrain;
    @FXML public HBox playerOneTrainLabel;
    @FXML public HBox playerTwoTrainLabel;
    @FXML public HBox playerThreeTrainLabel;
    @FXML public HBox playerFourTrainLabel;
    @FXML public HBox centerTrainBottom;
    @FXML public HBox mexicanTrainBottom;
    @FXML public HBox playerOneTrainBottom;
    @FXML public HBox playerTwoTrainBottom;
    @FXML public HBox playerThreeTrainBottom;
    @FXML public HBox playerFourTrainBottom;


    /**
     * The handlePlayButton method is the event handler for the play button.
     * It serves as a loop for any game in which there is a human player.
     * It takes the values in the textFields at the bottom of the page,
     * domino number, train, and flip (yes or no) and set the values in the
     * main game as these values before executing the playDomino method.
     * Afterwards, it checks some win conditions and then calls the computer
     * method if the next player is a computer before ending.
     */
    public void handlePlayButton(ActionEvent event) {
        if (cannotPlay == false) {
            // Sets value in GUI
            gui.whichDomino = Integer.valueOf(whichDomino.getText());
            gui.whichTrain = whichTrain.getText();
            if (flipDomino.getText().equals("yes")) {
                gui.playerArray.get(gui.currentPlayerIndex).hand.
                        get(gui.whichDomino - 1).flipDomino();
            }

            // Play domino
            gui.playDomino();
            gameState();
        }

        // Check all info
        // Check current player if they have a hand
        if ((gui.playerArray.get(gui.currentPlayerIndex).
                hand.isEmpty()) == true) {
            gui.playerHasHand = false;
        }

        gui.gameOverCounter = 0;

        // Check all players to see if they are out
        for (int i = 0; i < gui.playerArray.size(); i++) {
            if (gui.playerArray.get(i).getPlayerOut() == true) {
                gui.gameOverCounter++;
            }
        }

        // End round if over
        if (gui.gameOverCounter == gui.playerArray.size()) {
            roundOver = true;
        }

        // Move to next player and back to first if everyone has gone
        // Also check if the last domino played was a double
        if (gui.currentPlayer < gui.totalPlayers && (gui.playerArray.
                get(gui.currentPlayerIndex).getPlayedDouble() == false)) {
            gui.currentPlayer++;
            gui.currentPlayerIndex++;
            if(gui.doubleOpen()) {
                gui.closeTrains = true;
            } else {
                gui.closeTrains = false;
            }
        } else if (gui.currentPlayer >= gui.totalPlayers &&
                (gui.playerArray.get(gui.currentPlayerIndex).
                        getPlayedDouble() == false)) {
            gui.currentPlayer = 1;
            gui.currentPlayerIndex = 0;
            if(gui.doubleOpen()) {
                gui.closeTrains = true;
            } else {
                gui.closeTrains = false;
            }
        } else if (gui.playerArray.get(gui.currentPlayerIndex).
                getPlayedDouble() == true) {
            gui.playerArray.get(gui.currentPlayerIndex).
                    setPlayedDouble(false);
            gui.currentPlayer = gui.currentPlayer;
            gui.currentPlayerIndex = gui.currentPlayerIndex;
        }

        // Update image
        gameState();

        // Check if next player is a computer
        if (gui.playerArray.get(gui.currentPlayerIndex) instanceof Computer) {

            computer();

            // Check computer for ending conditions
            // Check all info
            // Check current player if they have a hand
            if ((gui.playerArray.get(gui.currentPlayerIndex).
                    hand.isEmpty()) == true) {
                gui.playerHasHand = false;
            }

            gui.gameOverCounter = 0;

            // Check all players to see if they are out
            for (int i = 0; i < gui.playerArray.size(); i++) {
                if (gui.playerArray.get(i).getPlayerOut() == true) {
                    gui.gameOverCounter++;
                }
            }

            // End round if over
            if (gui.gameOverCounter == gui.playerArray.size()) {
                roundOver = true;
            }

            // Move to next player and back to first if everyone has gone
            // Also check if the last domino played was a double
            if (gui.currentPlayer < gui.totalPlayers && (gui.playerArray.
                    get(gui.currentPlayerIndex).getPlayedDouble() == false)) {
                gui.currentPlayer++;
                gui.currentPlayerIndex++;
                if(gui.doubleOpen()) {
                    gui.closeTrains = true;
                } else {
                    gui.closeTrains = false;
                }
            } else if (gui.currentPlayer >= gui.totalPlayers &&
                    (gui.playerArray.get(gui.currentPlayerIndex).
                            getPlayedDouble() == false)) {
                gui.currentPlayer = 1;
                gui.currentPlayerIndex = 0;
                if(gui.doubleOpen()) {
                    gui.closeTrains = true;
                } else {
                    gui.closeTrains = false;
                }
            } else if (gui.playerArray.get(gui.currentPlayerIndex).
                    getPlayedDouble() == true) {
                gui.playerArray.get(gui.currentPlayerIndex).
                        setPlayedDouble(false);
                gui.currentPlayer = gui.currentPlayer;
                gui.currentPlayerIndex = gui.currentPlayerIndex;
            }

            gameState();
        }

        drawCounter = 0;
        cannotPlay = false;

        // Check round information
        if (roundOver == true) {
            nextRound();
        }
    }

    /**
     * The nextRound method is used for any game with at least one human and
     * will start a new round with the appropriate double domino at the center
     * and removed from the set. It will also print out the final win message
     * after all round have been completed. It's return type is void.
     */
    public void nextRound() {

        // Add round score to each player
        for (int i = 0; i < gui.playerArray.size(); i++) {
            gui.playerArray.get(i).roundScores.add(gui.playerArray.get(i).
                    tallyScore());
            inGameText.setText("Player " + i + ": ");
            for (int j = 0; j < gui.playerArray.get(i).roundScores.size();
                 j++) {
                inGameText.setText(gui.playerArray.get(i).roundScores.
                      get(j) + " ");
            }
        }

        // Start next round
        j--;

        if (j >= 0) {
            gui.setUpData(j);
            gameState();
        } else {
            inGameText.setText("Congratulations Player " + gui.finalScore()
                    + "! You are the winner!");
        }

    }

    /**
     * The handleCantPlay method is the event handler for the can't play button
     * which simply sets the marker on the player's train and prevents them
     * from being able to play once they press the play button.
     */
    @FXML public void handleCantPlay(ActionEvent event) {
        gui.playerArray.get(gui.currentPlayerIndex).setMarker(true);
        cannotPlay = true;
    }

    /**
     * The handleDraw button is the event handler for the draw domino button.
     * It checks if the player has already drawn a domino. If yes, it does
     * nothing, but if no, it gives the player a domino and removes it from
     * the boneyard.
     */
    @FXML public void handleDrawButton(ActionEvent event) {
        // Check draw counter
        if (drawCounter < 1) {
            // Deal domino
            if (gui.boneyard.dominoSetEmpty()) {
                inGameText.setText("Boneyard is empty.");
                gui.playerArray.get(gui.currentPlayerIndex).setMarker(true);
            } else {
                gui.playerArray.get(gui.currentPlayerIndex).hand.
                        add(gui.boneyard.dealDomino());
                inGameText.setText("You were dealt: " + gui.playerArray.
                        get(gui.currentPlayerIndex).hand.get(gui.playerArray.
                        get(gui.currentPlayerIndex).hand.size() - 1));
            }
        }

        drawCounter++;

        gameState();
    }

    /**
     * The handleQuitButton is the event handler for the quit button. It
     * simply shuts the application down.
     */
    @FXML public void handleQuitButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * The handleStartButton method is the event handler for the start
     * button. This takes the values from the text fields of the domino
     * set size, the number of human players, and the number of computer
     * players. It then sets these appropriate values to the GUI class.
     * It checks if it is only computers playing and if so, it only
     * calls the round method that allows the computers to run
     * uninterrupted. Otherwise, it initialized the game and prints the
     * gameState.
     */
    @FXML public void handleStartButton(ActionEvent event) {

        humans = Integer.valueOf(numHumans.getText());
        computers = Integer.valueOf(numComps.getText());
        totalPlayers = humans + computers;

        // Places values in GUI class initialize
        // Domino set size
        gui.setSize = Integer.valueOf(setSize.getText());
        gui.numberOfPlayers = humans;
        gui.numberOfComps = computers;

        j = gui.setSize;

        if (humans == 0) {
            // Call initialize
            gui.initialize();
            for (int i = gui.setSize; i >= 0; i--) {
                gui.setUpData(i);
                gameState();
                round();
            }
            inGameText.setText("Congratulations Player " + gui.finalScore()
                    + "! You are the winner!");
        } else {
            // Call initialize
            gui.initialize();
            gui.setUpData(gui.setSize);
            gameState();
        }

    }

    /**
     * The clear method simply clears all of the HBoxes that get dominoes
     * added to them so the screen can be redrawn.
     */
    public void clear() {
        // Clear the board
        playerOneHand.getChildren().clear();
        playerTwoHand.getChildren().clear();
        playerThreeHand.getChildren().clear();
        playerFourHand.getChildren().clear();

        playerOneTrain.getChildren().clear();
        playerTwoTrain.getChildren().clear();
        playerThreeTrain.getChildren().clear();
        playerFourTrain.getChildren().clear();

        centerTrain.getChildren().clear();
        mexicanTrain.getChildren().clear();
        boneyardTop.getChildren().clear();
        boneyardBottom.getChildren().clear();
        centerTrainBottom.getChildren().clear();
        mexicanTrainBottom.getChildren().clear();
        playerOneTrainBottom.getChildren().clear();
        playerTwoTrainBottom.getChildren().clear();
        playerThreeTrainBottom.getChildren().clear();
        playerFourTrainBottom.getChildren().clear();

    }

    /**
     * The gameState method redraws the entire board. It first calls the clear
     * method and then cycles through all of the player's hand and trains, the
     * center and mexican trains, and the boneyard, generates and adds the
     * dominoes to the HBoxes and finishes.
     */
    public void gameState() {

        clear();

        i++;
        // Current player
        currentPlayerLabel.setText(String.valueOf(gui.currentPlayer));

        inGameText.setText("Loop " + i);

        // Get player hands
        for (int j = 0; j < gui.playerArray.size(); j++) {
            // Get individual player hand
            Label label = new Label("Player " + j + " Hand");
            for (int i = 0; i < gui.playerArray.get(j).hand.size(); i++) {
                if (j == 0) {
                    StackPane stp = new StackPane();
                    Text dominoText = new Text(gui.playerArray.get(j).hand.
                            get(i).toString());
                    Rectangle rect = new Rectangle(width, height);
                    rect.setStroke(Color.WHITE);
                    dominoText.setFill(Color.WHITE);
                    stp.getChildren().addAll(rect, dominoText);
                    playerOneHand.getChildren().add(stp);
                } else if (j == 1) {
                    StackPane stp = new StackPane();
                    Text dominoText = new Text(gui.playerArray.get(j).hand.
                            get(i).toString());
                    Rectangle rect = new Rectangle(width, height);
                    rect.setStroke(Color.WHITE);
                    dominoText.setFill(Color.WHITE);
                    stp.getChildren().addAll(rect, dominoText);
                    playerTwoHand.getChildren().add(stp);
                } else if (j ==2) {
                    StackPane stp = new StackPane();
                    Text dominoText = new Text(gui.playerArray.get(j).
                            hand.get(i).toString());
                    Rectangle rect = new Rectangle(width, height);
                    rect.setStroke(Color.WHITE);
                    dominoText.setFill(Color.WHITE);
                    stp.getChildren().addAll(rect, dominoText);
                    playerThreeHand.getChildren().add(stp);
                } else if (j == 3) {
                    StackPane stp = new StackPane();
                    Text dominoText = new Text(gui.playerArray.get(j).hand.
                            get(i).toString());
                    Rectangle rect = new Rectangle(width, height);
                    rect.setStroke(Color.WHITE);
                    dominoText.setFill(Color.WHITE);
                    stp.getChildren().addAll(rect, dominoText);
                    playerFourHand.getChildren().add(stp);
                }
            }
        }

        // Get player trains
        for (int i = 0; i < gui.playerArray.size(); i++) {
            Label label = new Label("Player " + i + " Train");
            for (int j = 0; j < gui.playerArray.get(i).trainPlayer.size(); j++) {
                if (j % 2 == 0) {
                    if (i == 0) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerOneTrain.getChildren().add(stp);
                    } else if (i == 1) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerTwoTrain.getChildren().add(stp);
                    } else if (i == 2) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerThreeTrain.getChildren().add(stp);
                    } else if (i == 3) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerFourTrain.getChildren().add(stp);
                    }
                }
            }
        }

        // Train bottoms
        for (int i = 0; i < gui.playerArray.size(); i++) {
            Label label = new Label("Player " + i + " Train");
            if (i == 0) {
                Label labelPlayerOneBottom = new Label("      ");
                playerOneTrainBottom.getChildren().add(labelPlayerOneBottom);
            } else  if (i == 1 ) {
                Label labelPlayerTwoBottom = new Label("      ");
                playerTwoTrainBottom.getChildren().add(labelPlayerTwoBottom);
            } else  if (i == 2) {
                Label labelPlayerThreeBottom = new Label("      ");
                playerThreeTrainBottom.getChildren().add(labelPlayerThreeBottom);
            } else  if (i == 3) {
                Label labelPlayerFourBottom = new Label("      ");
                playerFourTrainBottom.getChildren().add(labelPlayerFourBottom);
            }


            for (int j = 0; j < gui.playerArray.get(i).trainPlayer.size(); j++) {
                if (j % 2 == 1) {
                    if (i == 0) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerOneTrainBottom.getChildren().add(stp);
                    } else if (i == 1) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerTwoTrainBottom.getChildren().add(stp);
                    } else if (i == 2) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerThreeTrainBottom.getChildren().add(stp);
                    } else if (i == 3) {
                        StackPane stp = new StackPane();
                        Text dominoText = new Text(gui.playerArray.get(i).
                                trainPlayer.get(j).toString());
                        Rectangle rect = new Rectangle(width, height);
                        rect.setStroke(Color.WHITE);
                        dominoText.setFill(Color.WHITE);
                        stp.getChildren().addAll(rect, dominoText);
                        playerFourTrainBottom.getChildren().add(stp);
                    }
                }
            }
        }


        // Get Center Train
        Label label = new Label("Center Train");
        for (int i = 0; i < gui.boneyard.trainCenter.size(); i++) {
            if (i % 2 == 0) {
                StackPane stp = new StackPane();
                Text dominoText = new Text(gui.boneyard.trainCenter.get(i).
                        toString());
                Rectangle rect = new Rectangle(width, height);
                rect.setStroke(Color.WHITE);
                dominoText.setFill(Color.WHITE);
                stp.getChildren().addAll(rect, dominoText);
                centerTrain.getChildren().add(stp);
            }
        }

        Label labelCenter = new Label("      ");
        centerTrainBottom.getChildren().add(labelCenter);
        for (int i = 0; i < gui.boneyard.trainCenter.size(); i++) {
            if (i % 2 == 1) {
                StackPane stp = new StackPane();
                Text dominoText = new Text(gui.boneyard.trainCenter.get(i).
                        toString());
                Rectangle rect = new Rectangle(width, height);
                rect.setStroke(Color.WHITE);
                dominoText.setFill(Color.WHITE);
                stp.getChildren().addAll(rect, dominoText);
                centerTrainBottom.getChildren().add(stp);
            }
        }

        // Get Mexican Train
        Label label1 = new Label("Mexican Train");
        for (int i = 0; i < gui.boneyard.trainMexican.size(); i++) {
            if (i % 2 == 0) {
                StackPane stp = new StackPane();
                Text dominoText = new Text(gui.boneyard.trainMexican.get(i).
                        toString());
                Rectangle rect = new Rectangle(width, height);
                rect.setStroke(Color.WHITE);
                dominoText.setFill(Color.WHITE);
                stp.getChildren().addAll(rect, dominoText);
                mexicanTrain.getChildren().add(stp);
            }
        }

        Label labelMexicanTrain = new Label("      ");
        mexicanTrainBottom.getChildren().add(labelMexicanTrain);
        for (int i = 0; i < gui.boneyard.trainMexican.size(); i++) {
            if (i % 2 == 1) {
                StackPane stp = new StackPane();
                Text dominoText = new Text(gui.boneyard.trainMexican.get(i).
                        toString());
                Rectangle rect = new Rectangle(width, height);
                rect.setStroke(Color.WHITE);
                dominoText.setFill(Color.WHITE);
                stp.getChildren().addAll(rect, dominoText);
                mexicanTrainBottom.getChildren().add(stp);
            }
        }

        // Get Boneyard
        Label label2 = new Label("Boneyard");
        for (int i = 0; i < gui.boneyard.dominoSet.size(); i++) {
            if (i % 2 == 0) {
                StackPane stp = new StackPane();
                Text dominoText = new Text(gui.boneyard.dominoSet.get(i).
                        toString());
                Rectangle rect = new Rectangle(width, height);
                rect.setStroke(Color.WHITE);
                dominoText.setFill(Color.WHITE);
                stp.getChildren().addAll(rect, dominoText);
                boneyardTop.getChildren().add(stp);
            }
        }

        Label label3 = new Label("      ");
        boneyardBottom.getChildren().add(label3);
        for (int i = 0; i < gui.boneyard.dominoSet.size(); i++) {
            if (i % 2 == 1) {
                StackPane stp = new StackPane();
                Text dominoText = new Text(gui.boneyard.dominoSet.get(i).
                        toString());
                Rectangle rect = new Rectangle(width, height);
                rect.setStroke(Color.WHITE);
                dominoText.setFill(Color.WHITE);
                stp.getChildren().addAll(rect, dominoText);
                boneyardBottom.getChildren().add(stp);
            }
        }
    }

    /**
     * The round method contains the computer logic that allows the computer
     * to play against other computers uninterrupted.
     */
    public void round() {
        while(gui.roundOver == false) {

            while (gui.playerHasHand == true) {
                gameState();
                // Check if computer
                if (gui.playerArray.
                        get(gui.currentPlayerIndex) instanceof Computer) {
                    // Run comp AI logic
                    computer();
                }

                // Check current player if they have a hand
                if ((gui.playerArray.get(gui.currentPlayerIndex).
                        hand.isEmpty()) == true) {
                    gui.playerHasHand = false;
                }

                gui.gameOverCounter = 0;

                // Check all players to see if they are out
                for (int i = 0; i < gui.playerArray.size(); i++) {
                    if (gui.playerArray.get(i).getPlayerOut() == true) {
                        gui.gameOverCounter++;
                    }
                }

                if (gui.gameOverCounter == gui.playerArray.size()) {
                    gui.playerHasHand = false;
                }

                // Move to next player and back to first if everyone has gone
                // Also check if the last domino played was a double
                if (gui.currentPlayer < totalPlayers && (gui.playerArray.
                        get(gui.currentPlayerIndex).getPlayedDouble() == false)) {
                    gui.currentPlayer++;
                    gui.currentPlayerIndex++;
                    if(gui.doubleOpen()) {
                        gui.closeTrains = true;
                    } else {
                        gui.closeTrains = false;
                    }
                } else if (gui.currentPlayer >= totalPlayers &&
                        (gui.playerArray.get(gui.currentPlayerIndex).
                                getPlayedDouble() == false)) {
                    gui.currentPlayer = 1;
                    gui.currentPlayerIndex = 0;
                    if(gui.doubleOpen()) {
                        gui.closeTrains = true;
                    } else {
                        gui.closeTrains = false;
                    }
                } else if (gui.playerArray.get(gui.currentPlayerIndex).
                        getPlayedDouble() == true) {
                    gui.playerArray.get(gui.currentPlayerIndex).
                            setPlayedDouble(false);
                    gui.currentPlayer = gui.currentPlayer;
                    gui.currentPlayerIndex = gui.currentPlayerIndex;
                }
            }

            //Exit loop
            gui.roundOver = true;

            // Add round score to each player
            for (int i = 0; i < gui.playerArray.size(); i++) {
                gui.playerArray.get(i).roundScores.add(gui.playerArray.get(i).
                        tallyScore());
                for (int j = 0; j < gui.playerArray.get(i).roundScores.size();
                     j++) {

                }
            }

        }

        gui.roundOver = false;
    }

    /**
     * The computer method contains the computer logic if there is at least
     * one human player.
     */
    public void computer() {
        while (gui.compMoves <= 1) {

            ((Computer)gui.playerArray.get(gui.currentPlayerIndex)).
                    clearOrderChain();
            ((Computer)gui.playerArray.get(gui.currentPlayerIndex)).
                    orderChain();
            if (gui.computerMatch()) {
                if (gui.computerMatch()) {
                    // Create combo list
                    // Play domino match
                    // iterate through hand, find and match played
                    // domino, remove from hand

                    if (gui.computerMatchIndex == 10) {
                        if (gui.closeTrains == false) {
                            gui.whichDomino = ((Computer)gui.playerArray.
                                    get(gui.currentPlayerIndex)).
                                    findHandMatch((gui.computerHighestPipIndex));
                            gui.whichDomino++;
                            gui.canPlayCenter();
                            gui.whichDomino--;
                            gui.dominoPlayed = false;
                            gui.needToFlip = false;
                        } else if (gui.closeTrains == true) {
                            if (gui.openDoubleTrains.
                                    get(gui.openTrainIterator)
                                    == gui.computerMatchIndex) {
                                gui.whichDomino =
                                        ((Computer)gui.playerArray.
                                                get(gui.currentPlayerIndex))
                                                .findHandMatch(
                                                        (gui.computerHighestPipIndex));
                                gui.whichDomino++;
                                gui.canPlayCenter();
                                gui.whichDomino--;
                                gui.dominoPlayed = false;
                                gui.needToFlip = false;
                                gui.openDoubleTrains.
                                        remove(gui.openTrainIterator);
                            }
                        }

                    } else if (gui.computerMatchIndex == 11) {
                        if (gui.closeTrains == false) {
                            gui.whichDomino = ((Computer)gui.playerArray.
                                    get(gui.currentPlayerIndex))
                                    .findHandMatch(
                                            (gui.computerHighestPipIndex));
                            gui.whichDomino++;
                            gui.canPlayMexican();
                            gui.whichDomino--;
                            gui.dominoPlayed = false;
                            gui.needToFlip = false;
                        } else if (gui.closeTrains == true) {
                            if (gui.openDoubleTrains.
                                    get(gui.openTrainIterator) == 11) {
                                gui.whichDomino =
                                        ((Computer)gui.playerArray.
                                                get(gui.currentPlayerIndex))
                                                .findHandMatch(
                                                        (gui.computerHighestPipIndex));
                                gui.whichDomino++;
                                gui.canPlayMexican();
                                gui.whichDomino--;
                                gui.dominoPlayed = false;
                                gui.needToFlip = false;
                                gui.openDoubleTrains.remove(
                                        gui.openTrainIterator);
                            }
                        }

                    } else  {
                        if (gui.closeTrains == false) {
                            gui.whichDomino = ((Computer)gui.playerArray.
                                    get(gui.currentPlayerIndex))
                                    .findHandMatch(
                                            (gui.computerHighestPipIndex)
                                    );
                            gui.whichDomino++;
                            gui.canPlay(gui.computerMatchIndex);
                            gui.whichDomino--;
                            gui.dominoPlayed = false;
                            gui.needToFlip = false;
                        } else if (gui.closeTrains == true) {
                            if (gui.openDoubleTrains.get(
                                    gui.openTrainIterator) ==
                                    gui.computerMatchIndex) {
                                gui.whichDomino =
                                        ((Computer)gui.playerArray.get(
                                                gui.currentPlayerIndex)
                                        )
                                                .findHandMatch(
                                                        (gui.computerHighestPipIndex));
                                gui.whichDomino++;
                                gui.canPlay(gui.computerMatchIndex);
                                gui.whichDomino--;
                                gui.dominoPlayed = false;
                                gui.needToFlip = false;
                                gui.openDoubleTrains.
                                        remove(gui.openTrainIterator);
                            }
                        }
                    }

                    if (gui.playerArray.get(gui.currentPlayerIndex).
                            getMarker() == true) {
                        gui.playerArray.get(gui.currentPlayerIndex).
                                setMarker(false);
                    }
                } else {
                    gui.playerArray.get(gui.currentPlayerIndex).
                            setMarker(true);
                }
                gui.compMoves += 2;

            } else {
                if (gui.boneyard.dominoSetEmpty()) {
                    gui.playerArray.get(gui.currentPlayerIndex).
                            setMarker(true);
                    gui.playerArray.get(gui.currentPlayerIndex).
                            setPlayerOut(true);
                } else {
                    gui.playerArray.get(gui.currentPlayerIndex).hand.
                            add(gui.boneyard.dealDomino());

                }
                gui.compMoves++;
            }

        }
        gui.compMoves = 0;
        gameState();
    }

    /**
     * The initialize method overrides initialize and instantiates the GUI
     * object.
     */
    @Override public void initialize(URL location, ResourceBundle resources) {

        gui = new GUI();

    }
}
