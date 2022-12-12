package ua.pp.kusochok.services.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.models.web.ScrapChapter;
import ua.pp.kusochok.models.web.ScrapChapterLink;
import ua.pp.kusochok.services.Logus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnePieceScrapperService implements IScrapper {
    @Override
    public List<ScrapChapterLink> scrapChapterLinks() throws LinkAccessException {
        try {
            Document doc = Jsoup.connect("https://ww10.1piecemanga.com/").get();

            List<Element> chapters = doc.getElementsByClass("comic-thumb-title").stream().toList();

            List<ScrapChapterLink> links = new ArrayList<>();

            for (Element chapter : chapters) {
                if (chapter.child(0).attr("href").equals("")) {
                    continue;
                }
                links.add(new ScrapChapterLink(
                        chapter.child(0).text().split(",")[1].replace("Chapter", "").strip(),
                        chapter.child(0).attr("href")
                ));
            }

            // Check if last title contains chapter or just Coming soon words

            doc = Jsoup.connect(links.get(0).getLink()).get();

            Element el = doc
                    .getElementsByClass("has-text-align-center has-vivid-red-color has-text-color has-x-large-font-size")
                    .first();

            if (el != null) {
                if (el.text().toLowerCase().contains("coming soon")) {
                    links.remove(0);
                }
            }

            return links;
        } catch (IOException e) {
            // TODO exception handling
            throw new LinkAccessException("https://ww10.1piecemanga.com/", e);
        }
    }

    @Override
    public ScrapChapter scrapChapter(ScrapChapterLink chapterLink) throws LinkAccessException {
        try {
            ScrapChapter scrapChapter = new ScrapChapter(chapterLink.getNumber());

            Document doc = Jsoup.connect(chapterLink.getLink()).get();

            List<Element> images = doc.getElementsByClass("entry-content").first().getElementsByTag("img").stream().toList();

            for (Element image:
                    images) {
                if (image.attr("src").equals("")) {
                    continue;
                }
                scrapChapter.addImage(image.attr("src"));
            }

            return scrapChapter;
        } catch (IOException e) {
            throw new LinkAccessException(chapterLink.getLink(), e);
        }
    }

    @Override
    public String getTitleName() {
        return TitleName.ONE_PIECE.getName();
    }
}
