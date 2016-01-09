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

    private JTextField textField;

    public SearchToolBar(String name) {
        super(name);
        setRollover(true);
        setFocusable(false);
        setLayout(new MigLayout());
        addLabel("Find:");
        addNewSeparator();
        addTextField(20);
        addNewSeparator();
        addComboBox();
        addNewSeparator();
        addSearchButton();
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
                browserSearcher.searchString(textField.getText());
            }
        });
        add(button);
    }

    private void addComboBox() {
        String[] websiteList = generateWebList();
        JComboBox comboBox = new JComboBox(websiteList);
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        add(comboBox);
    }

    private void saveWebsite() {
        WebsiteDao websiteDao = new WebsiteDao();
        Website website = new Website();
        website.setTitle("guitarWebsite2");
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
