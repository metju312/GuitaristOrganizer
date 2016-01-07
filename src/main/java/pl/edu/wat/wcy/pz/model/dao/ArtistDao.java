package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.music.Artist;

public class ArtistDao extends GenericDaoImpl<Artist> {
    public ArtistDao() {
        super(Artist.class);
    }
}
