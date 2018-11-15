package fr.kflamand.Backend.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name="RegistrationToken")
public class RegistrationToken implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String token;

    private Calendar expire;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private User user;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public RegistrationToken() {
    }

    public RegistrationToken(String token) {
        this.token = token;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Calendar getExpire() {
        return expire;
    }

    public void setExpire(Calendar expire) {
        this.expire = expire;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
