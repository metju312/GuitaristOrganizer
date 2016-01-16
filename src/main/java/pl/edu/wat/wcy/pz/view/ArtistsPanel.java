package pl.edu.wat.wcy.pz.view;

import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistsPanel extends JPanel {
    private MainWindow mainWindow;

    ArtistDao artistDao = new ArtistDao();

    private DefaultListModel listModel;
    private List<Artist> artistList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Artist actualArtist;
    private JToolBar toolBar;
    private JButton sortButton;
    private String sortStatus = "Down";

    public ArtistsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        saveUnknownArtistIfNotExists("Unknown Artist");
        saveAllArtistsArtistIfNotExists("All Artists");

        generateToolBar();
        add(toolBar, BorderLayout.NORTH);

        generateRestOfComponents();
        generateSortButton();
    }

    private void saveAllArtistsArtistIfNotExists(String name) {
        if(artistDao.findArtistsWithName(name).size()==0){
            Artist artist = new Artist();
            artist.setName(name);
            artistDao.create(artist);
        }
    }

    private void generateRestOfComponents() {
        generateArtistList();
        generateListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void saveUnknownArtistIfNotExists(String name) {
        if(artistDao.findArtistsWithName(name).size()==0){
            Artist artist = new Artist();
            artist.setName(name);
            artistDao.create(artist);
        }
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Artists ToolBar");
        toolBar.setLayout(new BorderLayout());
        JLabel label = new JLabel("Artists");
        toolBar.add(label, BorderLayout.WEST);
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
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                mainWindow.songsPanel.setSongsListWithArtist(jList.getSelectedValue().toString());
                mainWindow.tablePanel.setSongsListModel(mainWindow.songsPanel.songList);
            }
        });
    }

    private void generateListModel() {
        listModel = new DefaultListModel();
        for (Artist artist : artistList) {
            listModel.addElement(artist.getName());
        }

    }

    private void generateArtistList() {
        if(Objects.equals(sortStatus, "Down")){
            artistList = artistDao.findAllArtistsOrderByName();
        }else {
            artistList = artistDao.findAllArtistsOrderByNameDesc();
        }

        setAllArtistsArtistAndUnknownArtistInFirstPlace();
    }

    private void setAllArtistsArtistAndUnknownArtistInFirstPlace() {
        List<Artist> unknownArtist = artistDao.findArtistsWithName("Unknown Artist");
        List<Artist> allArtistsArtist = artistDao.findArtistsWithName("All Artists");
        List<Artist> newArtistList = new ArrayList<>();
        newArtistList.add(allArtistsArtist.get(0));
        newArtistList.add(unknownArtist.get(0));
        for (Artist artist : artistList) {
            if(artist != unknownArtist.get(0) && artist != allArtistsArtist.get(0)){
                newArtistList.add(artist);
            }
        }
        artistList = newArtistList;
    }


    public void revalidateMe() {
        remove(listScrollPane);
        generateRestOfComponents();
        revalidate();
        repaint();
    }

    private void generateSortButton() {
        sortButton = new JButton();
        setImageIcon();
        sortButton.setPreferredSize(new Dimension(22,22));
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipAndDrawArrowIcon();
                setArtistsList();
            }
        });
        toolBar.add(sortButton, BorderLayout.EAST);
    }

    private void setArtistsList() {
        generateArtistList();
        generateListModel();
        jList.setModel(listModel);
    }

    private void setImageIcon() {
        try {
            if(Objects.equals(sortStatus, "Down")){
                Image img = ImageIO.read(new File("src/images/arrowDownIcon.png"));
                sortButton.setIcon(new ImageIcon(img));
            }else{
                Image img = ImageIO.read(new File("src/images/arrowUpIcon.png"));
                sortButton.setIcon(new ImageIcon(img));
            }
        } catch (IOException ex) {

        }
    }

    private void flipAndDrawArrowIcon() {
        if(Objects.equals(sortStatus, "Down")){
            sortStatus = "Up";
        }else{
            sortStatus = "Down";
        }
        setImageIcon();
    }
}
