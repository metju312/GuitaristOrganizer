package pl.edu.wat.wcy.pz.view;

import com.sun.glass.events.KeyEvent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame{
    private static MainWindow instance = null;

    public int mainWindowWidth = 500;
    public int mainWindowHeight = 500;

    private MenuBar menuBar;

    private SearchToolBar searchToolBar;
    public ArtistsPanel artistsPanel;
    public SongsPanel songsPanel;
    private ListPanel listPanel;

    private JSplitPane horizontalSplitPane;
    private JSplitPane verticalSplitPane;

    private JPanel toolBarsPanel;

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

        generateMenuBar();
        setJMenuBar(menuBar);

        generateToolBarsPanel();
        getContentPane().add(toolBarsPanel, BorderLayout.NORTH);

        generateArtistsPanel();
        generateSonsPanel();
        generateListPanel();

        generateHorizontalSplitPane();
        generateVerticalSplitPane();

        getContentPane().add(verticalSplitPane, BorderLayout.CENTER);
    }

    private void generateToolBarsPanel() {
        toolBarsPanel = new JPanel();
        toolBarsPanel.setLayout(new BorderLayout());

        generateSearchToolBar();
        toolBarsPanel.add(searchToolBar, BorderLayout.SOUTH);
    }

    private void generateVerticalSplitPane() {
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, listPanel);
        verticalSplitPane.setOneTouchExpandable(true);
        verticalSplitPane.setDividerLocation(200);
    }

    private void generateListPanel() {
        listPanel = new ListPanel();
    }

    private void generateHorizontalSplitPane() {
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artistsPanel, songsPanel);
        horizontalSplitPane.setOneTouchExpandable(true);
        horizontalSplitPane.setDividerLocation(200);

    }

    private void generateSonsPanel() {
        songsPanel = new SongsPanel(this);
    }

    private void generateArtistsPanel() {
        artistsPanel = new ArtistsPanel(this);
    }

    private void generateSearchToolBar() {
        searchToolBar = new SearchToolBar("Search ToolBar");
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
        setLayout(new BorderLayout());
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

    private void generateMenuBar() {
        menuBar = new MenuBar(this);
    }
}
