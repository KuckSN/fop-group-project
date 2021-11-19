package com.fop.foptproject;

import com.fop.EmailUtil.emailTo;
import com.fop.sqlUtil.sqlConnect;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Movie Ticketing System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
//        System.out.println(new emailTo("helloworldisagurl@gmail.com").sendEmailVerification(true));
//        sqlConnect jc = new sqlConnect();
        //jc.addTestData();
//        jc.createTestQuery();
        System.out.println("hi");
        launch();
    }

}