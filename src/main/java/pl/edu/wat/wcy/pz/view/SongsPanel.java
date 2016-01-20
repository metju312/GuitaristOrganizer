package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SongsPanel extends JPanel {
    private MainWindow mainWindow;

    private DefaultListModel listModel;
    public List<Song> songList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Artist actualArtist;
    private JToolBar toolBar;
    private JButton sortButton;
    private String sortStatus = "Down";
    public JButton addSongButton;
    private int selectedRow;
    private JPopupMenu popupMenu;
    private JMenuItem updateMenuItem;
    private JMenuItem deleteMenuItem;

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

        generateRestOfComponentsWithEmptyListModel();

        generateMenuItems();
        generatePopMenu();
        setActualArtist();
    }
    private void generateMenuItems() {
        updateMenuItem = new JMenuItem("Update");
        updateMenuItem.setIcon(new ImageIcon("src/images/updateIcon.png"));
        updateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateSongDialog(mainWindow, songList.get(selectedRow));
            }
        });

        deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.setIcon(new ImageIcon("src/images/cancelIcon.png"));
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    mainWindow.coversPanel.deleteAllCoversWithSong(songList.get(selectedRow));
                    mainWindow.songDao.delete(songList.get(selectedRow).getId());
                    if(songList.get(selectedRow) != null){
                        mainWindow.songDao.delete(songList.get(selectedRow).getId());
                    }
                    revalidateMeWithActualArtist();
                    mainWindow.coversPanel.revalidateWithEmptyList();
                    mainWindow.tablePanel.revalidateMeWithEmptyModel();
                    mainWindow.tabsPanel.revalidateWithEmptyList();
                    mainWindow.coversPanel.addCoverButton.setEnabled(false);
                    mainWindow.tabsPanel.addTabButton.setEnabled(false);
                }
            }
        });
    }

    public void revalidateAllPanels() {
        revalidateMe();
        mainWindow.coversPanel.revalidateWithEmptyList();
        mainWindow.tablePanel.revalidateMeWithEmptyModel();
        mainWindow.tabsPanel.revalidateWithEmptyList();
        mainWindow.coversPanel.addCoverButton.setEnabled(false);
        mainWindow.tabsPanel.addTabButton.setEnabled(false);
    }

    private void generatePopMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.add(updateMenuItem);
        popupMenu.add(deleteMenuItem);
    }

    private void setActualArtist() {
        List<Artist> list = mainWindow.artistDao.findArtistsWithName("All Artists");
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
                revalidateAllPanels();
                revalidateMeWithActualArtist();
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

    private void generateRestOfComponentsWithEmptyListModel() {
        generateSongList();
        generateEmptyListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Songs ToolBar");
        toolBar.setLayout(new MigLayout());
        JLabel label = new JLabel("Songs");
        addSongButton = new JButton();
        addSongButton.setEnabled(false);

        Image img = null;
        try {
            img = ImageIO.read(new File("src/images/plusIcon14.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addSongButton.setIcon(new ImageIcon(img));
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddSongDialog(mainWindow, actualArtist);
            }
        });
        toolBar.add(label, BorderLayout.WEST);
        toolBar.add(addSongButton, BorderLayout.EAST);
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
                mainWindow.coversPanel.setCoversListWithSong(jList.getSelectedValue().toString());
                mainWindow.coversPanel.addCoverButton.setEnabled(true);
                mainWindow.tablePanel.setCoverListModel(mainWindow.coversPanel.coverList);
                mainWindow.tabsPanel.revalidateWithEmptyList();
                mainWindow.tabsPanel.addTabButton.setEnabled(false);

                mainWindow.tablePanel.typeOfTable = 2;
            }
        });


        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JList list = (JList)e.getSource();
                    int row = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(row);
                    selectedRow = row;

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void generateListModel() {
        listModel = new DefaultListModel();
        for (Song song : songList) {
            listModel.addElement(song.getTitle());
        }
    }

    private void generateEmptyListModel() {
        listModel = new DefaultListModel();
    }

    private void generateSongList() {
        if(Objects.equals(sortStatus, "Down")){
            songList = mainWindow.songDao.findAllSongsOrderByTitle();
        }else {
            songList = mainWindow.songDao.findAllSongsOrderByTitleDesc();
        }
    }

    public void revalidateMe() {
        remove(listScrollPane);
        generateRestOfComponents();
        revalidate();
        repaint();
    }

    public void setSongsListWithArtist(String chosenArtistName) {
        if(Objects.equals(chosenArtistName, "All Artists")){
            if(Objects.equals(sortStatus, "Down")){
                songList = mainWindow.songDao.findAllSongsOrderByTitle();
            }else {
                songList = mainWindow.songDao.findAllSongsOrderByTitleDesc();
            }
            List<Artist> list = mainWindow.artistDao.findArtistsWithName(chosenArtistName);
            actualArtist = list.get(0);
        }else{
            List<Artist> list = mainWindow.artistDao.findArtistsWithName(chosenArtistName);
            if(Objects.equals(sortStatus, "Down")){
                songList = mainWindow.songDao.findSongsWithArtistOrderByTitle(list.get(0));
            }else {
                songList = mainWindow.songDao.findSongsWithArtistOrderByTitleDesc(list.get(0));
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
            songList = mainWindow.songDao.findAllSongsOrderByTitleLike(text);
        }else {
            songList = mainWindow.songDao.findAllSongsOrderByTitleDescLike(text);
        }

        isSearchingTitleLike = true;
        generateListModel();
        jList.setModel(listModel);
    }


    public void setSongsListArtistLike(String text) {
        actualSearchingArtist = text;
        if(Objects.equals(sortStatus, "Down")) {
            songList = mainWindow.songDao.findAllSongsOrderByTitleWithArtistLike(text);
        }else {
            songList = mainWindow.songDao.findAllSongsOrderByTitleWithArtistDescLike(text);
        }

        isSearchingArtistLike = true;
        generateListModel();
        jList.setModel(listModel);
    }

    public void revalidateMeWithActualArtist() {
        revalidateMe();
        setSongsListWithArtist(actualArtist.getName());
    }

    public void deleteAllSongsWithArtist(Artist artistToDelete) {
        List<Song> songsToDelete = mainWindow.songDao.findSongsWithArtistOrderByTitle(artistToDelete);

        for (Song song : songsToDelete) {
            System.out.println("Song to delete: " + song.getTitle());
            mainWindow.coversPanel.deleteAllCoversWithSong(song);
            mainWindow.songDao.delete(song);
        }
    }

    public void revalidateMeWithEmptyList() {
        remove(listScrollPane);
        generateRestOfComponentsWithEmptyListModel();
        revalidate();
        repaint();
    }
}
