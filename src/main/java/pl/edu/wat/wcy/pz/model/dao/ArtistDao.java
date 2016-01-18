package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import java.util.List;
import java.util.logging.Logger;

public class ArtistDao extends GenericDaoImpl<Artist> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private User user;

    public ArtistDao(User user) {
        super(Artist.class);
        this.user = user;
    }

    public List<Artist> findArtistsWithName(String name){
        logger.info("Find artists with name: " + name);
        return em.createQuery("from Artist a where a.name = :t AND a.user = :u",Artist.class).setParameter("t", name).setParameter("u",user).getResultList();
    }

    public List<Artist> findAllArtistsOrderByName(){
        logger.info("Find all artists order by name");
        return em.createQuery("from Artist a WHERE a.user = :u ORDER BY LOWER(a.name)",Artist.class).setParameter("u",user).getResultList();
    }

    public List<Artist> findAllArtistsOrderByNameDesc(){
        logger.info("Find all artists order by name desc");
        return em.createQuery("from Artist a  WHERE a.user = :u ORDER BY LOWER(a.name) DESC",Artist.class).setParameter("u",user).getResultList();
    }
}
