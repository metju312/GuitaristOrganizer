package pl.edu.wat.wcy.pz.controller;

import javazoom.jlgui.basicplayer.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class AudioPlayer extends BasicPlayer {
	private String songPath;
	
	//Extension of BasicPlayer
	private static AudioPlayer instance = null;
	private int index = 0;
	private boolean paused = true;
	private boolean opened = false;
	private boolean isSeeking = false;
	
	//Current Audio Properties
	private float audioDurationInSeconds = 0;
	private int audioFrameSize = 0;
	private float audioFrameRate = 0;
	//Stream info/status
	private byte[] cpcmdata;
	private long csms = 0; //Current Song microseconds
	private int lastSeekMs = 0; //Every time we seek, basic player returns microseconds are resetted
	//we need a var to mantain the position we seeked to
	
	//Want to use a singleton
	private AudioPlayer() {
		super();
		//Wanna give the AudioPlayer class a basic behaviour
		this.addBasicPlayerListener(new BasicPlayerListener() {
			
			@Override
			public void stateUpdated(BasicPlayerEvent event) {
				if(event.getCode() == BasicPlayerEvent.EOM)
				{
					lastSeekMs = 0;
					paused = true; //reset player state
					opened = false;
				}
				if(event.getCode() == BasicPlayerEvent.SEEKING)
					isSeeking = true;
				if(event.getCode() == BasicPlayerEvent.SEEKED)
					isSeeking = false;
			}
			
			@Override
			public void setController(BasicController arg0) {
				//No need to use this
			}
			
			@Override
			public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
				csms = microseconds;
				cpcmdata = pcmdata;
			}
			
			@Override
			public void opened(Object stream, Map properties) {
				Object[] e = properties.entrySet().toArray();
				Object[] k = properties.keySet().toArray();
				String line = "Stream properties:";
				for(int i = 0; i<properties.size(); i++){
					line += "\n\t" + k[i] + ":" + e[i];
				}
				//Set Audio Properties
				File file = new File(songPath);
			    long audioFileLength = file.length();
				int frameSize = (int) properties.get("mp3.framesize.bytes");
			    float frameRate = (float) properties.get("mp3.framerate.fps");
			    audioFrameSize = frameSize;
			    audioFrameRate = frameRate;
			    audioDurationInSeconds = (audioFileLength / (frameSize * frameRate));
			}
		});
	}
	
	public static AudioPlayer getInstance(){
		if(instance == null)
			instance = new AudioPlayer();
		return instance;
	}
	/////////////////////////////////////
	
	
	@Override
	public void play() throws BasicPlayerException {
		if(!paused || !opened){
			File f = new File(songPath);
			open(f);
			opened = true;
			super.play();
		}
		if(paused)
			super.resume();
		paused = false;
	}
	
	@Override
	public void pause() throws BasicPlayerException {
		paused = true;
		super.pause();
	}
	
	@Override
	public void stop() throws BasicPlayerException {
		paused = false;
		super.stop();
	}
	
	public boolean isPaused(){ return paused; }
	
	public boolean isOpenFile() { return opened; }

	public int getIndexSong(){ return index; }
	
	public void setIndexSong(int index){ this.index = index; lastSeekMs = 0; }
	
	public boolean isSeeking() { return isSeeking; }
	
	public byte[] getPcmData(){return cpcmdata;}
	
	public long getProgressMicroseconds(){return csms+lastSeekMs;}
	
	public float getAudioDurationSeconds() {return audioDurationInSeconds;}
	
	public float getAudioFrameRate() { return audioFrameRate; }
	
	public float getAudioFrameSize() { return audioFrameSize; }
	
	/**
	 * Remembers what's the last position relative to the playing song
	 * when seeking
	 */
	public void setLastSeekPositionInMs(int seekMs)
	{
		lastSeekMs = seekMs;
	}

	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}
}
