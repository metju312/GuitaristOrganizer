package pl.edu.wat.wcy.pz.controller;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MP3Player {
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;

    public Player player;

    public long pauseLocation;
    public long songTotalLength;
    public String songPath;

    public void play(String path){
        try {
            fileInputStream = new FileInputStream(path);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            player = new Player(bufferedInputStream);
            songTotalLength = fileInputStream.available();

            songPath = path + "";
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run(){
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void stop(){
        if(player != null){
            player.close();
            pauseLocation = 0;
            songTotalLength = 0;
        }
    }

    public void pause(){
        if(player != null){
            try {
                pauseLocation = fileInputStream.available();
                player.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void resume(){
        try {
            fileInputStream = new FileInputStream(songPath);
            bufferedInputStream = new BufferedInputStream(fileInputStream);

            player = new Player(bufferedInputStream);
            songTotalLength = fileInputStream.available();

            if (pauseLocation != 0) {
                fileInputStream.skip(songTotalLength - pauseLocation);
            }
        } catch (JavaLayerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run(){
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
