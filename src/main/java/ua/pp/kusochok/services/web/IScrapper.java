package ua.pp.kusochok.services.web;

import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.models.web.ScrapChapter;
import ua.pp.kusochok.models.web.ScrapChapterLink;

import java.util.List;

public interface IScrapper {
    List<ScrapChapterLink> scrapChapterLinks() throws LinkAccessException;
    ScrapChapter scrapChapter(ScrapChapterLink chapterLink) throws LinkAccessException;
    String getTitleName();
}
