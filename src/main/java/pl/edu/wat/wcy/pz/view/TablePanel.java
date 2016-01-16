package pl.edu.wat.wcy.pz.view;


import javazoom.jlgui.basicplayer.BasicPlayerException;
import pl.edu.wat.wcy.pz.model.entities.music.Song;
import pl.edu.wat.wcy.pz.view.bluePlayer.BluePlayer;
import pl.edu.wat.wcy.pz.view.tables.SongsTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class TablePanel extends JPanel implements ActionListener {

    private JScrollPane listScroller;
    private JToolBar toolBar;
    private JTable table;
    private JPopupMenu popupMenu;

    private JMenuItem play;
    private JMenuItem wmpPlay;
    private JMenuItem open;
    private JMenuItem selectArtist;
    private JMenuItem webSearch;

    private BluePlayer bluePlayer;
    public boolean isBluePlayerOpen = false;

    private java.util.List<Song> actualSongsList;
    private int selectedRow;

    public TablePanel() {
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
                String path = actualSongsList.get(selectedRow).getPath();
                try {
                    Desktop.getDesktop().open(new File(path));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        open = new JMenuItem("Open Folder");
        open.setIcon(new ImageIcon("src/images/folderIcon.png"));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = actualSongsList.get(selectedRow).getPath();
                try {
                    Desktop.getDesktop().open(new File(path).getParentFile());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        selectArtist = new JMenuItem("Select Artist");
        selectArtist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        webSearch = new JMenuItem("Web Search");
        webSearch.setIcon(new ImageIcon("src/images/websiteIcon.png"));
        webSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void playClicked() {
        if(!isBluePlayerOpen){
            try {
                bluePlayer = new BluePlayer("Blue BluePlayer", this);
                bluePlayer.startPlay(actualSongsList.get(selectedRow).getPath());
                isBluePlayerOpen = true;
            } catch (BasicPlayerException e1) {
                e1.printStackTrace();
            }
        }else{
            bluePlayer.startPlay(actualSongsList.get(selectedRow).getPath());
        }
    }


    private void generatePopMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.add(play);
        popupMenu.add(wmpPlay);
        popupMenu.addSeparator();
        popupMenu.add(open);
        popupMenu.add(selectArtist);
        popupMenu.addSeparator();
        popupMenu.add(webSearch);
    }

    private void generateTable() {
        table = new JTable();
        table.setAutoCreateRowSorter(true);
    }

    private void generateAndAddToolBar() {
        toolBar = new JToolBar("Still draggable");
        JLabel label = new JLabel("Songs");
        toolBar.add(label);
        toolBar.setFloatable(false);
        add(toolBar, BorderLayout.NORTH);
    }

    private void generateAndAddListScroller() {
        listScroller = new JScrollPane(table);
        //listScroller.setPreferredSize(new Dimension(100, 200));
        add(listScroller);
    }

    public void setSongsListModel(java.util.List<Song> songs){
        actualSongsList = songs;
        table.setModel(new SongsTableModel(actualSongsList));
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());
                    selectedRow = row;
                    if (!source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component c = (Component)e.getSource();
        JPopupMenu popup = (JPopupMenu)c.getParent();
        JTable table = (JTable)popup.getInvoker();
        System.out.println(table.getSelectedRow() + " : " + table.getSelectedColumn());
    }
}
