package pl.edu.wat.wcy.pz.view;

import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ArtistsPanel extends JPanel {
    private MainWindow mainWindow;

    ArtistDao artistDao = new ArtistDao();

    private DefaultListModel listModel;
    private List<Artist> artistList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Artist actualArtist;
    private JToolBar toolBar;

    public ArtistsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        saveOtherArtistIfNotExists("Other Artists");

        generateToolBar();
        add(toolBar, BorderLayout.NORTH);

        generateRestOfComponents();
    }

    private void generateRestOfComponents() {
        generateArtistList();
        generateListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void saveOtherArtistIfNotExists(String otherName) {
        if(artistDao.findArtistsWithName(otherName).size()==0){
            Artist artist = new Artist();
            artist.setName(otherName);
            artistDao.create(artist);
        }
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Artists ToolBar");
        JLabel label = new JLabel("Artists");
        toolBar.add(label);
        toolBar.setFloatable(false);
    }

    private void generateListScroller() {
        listScrollPane = new JScrollPane(jList);
        listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
        artistList = artistDao.findAll();
    }

    private void saveArtist() {
        Artist artist = new Artist();
        artist.setName("Adele");
        artistDao.create(artist);
    }

    public void revalidateMe() {
        remove(listScrollPane);
        generateRestOfComponents();
        revalidate();
        repaint();
    }
}
