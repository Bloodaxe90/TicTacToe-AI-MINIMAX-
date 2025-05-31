module com.example.tictactoeaiminimax {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tictactoeaiminimax to javafx.fxml;
    exports com.example.tictactoeaiminimax;
}