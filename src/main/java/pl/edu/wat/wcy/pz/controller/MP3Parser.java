package pl.edu.wat.wcy.pz.controller;


import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.edu.wat.wcy.pz.model.dao.ArtistDao;
import pl.edu.wat.wcy.pz.model.dao.SongDao;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class MP3Parser {
    private SongDao songDao = new SongDao();
    private ArtistDao artistDao = new ArtistDao();

    public void addSongAndArtistToDatabase(String mp3Path) {

        try {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            InputStream input = new FileInputStream(new File(mp3Path));
            parser.parse(input, handler, metadata, parseCtx);
            input.close();


            Artist artist = generateNewArtist(metadata);
            artist = changeArtistIfAlreadyExists(artist);
            if(artist!=null){
                artistDao.create(artist);
            }

            generateSongAndCreateIfNotExists(metadata, artist, mp3Path);



//            System.out.println("Title: " + metadata.get("title"));
//            System.out.println("Artists: " + metadata.get("xmpDM:artist"));
//            System.out.println("Composer : "+metadata.get("xmpDM:composer"));
//            System.out.println("Genre : "+metadata.get("xmpDM:genre"));
//            System.out.println("Album : "+metadata.get("xmpDM:album"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    private Artist changeArtistIfAlreadyExists(Artist artist) {
        if(artist!=null){
            List<Artist> artists = artistDao.findArtistsWithName(artist.getName());
            if(artists.size() == 0){
                return artist;
            }else{
                return artists.get(0);
            }
        }
        return null;
    }

    private void generateSongAndCreateIfNotExists(Metadata metadata, Artist artist, String mp3Path) {
        Song song = new Song();
        if (metadata.get("title") == null || Objects.equals(metadata.get("title"), "")) {
            song.setTitle(FilenameUtils.getBaseName(mp3Path));
        } else {
            song.setTitle(metadata.get("title"));
        }

        if(metadata.get("xmpDM:artist")==null || Objects.equals(metadata.get("xmpDM:artist"), "")){
            song.setArtist(artistDao.findArtistsWithName("Unknown Artist").get(0));
        }else{
            song.setArtist(artist);
        }

        song.setPath(mp3Path);
        if(!songAlreadyExists(song)){
            songDao.create(song);
        }
    }

    private boolean songAlreadyExists(Song song) {
        List<Song> songList = songDao.findSongsWithTitle(song.getTitle());
        if(songList.size() == 0){
            return false;
        }
        return true;
    }


    private Artist generateNewArtist(Metadata metadata) {
        if(metadata.get("xmpDM:artist")!=null && !Objects.equals(metadata.get("xmpDM:artist"), "")){
            Artist artist = new Artist();
            artist.setName(metadata.get("xmpDM:artist"));
            return artist;
        }
        return null;
    }

}
