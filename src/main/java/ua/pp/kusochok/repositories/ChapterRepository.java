package ua.pp.kusochok.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.pp.kusochok.models.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends CrudRepository<Chapter, Long> {
    List<Chapter> getChaptersByTitleNameOrderByNumber(String titleName);
    Optional<Chapter> getChaptersByNumberAndTitleNameOrderByNumberDesc(Double number, String titleName);
    void deleteAllByTitleName(String titleName);
    Long deleteByTitleId(Long titleId);

    Long deleteChapterById(Long id);
}
