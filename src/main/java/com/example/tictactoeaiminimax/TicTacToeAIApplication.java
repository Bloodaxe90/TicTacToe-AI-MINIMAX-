package com.example.tictactoeaiminimax;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeAIApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeAIApplication.class.getResource("TicTacToeAI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("TicTacToeAI.css").toExternalForm());
            stage.setTitle("Tic-Tac-Toe");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
        stage.requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}