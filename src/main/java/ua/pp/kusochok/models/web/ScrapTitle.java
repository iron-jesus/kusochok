package ua.pp.kusochok.models.web;

import java.util.List;

public class ScrapTitle {
    String name;

    List<ScrapChapterLink> chaptersLink;

    public ScrapTitle(String name) {
        this.name = name;
    }

    public ScrapTitle(String name, List<ScrapChapterLink> chaptersLink) {
        this.name = name;
        this.chaptersLink = chaptersLink;
    }

    public String getName() {
        return name;
    }

    public List<ScrapChapterLink> getChaptersLink() {
        return chaptersLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChaptersLink(List<ScrapChapterLink> chaptersLink) {
        this.chaptersLink = chaptersLink;
    }
}
