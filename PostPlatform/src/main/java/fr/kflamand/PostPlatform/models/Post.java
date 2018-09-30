package fr.kflamand.PostPlatform.models;

import javax.persistence.*;

@Entity
@Table(name="POSTS")
public class Post {

    @Id
    @GeneratedValue
    private int id;


    private int loveIts;
    private int date;
    private String url;

    @Column(unique = true)
    private String title;

    private String contenu;

    @Column(unique = true)
    private String poster;

    //TODO comentaire

    public Post(){}

    public Post(int loveIts, int date, String url, String title, String contenu, String poster) {
        this.loveIts = loveIts;
        this.date = date;
        this.url = url;
        this.title = title;
        this.contenu = contenu;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoveIts() {
        return loveIts;
    }

    public void setLoveIts(int loveIts) {
        this.loveIts = loveIts;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


}
