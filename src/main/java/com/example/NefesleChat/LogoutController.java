package com.example.NefesleChat;

import javafx.scene.input.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

public class LogoutController {
    private MainView view;

    public LogoutController(MainView view) {
        this.view = view;
    }

    public void logout(MouseEvent event) {
        URL resourceURL = getClass().getResource("/jwtToken");
        File jwtTokenFile = new File(resourceURL.getFile());
        try {
            FileWriter fileWriter = new FileWriter(jwtTokenFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        HttpUtil.logOut();
        view.getPrimaryStage().close();
        LoginRegistrationView loginRegistrationView = new LoginRegistrationView();
        loginRegistrationView.show();
    }
}
