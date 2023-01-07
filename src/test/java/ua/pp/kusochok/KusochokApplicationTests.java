package ua.pp.kusochok;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.services.web.VinlandSagaScrapperService;

@SpringBootTest
class KusochokApplicationTests {

    @Autowired
    private VinlandSagaScrapperService scrapperService;

    @Test
    void contextLoads() throws LinkAccessException {
        scrapperService.scrapChapterLinks().forEach(scrapChapterLink -> {
            try {
                System.out.println(
                        scrapChapterLink.getNumber()
                                + " PASSED WITH CHAPTERS COUNT "
                                + scrapperService.scrapChapter(scrapChapterLink).getImages().size());
            } catch (LinkAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
