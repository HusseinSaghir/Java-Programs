package com.example;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Pig extends Application {


    // Scenes and dice arrays
    private ImageView diceImageView;
    private Image[] diceImages = new Image[7]; 
    private Stage primaryStage;
    private Scene mainMenu;
    private Scene gameMenu;
    private Scene historyMenu;


    //Game Stats Variables
    private int playerScore = 0;
    private int computerScore = 0;
    private int roundScore = 0;
    private boolean playerTurn = true; 
    private String playerName = "Player";


    // UI Labels
    private Label playerScoreLabel;
    private Label computerScoreLabel;
    private Label turnStatusLabel;
    private Label gameStatusLabel;
    private Label roundScoreLabel; 



    //Game history list and formatter
    private List<GameRecords> gameHistory = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
    private TableView<GameRecords> historyTable;


    public static void main(String[] args) {
        launch(args);
        
    }


//-------------------------------------------------------------------------------------- UI Elements / Game Save

    public void start(Stage stage) throws Exception {

        this.primaryStage = stage;
        primaryStage.setOnCloseRequest(e -> System.exit(0)); // <-- Closes the game when tabbed out. process would run 
        // In the background due to threads that keep running. It doesn't actually terminate the JVM


        loadSaveFile();

        //Make Dice images
        makeDiceImages();

        //Main Menu Scene
        createMainMenu();

        //Create game Scene                 <-- Creating Scenes
        createGameMenu();

        //Create History Scene
        createGameHistory();

        resetGame();

        


        primaryStage.setOnCloseRequest(e -> {
        saveGameFile(); // Save when closing
        Platform.exit();
        System.exit(0); //<-- this and above are required to close the game fully
    });


        primaryStage.setScene(mainMenu);
        primaryStage.setTitle("Game Of Pig");
        primaryStage.show();


    }


    //---------------------------------------------------------------------------- Initializiation
    
   public void makeDiceImages() throws Exception {

    
        //Dice path finding and array to see images

        for (int i = 1; i <= 6; i++ ) {
            String path = "/dice/" + (i) + ".png"; //loads png's
            
            try {

            diceImages[i] = new Image(getClass().getResourceAsStream(path));

            } catch (Exception e) { 
                System.err.println("Can't find images" + path);
            }
        }
    }

    private void loadSaveFile() {
        
        gameHistory = new ArrayList<>();

        try (java.io.BufferedReader reader = new java.io.BufferedReader(
            new java.io.FileReader("pig_history.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                gameHistory.add(new GameRecords(
                    parts[0], // playerName
                    parts[1], // results
                    parts[2], // dateAndTime
                    Integer.parseInt(parts[3]) // score
                ));
            }
        }
    } catch (Exception e) {
        // File doesn't exist yet - that's okay
    }

       }

      //---------------------------------------------------------------------------- Scene Building  
        
        private void createMainMenu () {
            
            Label menuTitle = new Label ("Welcome to my game!");

            Button startNewGameButton = new Button("Start New Game");
            startNewGameButton.setOnAction(e -> { resetGame(); primaryStage.setScene(gameMenu); });

            Button historyButton = new Button("See past games");
            historyButton.setOnAction(e -> primaryStage.setScene(historyMenu));

            //Input player nameInfo
            TextInputDialog nameInfo = new TextInputDialog("Player");
            nameInfo.setTitle("Player Name");
            nameInfo.setHeaderText("Enter your name:");
            nameInfo.setContentText("Name:");

            // Asks for name before game starts
            nameInfo.showAndWait().ifPresent(name -> {             
            this.playerName = name;
         });


            // Menu Box

            VBox menuWindow = new VBox(20, menuTitle, startNewGameButton, historyButton);
           
            menuWindow.setAlignment(Pos.CENTER);
            menuWindow.setPadding(new Insets(20));
            mainMenu = new Scene(menuWindow, 1000, 1000);

            

        }


        private void createGameMenu() {

        // Dice image stuff
        diceImageView = new ImageView(diceImages[0]); //<-- Starts dice on 1
        diceImageView.setFitHeight(100);
        diceImageView.setFitWidth(100);


        //Score Display
        
        playerScoreLabel = new Label(playerName + " Score: 0");                 // <-- Labels
        computerScoreLabel = new Label("Computer Score: 0");
        turnStatusLabel = new Label (playerName + "'s turn. Time to Roll!");
        gameStatusLabel = new Label ("Game started! " + playerName +"'s turn.");
        roundScoreLabel = new Label("Current round score: 0");

       
        //Button logic

        Button rollButton = new Button("Roll");
        Button holdButton = new Button("Hold");
        Button backButton = new Button("Back to Menu");

         rollButton.setOnAction(e -> diceRoll());
         backButton.setOnAction(e -> primaryStage.setScene(mainMenu));
         holdButton.setOnAction(e ->  { if (playerTurn) {
            hold();
          }
         } );
        

         // Score Board Settings 
        
        VBox scoreBoard = new VBox(10, gameStatusLabel, playerScoreLabel, computerScoreLabel, turnStatusLabel, roundScoreLabel);
         scoreBoard.setAlignment(Pos.CENTER);


        //Window settings

        VBox gameWindow = new VBox(20, diceImageView, rollButton, holdButton, backButton, scoreBoard);
        gameWindow.setAlignment(Pos.CENTER);
        gameWindow.setPadding(new Insets(20));

        gameMenu = new Scene(gameWindow, 1000, 1000);   
        
    }


    private void createGameHistory() {
        

        //Creating columns

        TableColumn<GameRecords, String> playerColumn = new TableColumn<>("Player");
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<GameRecords, String> resultsColumn = new TableColumn<>("Results");
        resultsColumn.setCellValueFactory(new PropertyValueFactory<>("results"));

        TableColumn<GameRecords, String> dateColumn = new TableColumn<>("Date & Time");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));

        TableColumn<GameRecords, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));





        //Table
        historyTable = new TableView<>();
        historyTable.getColumns().addAll(playerColumn, resultsColumn, dateColumn, scoreColumn);

       
        historyTable.setItems(FXCollections.observableArrayList(gameHistory)); //<-- Loads collection
        
    

        //Back Button for history window
        Button backButton = new Button("Back To Main Menu");
        backButton.setOnAction(e -> primaryStage.setScene(mainMenu));

        //History window settings
        VBox historyWindow = new VBox(20, new Label("Game History"), backButton, historyTable);
        historyWindow.setAlignment(Pos.CENTER);
        historyWindow.setPadding(new Insets(20));
        historyMenu = new Scene(historyWindow, 1000, 1000);

    }



    //------------------------------------------------------------------------- Player Actions

    //How the hold button functions
     public void hold() {

            playerScore += roundScore;
            roundScore = 0;
            roundScoreLabel.setText("Current round score: " + roundScore);
            playerScoreLabel.setText(playerName + " Score: " + playerScore);
            turnStatusLabel.setText(playerName+" held. Computer's turn.");
            

            // Check if win when held

             if (playerScore >= 100) {
             endGame(true);
                } else {
                 playerTurn = false;
                 startComputerTurn();
         }

        }


        // Dice roll logic

        public void diceRoll() {

            // Dice roll math
            int roll = (int) (Math.random() * 6) + 1;
            diceImageView.setImage(diceImages[roll]);


            //Losing player turn and round score if rolled a 1

            if (roll == 1) {
                    roundScore = 0;
                    roundScoreLabel.setText("Current round score: " + roundScore);
                    playerScoreLabel.setText(playerName + " Score: " + playerScore);
                    turnStatusLabel.setText(playerName +" rolled a 1! Lost this round's points. Computer's turn.");
                    playerTurn = false;
                    startComputerTurn();
    
            } else { // Add to player score

                roundScore += roll; 
                
                roundScoreLabel.setText("Current round score: " + roundScore);
                playerScoreLabel.setText(playerName + " Score: " + (playerScore + roundScore));
                turnStatusLabel.setText(playerName+" rolled a "+roll+". Roll again or Hold?");


                // Check win
                if (playerScore + roundScore >= 100) {
                    playerScore += roundScore;
                    endGame(true);
                }

            }
            

        }

    
// ------------------------------------------------------------------------------- Computer AI


        private void startComputerTurn() { //This method gives the computer a delay before it takes its turn

            new java.util.Timer().schedule(new java.util.TimerTask() {

                @Override
                public void run() {
                    javafx.application.Platform.runLater(() -> computersTurn());              //<-- AI generated
                }
            
            }, 1500

          );
        }


        public void computersTurn () {

            //Computers turn 

             int roll = (int) (Math.random() * 6) + 1;
            diceImageView.setImage(diceImages[roll]);

            if (roll == 1) {
                    roundScore = 0;
                    computerScoreLabel.setText("Computer Score: " + computerScore);
                    turnStatusLabel.setText("Computer rolled a 1! Lost this round's points. Your turn.");
                    gameStatusLabel.setText(playerName + "'s turn"); 
                    playerTurn = true;
    
            } else { // computer holds

                roundScore += roll; 
                computerScoreLabel.setText("Computer Score: " + (computerScore + roundScore));
                turnStatusLabel.setText("Computer rolled a "+roll);
                gameStatusLabel.setText(playerName + "'s turn");


                // Check win
                 if (computerScore + roundScore >= 100) {
                computerScore += roundScore;
                endGame(false);
                return;
            }

                //Computer logic

             if (roundScore >= 15 || (computerScore + roundScore) >= 100) {// ADD ROUND SCORE TO COMPUTER'S TOTAL BEFORE RESETTING
            computerScore += roundScore;
            roundScore = 0;
            computerScoreLabel.setText("Computer Score: " + computerScore);
            turnStatusLabel.setText("Computer held. Your turn.");
            playerTurn = true;

            } else {
                gameStatusLabel.setText("Computer rolled a " + roll + " and continues..."); //<-- Edit these
                startComputerTurn();
            }

          }

        }

// ------------------------------------------------------------------------------------- Game functions

        private void endGame(boolean playerWon) {

            int finalPlayerScore = playerScore;
            int finalComputerScore = computerScore;


            if (playerWon) {
                gameStatusLabel.setText(playerName+" wins with "+finalPlayerScore+ " points!");
                                                                                                
            } else {
                gameStatusLabel.setText("Computer wins with " + finalComputerScore + " points!");
            }  

            recordGameResults(playerWon, finalPlayerScore, finalComputerScore);
            
             // Add a delay before resetting so players can see the results
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> resetGame()); //<-- AI generated
            }
        }, 
        3000 ); // 3 second delay
            
        }

             private void resetGame() {
            playerScore = 0;
            computerScore = 0;
            roundScore = 0;
            playerTurn = true;

            playerScoreLabel.setText(playerName + " Score: 0");
            computerScoreLabel.setText("Computer Score: 0");             
            roundScoreLabel.setText("Current round score: " + roundScore);
            gameStatusLabel.setText("Your turn - Roll the dice!"); 
            diceImageView.setImage(diceImages[0]);
        }


// --------------------------------------------------------------------------------------- History Management

        private void recordGameResults(boolean playerWon, int finalPlayerScore, int finalComputerScore) {
    //Determine if player won or lost
    String result;
    if(playerWon) {
        result = "Won";
    } else {
        result = "Lost";
    }
    
    //Get the final score
    int finalScore;
    if(playerWon) {
        finalScore = finalPlayerScore;
    } else {
        finalScore = finalComputerScore;
    }
    
    //Create a timestamp
    String timestamp = LocalDateTime.now().format(formatter);
    
    //Create a new record and add to history
    GameRecords newRecord = new GameRecords(playerName, result, timestamp, finalScore);
    gameHistory.add(newRecord);
    
    //Save to file
    saveGameFile();

    //History table update
    historyTable.refresh(); 
    
    System.out.println("Game saved: " + playerName + " " + result + " with score " + finalScore);
}

       //Creating Save files for history table using IO Streams

       private void saveGameFile() {
    try (java.io.PrintWriter writer = new java.io.PrintWriter("pig_history.txt")) {
        for (GameRecords record : gameHistory) {
            writer.println(record.toTextString()); 
        }
        } catch (IOException e) {
        System.out.println("Couldn't save history: " + e.getMessage());
    }
}

//-------------------------------------------------------------------------------- Inner Classes

public static class GameRecords implements java.io.Serializable {
            
            public String toTextString() {
            return playerName + "," + results + "," + dateAndTime + "," + score;
            }

        private String playerName;
        private String results;
        private String dateAndTime;
        private int score;

   


    public GameRecords(String playerName, String results, String dateAndTime, int score) {

        this.playerName = playerName;
        this.results = results;
        this.dateAndTime = dateAndTime;
        this.score = score;
    }

                                                        // <--- Game data info used for history and so forth


    public String getPlayerName() {
        return playerName;
    }

    public String getResults() {
        return results;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public int getScore() {
        return score;
    }

    
}
       
        
}



