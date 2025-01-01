package com.example.library_management_system.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty dept;
    private final SimpleStringProperty intake;
    private final SimpleStringProperty sec;
    private final SimpleStringProperty email;

    public Student(int id, String name, String dept, String intake, String sec, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.dept = new SimpleStringProperty(dept);
        this.intake = new SimpleStringProperty(intake);
        this.sec = new SimpleStringProperty(sec);
        this.email = new SimpleStringProperty(email);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getDept() {
        return dept.get();
    }

    public String getIntake() {
        return intake.get();
    }

    public String getSec() {
        return sec.get();
    }

    public String getEmail() {
        return email.get();
    }
}
