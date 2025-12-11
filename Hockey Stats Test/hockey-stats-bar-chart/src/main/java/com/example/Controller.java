package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML private AnchorPane chartContainer;
    
    private BarChart<Number, String> barChart;  // generic types for horizontal chart
    private NumberAxis xAxis;                   // NumberAxis for goals 
    private CategoryAxis yAxis;                 // CategoryAxis for teams 
    
    @FXML
    public void initialize() {
        // Read data from file 
        List<TeamData> teamDataList = readTeamData();
        
        // Sort by goals (descending)
        teamDataList.sort(Comparator.comparingInt(TeamData::getGoals).reversed());
        
        // Create axes 
        xAxis = new NumberAxis();              // X-axis shows goals (numbers)
        yAxis = new CategoryAxis();            // Y-axis shows teams (categories)
        xAxis.setLabel("Goals Scored");        // Moved label to X-axis
        
        // Create the HORIZONTAL bar chart 
        barChart = new BarChart<>(xAxis, yAxis);  // BarChart<Number
        barChart.setTitle("NHL Team Goals 2018-19 Season");
        barChart.setLegendVisible(false);
        
        //  Adjust bar spacing
        barChart.setCategoryGap(20);  // Space between bars
        barChart.setBarGap(5);        // Space between groups
        
        // Make chart fill its container 
        AnchorPane.setTopAnchor(barChart, 0.0);
        AnchorPane.setBottomAnchor(barChart, 0.0);
        AnchorPane.setLeftAnchor(barChart, 0.0);
        AnchorPane.setRightAnchor(barChart, 0.0);
        
        // Add data to chart 
        XYChart.Series<Number, String> series = new XYChart.Series<>();  // Note type change
        
        for (TeamData team : teamDataList) {
            // Data order: (xValue=goals, yValue=teamName)
            series.getData().add(new XYChart.Data<>(team.getGoals(), team.getTeamName()));
        }
        
        barChart.getData().add(series);
        
        // Add chart to container 
        chartContainer.getChildren().add(barChart);
    }
    
   
    private List<TeamData> readTeamData() { // Creates an empty ArrayList to store team data
        List<TeamData> teamDataList = new ArrayList<>();
        
        try (InputStream is = getClass().getResourceAsStream("hockey_info.txt"); // Reads from a file named "hockey_info.txt"
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            
            String line;
            while ((line = reader.readLine()) != null) { //Each line is split by comma
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String teamName = parts[0].trim();
                    int goals = Integer.parseInt(parts[1].trim());
                    teamDataList.add(new TeamData(teamName, goals));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        
        return teamDataList;
    }
    
 
    private static class TeamData { //This is a simple immutable data holder
        private final String teamName;
        private final int goals;
        
        public TeamData(String teamName, int goals) {
            this.teamName = teamName;
            this.goals = goals;
        }
        
        public String getTeamName() {
            return teamName;
        }
        
        public int getGoals() {
            return goals;
        }
    }
}