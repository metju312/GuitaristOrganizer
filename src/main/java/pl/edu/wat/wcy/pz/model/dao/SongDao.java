package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import java.util.List;
import java.util.logging.Logger;

public class SongDao extends GenericDaoImpl<Song> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public SongDao() {
        super(Song.class);
    }

    public List<Song> findSongsWithPath(String path) {
        logger.info("Find songs with path: " + path);
        return em.createQuery("from Song s where s.path = :t", Song.class).setParameter("t", path).getResultList();
    }

    public List<Song> findSongsWithTitle(String title) {
        logger.info("Find songs with path: " + title);
        return em.createQuery("from Song s where s.title = :t", Song.class).setParameter("t", title).getResultList();
    }

    public List<Song> findSongsWithArtistOrderByTitle(Artist artist) {
        logger.info("Find songs with artist: " + artist.getName());
        return em.createQuery("from Song s where s.artist = :t ORDER BY s.title", Song.class).setParameter("t", artist).getResultList();
    }

    public List<Song> findSongsWithArtistOrderByTitleDesc(Artist artist) {
        logger.info("Find songs with artist: " + artist.getName());
        return em.createQuery("from Song s where s.artist = :t ORDER BY s.title DESC", Song.class).setParameter("t", artist).getResultList();
    }

    public List<Song> findAllSongsOrderByTitle() {
        logger.info("Find all songs order by title");
        return em.createQuery("from Song s ORDER BY s.title", Song.class).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleDesc() {
        logger.info("Find all songs order by title");
        return em.createQuery("from Song s ORDER BY s.title DESC", Song.class).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleLike(String text) {
        logger.info("Find all songs order by title like");
        return em.createQuery("from Song s WHERE LOWER(s.title) LIKE :t ORDER BY s.title", Song.class).setParameter("t", '%' + text + '%').getResultList();
    }

    public List<Song> findAllSongsOrderByTitleDescLike(String text) {
        logger.info("Find all songs order by title desc like");
        return em.createQuery("from Song s WHERE LOWER(s.title) LIKE :t ORDER BY s.title DESC", Song.class).setParameter("t", '%' + text + '%').getResultList();
    }

    public List<Song> findAllSongsOrderByTitleWithArtistLike(String text) {
        logger.info("Find all songs order by title with artist like");
        return em.createQuery("from Song s WHERE LOWER(s.artist.name) LIKE :t ORDER BY s.title", Song.class).setParameter("t", '%' + text + '%').getResultList();
    }

    public List<Song> findAllSongsOrderByTitleWithArtistDescLike(String text) {
        logger.info("Find all songs order by title with artist desc like");
        return em.createQuery("from Song s WHERE LOWER(s.artist.name) LIKE :t ORDER BY s.title DESC", Song.class).setParameter("t", '%' + text + '%').getResultList();
    }

}
