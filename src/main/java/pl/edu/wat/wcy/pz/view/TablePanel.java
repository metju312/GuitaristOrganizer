package pl.edu.wat.wcy.pz.view;


import javazoom.jlgui.basicplayer.BasicPlayerException;
import pl.edu.wat.wcy.pz.controller.BrowserSearcher;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Song;
import pl.edu.wat.wcy.pz.model.entities.music.Tab;
import pl.edu.wat.wcy.pz.view.bluePlayer.BluePlayer;
import pl.edu.wat.wcy.pz.view.tables.CoversTableModel;
import pl.edu.wat.wcy.pz.view.tables.SongsTableModel;
import pl.edu.wat.wcy.pz.view.tables.TabsTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TablePanel extends JPanel implements ActionListener {
    private MainWindow mainWindow;
    private BrowserSearcher browserSearcher = new BrowserSearcher(mainWindow);

    private JScrollPane listScroller;
    private JToolBar toolBar;

    private JTable table;

    public JLabel toolBarLabel;


    private JPopupMenu popupMenu;
    private JMenuItem play;
    private JMenuItem wmpPlay;
    private JMenuItem open;
    private JMenuItem webSearch;
    private JMenuItem urlSearch;

    public String urlToOpen;
    public String titleToWebSearch;
    public String pathToOpen;

    private BluePlayer bluePlayer;
    public boolean isBluePlayerOpen = false;

    private List<Song> actualSongList;
    private List<Cover> actualCoverList;
    private List<Tab> actualTabList;
    private int selectedRow;

    public int typeOfTable = 0; //0 -empty //1songs //2covers //3tabs

    public TablePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        generateMenuItems();
        generatePopMenu();
        generateTable();
        generateAndAddToolBar();
        generateAndAddListScroller();
    }

    private void generateMenuItems() {
        play = new JMenuItem("BluePlayer");
        play.setIcon(new ImageIcon("src/images/bluePlayerIcon20.png"));


        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playClicked();
            }
        });

        wmpPlay = new JMenuItem("Windows Media Player");
        wmpPlay.setIcon(new ImageIcon("src/images/wmpIcon.png"));
        wmpPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(pathToOpen));
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(wmpPlay, "Wrong song path.");
                }
            }
        });

        open = new JMenuItem("Open Folder");
        open.setIcon(new ImageIcon("src/images/folderIcon.png"));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(pathToOpen).getParentFile());
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(open, "Wrong path.");
                }
            }
        });

        webSearch = new JMenuItem("Web Search");
        webSearch.setIcon(new ImageIcon("src/images/websiteIcon.png"));
        webSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.searchToolBar.searchTitleInWeb(titleToWebSearch);
            }
        });

        urlSearch = new JMenuItem("Url Search");
        urlSearch.setIcon(new ImageIcon("src/images/urlIcon.png"));
        urlSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browserSearcher.openUrl(urlToOpen);
            }
        });
    }

    private void playClicked() {
        if(!isBluePlayerOpen){
            try {
                bluePlayer = new BluePlayer("Blue BluePlayer", this);
                bluePlayer.startPlay(pathToOpen);
                isBluePlayerOpen = true;
            } catch (BasicPlayerException e1) {
                e1.printStackTrace();
            }
        }else{
            bluePlayer.startPlay(pathToOpen);
        }
        mainWindow.bluePlayer = bluePlayer;
    }


    private void generatePopMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.add(play);
        popupMenu.add(wmpPlay);
        popupMenu.addSeparator();
        popupMenu.add(open);
        popupMenu.addSeparator();
        popupMenu.add(webSearch);
        popupMenu.add(urlSearch);
    }

    private void generateAndAddToolBar() {
        toolBar = new JToolBar("Still draggable");
        toolBarLabel = new JLabel("Songs");
        toolBar.add(toolBarLabel);
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);
    }

    private void generateAndAddListScroller() {
        listScroller = new JScrollPane(table);
        //listScroller.setPreferredSize(new Dimension(100, 200));
        add(listScroller);
    }
    private void generateTable() {
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());
                    selectedRow = row;

                    if(typeOfTable == 1){
                        urlToOpen = actualSongList.get(row).getUrl();
                        titleToWebSearch = actualSongList.get(row).getTitle();
                        pathToOpen = actualSongList.get(row).getPath();
                    }else if(typeOfTable == 2){
                        urlToOpen = actualCoverList.get(row).getUrl();
                        titleToWebSearch = actualCoverList.get(row).getTitle();
                        pathToOpen = actualCoverList.get(row).getPath();
                    }else if(typeOfTable ==3){
                        urlToOpen = actualTabList.get(row).getUrl();
                        titleToWebSearch = actualTabList.get(row).getTitle();
                        pathToOpen = actualTabList.get(row).getPath();

                    }

                    if (!source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public void setSongListModel(List<Song> songs){
        actualSongList = songs;
        table.setModel(new SongsTableModel(songs));
    }

    public void setCoverListModel(List<Cover> covers){
        actualCoverList = covers;
        table.setModel(new CoversTableModel(covers));
    }

    public void setTabListModel(List<Tab> tabs){
        actualTabList = tabs;
        table.setModel(new TabsTableModel(tabs));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component c = (Component)e.getSource();
        JPopupMenu popup = (JPopupMenu)c.getParent();
        JTable table = (JTable)popup.getInvoker();
        System.out.println(table.getSelectedRow() + " : " + table.getSelectedColumn());
    }

    public void revalidateMeWithEmptyModel() {
          table.setModel(new DefaultTableModel());
//        List<Song> list = new ArrayList<Song>();
//        setSongListModel(list);
    }
}
