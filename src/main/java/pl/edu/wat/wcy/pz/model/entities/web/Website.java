package pl.edu.wat.wcy.pz.model.entities.web;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Website implements Serializable {
    private int id;
    private String title;
    private String urlTitle;
    private String urlArtist;

    public Website() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getUrlArtist() {
        return urlArtist;
    }

    public void setUrlArtist(String urlArtist) {
        this.urlArtist = urlArtist;
    }
}
