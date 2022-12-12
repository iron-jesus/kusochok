package ua.pp.kusochok.rest.dto;

import java.util.ArrayList;
import java.util.List;

public class ChapterReadDto {
    public Long id;
    public String number;
    public String volume;
    public List<String> imgLinks;

    public ChapterReadDto(Long id, Double number, String volume, List<String> imgLinks) {
        this.id = id;

        String[] parts = number.toString().split("[.]");
        String num = parts[0];

        if (!parts[1].equals("0")) {
            num += "." + parts[1];
        }

        this.number = num;
        this.volume = volume;
        this.imgLinks = imgLinks;
    }

    public ChapterReadDto(Long id, Double number, String volume) {
        this(id, number, volume, new ArrayList<>());
    }
}
