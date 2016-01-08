package pl.edu.wat.wcy.pz.view;

import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArtistsPanel extends JPanel {
    private MainWindow mainWindow;

    private DefaultListModel listModel;
    private List<Artist> artistList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Artist actualArtist;
    private JToolBar toolBar;

    public ArtistsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());

        generateToolBar();
        add(toolBar, BorderLayout.NORTH);

        generateArtistList();
        generateListModel();
        generateJList();
        generateAndAddListScroller();

    }

    private void generateToolBar() {
        toolBar = new JToolBar("Artists ToolBar");
        JLabel label = new JLabel("Artists");
        toolBar.add(label);
        toolBar.setFloatable(false);
    }

    private void generateAndAddListScroller() {
        listScrollPane = new JScrollPane(jList);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(listScrollPane);
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

    public void drawAllArtists() {
        mainWindow.songsPanel.drawAllSongs();
    }
}
