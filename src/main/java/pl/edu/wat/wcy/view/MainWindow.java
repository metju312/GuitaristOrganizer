package pl.edu.wat.wcy.view;

import com.sun.glass.events.KeyEvent;
import net.miginfocom.swing.MigLayout;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame{
    private static MainWindow instance = null;

    public int mainWindowWidth = 500;
    public int mainWindowHeight = 500;

    public MP3Player mp3Player;

    private String songPath;
    private String songName;



    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {
        super("Guitarist Organizer");
        setLookAndFeel();
        setMainWindowValues();
        setMainWindowLayout();

        add(generateMainPanel());

        createMenuBar();
    }

    private JPanel generateMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        JLabel songNameLabel = new JLabel("Actual song: ");
        JButton play = new JButton("Play");
        play.setEnabled(false);
        JButton stop = new JButton("Stop");
        JButton pause = new JButton("Pause");


        JButton choseFile = new JButton("ChoseFile");

        mp3Player = new MP3Player();


        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mp3Player.resume();
                play.setEnabled(false);
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mp3Player.stop();
                play.setEnabled(true);
            }
        });

        choseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mp3Player.stop();
                generateFileChooserWindow();
                mp3Player.play(songPath);
                songNameLabel.setText("Actual song: " + songName);
                play.setEnabled(false);
            }
        });

        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mp3Player.pause();
                play.setEnabled(true);
            }
        });

        panel.add(songNameLabel);
        panel.add(play);
        panel.add(stop);
        panel.add(pause, "wrap");
        panel.add(choseFile, "span");
        return panel;
    }

    private void generateFileChooserWindow() {
        FileFilter fileFilter = new FileNameExtensionFilter("MP3 Files", "mp3");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.showOpenDialog(null);
        songPath = fileChooser.getSelectedFile().getAbsolutePath() + "";
        songName = fileChooser.getSelectedFile().getName();
    }

    private void createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        final JMenu aboutMenu = new JMenu("About");
        final JMenu linkMenu = new JMenu("Links");

        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setActionCommand("New");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setActionCommand("Cut");

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setActionCommand("Copy");

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setActionCommand("Paste");

        MenuItemListener menuItemListener = new MenuItemListener();

        newMenuItem.addActionListener(menuItemListener);
        openMenuItem.addActionListener(menuItemListener);
        saveMenuItem.addActionListener(menuItemListener);
        exitMenuItem.addActionListener(menuItemListener);
        cutMenuItem.addActionListener(menuItemListener);
        copyMenuItem.addActionListener(menuItemListener);
        pasteMenuItem.addActionListener(menuItemListener);

        final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem("Show About", true);
        showWindowMenu.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(showWindowMenu.getState()){
                    menuBar.add(aboutMenu);
                }else{
                    menuBar.remove(aboutMenu);
                }
            }
        });

        final JRadioButtonMenuItem showLinksMenu =
                new JRadioButtonMenuItem("Show Links", true);
        showLinksMenu.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(menuBar.getMenu(3)!= null){
                    menuBar.remove(linkMenu);
                    repaint();
                }else{
                    menuBar.add(linkMenu);
                    repaint();
                }
            }
        });

        //add menu items to menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(showWindowMenu);
        fileMenu.addSeparator();
        fileMenu.add(showLinksMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);
        menuBar.add(linkMenu);

        setJMenuBar(menuBar);
    }

    private void setMainWindowValues() {
        setSize(mainWindowWidth, mainWindowHeight);
        centerWindow();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    private void setMainWindowLayout() {
        setLayout(new MigLayout());
    }

    private void setLookAndFeel() {
        try {
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
