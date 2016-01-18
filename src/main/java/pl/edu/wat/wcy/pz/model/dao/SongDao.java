package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.music.Song;

import java.util.List;
import java.util.logging.Logger;

public class SongDao extends GenericDaoImpl<Song> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private User user;

    public SongDao(User user) {
        super(Song.class);
        this.user = user;
    }

    public List<Song> findAllSongs() {
        logger.info("Find songs");
        return em.createQuery("from Song s where s.artist.user = :u", Song.class).setParameter("u", user).getResultList();
    }

    public List<Song> findSongsWithPath(String path) {
        logger.info("Find songs with path: " + path);
        return em.createQuery("from Song s where s.path = :t AND s.artist.user = :u", Song.class).setParameter("t", path).setParameter("u", user).getResultList();
    }

    public List<Song> findSongsWithTitle(String title) {
        logger.info("Find songs with path: " + title);
        return em.createQuery("from Song s where s.title = :t AND s.artist.user = :u", Song.class).setParameter("t", title).setParameter("u", user).getResultList();
    }

    public List<Song> findSongsWithArtistOrderByTitle(Artist artist) {
        logger.info("Find songs with artist: " + artist.getName());
        return em.createQuery("from Song s where s.artist = :t  AND s.artist.user = :u ORDER BY LOWER(s.title)", Song.class).setParameter("t", artist).setParameter("u", user).getResultList();
    }

    public List<Song> findSongsWithArtistOrderByTitleDesc(Artist artist) {
        logger.info("Find songs with artist: " + artist.getName());
        return em.createQuery("from Song s where s.artist = :t  AND s.artist.user = :u ORDER BY LOWER(s.title) DESC", Song.class).setParameter("t", artist).setParameter("u", user).getResultList();
    }

    public List<Song> findAllSongsOrderByTitle() {
        logger.info("Find all songs order by title");
        return em.createQuery("from Song s WHERE s.artist.user = :u ORDER BY LOWER(s.title)", Song.class).setParameter("u", user).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleDesc() {
        logger.info("Find all songs order by title");
        return em.createQuery("from Song s WHERE s.artist.user = :u ORDER BY LOWER(s.title) DESC", Song.class).setParameter("u", user).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleLike(String text) {
        logger.info("Find all songs order by title like");
        return em.createQuery("from Song s WHERE LOWER(s.title) LIKE LOWER (:t) AND s.artist.user = :u ORDER BY LOWER(s.title)", Song.class).setParameter("t", '%' + text + '%').setParameter("u", user).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleDescLike(String text) {
        logger.info("Find all songs order by title desc like");
        return em.createQuery("from Song s WHERE s.artist.user = :u AND LOWER(s.title) LIKE LOWER(:t) ORDER BY LOWER(s.title) DESC", Song.class).setParameter("t", '%' + text + '%').setParameter("u", user).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleWithArtistLike(String text) {
        logger.info("Find all songs order by title with artist like");
        return em.createQuery("from Song s WHERE s.artist.user = :u AND LOWER(s.artist.name) LIKE LOWER(:t) ORDER BY LOWER(s.title)", Song.class).setParameter("t", '%' + text + '%').setParameter("u", user).getResultList();
    }

    public List<Song> findAllSongsOrderByTitleWithArtistDescLike(String text) {
        logger.info("Find all songs order by title with artist desc like");
        return em.createQuery("from Song s WHERE s.artist.user = :u AND LOWER(s.artist.name) LIKE LOWER(:t) ORDER BY LOWER(s.title) DESC", Song.class).setParameter("t", '%' + text + '%').setParameter("u", user).getResultList();
    }

}
