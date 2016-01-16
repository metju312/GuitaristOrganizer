package pl.edu.wat.wcy.pz.view;

import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.dao.SongDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SongsPanel extends JPanel {
    private MainWindow mainWindow;
    SongDao songDao = new SongDao();
    ArtistDao artistDao = new ArtistDao();

    private DefaultListModel listModel;
    public List<Song> songList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Song actualSong;
    private Artist actualArtist;
    private JToolBar toolBar;
    private JButton sortButton;
    private String sortStatus = "Down";

    private boolean isSearchingTitleLike = false;
    private boolean isSearchingArtistLike = false;
    private String actualSearchingTitle = "";
    private String actualSearchingArtist = "";


    public SongsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());

        generateToolBar();
        generateSortButton();
        add(toolBar, BorderLayout.NORTH);

        generateRestOfComponents();

        setActualArtist();
    }

    private void setActualArtist() {
        List<Artist> list = artistDao.findArtistsWithName("All Artists");
        actualArtist = list.get(0);
    }

    private void generateSortButton() {
        sortButton = new JButton();
        setImageIcon();
        sortButton.setPreferredSize(new Dimension(22,22));
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipAndDrawArrowIcon();
                if (isSearchingTitleLike) {
                    setSongsListLike(actualSearchingTitle);

                }else if(isSearchingArtistLike){
                    setSongsListArtistLike(actualSearchingArtist);
                }else {
                    setSongsListWithArtist(actualArtist.getName());
                }
        }
    });
        toolBar.add(sortButton, BorderLayout.EAST);
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

    private void generateRestOfComponents() {
        generateSongList();
        generateListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Songs ToolBar");
        toolBar.setLayout(new BorderLayout());
        JLabel label = new JLabel("Songs");
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
                //TODO change coverPanelList
            }
        });
    }

    private void generateListModel() {
        listModel = new DefaultListModel();
        for (Song song : songList) {
            listModel.addElement(song.getTitle());
        }

    }

    private void generateSongList() {
        songList = songDao.findAll();
    }

    public void revalidateMe() {
        remove(listScrollPane);
        generateRestOfComponents();
    }

    public void setSongsListWithArtist(String chosenArtistName) {
        if(Objects.equals(chosenArtistName, "All Artists")){
            if(Objects.equals(sortStatus, "Down")){
                songList = songDao.findAllSongsOrderByTitle();
            }else {
                songList = songDao.findAllSongsOrderByTitleDesc();
            }
            List<Artist> list = artistDao.findArtistsWithName(chosenArtistName);
            actualArtist = list.get(0);
        }else{
            List<Artist> list = artistDao.findArtistsWithName(chosenArtistName);
            if(Objects.equals(sortStatus, "Down")){
                songList = songDao.findSongsWithArtistOrderByTitle(list.get(0));
            }else {
                songList = songDao.findSongsWithArtistOrderByTitleDesc(list.get(0));
            }
            actualArtist = list.get(0);
        }

        isSearchingTitleLike = false;
        generateListModel();
        jList.setModel(listModel);
    }

    public void setSongsListLike(String text) {
        actualSearchingTitle = text;
        if(Objects.equals(sortStatus, "Down")) {
            songList = songDao.findAllSongsOrderByTitleLike(text);
        }else {
            songList = songDao.findAllSongsOrderByTitleDescLike(text);
        }

        isSearchingTitleLike = true;
        generateListModel();
        jList.setModel(listModel);
    }


    public void setSongsListArtistLike(String text) {
        actualSearchingArtist = text;
        if(Objects.equals(sortStatus, "Down")) {
            songList = songDao.findAllSongsOrderByTitleWithArtistLike(text);
        }else {
            songList = songDao.findAllSongsOrderByTitleWithArtistDescLike(text);
        }

        isSearchingArtistLike = true;
        generateListModel();
        jList.setModel(listModel);
    }
}
