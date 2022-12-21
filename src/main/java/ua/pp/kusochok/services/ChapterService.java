package ua.pp.kusochok.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.pp.kusochok.errors.EntityNotFoundException;
import ua.pp.kusochok.errors.InvalidInput;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.models.Chapter;
import ua.pp.kusochok.models.Title;
import ua.pp.kusochok.models.web.ScrapChapterLink;
import ua.pp.kusochok.repositories.ChapterRepository;
import ua.pp.kusochok.repositories.TitleRepository;
import ua.pp.kusochok.rest.dto.ChapterLinkReadDto;
import ua.pp.kusochok.rest.dto.ChapterReadDto;
import ua.pp.kusochok.services.web.ScrapperServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChapterService {
    private final ScrapperServices services;

    private final ChapterRepository chapterRepository;

    private final TitleRepository titleRepository;

    public ChapterService(ScrapperServices services, ChapterRepository chapterRepository, TitleRepository titleRepository) {
        this.services = services;
        this.chapterRepository = chapterRepository;
        this.titleRepository = titleRepository;
    }

    @Transactional
    public List<ChapterLinkReadDto> getChapterNumbersByTitleName(String titleName, Boolean force) throws Exception {
        Title title = titleRepository.findByName(titleName).orElseThrow(() -> new EntityNotFoundException("title", "name", titleName));

        if (title.getChapters().isEmpty() || force) {
            try {
                return ChapterLinkReadDto.fromChapterList(reGetChaptersByTitleAndQualifyIfChapterExists(title));
            } catch (EntityNotFoundException e) {
                throw new Exception("Failed to load links: title has no chapters");
            }
        }

        return ChapterLinkReadDto.fromChapterList(title.getChapters());
    }

    @Transactional
    public List<Chapter> reGetChaptersByTitleAndQualifyIfChapterExists(Title title) throws LinkAccessException, EntityNotFoundException {
        return reGetChaptersByTitleAndQualifyIfChapterExists(title, 1D);
    }

    @Transactional
    public List<Chapter> reGetChaptersByTitleNameAndQualifyIfChapterExists(String titleName) throws LinkAccessException, EntityNotFoundException {
        return reGetChaptersByTitleAndQualifyIfChapterExists(
                titleRepository.findByName(titleName).orElseThrow(() -> new EntityNotFoundException("title", "name", titleName)),
                1D
        );
    }

    // TODO split
    @Transactional
    public List<Chapter> reGetChaptersByTitleAndQualifyIfChapterExists(Title title, Double chapterNum) throws LinkAccessException, EntityNotFoundException {
        List<ScrapChapterLink> scrapChapters = services.getService(title.getName()).scrapChapterLinks();

        int prevChaptersCount = Optional.ofNullable(title.getChapters()).orElse(new ArrayList<>()).size();
        List<Chapter> chapters = new ArrayList<>();

        scrapChapters.forEach(scrapChapterLink -> {
            try {
                chapters.add(new Chapter(
                        scrapChapterLink.getNumber(),
                        0,
                        scrapChapterLink.getLink(),
                        title
                ));
            } catch (InvalidInput e) {
                throw new RuntimeException(e);
            }
        });

        if (chapters.stream().noneMatch(chapter -> chapter.getNumber().equals(chapterNum))) {
            throw new EntityNotFoundException("chapter", "number", chapterNum.toString());
        }

        chapterRepository.deleteByTitleId(title.getId());
        if (prevChaptersCount != 0) {
            Chapter.setLastId(chapters.stream().min(Comparator.comparing(Chapter::getId)).get().getId());
        }

        if (chapters.size() > prevChaptersCount) {
            title.setLastChapter(
                    Integer.toString(
                            chapters.stream()
                                    .max(Comparator.comparing(Chapter::getNumber))
                                    .get()
                                    .getNumber()
                                    .intValue()
                    )
            );
            if (prevChaptersCount != 0) {
                title.setLastChapterUpdated(LocalDate.now());
            }
            titleRepository.save(title);
        }

        return StreamSupport.stream(chapterRepository.saveAll(chapters).spliterator(), false).collect(Collectors.toList());
    }

    @javax.transaction.Transactional
    public ChapterReadDto getChapter(String titleName, String number) throws Exception {
        Title title = titleRepository.findByName(titleName).orElseThrow(() -> new EntityNotFoundException("title", "name", titleName));
        Chapter chapter;
        Double chapNum = Double.parseDouble(number);
        Optional<Chapter> repoData = chapterRepository.getChaptersByNumberAndTitleName(chapNum, titleName);

        if (repoData.isEmpty()) {

            List<Chapter> chapters = reGetChaptersByTitleAndQualifyIfChapterExists(title);

            chapter = chapters.stream()
                    .filter(chapter1 -> chapter1.getNumber().equals(chapNum))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("chapter", "number", number));
        } else {
            chapter = repoData.get();
        }

        ChapterReadDto chapterReadDto = new ChapterReadDto(chapter.getId(), chapter.getNumber(), chapter.getVolume().toString());

        chapterReadDto.imgLinks.addAll(
                services
                        .getService(titleName)
                        .scrapChapter(new ScrapChapterLink(number, chapter.getParseUrl()))
                        .getImages()
        );

        return chapterReadDto;
    }
}
