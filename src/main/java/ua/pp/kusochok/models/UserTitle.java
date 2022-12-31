package ua.pp.kusochok.models;

import ua.pp.kusochok.models.enums.ReadStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_titles")
public class UserTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean isLoved;

    @Column
    private Boolean isDescendingSorting;

    @Column
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    private Title title;

    @OneToMany(mappedBy = "userTitle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserChapter> userChapters;

    public UserTitle(Boolean isLoved, Boolean isDescendingSorting, ReadStatus readStatus, User user, Title title) {
        this.isLoved = isLoved;
        this.isDescendingSorting = isDescendingSorting;
        this.readStatus = readStatus;
        this.user = user;
        this.title = title;
    }

    public UserTitle() {
    }

    public Long getId() {
        return id;
    }

    public Boolean getLoved() {
        return isLoved;
    }

    public Boolean getDescendingSorting() {
        return isDescendingSorting;
    }

    public ReadStatus getReadStatus() {
        return readStatus;
    }

    public User getUser() {
        return user;
    }

    public Title getTitle() {
        return title;
    }

    public List<UserChapter> getUserChapters() {
        return userChapters;
    }

    public void setLoved(Boolean loved) {
        isLoved = loved;
    }

    public void setDescendingSorting(Boolean descendingSorting) {
        isDescendingSorting = descendingSorting;
    }

    public void setReadStatus(ReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    // TODO maybe remove if it wouldn't cause errors

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    // TODO end

    public void setUserChapters(List<UserChapter> userChapters) {
        this.userChapters = userChapters;
    }
}
