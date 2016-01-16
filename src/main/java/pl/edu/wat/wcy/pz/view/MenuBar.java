package pl.edu.wat.wcy.pz.view;

import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class MenuBar extends JMenuBar implements ActionListener{
    private MainWindow mainWindow;

    private JMenu databaseMenu;
    private JMenu searchMenu;
    private JMenu viewMenu;

    private JMenuItem update;
    private JMenuItem exit;
    private JMenuItem find;
    private JMenuItem settings;
    private JMenuItem websiteSearch;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        generateDatabaseMenuWithMenuItems();
        add(databaseMenu);

        generateSearchMenuWithMenuItems();
        add(searchMenu);

        generateViewMenuWithMenuItems();
        add(viewMenu);
    }

    private void generateViewMenuWithMenuItems() {
        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        generateSettingsMenuItem();
        viewMenu.add(settings);

    }

    private void generateSettingsMenuItem() {
        settings = new JMenuItem(new AbstractAction("Settings action") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO generate settings dialog
            }
        });
        settings.setText("Settings...");
        settings.setIcon(new ImageIcon("src/images/settingsIcon.png"));
        settings.setActionCommand("Settings");
    }

    private void generateSearchMenuWithMenuItems() {
        searchMenu = new JMenu("Search");
        searchMenu.setMnemonic(KeyEvent.VK_S);

        generateFindMenuItem();
        searchMenu.add(find);

        generateWebsiteSearchMenuItem();
        searchMenu.add(websiteSearch);
    }

    private void generateWebsiteSearchMenuItem() {
        websiteSearch = new JMenuItem(new AbstractAction("WebsiteSearch action") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO find
            }
        });
        websiteSearch.setText("Website Search...");
        websiteSearch.setIcon(new ImageIcon("src/images/websiteIcon.png"));
        websiteSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        websiteSearch.setActionCommand("websiteSearch");
    }

    private void generateFindMenuItem() {
        find = new JMenuItem(new AbstractAction("Find action") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO find
            }
        });
        find.setText("Find");
        find.setIcon(new ImageIcon("src/images/searchIcon.png"));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        find.setActionCommand("Find");
    }

    private void generateDatabaseMenuWithMenuItems() {
        databaseMenu = new JMenu("Database");
        databaseMenu.setMnemonic(KeyEvent.VK_D);

        generateUpdateMenuItem();
        databaseMenu.add(update);


        databaseMenu.addSeparator();
        generateExitMenuItem();
        databaseMenu.add(exit);
    }

    private void generateExitMenuItem() {
        exit = new JMenuItem(new AbstractAction("Exit action") {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.dispatchEvent(new WindowEvent(mainWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        exit.setText("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        exit.setActionCommand("Exit");
    }

    private void generateUpdateMenuItem() {
        update = new JMenuItem(new AbstractAction("Update action") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateDatabaseDialog(mainWindow);
            }
        });
        update.setText("Update");
        update.setIcon(new ImageIcon("src/images/updateIcon.png"));
        update.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        update.setActionCommand("Update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == update){
            JOptionPane.showMessageDialog(null, "Selected Item: " + e.getActionCommand());
        }
    }
}
