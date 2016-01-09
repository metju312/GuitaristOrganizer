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
import pl.edu.wat.wcy.pz.model.dao.SongDao;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import java.io.*;

public class MP3Parser {
    private SongDao songDao = new SongDao();

    public void addSongToDatabase(String mp3Path) {

        try {
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            InputStream input = new FileInputStream(new File(mp3Path));
            parser.parse(input, handler, metadata, parseCtx);
            input.close();


            String[] metadataNames = metadata.names();

            for (String name : metadataNames) {
                metadata.get(name);
            }

            Song song = new Song();
            if (metadata.get("title") == null) {
                song.setTitle(FilenameUtils.getBaseName(mp3Path));
            } else {
                song.setTitle(metadata.get("title"));
            }
            song.setPath(mp3Path);
            songDao.create(song);

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
}
