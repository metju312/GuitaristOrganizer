package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.controller.BrowserSearcher;
import pl.edu.wat.wcy.pz.model.dao.WebsiteDao;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SearchToolBar extends JToolBar {
    private MainWindow mainWindow;
    private WebsiteDao websiteDao = new WebsiteDao();

    private JTextField textField;
    private JComboBox comboBoxTitleAndArtist;
    private JComboBox comboBoxWebsites;
    private JButton searchButton;
    private JButton websiteSearchButton;
    private JButton clearButton;
    private JTextPane textPane;

    public SearchToolBar(String name, MainWindow mainWindow) {
        super(name);
        this.mainWindow = mainWindow;
        setRollover(true);
        setFocusable(false);
        setLayout(new MigLayout());

        setDefaultWebsitesIfNotExists();
        addLabel("   Find:");
        addNewSeparator(0);
        addTextPane();
        addNewSeparator(0);
        addComboBoxTitleOrArtist();
        addNewSeparator(0);
        addSearchButton();
        addNewSeparator(20);
        addWebsiteSearchButton();
        addNewSeparator(0);
        addComboBoxWebsite();
    }

    private void addTextPane() {
        textPane = new JTextPane();
        //textPane.setPreferredSize(new Dimension(340, 400));
        textPane.setEditable(false);
        setTextPaneContent();
        add(textPane);
    }

    private void setTextPaneContent() {
        generateTextField(20);
        textPane.insertComponent(textField);
        generateClearButton();
        textPane.insertComponent(clearButton);
    }

    private void addComboBoxTitleOrArtist() {
        String[] strings = {"Title", "Artist"};
        comboBoxTitleAndArtist = new JComboBox(strings);
        comboBoxTitleAndArtist.setSelectedIndex(0);
        //comboBoxTitleAndArtist.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        add(comboBoxTitleAndArtist);
    }

    private void addWebsiteSearchButton() {
        websiteSearchButton = new JButton(new ImageIcon("src/images/websiteIcon.png"));
        websiteSearchButton.setToolTipText("Web Search");

        websiteSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(textField.getText(), "")){
                    JOptionPane.showMessageDialog(websiteSearchButton, "Text field is empty.");
                }else{
                    BrowserSearcher browserSearcher = new BrowserSearcher();
                    browserSearcher.searchString(textField.getText(), comboBoxWebsites.getSelectedItem(), comboBoxTitleAndArtist.getSelectedItem());
                }
            }
        });
        add(websiteSearchButton);
    }

    private void setDefaultWebsitesIfNotExists() {
        if (!defaultWebsitesExists()) {
            saveWebsite("YouTube"
                    , "https://www.youtube.com/results?search_query="
                    , "https://www.youtube.com/results?search_query=");

            saveWebsite("Songster"
                    , "http://www.songsterr.com/a/wa/search?pattern="
                    , "http://www.songsterr.com/a/wa/search?pattern=");

            saveWebsite("Ultimate Guitar"
                    , "https://www.ultimate-guitar.com/search.php?search_type=title&order=&value="
                    , "https://www.ultimate-guitar.com/search.php?search_type=band&order=&value=");
        }
    }

    private boolean defaultWebsitesExists() {
        java.util.List<Website> w = websiteDao.findWebsitesWithTitle("YouTube");
        if (w.size() == 0) {
            return false;
        }
        return true;
    }

    private void addNewSeparator(int size) {
        addSeparator(new Dimension(size, this.getSize().height));
    }

    private void addSearchButton() {
        searchButton = new JButton(new ImageIcon("src/images/searchIcon.png"));
        searchButton.setToolTipText("Find");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(textField.getText(), "")){
                    JOptionPane.showMessageDialog(searchButton, "Text field is empty.");
                }else {
                    afterWriteInTextFieldMethod();
                }
            }
        });
        add(searchButton);
    }

    private void afterWriteInTextFieldMethod() {
        if(comboBoxTitleAndArtist.getSelectedIndex() == 0){
            selectTitlesLike(textField.getText());
        }else{
            selectTitlesWhereArtistLike(textField.getText());
        }
    }

    private void selectTitlesWhereArtistLike(String text) {
        mainWindow.songsPanel.setSongsListArtistLike(text);
        mainWindow.tablePanel.setSongsListModel(mainWindow.songsPanel.songList);
    }

    private void selectTitlesLike(String text) {
        mainWindow.songsPanel.setSongsListLike(text);
        mainWindow.tablePanel.setSongsListModel(mainWindow.songsPanel.songList);
    }

    private void addComboBoxWebsite() {
        String[] websiteList = generateWebList();
        comboBoxWebsites = new JComboBox(websiteList);
        comboBoxWebsites.setSelectedIndex(0);
        //comboBoxWebsites.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        add(comboBoxWebsites);
    }

    private void saveWebsite(String title, String urlTitle, String urlArtist) {
        Website website = new Website();
        website.setTitle(title);
        website.setUrlTitle(urlTitle);
        website.setUrlArtist(urlArtist);
        websiteDao.create(website);
    }

    private String[] generateWebList() {
        WebsiteDao websiteDao = new WebsiteDao();
        java.util.List<Website> websiteList;
        websiteList = websiteDao.findAll();
        String[] strings = new String[websiteList.size()];
        int i = 0;
        for (Website website : websiteList) {
            strings[i] = website.getTitle();
            i++;
        }
        return strings;
    }

    private void generateTextField(int length) {
        textField = new JTextField(length);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                afterWriteInTextFieldMethod();
            }
        });
    }

    private void generateClearButton() {
        clearButton = new JButton(new ImageIcon("src/images/clearIcon.png"));
        clearButton.setPreferredSize(new Dimension(20, 20));
        clearButton.setToolTipText("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });
    }

    private void addLabel(String find) {
        JLabel label = new JLabel(find);
        add(label);
    }
}
