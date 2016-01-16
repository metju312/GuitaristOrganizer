package pl.edu.wat.wcy.pz.view;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel{

    private Image img;

    public CustomPanel(String path) {
        img = new ImageIcon(path).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
