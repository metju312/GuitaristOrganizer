package pl.edu.wat.wcy.pz.view;

import net.miginfocom.swing.MigLayout;
import pl.edu.wat.wcy.pz.controller.MP3Player;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Metju on 2016-01-06.
 */
public class Mp3Panel {

    public MP3Player mp3Player;

    private String songPath;
    private String songName;

    public JPanel generateMp3Panel() {
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
}
