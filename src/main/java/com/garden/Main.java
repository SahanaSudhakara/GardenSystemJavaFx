package com.garden;


import com.garden.View.UserInterface;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        launch(UserInterface.class, args);
    }

    private static void launch(Class<UserInterface> userInterfaceClass, String[] args) {
        Application.launch(userInterfaceClass, args);
    }
}


