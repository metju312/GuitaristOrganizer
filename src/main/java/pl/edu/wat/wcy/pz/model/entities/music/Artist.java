package pl.edu.wat.wcy.pz.model.entities.music;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Artist implements Serializable {
    private int id;
    private String name;
    private Song song;

    public Artist() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
