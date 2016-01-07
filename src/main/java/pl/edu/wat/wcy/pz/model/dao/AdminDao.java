package pl.edu.wat.wcy.pz.model.dao;

import pl.edu.wat.wcy.pz.model.entities.accounts.Admin;

public class AdminDao extends GenericDaoImpl<Admin> {
    public AdminDao() {
        super(Admin.class);
    }
}
