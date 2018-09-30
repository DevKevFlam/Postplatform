package fr.kflamand.PostPlatform.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    private String pseudo;

    public User() {
    }

    public User(String email, String pseudo) {
        this.email = email;
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
