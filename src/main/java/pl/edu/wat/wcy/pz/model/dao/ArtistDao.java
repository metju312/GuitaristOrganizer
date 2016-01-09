package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import java.util.List;
import java.util.logging.Logger;

public class ArtistDao extends GenericDaoImpl<Artist> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ArtistDao() {
        super(Artist.class);
    }

    public List<Artist> findArtistsWithName(String name){
        logger.info("Find artists with name: " + name);
        return em.createQuery("from Artist a where a.name = :t",Artist.class).setParameter("t",name).getResultList();
    }
}
