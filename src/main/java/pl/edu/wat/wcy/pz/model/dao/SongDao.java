package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.music.Song;

public class SongDao extends GenericDaoImpl<Song> {
    public SongDao() {
        super(Song.class);
    }
}
