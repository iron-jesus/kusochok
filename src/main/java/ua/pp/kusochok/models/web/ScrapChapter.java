package ua.pp.kusochok.models.web;

import java.util.ArrayList;
import java.util.List;

public class ScrapChapter {
    private String number;

    private List<String> images;

    public ScrapChapter(String number, List<String> images) {
        this.number = number;
        this.images = images;
    }

    public ScrapChapter(String number) {
        this(number, new ArrayList<>());
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        this.images.add(image);
    }
}
