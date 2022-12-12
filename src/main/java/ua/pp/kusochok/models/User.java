package ua.pp.kusochok.models;

import ua.pp.kusochok.models.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String displayName;

    @Column
    private String avatarUrl;

    @Column
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column()
    private Role role;

    // TODO Role

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserTitle> titles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id", referencedColumnName = "id")
    private Settings settings;

    public User(String username, String displayName, String password, Settings settings) {
        this(username, displayName, password, "", Role.USER, settings);
    }

    public User(String username, String displayName, String password, Role role, Settings settings) {
        this(username, displayName, "", password, role, settings);
    }

    public User(String username, String displayName, String avatarUrl, String password, Role role, Settings settings) {
        this.username = username;
        this.displayName = displayName;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.role = role;
        this.settings = settings;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public List<UserTitle> getTitles() {
        return titles;
    }

    public Role getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitles(List<UserTitle> titles) {
        this.titles = titles;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
