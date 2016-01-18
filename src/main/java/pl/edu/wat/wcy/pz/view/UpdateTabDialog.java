package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Tab;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UpdateTabDialog extends JDialog{
    private MainWindow mainWindow;

    private JTextField titleTextField;
    private JTextField pathTextField;
    private JTextField urlTextField;
    private JPanel buttonsPanel;

    private JButton okButton;
    private JButton cancelButton;

    private Tab tabToUpdate;
    private ChoseFileButtonFile choseFileButtonFile;

    public UpdateTabDialog(MainWindow mainWindow, Tab tabToUpdate) {
        super(mainWindow, "Update Tab", true);
        this.mainWindow = mainWindow;
        this.tabToUpdate = tabToUpdate;
        setMyValues();
        setMyLayout();

        titleTextField = new JTextField(40);
        titleTextField.setText(tabToUpdate.getTitle());
        pathTextField = new JTextField(40);
        pathTextField.setText(tabToUpdate.getPath());
        choseFileButtonFile = new ChoseFileButtonFile(pathTextField);
        urlTextField = new JTextField(40);
        urlTextField.setText(tabToUpdate.getUrl());

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
                    tabToUpdate.setTitle(titleTextField.getText());
                    tabToUpdate.setPath(pathTextField.getText());
                    tabToUpdate.setUrl(urlTextField.getText());
                    tabToUpdate = mainWindow.tabDao.update(tabToUpdate);

                    mainWindow.tabsPanel.revalidateMeWithActualCover();
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
