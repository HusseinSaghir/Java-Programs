package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ConverterController {
    private final ToggleGroup conversionType = new ToggleGroup(); // ensures only one radio button can be selected at a time
                                                                // This groups all conversion type radio buttons together

    //Annotated with @FXML to link with FXML-defined components. Each represents a different conversion type
    @FXML private RadioButton milesKmRadio;
    @FXML private RadioButton celsiusFahrenheitRadio;
    @FXML private RadioButton poundsKgRadio;
    
    @FXML private TextField unit1Field; //Text fields for user input and displaying results
    @FXML private TextField unit2Field; // The second one
    @FXML private Label unit1Label; //Display the current unit names
    @FXML private Label unit2Label; 

    private ConversionType currentType = ConversionType.MILES_KM;

    @FXML
    public void initialize() {
        // Set up the toggle group
        //Groups the radio buttons together so only one can be selected at a time

        milesKmRadio.setToggleGroup(conversionType);
        celsiusFahrenheitRadio.setToggleGroup(conversionType);
        poundsKgRadio.setToggleGroup(conversionType);
        
        setupTextFields(); //As it says
        updateLabels(); // Updates to new unit
        
        conversionType.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {  // Ensures newVal isn't null
                if (newVal == milesKmRadio) { 
                    currentType = ConversionType.MILES_KM;
                } else if (newVal == celsiusFahrenheitRadio) {
                    currentType = ConversionType.CELSIUS_FAHRENHEIT;
                } else if (newVal == poundsKgRadio) {
                    currentType = ConversionType.POUNDS_KG;
                }
                updateLabels(); //Refreshes the unit names displayed
                clearFields();
            }
        });
    }

    private void setupTextFields() {
        unit1Field.setOnAction(e -> convertUnit1ToUnit2());
        unit2Field.setOnAction(e -> convertUnit2ToUnit1());
    }

    private void updateLabels() { //Updates the text labels to show the current unit names
        unit1Label.setText(currentType.getUnit1() + ":");
        unit2Label.setText(currentType.getUnit2() + ":");
    }

    private void clearFields() { //Resets both input fields to empty state
        unit1Field.clear();
        unit2Field.clear();
    }

    private void convertUnit1ToUnit2() { //Attempts to parse input from unit1Field as a number
        try {
            double value = Double.parseDouble(unit1Field.getText());
            double converted = ConversionService.convert(currentType, true, value);
            unit2Field.setText(String.format("%.2f", converted));
        } catch (NumberFormatException e) {
            unit2Field.setText("Invalid input");
        }
    }

    private void convertUnit2ToUnit1() { //Reverse of previous 
        try {
            double value = Double.parseDouble(unit2Field.getText());
            double converted = ConversionService.convert(currentType, false, value);
            unit1Field.setText(String.format("%.2f", converted));
        } catch (NumberFormatException e) {
            unit1Field.setText("Invalid input");
        }
    }
}