package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.User;

public class UserDao extends GenericDaoImpl<User> {
    public UserDao() {
        super(User.class);
    }
}
