package pl.edu.wat.wcy.pz;

import pl.edu.wat.wcy.pz.view.LoginWindow;
import pl.edu.wat.wcy.pz.view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginWindow.getInstance().setVisible(true);
                //MainWindow.getInstance().setVisible(true);
            }
        });
    }
}
