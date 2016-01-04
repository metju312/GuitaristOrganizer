package pl.edu.wat.wcy;

import pl.edu.wat.wcy.view.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow.getInstance().setVisible(true);
            }
        });
    }
}
