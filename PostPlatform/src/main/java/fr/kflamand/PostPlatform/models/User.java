package fr.kflamand.PostPlatform.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    //Encryption + pb de mise en base
    private String mdp;

    @NotNull
    private String email;

    @NotNull
    private String pseudo;

    public User() {
    }

    public User(String email, String pseudo, String mdp) {
        this.email = email;
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    public User(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    */
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + mdp + '\'' +
                ", email='" + email + '\'' +
                ", pseudo='" + pseudo + '\'' +
                '}';
    }
}
