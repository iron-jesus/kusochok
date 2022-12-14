package ua.pp.kusochok.models;

import ua.pp.kusochok.models.enums.ReadStatus;

import javax.persistence.*;

@Entity
@Table(name = "user_chapters")
public class UserChapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chapter_id")
    private UserTitle chapter;

    @Column
    private boolean isBookmarked;

    @Column
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_title_id")
    private UserTitle userTitle;

    public UserChapter(UserTitle chapter, boolean isBookmarked, ReadStatus readStatus, UserTitle userTitle) {
        this.chapter = chapter;
        this.isBookmarked = isBookmarked;
        this.readStatus = readStatus;
        this.userTitle = userTitle;
    }

    public UserChapter(UserTitle chapter, boolean isBookmarked, UserTitle userTitle) {
        this.chapter = chapter;
        this.isBookmarked = isBookmarked;
        this.userTitle = userTitle;
        this.readStatus = ReadStatus.START;
    }

    public UserChapter(UserTitle chapter, UserTitle userTitle) {
        this.chapter = chapter;
        this.isBookmarked = false;
        this.userTitle = userTitle;
        this.readStatus = ReadStatus.START;
    }

    public UserChapter() {

    }

    public Long getId() {
        return id;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public ReadStatus getReadStatus() {
        return readStatus;
    }

    public UserTitle getUserTitle() {
        return userTitle;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public void setReadStatus(ReadStatus readStatus) {
        this.readStatus = readStatus;
    }

    public void setUserTitle(UserTitle userTitle) {
        this.userTitle = userTitle;
    }
}
