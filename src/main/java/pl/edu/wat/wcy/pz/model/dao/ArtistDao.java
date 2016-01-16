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

    public List<Artist> findAllArtistsOrderByName(){
        logger.info("Find all artists order by name");
        return em.createQuery("from Artist a ORDER BY a.name",Artist.class).getResultList();
    }

    public List<Artist> findAllArtistsOrderByNameDesc(){
        logger.info("Find all artists order by name desc");
        return em.createQuery("from Artist a ORDER BY a.name DESC",Artist.class).getResultList();
    }
}
