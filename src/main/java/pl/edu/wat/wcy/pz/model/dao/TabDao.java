package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Cover;
import pl.edu.wat.wcy.pz.model.entities.music.Tab;

import java.util.List;
import java.util.logging.Logger;

public class TabDao extends GenericDaoImpl<Tab> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private User user;

    public TabDao(User user) {
        super(Tab.class);
        this.user = user;
    }

    public List<Tab> findAllTabsOrderByTitle() {
        logger.info("Find tabs order by title");
        return em.createQuery("from Tab s where s.cover.song.artist.user = :u ORDER BY LOWER(s.title)", Tab.class).setParameter("u", user).getResultList();
    }

    public List<Tab> findAllTabsOrderByTitleDesc() {
        logger.info("Find tabs order by title desc");
        return em.createQuery("from Tab s where s.cover.song.artist.user = :u ORDER BY LOWER(s.title) Desc", Tab.class).setParameter("u", user).getResultList();
    }

    public List<Tab> findTabsWithCoverOrderByTitle(Cover cover) {
        logger.info("Find tabs with cover order by title");
        return em.createQuery("from Tab s where s.cover.song.artist.user = :u AND s.cover = :t ORDER BY LOWER(s.title)", Tab.class).setParameter("u", user).setParameter("t", cover).getResultList();
    }

    public List<Tab> findTabsWithCoverOrderByTitleDesc(Cover cover) {
        logger.info("Find tabs with cover order by title");
        return em.createQuery("from Tab s where s.cover.song.artist.user = :u AND s.cover = :t ORDER BY LOWER(s.title) DESC", Tab.class).setParameter("u", user).setParameter("t", cover).getResultList();
    }
}
