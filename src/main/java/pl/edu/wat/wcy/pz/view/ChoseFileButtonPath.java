package pl.edu.wat.wcy.pz.view;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoseFileButtonPath extends JButton{
    private JTextField textField;
    public ChoseFileButtonPath(JTextField textField) {
        super("...");
        this.textField = textField;

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateFileChooserWindow();
            }
        });
    }

    private void generateFileChooserWindow() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chose Dictionary");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String s = fileChooser.getSelectedFile().getAbsolutePath();
            textField.setText(s);
        }
    }
}
