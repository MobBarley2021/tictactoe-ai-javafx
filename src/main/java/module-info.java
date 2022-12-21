module com.example.tictactoewithui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tictactoewithui to javafx.fxml;
    exports com.example.tictactoewithui;
}