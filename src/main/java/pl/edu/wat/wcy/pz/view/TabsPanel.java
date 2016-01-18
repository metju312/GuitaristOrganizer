package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Song;
import pl.edu.wat.wcy.pz.model.entities.music.Tab;

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

public class TabsPanel extends JPanel {
    private MainWindow mainWindow;

    private DefaultListModel listModel;
    public List<Tab> tabList;
    private JList jList;
    private JScrollPane listScrollPane;
    private Cover actualCover;
    private JToolBar toolBar;
    private JButton sortButton;
    private String sortStatus = "Down";
    public JButton addTabButton;
    private int selectedRow;
    private JPopupMenu popupMenu;
    private JMenuItem updateMenuItem;
    private JMenuItem deleteMenuItem;

    public TabsPanel(MainWindow mainWindow) {
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
                new UpdateTabDialog(mainWindow, tabList.get(selectedRow));
            }
        });

        deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.setIcon(new ImageIcon("src/images/cancelIcon.png"));
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    mainWindow.tabDao.delete(tabList.get(selectedRow).getId());
                    if (tabList.get(selectedRow) != null) {
                        mainWindow.tabDao.delete(tabList.get(selectedRow).getId());
                    }
                    revalidateMeWithActualCover();
                }
            }
        });
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
                setTabListWithCover(actualCover.getTitle());
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
        generateTabList();
        generateListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generateRestOfComponentsWithEmptyList() {
        generateTabList();
        generateEmptyListModel();
        generateJList();
        generateListScroller();
        add(listScrollPane);
    }

    private void generateToolBar() {
        toolBar = new JToolBar("Tabs ToolBar");
        toolBar.setLayout(new MigLayout());
        JLabel label = new JLabel("Tabs");
        addTabButton = new JButton();
        addTabButton.setEnabled(false);

        Image img = null;
        try {
            img = ImageIO.read(new File("src/images/plusIcon14.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addTabButton.setIcon(new ImageIcon(img));
        addTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTabDialog(mainWindow, actualCover);
            }
        });
        toolBar.add(label, BorderLayout.WEST);
        toolBar.add(addTabButton, BorderLayout.EAST);
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
                //TODO change tabs
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
        for (Tab tab : tabList) {
            listModel.addElement(tab.getTitle());
        }
    }

    private void generateEmptyListModel() {
        listModel = new DefaultListModel();
    }

    private void generateTabList() {
        if(Objects.equals(sortStatus, "Down")){
            tabList = mainWindow.tabDao.findAllTabsOrderByTitle();
        }else {
            tabList = mainWindow.tabDao.findAllTabsOrderByTitleDesc();
        }
    }

    public void revalidateMe() {
        remove(listScrollPane);
        generateRestOfComponents();
        revalidate();
        repaint();
    }

    public void setTabListWithCover(String chosenCoverTitle) {
        List<Cover> list = mainWindow.coverDao.findAllCoversWithTitleOrderByTitle(chosenCoverTitle);
        if(Objects.equals(sortStatus, "Down")){
            tabList = mainWindow.tabDao.findTabsWithCoverOrderByTitle(list.get(0));
        }else {
            tabList = mainWindow.tabDao.findTabsWithCoverOrderByTitleDesc(list.get(0));
        }
        actualCover = list.get(0);

        generateListModel();
        jList.setModel(listModel);
    }

    public void revalidateWithEmptyList() {
        remove(listScrollPane);
        generateRestOfComponentsWithEmptyList();
        revalidate();
        repaint();
    }

    public void revalidateMeWithActualCover() {
        setTabListWithCover(actualCover.getTitle());
    }

    public void deleteAllTabsWithCover(Cover coverToDelete) {
        List<Tab> tabsToDelete = mainWindow.tabDao.findTabsWithCoverOrderByTitle(coverToDelete);

        for (Tab tab : tabsToDelete) {
            mainWindow.tabDao.delete(tab);
        }
    }
}
