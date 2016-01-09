package pl.edu.wat.wcy.pz.view;

import com.sun.glass.events.KeyEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuBar extends JMenuBar implements ActionListener{
    private MainWindow mainWindow;

    private JMenu databaseMenu;
    private JMenu searchMenu;

    private JMenuItem update;

    public MenuBar(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        generateDatabaseMenuWithMenuItems();
        add(databaseMenu);

        generateSearchMenuWithMenuItems();
        add(searchMenu);


    }

    private void generateSearchMenuWithMenuItems() {
        searchMenu = new JMenu("Search");
        searchMenu.setMnemonic(KeyEvent.VK_S);
    }

    private void generateDatabaseMenuWithMenuItems() {
        databaseMenu = new JMenu("Database");
        databaseMenu.setMnemonic(KeyEvent.VK_D);

        generateUpdateMenuItem();
        databaseMenu.add(update);


        JMenu export = new JMenu("Export");
        export.setMnemonic(KeyEvent.VK_E);

        JMenuItem first = new JMenuItem("first type");
        first.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        export.add(first);

        JMenuItem second = new JMenuItem("second type");
        export.add(second);

        databaseMenu.addSeparator();
        databaseMenu.add(export);
    }

    private void generateUpdateMenuItem() {
        update = new JMenuItem(new AbstractAction("Update action") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateDatabaseDialog(mainWindow);
            }
        });
        update.setText("Update");
        update.setIcon(new ImageIcon("src/images/searchUpdate.png"));
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
