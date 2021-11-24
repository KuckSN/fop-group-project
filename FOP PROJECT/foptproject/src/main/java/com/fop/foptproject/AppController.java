/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fop.foptproject;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class AppController implements Initializable {
    
    @FXML
    private ImageView logo;
    @FXML
    private Button logInBtn;
    @FXML
    private ImageView imgBanner;
    @FXML
    private GridPane landingGrid;
    @FXML
    private StackPane landingStackPane;
    @FXML
    private ScrollPane scrollpane;
    @FXML 
    private HBox landingFooter;
    @FXML 
    private Hyperlink hyperlink;
    //Hyperlink for instagram
    @FXML
    void openNwjun (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.instagram.com/nw_jun/?hl=en"));
    }
    @FXML
    void openWxinlim (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.instagram.com/wxinlim/?hl=en"));
    }
    @FXML
    void openKuck (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.instagram.com/kuck.nien_s/?hl=en"));
    }
    @FXML
    void openXyu27 (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.instagram.com/xyu_27/?hl=en"));
    }
    @FXML
    void openShxao (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.instagram.com/shxao.yxn_/?hl=en"));
    }
    @FXML 
    void openCinemas (ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.google.com/"));
    }
    @FXML
    void scrollToMovies (ActionEvent event){
        scrollpane.setVvalue(0.3);
    }
    @FXML
    void scrollToFB (ActionEvent event){
        scrollpane.setVvalue(0.7);
    }
    @FXML
    private HBox movieList;
    @FXML
    private HBox foodList;
   
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] movies = new String[] {"no-time-to-die.jpg", "venom.jpg","eternals.jpg","anita.jpg","dune.jpg","enemy.jpg"};
        String[] movieTitles = new String[] {"No Time To Die", "Venom: Let There Be Carnage", "Eternals", "Anita", "Dune", "Enemy"};
        StackPane[] movieCards = createCardList(movies,movieTitles, "movies", 140, 210);
        movieList.getChildren().addAll(movieCards);
//        
        String[] foods = new String[] {"food1.jpg","food2.jpg","food4.jpg","food5.jpg"};
        String[] foodNames = new String[]{"Chicken Hotdog Combo 1","Cocktail Sausage Combo 1","Regular Combo 1", "food"};
        StackPane[] foodCards = createCardList(foods, foodNames, "foods", 100, 100);
        foodList.getChildren().addAll(foodCards);
    }

    public StackPane createCard(String name, Image poster) {
        StackPane card = new StackPane();
        Label title = new Label(name);
        ImageView imageView = new ImageView(poster);
        title.setTextFill(Color.color(1, 1, 1));
        card.getChildren().addAll(title, imageView);
        StackPane.setAlignment(title, Pos.BOTTOM_CENTER);

        return card;
    }
    
    public StackPane[] createCardList(String[] filenames, String[] titles, String subfile, int posterW, int posterH){
        assert filenames.length == titles.length;
        StackPane[] cards = new StackPane[filenames.length];
//        final int posterH = 210, posterW = 140;
        final double scale = 1.5;
        
        for(int i = 0; i<filenames.length; i ++){
            String path = getClass().getResource("assets/" + subfile + "/" + filenames[i]).toString();
            // Image(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth)
            Image img = new Image(path, posterW * scale, posterH * scale, false, false);
            StackPane card = createCard(titles[i], img);
            HBox.setHgrow(card, Priority.ALWAYS);
            cards[i] = card;
        }
        
        return cards;
    }
    }
