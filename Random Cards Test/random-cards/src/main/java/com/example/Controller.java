package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    // Card Image Paths
    private static final String IMAGE_BASE_PATH = "/com/example/images/card/";
    private static final String[] BACK_IMAGE_PATHS = {
        IMAGE_BASE_PATH + "back.png",       // Default
        IMAGE_BASE_PATH + "b1fv.png",       // Blue vertical
        IMAGE_BASE_PATH + "b1fh.png",       // Blue horizontal
        IMAGE_BASE_PATH + "b2fv.png",       // Red vertical
        IMAGE_BASE_PATH + "b2fh.png"        // Red horizontal
    };

   
    @FXML private TextArea debugConsole;
    @FXML private ImageView card1, card2, card3, card4;

    // Game State
    private final List<Integer> currentCards = new ArrayList<>();
    private final boolean[] cardFlipped = new boolean[4];
    private Image cardBackImage;
    private final Image[] cardImages = new Image[54]; // 1-54 including jokers
    private int currentBackStyle = 0;

    @FXML
    public void initialize() {
        setupDebugConsole();
        loadAllCardImages();
        setBackImage(0); // Default back
        handleRefresh();
        addDebugMarkers();
    }

    private void setupDebugConsole() { //responsible for initializing and setting up a debug console in a card game application
        if (debugConsole == null) {
            System.err.println("ERROR: debugConsole not injected!");
            return;
        }
        debugConsole.setText("=== Card Game Initialized ===\n");
        log("Image base path: " + IMAGE_BASE_PATH);
    }

    private void loadAllCardImages() { // loads image files for a card game into an array 
                                        // Loads multiple card images from a specified directory into memory
        log("\nLoading card images...");
        for (int i = 0; i < cardImages.length; i++) {
            String path = IMAGE_BASE_PATH + (i + 1) + ".png";
            try {
                cardImages[i] = loadImage(path);
                log("Loaded: " + path);
            } catch (Exception e) {
                log("ERROR loading " + path + ": " + e.getMessage());
            }
        }
    }

    private Image loadImage(String path) throws Exception {
        String url = Objects.requireNonNull(getClass().getResource(path)).toExternalForm();
        // looks for the resource at the given path relative to the class's location
        Image image = new Image(url);
        // JavaFX's Image class loads the image from the URL asynchronously
        if (image.isError()) {
            throw image.getException();
        } //Checks if the image failed to load 
        
        log("Success: " + path + " (" + image.getWidth() + "x" + image.getHeight() + ")");
        return image;
    }

    private void setBackImage(int backIndex) { //Sets a new card back design based on the provided backIndex. Updates the game state and logs the operation
        try {
            currentBackStyle = backIndex; //Stores the selected back style index in currentBackStyle
            cardBackImage = loadImage(BACK_IMAGE_PATHS[backIndex]);
            resetCards();
            log("Set back image: " + BACK_IMAGE_PATHS[backIndex]);
        } catch (Exception e) {
            log("ERROR setting back image: " + e.getMessage());
        }
    }

    @FXML
    private void handleRefresh() {
        log("\nDealing new cards..."); // Outputs a message to indicate the start of a new card deal.
        currentCards.clear(); // This empties the list for fresh dealing
        Random rand = new Random(); // Prepares to generate random card numbers.
        
        while (currentCards.size() < 4) { //Loop: Runs until 4 unique cards are added
            int card = rand.nextInt(54) + 1;
            if (!currentCards.contains(card)) {
                currentCards.add(card);
                log("Dealt: Card " + card + getCardType(card));
            }
        }
        
        resetCards();
    }

    private String getCardType(int cardNumber) {
        if (cardNumber >= 53) return " (Joker)"; // Cards 53 and 54 are treated as Jokers
        String[] ranks = {"", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        return " (" + ranks[cardNumber % 13] + ")";
    }

    private void resetCards() {
        ImageView[] cards = {card1, card2, card3, card4};
        for (int i = 0; i < cards.length; i++) {
            cards[i].setImage(cardBackImage);
            cardFlipped[i] = false;
        }
    }

    @FXML
    private void handleCardClick(MouseEvent event) {
        ImageView clickedCard = (ImageView) event.getSource(); // event.getSource() gets the specific ImageView (card) that was clicked
        int cardIndex = getCardIndex(clickedCard);
        
        if (cardIndex == -1 || cardFlipped[cardIndex]) return;
        
        try { // Flips the card. Retrieves the corresponding face image from cardImages array
            int cardNumber = currentCards.get(cardIndex);
            Image cardImage = cardImages[cardNumber - 1];
            
            clickedCard.setImage(cardImage);
            cardFlipped[cardIndex] = true;
            log("Flipped card " + (cardIndex+1) + ": " + cardNumber + getCardType(cardNumber));
        } catch (Exception e) { //Catches and logs any errors
            log("ERROR flipping card: " + e.getMessage());
        }
    }

    // Back style selection methods
    /*
     * These are UI controller methods that handle card back style selection from menu buttons.
     * Each corresponds to a different card back design.
     */
    @FXML private void setDefaultBack() { setBackImage(0); }
    @FXML private void setBlueVerticalBack() { setBackImage(1); }
    @FXML private void setBlueHorizontalBack() { setBackImage(2); }
    @FXML private void setRedVerticalBack() { setBackImage(3); }
    @FXML private void setRedHorizontalBack() { setBackImage(4); }

    // Helper methods
    // Maps physical card ImageViews to their logical positions
    private int getCardIndex(ImageView card) {
        if (card == card1) return 0;
        if (card == card2) return 1;
        if (card == card3) return 2;
        if (card == card4) return 3;
        return -1;
    }

    //Dual-output logging system for debugging
    private void log(String message) {
        System.out.println(message);
        if (debugConsole != null) {
            debugConsole.appendText(message + "\n");
        }
    }

    private void addDebugMarkers() {
        // Visual debug borders
        //Visual and textual debugging aid during development
        String[] colors = {"red", "blue", "green", "purple"};
        ImageView[] cards = {card1, card2, card3, card4};
        
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                cards[i].setStyle("-fx-border-color: " + colors[i] + "; -fx-border-width: 2;");
                log("Card " + (i+1) + " dimensions: " + 
                    cards[i].getFitWidth() + "x" + cards[i].getFitHeight());
            }
        }
    }
}