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

public class AddArtistDialog extends JDialog{
    private MainWindow mainWindow;

    private JTextField nameTextField;
    private JPanel buttonsPanel;

    private JButton okButton;
    private JButton cancelButton;

    public AddArtistDialog(MainWindow mainWindow) {
        super(mainWindow, "Add Artist", true);
        this.mainWindow = mainWindow;
        setMyValues();
        setMyLayout();

        nameTextField = new JTextField(40);

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
        okButton.setText("Add");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(nameTextField.getText(), "")){
                    JOptionPane.showMessageDialog(nameTextField, "Set name.");
                }else{
                    Artist artist = new Artist();
                    artist.setName(nameTextField.getText());
                    artist.setUser(mainWindow.actualUser);
                    mainWindow.artistDao.create(artist);

                    mainWindow.artistsPanel.revalidateAllPanels();
                    dispose();
                }
            }
        });
        buttonsPanel.add(okButton, "gapleft 155");

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
