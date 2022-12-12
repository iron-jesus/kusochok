package ua.pp.kusochok.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.pp.kusochok.models.Chapter;
import ua.pp.kusochok.models.Title;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends CrudRepository<Chapter, Long> {
    List<Chapter> getChaptersByTitleName(String titleName);
    Optional<Chapter> getChaptersByNumberAndTitleName(Double number, String titleName);
    void deleteAllByTitleName(String titleName);
    Long deleteByTitleId(Long titleId);
}
