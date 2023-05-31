module com.example.national_id_parser {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.national_id_parser to javafx.fxml;
    exports com.example.national_id_parser;
}