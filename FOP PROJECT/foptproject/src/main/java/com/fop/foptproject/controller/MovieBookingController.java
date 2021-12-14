/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.fop.foptproject.controller;

import com.fop.Utility.JSONToolSets;
import com.fop.Utility.sqlConnect;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author shiao
 */
public class MovieBookingController implements Initializable {
    
    @FXML 
    private ComboBox selectCinema;
    @FXML 
    private ComboBox selectTime;
    @FXML 
    private DatePicker selectDate;
    @FXML 
    private RadioButton selectClassic;
    @FXML 
    private RadioButton selectPremium;
    @FXML
    private Button homeButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button backButton;
    @FXML
    private Label cinemaFieldWarningText;
    @FXML
    private Label theaterTypeWarningText;
    @FXML
    private Label dateWarningText;
    @FXML
    private Label showTimeWarningText;
    
    private sqlConnect sql = new sqlConnect();
    private String chosenDay;
    private String chosenDate;
    private String chosenCinema;
    private String chosenTime;
    private String chosenType;
    
    @FXML
    public void changeToMoviesDetails(ActionEvent event) throws IOException{
        SceneController switchScene = new SceneController();
        switchScene.switchToMoviesDetails(event); 
    }
    
    @FXML
    public void changeToSeats(ActionEvent event) throws IOException{
        SceneController switchScene = new SceneController();
        switchScene.switchToSeats(event);
    }
    
    @FXML
    public void validateCinemaField(){
        try{
            getCinema();
            if(this.chosenCinema.isEmpty()){
                cinemaFieldWarningText.setVisible(true);
                nextButton.setDisable(true);
            }
            else{
                cinemaFieldWarningText.setVisible(false);
                nextButton.setDisable(false);        
            }
        }
        catch(Exception e){
            // do nothing
        }
        System.out.println(validateAll());
        nextButton.setDisable(validateAll());
    }

    @FXML
    public void validateDateField(){
        try{
            getDate();
            System.out.println(this.chosenDate);
            if(this.chosenDate.isEmpty()){
                dateWarningText.setVisible(true);
                nextButton.setDisable(true);
            }
            else{
                dateWarningText.setVisible(false);
                nextButton.setDisable(false);        
            }
        }
        catch(Exception e){
            // do nothing
        }
        System.out.println(validateAll());
        nextButton.setDisable(validateAll());
    }

    @FXML
    public void validateShowtimeField(){
        getTime();
        if(this.chosenTime.isEmpty()){
            showTimeWarningText.setVisible(true);
            nextButton.setDisable(true);    
        }
        else{
            showTimeWarningText.setVisible(false);
            nextButton.setDisable(false);
        }
        System.out.println(validateAll());
        nextButton.setDisable(validateAll());
    }
    
    @FXML
    public void next(ActionEvent event) throws IOException{
        if(!validateAll()){
            getTheaterType();
            RealTimeStorage.updateMovieBookingByKey("movieId",RealTimeStorage.getLookingAt());
            RealTimeStorage.updateMovieBookingByKey("movieName",RealTimeStorage.getMovieDetail("movieName").get(RealTimeStorage.getMovieDetail("movieId").indexOf(RealTimeStorage.getLookingAt())));
            RealTimeStorage.updateMovieBookingByKey("cinemaName",this.chosenCinema);
            RealTimeStorage.updateMovieBookingByKey("showdate",this.chosenDate);
            RealTimeStorage.updateMovieBookingByKey("theaterType",this.chosenType);
            RealTimeStorage.updateMovieBookingByKey("showTime",this.chosenTime);
            new SceneController().switchToSeats(event);
        }
    }
    
    public boolean validateAll(){
        return (this.chosenCinema==null||this.chosenDate==null||this.chosenTime==null);
    }
    
    public void getTheaterType(){
        this.chosenType = (selectClassic.isPressed())?"Classic":"Premium";
    }
    
    public void getCinema(){
        this.chosenCinema = selectCinema.getValue().toString();
    }
    
    public void getDate(){
        this.chosenDate = selectDate.getValue().toString();
        LocalDate date = selectDate.getValue();
        Calendar c = Calendar.getInstance();
        // localdate -> date only
        // date -> localdate+atStartOfDay()+default time zone+toInstant()
        c.setTime(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        int day = (c.get(Calendar.DAY_OF_WEEK)==1)?6:c.get(Calendar.DAY_OF_WEEK)-2;
        String Day = Integer.toString(day);     
        this.chosenDay = Day;

        int index = RealTimeStorage.getMovieDetail("movieId").indexOf(RealTimeStorage.getLookingAt());
        String theaterId = RealTimeStorage.getMovieDetail("theaterId").get(index);

        HashMap<String,ArrayList<String>> result;
        int total;
        int unoccupied=0;
        for(int i = 1 ; i <= 3 ; i++){
            JSONToolSets json = new JSONToolSets(sql.querySeats(theaterId,Integer.toString(i),false),false);
            result = json.parseTheaterSeat(day);
            total = result.size()*result.get("0").size();
            for(int m = 0 ; m < result.size() ; m++){
                for(int n = 0 ; n < result.get("0").size() ; n++){
                    if(result.get(Integer.toString(m)).get(n).equals("0")){
                        unoccupied++;
                    }
                    if(result.get(Integer.toString(m)).get(n).equals("-1")){
                        total--;
                    }
                }
            }
            if(unoccupied > 0){
                selectTime.getItems().add(String.format("%s - %d/%d",RealTimeStorage.getSlot(i),unoccupied,total));
            }
            unoccupied = 0;
            total = 0;
        }
        
        if(selectTime.getItems().isEmpty()){
            selectTime.setPromptText("The seats for today are all booked!");
        }
        else{
            selectTime.setDisable(false);
        }
    }
    
    public void getTime(){
        this.chosenTime = selectTime.getValue().toString();
    }
    
    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = (final DatePicker datePicker1) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(minDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffee00; -fx-opacity : 0.5;");
                }else if (item.isAfter(maxDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffee00; -fx-opacity : 0.5;");
                }
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // init
        nextButton.setDisable(true);
        selectClassic.setSelected(true);
        
        // populate cinema choice
        selectCinema.getItems().add("KL - MidValley");
        // limit date range
        restrictDatePicker(selectDate,LocalDate.now(),LocalDate.now().plusDays(7));
        
        // load previous data if exist
        if(RealTimeStorage.getMovieBooking().containsKey("movieId")){
            selectDate.setValue(LocalDate.parse(RealTimeStorage.getMovieBooking().get("showdate").toString()));
            selectTime.setValue(RealTimeStorage.getMovieBooking().get("showTime").toString());
            selectCinema.setValue(RealTimeStorage.getMovieBooking().get("cinemaName").toString());
            if(RealTimeStorage.getMovieBooking().get("theaterType").toString().equals("Classic")){
                selectClassic.setSelected(true);
            }
            else{
                selectPremium.setSelected(true);
            }
            selectTime.setDisable(false);
        }
        else{
            selectTime.setDisable(true);
        }
        
    }    
    
}
