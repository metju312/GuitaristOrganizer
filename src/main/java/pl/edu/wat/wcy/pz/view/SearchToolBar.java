package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.edu.wat.wcy.pz.controller.BrowserSearcher;
import pl.edu.wat.wcy.pz.model.dao.WebsiteDao;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class SearchToolBar extends JToolBar {
    private WebsiteDao websiteDao = new WebsiteDao();

    private JTextField textField;
    private JComboBox comboBox;

    public SearchToolBar(String name) {
        super(name);
        setRollover(true);
        setFocusable(false);
        setLayout(new MigLayout());

        setDefaultWebsitesIfNotExists();
        addLabel("Find:");
        addNewSeparator();
        addTextField(20);
        addNewSeparator();
        addComboBox();
        addNewSeparator();
        addSearchButton();
    }

    private void setDefaultWebsitesIfNotExists() {
        if(!defaultWebsitesExists()){
            saveWebsite("YouTube"
                    ,"https://www.youtube.com/results?search_query="
                    ,"https://www.youtube.com/results?search_query=");

            saveWebsite("Songster"
                    ,"http://www.songsterr.com/a/wa/search?pattern="
                    ,"http://www.songsterr.com/a/wa/search?pattern=");

            saveWebsite("Ultimate Guitar"
                    ,"https://www.ultimate-guitar.com/search.php?search_type=title&order=&value="
                    , "https://www.ultimate-guitar.com/search.php?search_type=band&order=&value=");
        }
    }

    private boolean defaultWebsitesExists() {
        java.util.List<Website> w = websiteDao.findWebsitesWithTitle("YouTube");
        if(w.size()==0){
            return false;
        }
        return true;
    }

    private void addNewSeparator() {
        addSeparator(new Dimension(1,this.getSize().height));
    }

    private void addSearchButton() {
        CustomButton button = new CustomButton("src/images/searchIcon.png");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BrowserSearcher browserSearcher = new BrowserSearcher();
                browserSearcher.searchString(textField.getText(), comboBox.getSelectedItem());
            }
        });
        add(button);
    }

    private void addComboBox() {
        String[] websiteList = generateWebList();
        comboBox = new JComboBox(websiteList);
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        add(comboBox);
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

    private void addTextField(int length) {
        textField = new JTextField(length);
        add(textField);
    }

    private void addLabel(String find) {
        JLabel label = new JLabel(find);
        add(label);
    }
}
