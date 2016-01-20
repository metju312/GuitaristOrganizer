package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
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

public class CoversPanel extends JPanel {
    private MainWindow mainWindow;

    private DefaultListModel listModel;
    public List<Cover> coverList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Song actualSong;
    private JToolBar toolBar;
    private JButton sortButton;
    private String sortStatus = "Down";
    public JButton addCoverButton;
    private int selectedRow;
    private JPopupMenu popupMenu;
    private JMenuItem updateMenuItem;
    private JMenuItem deleteMenuItem;

    public CoversPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());

        generateToolBar();
        generateSortButton();
        add(toolBar, BorderLayout.NORTH);

        generateRestOfComponentsWithEmptyList();

        generateMenuItems();
        generatePopMenu();
    }

    private void generateMenuItems() {
        updateMenuItem = new JMenuItem("Update");
        updateMenuItem.setIcon(new ImageIcon("src/images/updateIcon.png"));
        updateMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateCoverDialog(mainWindow, coverList.get(selectedRow));
            }
        });

        deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.setIcon(new ImageIcon("src/images/cancelIcon.png"));
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    mainWindow.tabsPanel.deleteAllTabsWithCover(coverList.get(selectedRow));
                    mainWindow.coverDao.delete(coverList.get(selectedRow).getId());
                    if (coverList.get(selectedRow) != null) {
                        mainWindow.coverDao.delete(coverList.get(selectedRow).getId());
                    }
                    revalidateAllPanels();
                    revalidateMeWithActualSong();
                }
            }
        });
    }

    public void revalidateAllPanels() {
        mainWindow.tablePanel.revalidateMeWithEmptyModel();
        mainWindow.tabsPanel.revalidateWithEmptyList();
        mainWindow.tabsPanel.addTabButton.setEnabled(false);
    }

    private void generatePopMenu() {
        popupMenu = new JPopupMenu();
        popupMenu.add(updateMenuItem);
        popupMenu.add(deleteMenuItem);
    }

    private void generateSortButton() {
        sortButton = new JButton();
        setImageIcon();
        sortButton.setPreferredSize(new Dimension(22,22));
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flipAndDrawArrowIcon();
                setCoversListWithSong(actualSong.getTitle());
                revalidateAllPanels();
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
        generateCoverList();
        generateListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generateRestOfComponentsWithEmptyList() {
        generateCoverList();
        generateEmptyListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Covers ToolBar");
        toolBar.setLayout(new MigLayout());
        JLabel label = new JLabel("Covers");
        addCoverButton = new JButton();
        addCoverButton.setEnabled(false);

        Image img = null;
        try {
            img = ImageIO.read(new File("src/images/plusIcon14.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addCoverButton.setIcon(new ImageIcon(img));
        addCoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCoverDialog(mainWindow, actualSong);
            }
        });
        toolBar.add(label, BorderLayout.WEST);
        toolBar.add(addCoverButton, BorderLayout.EAST);
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
                mainWindow.tabsPanel.setTabListWithCover(jList.getSelectedValue().toString());
                mainWindow.tablePanel.setTabListModel(mainWindow.tabsPanel.tabList);
                mainWindow.tabsPanel.addTabButton.setEnabled(true);

                mainWindow.tablePanel.typeOfTable = 3;
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
        for (Cover cover : coverList) {
            listModel.addElement(cover.getTitle());
        }
    }

    private void generateEmptyListModel() {
        listModel = new DefaultListModel();
    }

    private void generateCoverList() {
        if(Objects.equals(sortStatus, "Down")){
            coverList = mainWindow.coverDao.findAllCoversOrderByTitle();
        }else {
            coverList = mainWindow.coverDao.findAllCoversOrderByTitleDesc();
        }
    }

    public void revalidateMe() {
        remove(listScrollPane);
        generateRestOfComponents();
        revalidate();
        repaint();
    }

    public void setCoversListWithSong(String chosenSongTitle) {
        List<Song> list = mainWindow.songDao.findSongsWithTitle(chosenSongTitle);
        if(Objects.equals(sortStatus, "Down")){
            coverList = mainWindow.coverDao.findCoversWithSongOrderByTitle(list.get(0));
        }else {
            coverList = mainWindow.coverDao.findCoversWithSongOrderByTitleDesc(list.get(0));
        }
        actualSong = list.get(0);

        generateListModel();
        jList.setModel(listModel);
    }

    public void revalidateWithEmptyList() {
        remove(listScrollPane);
        generateRestOfComponentsWithEmptyList();
        revalidate();
        repaint();
    }

    public void revalidateMeWithActualSong() {
        revalidateMe();
        setCoversListWithSong(actualSong.getTitle());
    }

    public void deleteAllCoversWithSong(Song songToDelete) {
        List<Cover> coversToDelete = mainWindow.coverDao.findCoversWithSongOrderByTitle(songToDelete);

        for (Cover cover : coversToDelete) {
            mainWindow.tabsPanel.deleteAllTabsWithCover(cover);
            mainWindow.coverDao.delete(cover);
        }
    }
}
