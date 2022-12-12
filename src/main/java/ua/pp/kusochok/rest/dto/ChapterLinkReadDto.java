package ua.pp.kusochok.rest.dto;

import ua.pp.kusochok.models.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterLinkReadDto {
    public String number;
    public String volume;

    public ChapterLinkReadDto(Double number, String volume) {
        String[] parts = number.toString().split("[.]");
        String num = parts[0];

        if (!parts[1].equals("0")) {
            num += "." + parts[1];
        }

        this.number = num;
        this.volume = volume;
    }

    public static List<ChapterLinkReadDto> fromChapterList(List<Chapter> chapters) {
        List<ChapterLinkReadDto> chapterLinkReadDtos = new ArrayList<>();

        chapters.forEach(chapter -> {
            chapterLinkReadDtos.add(new ChapterLinkReadDto(chapter.getNumber(), "x"));
        });

        return chapterLinkReadDtos;
    }
}
