package fr.kflamand.Backend.entities;

import javax.persistence.*;

@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    private int loveIts;

    private long date; //TODO lib date
    private String urlSource;
    private String urlImage;

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
