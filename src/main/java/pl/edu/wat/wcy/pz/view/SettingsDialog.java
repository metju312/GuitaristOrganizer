package pl.edu.wat.wcy.pz.view;

import de.javasoft.plaf.synthetica.*;
import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SettingsDialog extends JDialog {
    private MainWindow mainWindow;

    CustomPanel buttonsPanel;
    JPanel tabbedPanePanel;
    JPanel okAndCancelPanel;
    JTabbedPane basicOptionsTabbedPane;
    JTabbedPane searchOptionsTabbedPane;
    JTabbedPane customizeGuiTabbedPane;

    private JComboBox lafComboBox;
    private JTextField titleTextField;
    private JTextArea titleTextArea;
    private JTextArea artistTextArea;

    private DefaultListModel listModel;
    private JList list;
    private JTabbedPane urlsTabbedPane;

    public SettingsDialog(MainWindow mainWindow) {
        super(mainWindow, "Settings", true);
        this.mainWindow = mainWindow;
        setMyValues();
        setLayout(new BorderLayout());
        generateButtonsPanel();
        generateTabbedPanePanel();
        generateOkAndCancelPanel();

        add(buttonsPanel, BorderLayout.WEST);
        add(tabbedPanePanel, BorderLayout.CENTER);
        add(okAndCancelPanel, BorderLayout.SOUTH);
        setVisible(true);
    }


    private void generateOkAndCancelPanel() {
        okAndCancelPanel = new JPanel();
        okAndCancelPanel.setLayout(new MigLayout());

        JButton okButton = new JButton(new ImageIcon("src/images/okIcon.png"));
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okAndCancelPanel.add(okButton, "gapleft 210");

        JButton cancelButton = new JButton(new ImageIcon("src/images/cancelIcon.png"));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okAndCancelPanel.add(cancelButton, "gapleft 4");
    }

    private void generateTabbedPanePanel() {
        tabbedPanePanel = new JPanel();
        tabbedPanePanel.setLayout(new BorderLayout());

        generateBasicOptionsTabbedPane();
        generateSearchOptionsTabbedPane();
        generateCustomizeGuiTabbedPane();

        tabbedPanePanel.add(basicOptionsTabbedPane);
    }

    private void generateCustomizeGuiTabbedPane() {
        customizeGuiTabbedPane = new JTabbedPane();
        JPanel lookAndFeel = new JPanel(new BorderLayout());

        JPanel lafPanel = new JPanel(new MigLayout());
        lafPanel.setBorder(BorderFactory.createTitledBorder("Look And Feel"));
        JLabel choseLaf = new JLabel("Chose Look and Feel");
        lafComboBox = new JComboBox(mainWindow.lafTitles);
        lafComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lafComboBoxCicked();
            }
        });

        lafPanel.add(choseLaf, "wrap");
        lafPanel.add(lafComboBox);
        lookAndFeel.add(lafPanel);

        customizeGuiTabbedPane.addTab("GUI Settings", lookAndFeel);
    }

    private void lafComboBoxCicked() {
        mainWindow.setLookAndFeel(lafComboBox.getSelectedIndex());
        User newUser = mainWindow.actualUser;
        newUser.setLafIndex(lafComboBox.getSelectedIndex());
        mainWindow.actualUser = mainWindow.userDao.update(newUser);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void generateSearchOptionsTabbedPane() {
        searchOptionsTabbedPane = new JTabbedPane();
        JPanel searchOptions = new JPanel(new BorderLayout());

        JPanel listPanel = new JPanel();
        generateListModel();
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                generateTitleArtistAndTitleUrls();
            }
        });
        listPanel.add(list);


        JPanel websiteAddPanel = generateWebsiteAddPanel();
        JPanel updateWebsitePanel = generateUpdateWebsitePanel();

        searchOptions.add(updateWebsitePanel, BorderLayout.SOUTH);
        searchOptions.add(websiteAddPanel, BorderLayout.EAST);
        searchOptions.add(listPanel, BorderLayout.WEST);
        searchOptionsTabbedPane.addTab("Web Search Options", searchOptions);
    }

    private void generateListModel() {
        listModel = new DefaultListModel();

        java.util.List<Website> websiteList = mainWindow.websiteDao.findAllWebsites();
        for (Website website : websiteList) {
            listModel.addElement(website.getTitle());
        }
    }

    private void generateTitleArtistAndTitleUrls() {
        List<Website> websiteList = mainWindow.websiteDao.findAllWebsites();
        titleTextField.setText(websiteList.get(list.getSelectedIndex()).getTitle());

        titleTextArea.setText(websiteList.get(list.getSelectedIndex()).getUrlTitle());
        titleTextArea.setText(websiteList.get(list.getSelectedIndex()).getUrlArtist());
    }

    private JPanel generateUpdateWebsitePanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Title");
        titleTextField = new JTextField(40);
        JLabel labelSearchURL = new JLabel("Search URLs:");
        urlsTabbedPane = new JTabbedPane();

        titleTextArea = new JTextArea();
        artistTextArea = new JTextArea();

        urlsTabbedPane.addTab("Title", titleTextArea);
        urlsTabbedPane.addTab("Artist", artistTextArea);

        panel.add(label, "wrap");
        panel.add(titleTextField, "wrap");
        panel.add(new JPanel(), "wrap");
        panel.add(labelSearchURL, "wrap");
        panel.add(generateUrlsPanel());

        return panel;
    }

    private JPanel generateUrlsPanel() {
        JPanel panel = new JPanel();
        panel.add(urlsTabbedPane);


        return panel;
    }

    private JPanel generateWebsiteAddPanel() {
        JPanel panel = new JPanel(new MigLayout());

        JButton newWebsite = new JButton();  //TODO icon
        newWebsite.setText("New");
        newWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton deleteWebsite = new JButton();
        deleteWebsite.setText("Delete");
        deleteWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton upWebsite = new JButton();
        upWebsite.setText("Up");
        upWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton downWebsite = new JButton();
        downWebsite.setText("Down");
        upWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel.add(newWebsite, "wrap");
        panel.add(deleteWebsite, "wrap");
        panel.add(upWebsite, "wrap");
        panel.add(downWebsite, "wrap");
        return panel;
    }

    private void generateBasicOptionsTabbedPane() {
        basicOptionsTabbedPane = new JTabbedPane();
        JPanel licensePanel = new JPanel();
        basicOptionsTabbedPane.addTab("License", licensePanel);


    }

    private void generateButtonsPanel() {
        buttonsPanel = new CustomPanel("src/images/officeImage.jpg");
        buttonsPanel.setLayout(new MigLayout());

        Dimension buttonsSize = new Dimension(70,70);

        JButton basicOptions = new JButton(" Basic Options  ");
        basicOptions.setIcon(new ImageIcon("src/images/settings64Icon.png"));
        basicOptions.setVerticalTextPosition(SwingConstants.BOTTOM);
        basicOptions.setHorizontalTextPosition(SwingConstants.CENTER);
        basicOptions.setPreferredSize(buttonsSize);
        basicOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBasicOptionsTabbedPane();
            }
        });
        buttonsPanel.add(basicOptions, "wrap");

        JButton searchOptions = new JButton("Search Options");
        searchOptions.setIcon(new ImageIcon("src/images/findIcon.png"));
        searchOptions.setVerticalTextPosition(SwingConstants.BOTTOM);
        searchOptions.setHorizontalTextPosition(SwingConstants.CENTER);
        searchOptions.setPreferredSize(buttonsSize);
        searchOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSearchOptionsTabbedPane();
            }
        });
        buttonsPanel.add(searchOptions, "wrap");

        JButton customizeGui = new JButton("Customize GUI ");
        customizeGui.setIcon(new ImageIcon("src/images/guiIcon.png"));
        customizeGui.setVerticalTextPosition(SwingConstants.BOTTOM);
        customizeGui.setHorizontalTextPosition(SwingConstants.CENTER);
        customizeGui.setPreferredSize(buttonsSize);
        customizeGui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCustomizeGuiTabbedPane();
            }
        });
        buttonsPanel.add(customizeGui);

    }

    private void setCustomizeGuiTabbedPane() {
        tabbedPanePanel.removeAll();
        tabbedPanePanel.add(customizeGuiTabbedPane);
        tabbedPanePanel.revalidate();
        tabbedPanePanel.repaint();
    }

    private void setSearchOptionsTabbedPane() {
        tabbedPanePanel.removeAll();
        tabbedPanePanel.add(searchOptionsTabbedPane);
        tabbedPanePanel.revalidate();
        tabbedPanePanel.repaint();

    }

    private void setBasicOptionsTabbedPane() {
        tabbedPanePanel.removeAll();
        tabbedPanePanel.add(basicOptionsTabbedPane);
        tabbedPanePanel.revalidate();
        tabbedPanePanel.repaint();
    }


    private void setMyValues() {
        setSize(400,500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
