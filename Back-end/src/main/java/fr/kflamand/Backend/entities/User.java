package fr.kflamand.Backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private Long userId;

    //Pseudo
    @Column(name = "pseudo" , unique = true)
    private String pseudo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String userName;

    @Column(name ="enabled")
    private Boolean enabled;

    @Transient
    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE ,fetch = FetchType.LAZY)
    private RegistrationToken registrationToken;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany
    private List<Post> posts;


    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public RegistrationToken getRegistrationToken() { return registrationToken; }

    public void setRegistrationToken(RegistrationToken registrationToken) { this.registrationToken = registrationToken; }

    public List<Post> getPosts() { return posts; }

    public void setPosts(List<Post> posts) { this.posts = posts;}

    public String getPseudo() { return pseudo; }

    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public Boolean getEnabled() { return enabled; }

    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
}