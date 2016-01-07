package pl.edu.wat.wcy.pz.view;

import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListPanel extends JPanel {

    private DefaultListModel listModel;
    private List<Artist> artistList;
    private JList jList;
    private JScrollPane listScroller;
    private Artist actualArtist;
    private JToolBar toolBar;

    public ListPanel() {
        setLayout(new BorderLayout());
        generateAndAddToolBar();
        generateArtistList();
        generateListModel();
        generateJList();
        generateAndAddListScroller();

    }

    private void generateAndAddToolBar() {
        toolBar = new JToolBar("Still draggable");
        JLabel label = new JLabel("Songs");
        toolBar.add(label);
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);
    }

    private void generateAndAddListScroller() {
        listScroller = new JScrollPane(jList);
        //listScroller.setPreferredSize(new Dimension(100, 200));
        add(listScroller);
    }

    private void generateJList() {
        jList = new JList(listModel);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setVisibleRowCount(-1);
    }

    private void generateListModel() {
        listModel = new DefaultListModel();
        for (Artist artist : artistList) {
            listModel.addElement(artist.getName());
        }

    }

    private void generateArtistList() {
        ArtistDao artistDao = new ArtistDao();
        artistList = artistDao.findAll();
    }

    private void saveArtist() {
        ArtistDao artistDao = new ArtistDao();
        Artist artist = new Artist();
        artist.setName("Adele");
        artistDao.create(artist);
    }

}
