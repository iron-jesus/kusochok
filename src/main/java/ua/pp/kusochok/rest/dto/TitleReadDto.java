package ua.pp.kusochok.rest.dto;

import ua.pp.kusochok.models.Title;
import ua.pp.kusochok.models.enums.TitleStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TitleReadDto {
    private Long id;

    private String name;

    private String lastChapter;

    private LocalDate lastChapterUpdated;

    private String author;

    private Integer releaseYear;

    private TitleStatus status;

    private Boolean isHot;

    private String photoUrl;

    private List<ChapterLinkReadDto> chapters;

    public TitleReadDto(Long id,
                        String name,
                        String lastChapter,
                        LocalDate lastChapterUpdated,
                        String author,
                        Integer releaseYear,
                        TitleStatus status,
                        Boolean isHot,
                        String photoUrl,
                        List<ChapterLinkReadDto> chapters) {
        this.id = id;
        this.name = name;
        this.lastChapter = lastChapter;
        this.lastChapterUpdated = lastChapterUpdated;
        this.author = author;
        this.releaseYear = releaseYear;
        this.status = status;
        this.isHot = isHot;
        this.photoUrl = photoUrl;
        this.chapters = chapters;
    }

    public TitleReadDto(Long id,
                        String name,
                        LocalDate lastChapterUpdated,
                        String author,
                        Integer releaseYear,
                        TitleStatus status,
                        Boolean isHot,
                        String photoUrl) {
        this.id = id;
        this.name = name;
        this.lastChapterUpdated = lastChapterUpdated;
        this.author = author;
        this.releaseYear = releaseYear;
        this.status = status;
        this.isHot = isHot;
        this.photoUrl = photoUrl;
    }

    public static TitleReadDto fromTitle(Title title, Boolean excludeChapters) {
        TitleReadDto titleReadDto = new TitleReadDto(
                title.getId(),
                title.getName(),
                title.getLastChapter(),
                title.getLastChapterUpdated(),
                title.getAuthor(),
                title.getReleaseYear(),
                title.getStatus(),
                title.getHot(),
                title.getPhotoUrl(),
                new ArrayList<>()
        );

        if (title.getChapters() != null && !title.getChapters().isEmpty() && !excludeChapters) {
            titleReadDto.chapters = ChapterLinkReadDto.fromChapterList(title.getChapters());
            titleReadDto.lastChapter = titleReadDto.chapters.get(0).number;
        }

        return titleReadDto;
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
        return isHot;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public List<ChapterLinkReadDto> getChapters() {
        return chapters;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setChapters(List<ChapterLinkReadDto> chapters) {
        this.chapters = chapters;
        if (chapters != null && !chapters.isEmpty()) {
            this.lastChapter = chapters.get(0).number;
        }
    }
}
