module com.example.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;

    opens com.example.library_management_system to javafx.fxml;
    opens com.example.library_management_system.controllers to javafx.fxml;
    opens com.example.library_management_system.utils to javafx.base; // Add this line

    exports com.example.library_management_system;
}
