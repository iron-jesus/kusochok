package ua.pp.kusochok.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.pp.kusochok.errors.EntityNotFoundException;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.rest.dto.TitleAddDto;
import ua.pp.kusochok.rest.dto.TitleReadDto;
import ua.pp.kusochok.services.ChapterService;
import ua.pp.kusochok.services.TitleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/titles")
public class TitleRestController {
    final TitleService titleService;
    final ChapterService chapterService;

    public TitleRestController(TitleService titleService, ChapterService chapterService) {
        this.titleService = titleService;
        this.chapterService = chapterService;
    }

    @GetMapping
    public ResponseEntity<List<TitleReadDto>> get() throws LinkAccessException {
        List<TitleReadDto> titleReadDtos = new ArrayList<>();

        this.titleService.getAll().forEach(title -> {
            titleReadDtos.add(TitleReadDto.fromTitle(title, true));
        });

        return ResponseEntity.ok(titleReadDtos);
    }

    @GetMapping("/{titleQualifier}")
    public ResponseEntity<?> getTitle(
            @PathVariable String titleQualifier,
            @RequestParam(defaultValue = "false") Boolean excludeChapters,
            @RequestParam(defaultValue = "false") Boolean force
    ) throws LinkAccessException {
        try {
            TitleReadDto titleReadDto = TitleReadDto.fromTitle(titleService.getByQualifier(titleQualifier), excludeChapters);

            if (!excludeChapters && titleReadDto.getChapters().isEmpty() || force) {
                titleReadDto.setChapters(chapterService.getChapterNumbersByTitleName(
                        TitleService.getTitleNameByQualifier(titleQualifier), force
                ));
            }

            return ResponseEntity.ok(titleReadDto);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('title:write')")
    public ResponseEntity<TitleReadDto> add(@RequestBody TitleAddDto titleAddDto) {
        return ResponseEntity.ok(TitleReadDto.fromTitle(titleService.addTitle(titleAddDto), true));
    }
}
