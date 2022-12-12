package ua.pp.kusochok.repositories;

import org.springframework.data.repository.CrudRepository;
import ua.pp.kusochok.models.Title;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends CrudRepository<Title, Long> {
    Optional<Title> findByName(String name);
    @Override
    List<Title> findAll();
}
