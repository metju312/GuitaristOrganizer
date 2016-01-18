package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;
import pl.edu.wat.wcy.pz.model.entities.music.Folder;

import java.util.List;
import java.util.logging.Logger;

public class FolderDao extends GenericDaoImpl<Folder> {
    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private User user;
    public FolderDao(User user) {
        super(Folder.class);
        this.user = user;
    }

    public List<Folder> findUserFolders(){
        logger.info("Find folders for user: " + user.getLogin());
        return em.createQuery("from Folder a where a.user = :u AND a.user = :u",Folder.class).setParameter("u", user).getResultList();
    }
}
