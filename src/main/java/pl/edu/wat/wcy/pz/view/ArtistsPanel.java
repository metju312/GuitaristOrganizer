package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistsPanel extends JPanel {
    private MainWindow mainWindow;

    private DefaultListModel listModel;
    private List<Artist> artistList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Artist actualArtist;
    private JToolBar toolBar;
    private JButton sortButton;
    private String sortStatus = "Down";
    public JButton addArtistButton;
    private int selectedRow;
    private JPopupMenu popupMenu;
    private JMenuItem updateMenuItem;
    private JMenuItem deleteMenuItem;

    public ArtistsPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.artistDao = new ArtistDao(mainWindow.actualUser);
        setLayout(new BorderLayout());
        saveUnknownArtistIfNotExists("Unknown Artist");
        saveAllArtistsArtistIfNotExists("All Artists");

        generateToolBar();
        add(toolBar, BorderLayout.NORTH);

        generateRestOfComponents();
        generateSortButton();

        generateMenuItems();
        generatePopMenu();
    }

    private void generateMenuItems() {
        updateMenuItem = new JMenuItem("Update");
        updateMenuItem.setIcon(new ImageIcon("src/images/updateIcon.png"));
        updateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateArtistDialog(mainWindow, artistList.get(selectedRow));
            }
        });

        deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.setIcon(new ImageIcon("src/images/cancelIcon.png"));
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedRow == 0 || selectedRow == 1){
                    JOptionPane.showMessageDialog(null, "Removal impossible.");
                }else {
                    int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        System.out.println("Artist to delete: " + artistList.get(selectedRow).getName());
                        mainWindow.songsPanel.deleteAllSongsWithArtist(artistList.get(selectedRow));
                        mainWindow.artistDao.delete(artistList.get(selectedRow).getId());
                        if(artistList.get(selectedRow) != null){
                            mainWindow.artistDao.delete(artistList.get(selectedRow).getId());
                        }
                        revalidateAllPanels();
                    }
                }
            }
        });
    }

    public void revalidateAllPanels() {
        revalidateMe();
        mainWindow.songsPanel.revalidateMeWithEmptyList();
        mainWindow.coversPanel.revalidateWithEmptyList();
        mainWindow.tablePanel.revalidateMeWithEmptyModel();
        mainWindow.tabsPanel.revalidateWithEmptyList();
        mainWindow.songsPanel.addSongButton.setEnabled(false);
        mainWindow.coversPanel.addCoverButton.setEnabled(false);
        mainWindow.tabsPanel.addTabButton.setEnabled(false);
    }

    private void saveAllArtistsArtistIfNotExists(String name) {
        if(mainWindow.artistDao.findArtistsWithName(name).size()==0){
            Artist artist = new Artist();
            artist.setName(name);
            artist.setUser(mainWindow.actualUser);
            mainWindow.artistDao.create(artist);
        }
    }

    private void generateRestOfComponents() {
        generateArtistList();
        generateListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generatePopMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.add(updateMenuItem);
        popupMenu.add(deleteMenuItem);
    }

    private void saveUnknownArtistIfNotExists(String name) {
        if(mainWindow.artistDao.findArtistsWithName(name).size()==0){
            Artist artist = new Artist();
            artist.setName(name);
            artist.setUser(mainWindow.actualUser);
            mainWindow.artistDao.create(artist);
        }
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Artists ToolBar");
        toolBar.setLayout(new MigLayout());
        JLabel label = new JLabel("Artists");

        addArtistButton = new JButton();
        Image img = null;
        try {
            img = ImageIO.read(new File("src/images/plusIcon14.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addArtistButton.setIcon(new ImageIcon(img));
        addArtistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddArtistDialog(mainWindow);
            }
        });
        toolBar.add(label, BorderLayout.WEST);
        toolBar.add(addArtistButton, BorderLayout.EAST);
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
                mainWindow.tablePanel.setSongListModel(mainWindow.songsPanel.songList);
                mainWindow.coversPanel.revalidateWithEmptyList();
                mainWindow.tabsPanel.revalidateWithEmptyList();
                mainWindow.coversPanel.addCoverButton.setEnabled(false);
                mainWindow.songsPanel.addSongButton.setEnabled(true);
                mainWindow.tabsPanel.addTabButton.setEnabled(false);
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
        for (Artist artist : artistList) {
            listModel.addElement(artist.getName());
        }

    }

    private void generateArtistList() {
        if(Objects.equals(sortStatus, "Down")){
            artistList = mainWindow.artistDao.findAllArtistsOrderByName();
        }else {
            artistList = mainWindow.artistDao.findAllArtistsOrderByNameDesc();
        }

        setAllArtistsArtistAndUnknownArtistInFirstPlace();
    }

    private void setAllArtistsArtistAndUnknownArtistInFirstPlace() {
        List<Artist> unknownArtist = mainWindow.artistDao.findArtistsWithName("Unknown Artist");
        List<Artist> allArtistsArtist = mainWindow.artistDao.findArtistsWithName("All Artists");
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
                revalidateAllPanels();
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

    public void setSelectedArtist(Artist artistToSelect) {
        System.out.println("Artist to select" + artistToSelect.getName());
        int i = 0;
        for (Artist artist : artistList) {
            System.out.println("Artist Name: " + artist.getName());
            if(Objects.equals(artist.getName(), artistToSelect.getName())){
                jList.setSelectedIndex(i);
                return;
            }
            i++;
        }
    }
}
