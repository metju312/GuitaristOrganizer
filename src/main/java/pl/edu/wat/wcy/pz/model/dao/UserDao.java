package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Artist;

import java.util.List;
import java.util.logging.Logger;

public class UserDao extends GenericDaoImpl<User> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public UserDao() {
        super(User.class);
    }

    public List<User> findUsersWithLogin(String login){
        logger.info("Find users with login: " + login);
        return em.createQuery("from User a where a.login = :t",User.class).setParameter("t",login).getResultList();
    }
}
