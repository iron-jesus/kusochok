package ua.pp.kusochok.services.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.models.web.ScrapChapter;
import ua.pp.kusochok.models.web.ScrapChapterLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChainsawManScrapperService implements IScrapper {
    @Override
    public List<ScrapChapterLink> scrapChapterLinks() throws LinkAccessException {
        try {
            Document doc = Jsoup.connect("https://readchainsaw.online/").get();

            List<Element> chapters = doc.getElementsByClass("entry-content").first().getElementsByClass("su-post").stream().toList();

            List<ScrapChapterLink> links = new ArrayList<>();

            for (Element chapter : chapters) {
                if (chapter.child(0).attr("href").equals("")) {
                    continue;
                }
                links.add(new ScrapChapterLink(
                        chapter.child(0)
                                .text()
                                .toLowerCase()
                                .split("chapter ")[1],
                        chapter.child(0).attr("href")
                ));
            }

            // Check if last title contains chapter or just Coming soon words

            doc = Jsoup.connect(links.get(0).getLink()).get();

            Elements el = doc
                    .getElementsByClass("tickcounter");

            if (el.size() == 1) {
                links.remove(0);
            }

            return links;
        } catch (IOException e) {
            // TODO exception handling
            throw new LinkAccessException("https://readchainsaw.online/", e);
        }
    }

    @Override
    public ScrapChapter scrapChapter(ScrapChapterLink chapterLink) throws LinkAccessException {
        try {
            ScrapChapter scrapChapter = new ScrapChapter(chapterLink.getNumber());

            Document doc = Jsoup.connect(chapterLink.getLink()).get();

            List<Element> imgContainers = doc.getElementsByClass("separator").stream().toList();

            for (Element imgContainer:
                    imgContainers) {
                Element img = imgContainer.getElementsByTag("img").first();
                if (img == null || img.attr("src").equals("")) {
                    continue;
                }
                scrapChapter.addImage(img.attr("src"));
            }

            return scrapChapter;
        } catch (IOException e) {
            throw new LinkAccessException(chapterLink.getLink(), e);
        }
    }

    @Override
    public String getTitleName() {
        return TitleName.CHAINSAW_MAN.getName();
    }
}
