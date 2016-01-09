package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import java.util.List;
import java.util.logging.Logger;

public class WebsiteDao extends GenericDaoImpl<Website> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public WebsiteDao() {
        super(Website.class);
    }

    public List<Website> findWebsitesWithTitle(String title){
        logger.info("Find websites with title: " + title);
        return em.createQuery("from Website w where w.title = :t",Website.class).setParameter("t",title).getResultList();
    }
}
