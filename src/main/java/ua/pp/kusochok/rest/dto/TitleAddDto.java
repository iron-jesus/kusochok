package ua.pp.kusochok.rest.dto;

import ua.pp.kusochok.models.enums.TitleStatus;

import java.time.LocalDate;

public class TitleAddDto {
    public String name;

    public String author;

    public Integer releaseYear;

    public TitleStatus status;

    public String photoUrl;

    public TitleAddDto(String name, String author, Integer releaseYear, TitleStatus status, String photoUrl) {
        this.name = name;
        this.author = author;
        this.releaseYear = releaseYear;
        this.status = status;
        this.photoUrl = photoUrl;
    }

    public TitleAddDto() {
    }
}
