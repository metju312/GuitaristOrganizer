package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UpdateArtistDialog extends JDialog{
    private MainWindow mainWindow;

    private JTextField nameTextField;
    private JPanel buttonsPanel;

    private JButton okButton;
    private JButton cancelButton;

    private Artist artistToUpdate;

    public UpdateArtistDialog(MainWindow mainWindow, Artist artistToUpdate) {
        super(mainWindow, "Update Artist", true);
        this.mainWindow = mainWindow;
        this.artistToUpdate = artistToUpdate;
        setMyValues();
        setMyLayout();

        nameTextField = new JTextField(40);
        nameTextField.setText(artistToUpdate.getName());

        add(new JPanel(), "wrap");
        add(new JLabel("Name"), "wrap");
        add(nameTextField, "wrap");
        add(new JPanel(), "wrap");

        generateButtonsPanel();
        add(buttonsPanel);


        setVisible(true);
    }

    private void generateButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new MigLayout());

        okButton = new JButton(new ImageIcon("src/images/plusIcon.png"));
        okButton.setText("Update");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(nameTextField.getText(), "")) {
                    JOptionPane.showMessageDialog(nameTextField, "Set name.");
                } else {
                    artistToUpdate.setName(nameTextField.getText());
                    artistToUpdate.setUser(mainWindow.actualUser);
                    artistToUpdate = mainWindow.artistDao.update(artistToUpdate);

                    mainWindow.artistsPanel.revalidateAllPanels();
                    dispose();
                }
            }
        });
        buttonsPanel.add(okButton, "gapleft 140");

        cancelButton = new JButton(new ImageIcon("src/images/cancelIcon.png"));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonsPanel.add(cancelButton, "gapleft 4");
    }

    private void setMyLayout() {
        setLayout(new MigLayout());
    }

    private void setMyValues() {
        setSize(350,160);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
