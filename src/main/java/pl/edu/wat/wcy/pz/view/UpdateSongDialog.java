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

public class UpdateSongDialog extends JDialog{
    private MainWindow mainWindow;

    private JTextField titleTextField;
    private JTextField pathTextField;
    private JTextField urlTextField;
    private JPanel buttonsPanel;

    private JButton okButton;
    private JButton cancelButton;

    private Song songToUpdate;

    private ChoseFileButtonFile choseFileButtonFile;

    public UpdateSongDialog(MainWindow mainWindow, Song songToUpdate) {
        super(mainWindow, "Update Song", true);
        this.mainWindow = mainWindow;
        this.songToUpdate = songToUpdate;
        setMyValues();
        setMyLayout();

        titleTextField = new JTextField(40);
        titleTextField.setText(songToUpdate.getTitle());
        pathTextField = new JTextField(40);
        pathTextField.setText(songToUpdate.getPath());
        choseFileButtonFile = new ChoseFileButtonFile(pathTextField);
        urlTextField = new JTextField(40);
        urlTextField.setText(songToUpdate.getUrl());

        add(new JPanel(), "wrap");
        add(new JLabel("Title"), "wrap");
        add(titleTextField, "wrap");
        add(new JPanel(), "wrap");
        add(new JLabel("Path"), "wrap");
        add(pathTextField);
        add(choseFileButtonFile, "wrap");
        add(new JPanel(), "wrap");
        add(new JLabel("URL"), "wrap");
        add(urlTextField, "wrap");
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
                if (Objects.equals(titleTextField.getText(), "")) {
                    JOptionPane.showMessageDialog(titleTextField, "Set title.");
                } else {
                    songToUpdate.setTitle(titleTextField.getText());
                    songToUpdate.setPath(pathTextField.getText());
                    songToUpdate.setUrl(urlTextField.getText());
                    songToUpdate = mainWindow.songDao.update(songToUpdate);

                    mainWindow.songsPanel.revalidateAllPanels();
                    mainWindow.songsPanel.revalidateMeWithActualArtist();
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
        setSize(400,280);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
