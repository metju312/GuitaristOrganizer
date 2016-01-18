package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import java.util.List;
import java.util.logging.Logger;

public class CoverDao extends GenericDaoImpl<Cover> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private User user;

    public CoverDao(User user) {
        super(Cover.class);
        this.user = user;
    }

    public List<Cover> findAllCoversOrderByTitle() {
        logger.info("Find covers order by title");
        return em.createQuery("from Cover s where s.song.artist.user = :u ORDER BY LOWER(s.title)", Cover.class).setParameter("u", user).getResultList();
    }

    public List<Cover> findAllCoversOrderByTitleDesc() {
        logger.info("Find covers order by title desc");
        return em.createQuery("from Cover s where s.song.artist.user = :u ORDER BY LOWER(s.title) DESC", Cover.class).setParameter("u", user).getResultList();
    }

    public List<Cover> findCoversWithSongOrderByTitle(Song song) {
        logger.info("Find covers with song order by title");
        return em.createQuery("from Cover s where s.song.artist.user = :u AND s.song = :t ORDER BY LOWER(s.title)", Cover.class).setParameter("u", user).setParameter("t", song).getResultList();
    }

    public List<Cover> findCoversWithSongOrderByTitleDesc(Song song) {
        logger.info("Find covers with song order by title desc");
        return em.createQuery("from Cover s where s.song.artist.user = :u AND s.song = :t ORDER BY LOWER(s.title) DESC", Cover.class).setParameter("u", user).setParameter("t", song).getResultList();
    }


    public List<Cover> findAllCoversWithTitleOrderByTitle(String title) {
        logger.info("Find covers with title order by title" + title);
        return em.createQuery("from Cover s where s.title = :t AND s.song.artist.user = :u ORDER BY LOWER(s.title)", Cover.class).setParameter("t", title).setParameter("u", user).getResultList();
    }


    public List<Cover> findAllCoversWithTitleOrderByTitleDesc(String title) {
        logger.info("Find covers with title order by title desc" + title);
        return em.createQuery("from Cover s where s.title = :t AND s.song.artist.user = :u ORDER BY LOWER(s.title) DESC", Cover.class).setParameter("t", title).setParameter("u", user).getResultList();
    }

}
