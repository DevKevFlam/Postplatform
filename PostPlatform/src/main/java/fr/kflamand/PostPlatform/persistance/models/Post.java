package fr.kflamand.PostPlatform.persistance.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private int loveIts;
    private long date;
    private String url;

    @Column(unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @ManyToOne
    private User poster;

    //TODO comentaire

    public Post(){}

    public Post(long id){
        this.id =id ;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLoveIts() {
        return loveIts;
    }

    public void setLoveIts(int loveIts) {
        this.loveIts = loveIts;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }



}
