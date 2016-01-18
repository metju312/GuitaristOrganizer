package pl.edu.wat.wcy.pz.view;

import de.javasoft.plaf.synthetica.*;
import pl.edu.wat.wcy.pz.model.dao.*;
import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.view.bluePlayer.BluePlayer;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static MainWindow instance = null;
    public ArtistDao artistDao;
    public CoverDao coverDao;
    public FolderDao folderDao;
    public SongDao songDao;
    public TabDao tabDao;
    public UserDao userDao;
    public WebsiteDao websiteDao;

    public int mainWindowWidth = 1000;
    public int mainWindowHeight = 700;

    private MenuBar menuBar;

    public SearchToolBar searchToolBar;
    public ArtistsPanel artistsPanel;
    public SongsPanel songsPanel;
    public CoversPanel coversPanel;
    public TabsPanel tabsPanel;
    public TablePanel tablePanel;

    private JSplitPane horizontalSplitPaneArtistsAndSongs;
    private JSplitPane horizontalSplitPaneCoversAndTabs;
    private JSplitPane horizontalSplitPaneOf2SplitPanes;
    private JSplitPane verticalSplitPane;

    private JPanel toolBarsPanel;

    public BluePlayer bluePlayer;

    public User actualUser;

    public SettingsDialog settingsDialog;

    private JPanel artistsAndSongsPanel;
    private JPanel coversAndTabsPanel;

    public String[] lafTitles = {"Alu Oxide"
            , "Black Eye"
            , "Black Moon"
            , "Black Star"
            , "Blue Ice"
            , "Blue Light"
            , "Blue Steel"
            , "Classy"
            , "Green Dream"
            , "Mauve Metallic"
            , "Orange Metallic"
            , "Plain"
            , "Silver Moon"
            , "Simple 2D"
            , "Sky Metallic"
            , "White Vision"
    };

    public static MainWindow getInstance(User user) {
        if (instance == null) {
            instance = new MainWindow(user);
        }
        return instance;
    }

    private MainWindow(User user) {
        super("Guitarist Organizer");
        this.actualUser = user;
        generateDaos();
        setIcon();
        setLookAndFeel(user.getLafIndex());
        setMainWindowValues();
        setMainWindowLayout();

        generateMenuBar();
        setJMenuBar(menuBar);

        generateToolBarsPanel();
        getContentPane().add(toolBarsPanel, BorderLayout.NORTH);

        generateArtistsPanel();
        generateSonsPanel();
        generateCoversPanel();
        generateTabsPanel();
        generateTablePanel();

        generateHorizontalSplitPaneArtistsAndSongs();
        generateHorizontalSplitPaneCoversAndTabs();
        generateHorizontalSplitPaneOf2SplitPanes();

        generateArtistsAndSongsPanel();
        generateCoversAndTabsPanel();

        generateVerticalSplitPane();

        getContentPane().add(verticalSplitPane, BorderLayout.CENTER);
    }

    private void generateHorizontalSplitPaneOf2SplitPanes() {
        horizontalSplitPaneOf2SplitPanes = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, horizontalSplitPaneArtistsAndSongs, horizontalSplitPaneCoversAndTabs);
        horizontalSplitPaneOf2SplitPanes.setContinuousLayout(true);
        horizontalSplitPaneOf2SplitPanes.setOneTouchExpandable(true);
        horizontalSplitPaneOf2SplitPanes.setDividerLocation(500);
    }

    private void generateCoversAndTabsPanel() {
        coversAndTabsPanel = new JPanel(new BorderLayout());
    }

    private void generateArtistsAndSongsPanel() {
        artistsAndSongsPanel = new JPanel(new BorderLayout());
    }

    private void generateTabsPanel() {
        tabsPanel = new TabsPanel(this);
    }

    private void generateCoversPanel() {
        coversPanel = new CoversPanel(this);
    }

    private void generateDaos() {
        artistDao = new ArtistDao(actualUser);
        coverDao = new CoverDao(actualUser);
        folderDao = new FolderDao(actualUser);
        songDao = new SongDao(actualUser);
        tabDao = new TabDao(actualUser);
        userDao = new UserDao();
        websiteDao = new WebsiteDao(actualUser);
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
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPaneOf2SplitPanes, tablePanel);
        verticalSplitPane.setContinuousLayout(true);
        verticalSplitPane.setOneTouchExpandable(true);
        verticalSplitPane.setDividerLocation(300);
    }

    private void generateTablePanel() {
        tablePanel = new TablePanel(this);
    }

    private void generateHorizontalSplitPaneArtistsAndSongs() {
        horizontalSplitPaneArtistsAndSongs = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artistsPanel, songsPanel);
        horizontalSplitPaneArtistsAndSongs.setContinuousLayout(true);
        horizontalSplitPaneArtistsAndSongs.setOneTouchExpandable(true);
        horizontalSplitPaneArtistsAndSongs.setDividerLocation(250);

    }

    private void generateHorizontalSplitPaneCoversAndTabs() {
        horizontalSplitPaneCoversAndTabs = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, coversPanel, tabsPanel);
        horizontalSplitPaneCoversAndTabs.setContinuousLayout(true);
        horizontalSplitPaneCoversAndTabs.setOneTouchExpandable(true);
        horizontalSplitPaneCoversAndTabs.setDividerLocation(250);

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

    private void generateMenuBar() {
        menuBar = new MenuBar(this);
    }

    public void setLookAndFeel(int index) {

        try {
            UIManager.removeAuxiliaryLookAndFeel(UIManager.getLookAndFeel());
            SyntheticaLookAndFeel.setWindowsDecorated(false);

            switch (index) {
                case 0:
                    UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
                    break;
                case 1:
                    UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
                    break;
                case 2:
                    UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
                    break;
                case 3:
                    UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
                    break;
                case 4:
                    UIManager.setLookAndFeel(new SyntheticaBlueIceLookAndFeel());
                    break;
                case 5:
                    UIManager.setLookAndFeel(new SyntheticaBlueLightLookAndFeel());
                    break;
                case 6:
                    UIManager.setLookAndFeel(new SyntheticaBlueSteelLookAndFeel());
                    break;
                case 7:
                    UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
                    break;
                case 8:
                    UIManager.setLookAndFeel(new SyntheticaGreenDreamLookAndFeel());
                    break;
                case 9:
                    UIManager.setLookAndFeel(new SyntheticaMauveMetallicLookAndFeel());
                    break;
                case 10:
                    UIManager.setLookAndFeel(new SyntheticaOrangeMetallicLookAndFeel());
                    break;
                case 11:
                    UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
                    break;
                case 12:
                    UIManager.setLookAndFeel(new SyntheticaSilverMoonLookAndFeel());
                    break;
                case 13:
                    UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
                    break;
                case 14:
                    UIManager.setLookAndFeel(new SyntheticaSkyMetallicLookAndFeel());
                    break;
                case 15:
                    UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());
                    break;
            }
            SwingUtilities.updateComponentTreeUI(this);
            if(settingsDialog != null){
                SwingUtilities.updateComponentTreeUI(settingsDialog);
            }
            if(bluePlayer != null){
                SwingUtilities.updateComponentTreeUI(bluePlayer);
            }

        } catch (Exception ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
