package ua.pp.kusochok.models;

import ua.pp.kusochok.models.enums.TitleStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "titles")
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastChapter;

    @Column(nullable = false)
    private LocalDate lastChapterUpdated;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TitleStatus status;

    @Transient
    private Boolean isHot;

    @Column
    private String photoUrl;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserTitle> userTitles;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    public Title(String name, String lastChapter, String author, Integer releaseYear, TitleStatus status, String photoUrl) {
        this.name = name;
        this.lastChapter = lastChapter;
        this.author = author;
        this.releaseYear = releaseYear;
        this.lastChapterUpdated = LocalDate.now().minusWeeks(2);
        this.status = status;
        this.photoUrl = photoUrl;
    }

    public Title() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public LocalDate getLastChapterUpdated() {
        return lastChapterUpdated;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public TitleStatus getStatus() {
        return status;
    }

    public Boolean getHot() {
        return lastChapterUpdated.isAfter(LocalDate.now().minusWeeks(1));
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public List<UserTitle> getUserTitles() {
        return userTitles;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public void setLastChapterUpdated(LocalDate lastChapterUpdated) {
        this.lastChapterUpdated = lastChapterUpdated;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setStatus(TitleStatus status) {
        this.status = status;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setUserTitles(List<UserTitle> userTitles) {
        this.userTitles = userTitles;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
