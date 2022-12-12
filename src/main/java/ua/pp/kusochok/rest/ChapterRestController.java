package ua.pp.kusochok.rest;

import com.itextpdf.text.DocumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.rest.dto.ChapterLinkReadDto;
import ua.pp.kusochok.services.ChapterService;
import ua.pp.kusochok.services.TitleService;
import ua.pp.kusochok.services.doc.PdfService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/titles/{titleQualifier}/chapters")
public class ChapterRestController {
    private final ChapterService chapterService;
    private final PdfService pdfService;

    public ChapterRestController(ChapterService chapterService, PdfService pdfService) {
        this.chapterService = chapterService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public List<ChapterLinkReadDto> get(@PathVariable String titleQualifier) throws Exception {
        return chapterService.getChapterNumbersByTitleName(TitleService.getTitleNameByQualifier(titleQualifier), false);
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> getChapter(@PathVariable String titleQualifier, @PathVariable String number) throws LinkAccessException {
        try {
            return ResponseEntity.ok(chapterService.getChapter(TitleService.getTitleNameByQualifier(titleQualifier), number));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{number}/download")
    public ResponseEntity<?> download(@PathVariable String titleQualifier, @PathVariable String number) {
        try {
            ByteArrayOutputStream outputStream = pdfService.generatePdfFromImgLinks(
                    chapterService.getChapter(TitleService.getTitleNameByQualifier(titleQualifier), number).imgLinks
            );

            return ResponseEntity.ok()
                    .contentLength(outputStream.size())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
