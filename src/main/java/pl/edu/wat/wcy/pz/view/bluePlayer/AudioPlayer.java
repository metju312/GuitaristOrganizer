package pl.edu.wat.wcy.pz.view.bluePlayer;


import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

public class AudioPlayer extends BasicPlayer {
	
	//Extension of BasicPlayer
	private static AudioPlayer instance = null;
	private ArrayList<String> playlist = new ArrayList<String>();
	private int index = 0;
	private boolean paused = true;
	private boolean opened = false;
	private boolean isSeeking = false;
	public boolean isStoped = true;

	private float audioDurationInSeconds = 0;
	private int audioFrameSize = 0;
	private float audioFrameRate = 0;

	private byte[] cpcmdata;
	private long currentSongMs = 0;
	private int lastSeekMs = 0;

	private AudioPlayer() {
		super();
		this.addBasicPlayerListener(new BasicPlayerListener() {
			
			@Override
			public void stateUpdated(BasicPlayerEvent event) {
				if(event.getCode() == BasicPlayerEvent.EOM)
				{
					lastSeekMs = 0;
					paused = true;
					opened = false;
				}
				if(event.getCode() == BasicPlayerEvent.SEEKING)
					isSeeking = true;
				if(event.getCode() == BasicPlayerEvent.SEEKED)
					isSeeking = false;
			}
			
			@Override
			public void setController(BasicController arg0) {

			}
			
			@Override
			public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {
				currentSongMs = microseconds;
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
				log(line);
				//Set Audio Properties
				File file = new File(playlist.get(index));
			    long audioFileLength = file.length();
				int frameSize = (int) properties.get("mp3.framesize.bytes");
			    float frameRate = (float) properties.get("mp3.framerate.fps");
			    audioFrameSize = frameSize;
			    audioFrameRate = frameRate;
			    audioDurationInSeconds = (audioFileLength / (frameSize * frameRate));
			    log("\tframesize " + frameSize + " framerate " + frameRate);
			    log("\tAudio File lenght in seconds is: " + audioDurationInSeconds);
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
		if(playlist.size() == 0)
			return;
		if(!paused || !opened){
			File f = new File(playlist.get(index));
			log("Opening file... " + f.getAbsolutePath());
			open(f);
			opened = true;
			super.play();
		}
		if(paused){
			for (String s : playlist) {
				System.out.println(s);
			}
			super.resume();
		}

		paused = false;
		isStoped = false;
	}
	
	@Override
	public void pause() throws BasicPlayerException {
		log("Paused");
		paused = true;
		isStoped = false;
		super.pause();
	}
	
	@Override
	public void stop() throws BasicPlayerException {
		paused = false;
		isStoped = true;
		super.stop();
	}
	
	public boolean isPaused(){ return paused; }
	
	public boolean isOpenFile() { return opened; }
	
	public ArrayList<String> getPlaylist(){ return playlist; }
	
	public int getIndexSong(){ return index; }
	
	public void setIndexSong(int index){ this.index = index; lastSeekMs = 0; }
	
	public boolean isSeeking() { return isSeeking; }

	public void nextSong() throws BasicPlayerException {
		if(playlist.size() == 0)
			return;
		lastSeekMs = 0;
		paused = false;
		index = (index+1)%playlist.size();
		play();
	}

	public void prvSong() throws BasicPlayerException {
		if(playlist.size() == 0)
			return;
		lastSeekMs = 0;
		paused = false;
		index = (index-1)%playlist.size();
		play();
	}

	public void addSong(String songPath) {
		playlist.add(songPath);
	}

	public void removeSong(int index) {
		playlist.remove(index);
	}

	public void removeSong(String songPath)
	{
		playlist.remove(songPath);
	}
	
	public byte[] getPcmData(){
		return cpcmdata;}
	
	public long getProgressMicroseconds(){
		return currentSongMs +lastSeekMs;}
	
	public float getAudioDurationSeconds() {
		return audioDurationInSeconds;}
	
	public float getAudioFrameRate() {
		return audioFrameRate; }
	
	public float getAudioFrameSize() {
		return audioFrameSize; }

	public void setLastSeekPositionInMs(int seekMs)
	{
		lastSeekMs = seekMs;
	}

	private void log(String line)
	{
		System.out.println("AudioPlayer] " + line);
	}
}
