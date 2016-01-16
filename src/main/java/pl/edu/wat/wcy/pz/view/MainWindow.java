package pl.edu.wat.wcy.pz.view;

import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static MainWindow instance = null;

    public int mainWindowWidth = 700;
    public int mainWindowHeight = 600;

    private MenuBar menuBar;

    public SearchToolBar searchToolBar;
    public ArtistsPanel artistsPanel;
    public SongsPanel songsPanel;
    public TablePanel tablePanel;

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
        setIcon();
        setLookAndFeel();
        setMainWindowValues();
        setMainWindowLayout();

        generateMenuBar();
        setJMenuBar(menuBar);

        generateToolBarsPanel();
        getContentPane().add(toolBarsPanel, BorderLayout.NORTH);

        generateArtistsPanel();
        generateSonsPanel();
        generateTablePanel();

        generateHorizontalSplitPane();
        generateVerticalSplitPane();

        getContentPane().add(verticalSplitPane, BorderLayout.CENTER);
    }

    private void setIcon() {
        ImageIcon img = new ImageIcon("src/images/guitarIcon.png");
        setIconImage(img.getImage());
    }

    private void generateToolBarsPanel() {
        toolBarsPanel = new JPanel();
        toolBarsPanel.setLayout(new BorderLayout());

        generateSearchToolBar();
        toolBarsPanel.add(searchToolBar, BorderLayout.SOUTH);
    }

    private void generateVerticalSplitPane() {
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, tablePanel);
        verticalSplitPane.setContinuousLayout(true);
        verticalSplitPane.setOneTouchExpandable(true);
        verticalSplitPane.setDividerLocation(200);
    }

    private void generateTablePanel() {
        tablePanel = new TablePanel();
    }

    private void generateHorizontalSplitPane() {
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artistsPanel, songsPanel);
        horizontalSplitPane.setContinuousLayout(true);
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
        searchToolBar = new SearchToolBar("Search ToolBar", this);
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

            UIManager.removeAuxiliaryLookAndFeel(UIManager.getLookAndFeel());
            SyntheticaLookAndFeel.setWindowsDecorated(false);
            UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);

        } catch (Exception ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateMenuBar() {
        menuBar = new MenuBar(this);
    }
}
