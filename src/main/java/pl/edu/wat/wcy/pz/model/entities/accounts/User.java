package pl.edu.wat.wcy.pz.model.entities.accounts;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private int lafIndex;

    public User() {
        super();
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLafIndex() {
        return lafIndex;
    }

    public void setLafIndex(int lafIndex) {
        this.lafIndex = lafIndex;
    }

    @PrePersist
    protected void onCreate() {
        lafIndex = 0;
    }
}
