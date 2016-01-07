package pl.edu.wat.wcy.pz.model.entities.music;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Song implements Serializable {
    private int id;
    private String title;
    private String path;
    private String url;
    private List<Artist> artistList = new ArrayList<>();
    private List<Cover> coverList = new ArrayList<>();
    private List<Tab> tabList = new ArrayList<>();

    public Song() {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Cover> getCoverList() {
        return coverList;
    }

    public void setCoverList(List<Cover> coverList) {
        this.coverList = coverList;
    }

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Tab> getTabList() {
        return tabList;
    }

    public void setTabList(List<Tab> tabList) {
        this.tabList = tabList;
    }
}
