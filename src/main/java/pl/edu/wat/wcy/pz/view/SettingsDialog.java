package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.controller.BrowserSearcher;
import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

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
    private JTextField titleUrlTextField;
    private JTextField artistUrlTextField;

    private JScrollPane listScrollPane;
    private DefaultListModel listModel;
    private JList jList;
    private JTabbedPane urlsTabbedPane;
    private JButton deleteWebsite;
    private JButton updateWebsite;

    private int selectedRow = 0;

    private List<Website> actualWebsites;

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
                revalidateComboBox();
                dispose();
            }
        });
        okAndCancelPanel.add(okButton, "gapleft 210");

        JButton cancelButton = new JButton(new ImageIcon("src/images/cancelIcon.png"));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revalidateComboBox();
                dispose();
            }
        });
        okAndCancelPanel.add(cancelButton, "gapleft 4");
    }

    private void revalidateComboBox() {
        mainWindow.searchToolBar.revalidateComboBox();
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

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setPreferredSize(new Dimension(140,120));
        generateListModel();
        jList = new JList(listModel);
        //jList.setPreferredSize(new Dimension(160,120));
        jList.setBorder(new BevelBorder(BevelBorder.LOWERED));
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(-1);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedRow = jList.getSelectedIndex();
                generateTitleArtistAndTitleUrls();
                deleteWebsite.setEnabled(true);
                updateWebsite.setEnabled(true);
            }
        });

        listScrollPane = new JScrollPane(jList);
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        listPanel.add(listScrollPane, BorderLayout.CENTER);


        JPanel websiteAddPanel = generateWebsiteAddPanel();
        JPanel updateWebsitePanel = generateUpdateWebsitePanel();

        searchOptions.add(updateWebsitePanel, BorderLayout.SOUTH);
        searchOptions.add(websiteAddPanel, BorderLayout.EAST);
        searchOptions.add(listPanel, BorderLayout.WEST);
        searchOptionsTabbedPane.addTab("Web Search Options", searchOptions);
    }

    private void generateListModel() {
        listModel = new DefaultListModel();

        actualWebsites = mainWindow.websiteDao.findAllWebsites();
        for (Website website : actualWebsites) {
            listModel.addElement(website.getTitle());
        }
    }

    private void generateTitleArtistAndTitleUrls() {
        actualWebsites = mainWindow.websiteDao.findAllWebsites();
        System.out.println("selectedRow:" + selectedRow);
        if(selectedRow == -1){
            titleTextField.setText("");
            titleUrlTextField.setText("");
            titleUrlTextField.setText("");
        }else{
            titleTextField.setText(actualWebsites.get(selectedRow).getTitle());
            titleUrlTextField.setText(actualWebsites.get(selectedRow).getUrlTitle());
            titleUrlTextField.setText(actualWebsites.get(selectedRow).getUrlArtist());
        }
    }

    private JPanel generateUpdateWebsitePanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel label = new JLabel("Title");
        titleTextField = new JTextField(40);
        JLabel labelSearchURL = new JLabel("Search URLs:");
        urlsTabbedPane = new JTabbedPane();

        int textFieldsLength = 26;
        titleUrlTextField = new JTextField(textFieldsLength);
        artistUrlTextField = new JTextField(textFieldsLength);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new MigLayout());
        titlePanel.add(titleUrlTextField);

        JPanel artistPanel = new JPanel();
        artistPanel.setLayout(new MigLayout());
        artistPanel.add(artistUrlTextField);

        urlsTabbedPane.addTab("Title", titlePanel);
        urlsTabbedPane.addTab("Artist", artistPanel);

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

        JButton newWebsite = new JButton();
        newWebsite.setText("New");
        newWebsite.setIcon(new ImageIcon("src/images/plusIcon.png"));
        newWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(titleTextField.getText(), "")){
                    JOptionPane.showMessageDialog(titleTextField, "Set title.");
                }
                Website website = new Website();
                website.setTitle(titleTextField.getText());
                website.setUrlTitle(titleUrlTextField.getText());
                website.setUrlArtist(artistUrlTextField.getText());
                website.setUser(mainWindow.actualUser);
                mainWindow.websiteDao.create(website);
                revalidateList();
            }
        });

        deleteWebsite = new JButton();
        deleteWebsite.setText("Delete");
        deleteWebsite.setEnabled(false);
        deleteWebsite.setIcon(new ImageIcon("src/images/cancelIcon.png"));
        deleteWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.websiteDao.delete(actualWebsites.get(selectedRow).getId());
                deleteWebsite.setEnabled(false);
                updateWebsite.setEnabled(false);
                revalidateList();
            }
        });


        updateWebsite = new JButton();
        updateWebsite.setText("Update");
        updateWebsite.setEnabled(false);
        updateWebsite.setIcon(new ImageIcon("src/images/updateIcon.png"));
        updateWebsite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualWebsites.get(selectedRow).setTitle(titleTextField.getText());
                actualWebsites.get(selectedRow).setUrlTitle(titleUrlTextField.getText());
                actualWebsites.get(selectedRow).setUrlArtist(artistUrlTextField.getText());
                mainWindow.websiteDao.update(actualWebsites.get(selectedRow));
                revalidateList();
            }
        });

        panel.add(newWebsite, "wrap");
        panel.add(deleteWebsite, "wrap");
        panel.add(updateWebsite, "wrap");
        return panel;
    }

    private void revalidateList() {
        generateListModel();
        jList.setModel(listModel);
        deleteWebsite.setEnabled(false);
        updateWebsite.setEnabled(false);
    }

    private void generateBasicOptionsTabbedPane() {
        basicOptionsTabbedPane = new JTabbedPane();
        JPanel licensePanel = new JPanel();
        basicOptionsTabbedPane.addTab("License", licensePanel);

        JButton buyLicense = new JButton("Buy License");
        buyLicense.setIcon(new ImageIcon("src/images/cashIcon.png"));
        buyLicense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BrowserSearcher browserSearcher = new BrowserSearcher(mainWindow);
                browserSearcher.openUrl("http://localhost:8080/");
            }
        });

        licensePanel.add(buyLicense);
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
