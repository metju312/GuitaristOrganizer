package pl.edu.wat.wcy.pz.model.dao;

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
}
