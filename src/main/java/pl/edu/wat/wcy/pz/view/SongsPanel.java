package pl.edu.wat.wcy.pz.view;

import pl.edu.wat.wcy.pz.model.dao.SongDao;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SongsPanel extends JPanel {

    private DefaultListModel listModel;
    private List<Song> songList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Song actualSong;
    private JToolBar toolBar;

    public SongsPanel() {
        setLayout(new BorderLayout());
        generateAndAddToolBar();
        generateSongList();
        generateListModel();
        generateJList();
        generateAndAddListScroller();


    }



    private void generateAndAddToolBar() {
        toolBar = new JToolBar("Songs toolBar");
        JLabel label = new JLabel("Songs");
        toolBar.add(label);
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);
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
        for (Song song : songList) {
            listModel.addElement(song.getTitle());
        }

    }

    private void generateSongList() {
        SongDao songDao = new SongDao();
        songList = songDao.findAll();
    }

    private void saveSong() {
        SongDao songDao = new SongDao();
        Song song = new Song();
        song.setTitle("title1");
        song.setPath("D:");
        song.setUrl("http");
        songDao.create(song);
    }

}
