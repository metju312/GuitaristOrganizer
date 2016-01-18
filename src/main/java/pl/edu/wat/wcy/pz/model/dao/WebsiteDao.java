package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;
import pl.edu.wat.wcy.pz.model.entities.web.Website;

import java.util.List;
import java.util.logging.Logger;

public class WebsiteDao extends GenericDaoImpl<Website> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private User user;

    public WebsiteDao(User user) {
        super(Website.class);
        this.user = user;
    }

    public List<Website> findWebsitesWithTitle(String title){
        logger.info("Find websites with title: " + title);
        return em.createQuery("from Website w where w.title = :t AND w.user = :u", Website.class).setParameter("t", title).setParameter("u",user).getResultList();
    }

    public List<Website> findAllWebsites(){
        logger.info("Find websites");
        return em.createQuery("from Website w where w.user = :u", Website.class).setParameter("u",user).getResultList();
    }
}
