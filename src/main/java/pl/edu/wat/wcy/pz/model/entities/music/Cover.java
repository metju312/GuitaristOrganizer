package pl.edu.wat.wcy.pz.model.entities.music;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Cover implements Serializable {
    private int id;
    private String title;
    private String path;
    private String url;
    private Song song;
    private List<Tab> tabList = new ArrayList<>();

    public Cover() {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @OneToMany(mappedBy = "cover", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    public List<Tab> getTabList() {
        return tabList;
    }

    public void setTabList(List<Tab> tabList) {
        this.tabList = tabList;
    }
}
