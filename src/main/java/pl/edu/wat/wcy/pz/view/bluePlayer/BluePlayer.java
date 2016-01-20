package pl.edu.wat.wcy.pz.view.bluePlayer;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import pl.edu.wat.wcy.pz.view.TablePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class BluePlayer extends JFrame {
    private TablePanel tablePanel;


    public int windowWidth = 600;
    public int windowHeight = 500;

    private JPanel buttonsPanel;
    private JButton play;
    private JButton pause;
    private JButton stop;

    private AudioPlayer player;

    public String songPath;


    private SeekBar seekbar;

    float currentAudioDurationSec = 0;


    WaveformParallelPanel wff = null;
    FFTParallelPanel fdf = null;


    private JSplitPane verticalSplitPane;


    private int index = 0;
    public BluePlayer(String title, TablePanel tablePanel) throws HeadlessException, BasicPlayerException {
        super(title);
        this.tablePanel = tablePanel;
        setLayout(new BorderLayout());
        setWindowValues();
        setIcon();
        addOnExitWindowBehavior();

        generatePlayer();

        generateWaveformPanel();
        generateFFTParallelPanel();
        generateButtonsPanel();


        generateVerticalSplitPane();
        add(verticalSplitPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);



        getRootPane().setDefaultButton(play);
        setVisible(true);
    }

    private void generateVerticalSplitPane() {
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, wff, fdf);
        verticalSplitPane.setContinuousLayout(true);
        verticalSplitPane.setOneTouchExpandable(true);
        verticalSplitPane.setDividerLocation(200);
    }

    public void startPlay(String songPath) {
        this.songPath = songPath;

        player.addSong(songPath);
        player.setIndexSong(index);
        try {
            player.stop();
            player.play();
        } catch (BasicPlayerException e) {
            e.printStackTrace();
        }
        player.setLastSeekPositionInMs(0);
        index++;
    }

    private void generatePlayer() throws BasicPlayerException {
        player = AudioPlayer.getInstance();
        player.addBasicPlayerListener(new BasicPlayerListener() {
            @Override
            public void stateUpdated(BasicPlayerEvent event) {

            }

            @Override
            public void setController(BasicController arg0) {

            }

            @Override
            public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
                seekbar.updateSeekBar(player.getProgressMicroseconds(), currentAudioDurationSec);
                if(wff != null)
                    wff.updateWave(pcmdata);
                if(fdf != null)
                    fdf.updateWave(pcmdata);
            }

            @Override
            public void opened(Object arg0, Map arg1) {
                currentAudioDurationSec = player.getAudioDurationSeconds();
            }
        });
    }

    private void generateButtonsPanel() {
        buttonsPanel = new JPanel(new BorderLayout());

        generateStopButton();
        buttonsPanel.add(stop, BorderLayout.WEST);

        generatePauseButton();
        buttonsPanel.add(pause, BorderLayout.EAST);

        generatePlayButton();
        buttonsPanel.add(play, BorderLayout.CENTER);

        generateSeekBar();
        buttonsPanel.add(seekbar, BorderLayout.NORTH);
    }

    private void generateSeekBar() {
        seekbar = new SeekBar();
        seekbar.setBounds(5, 10, 600-15, 10);
    }

    private void generateStopButton() {
        stop = new JButton();
        stop.setIcon(new ImageIcon("src/images/stopIcon.png"));
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!player.isPaused()){
                        player.stop();
                    }
                    else{
                        player.stop();
                        player.addSong(songPath);
                        player.play();
                    }
                } catch (BasicPlayerException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void generatePauseButton() {
        pause = new JButton();
        pause.setIcon(new ImageIcon("src/images/pauseIcon.png"));
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playClicked();
                } catch (BasicPlayerException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void pauseClicked() {

    }

    private void generatePlayButton() {
        play = new JButton();
        play.setIcon(new ImageIcon("src/images/playIcon.png"));
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    playClicked();
                } catch (BasicPlayerException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void playClicked() throws BasicPlayerException {
//        if(!player.isPaused()){
//            player.pause();
//        }
//        else{player.play();}



        if(player.isStoped){
            player.play();
        }else{
            if(!player.isPaused()){
                player.pause();
            }
            else{
                player.play();
            }
        }
    }

    private void generateFFTParallelPanel() {
        fdf = new FFTParallelPanel();
        fdf.setVisible(true);
       // wff.setPreferredSize(new Dimension(windowWidth,windowHeight-400));
    }

    private void generateWaveformPanel() {
        wff = new WaveformParallelPanel();
        //wff.setPreferredSize(new Dimension(windowWidth,windowHeight-400));
        wff.setVisible(true);
    }

    private void setIcon() {
        ImageIcon img = new ImageIcon("src/images/bluePlayerIcon.png");
        setIconImage(img.getImage());
    }

    private void setWindowValues() {
        setSize(windowWidth, windowHeight);
        centerWindow();
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    public void addOnExitWindowBehavior(){
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    tablePanel.isBluePlayerOpen = false;
                    player.stop();
                } catch (BasicPlayerException e) {
                    e.printStackTrace();
                }
                //System.exit(0);
            }
        });
    }
}
