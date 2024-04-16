module org.example.snakeandladder {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.snakeandladder to javafx.fxml;
    exports org.example.snakeandladder;
}