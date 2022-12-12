package ua.pp.kusochok.models.web;

public class ScrapChapterLink {
    String number;

    String link;

    public ScrapChapterLink(String number, String link) {
        this.number = number;
        this.link = link;
    }

    public ScrapChapterLink() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
