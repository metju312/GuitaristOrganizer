package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UpdateCoverDialog extends JDialog{
    private MainWindow mainWindow;

    private JTextField titleTextField;
    private JTextField pathTextField;
    private JTextField urlTextField;
    private JPanel buttonsPanel;

    private JButton okButton;
    private JButton cancelButton;

    private Cover coverToUpdate;
    private ChoseFileButtonFile choseFileButtonFile;

    public UpdateCoverDialog(MainWindow mainWindow, Cover coverToUpdate) {
        super(mainWindow, "Update Cover", true);
        this.mainWindow = mainWindow;
        this.coverToUpdate = coverToUpdate;
        setMyValues();
        setMyLayout();

        titleTextField = new JTextField(40);
        titleTextField.setText(coverToUpdate.getTitle());
        pathTextField = new JTextField(40);
        pathTextField.setText(coverToUpdate.getPath());
        choseFileButtonFile = new ChoseFileButtonFile(pathTextField);
        urlTextField = new JTextField(40);
        urlTextField.setText(coverToUpdate.getUrl());

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
                    coverToUpdate.setTitle(titleTextField.getText());
                    coverToUpdate.setPath(pathTextField.getText());
                    coverToUpdate.setUrl(urlTextField.getText());
                    coverToUpdate = mainWindow.coverDao.update(coverToUpdate);

                    mainWindow.coversPanel.revalidateMeWithActualSong();
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
