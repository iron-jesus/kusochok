package ua.pp.kusochok.services;

import org.springframework.stereotype.Component;
import ua.pp.kusochok.errors.EntityNotFoundException;
import ua.pp.kusochok.models.Title;
import ua.pp.kusochok.repositories.TitleRepository;
import ua.pp.kusochok.rest.dto.TitleAddDto;
import ua.pp.kusochok.services.web.IScrapper;
import ua.pp.kusochok.services.web.ScrapperServices;

import java.util.Arrays;
import java.util.List;

@Component
public class TitleService {
    private final ScrapperServices services;

    private final TitleRepository titleRepository;
    private final TaskScheduler taskScheduler;

    public TitleService(ScrapperServices services, TitleRepository titleRepository, TaskScheduler taskScheduler) {
        this.services = services;
        this.titleRepository = titleRepository;
        this.taskScheduler = taskScheduler;
    }

    public void doSomethingWithTitleName(String titleName){
        IScrapper service = services.getService(titleName);
        System.out.println(service.getTitleName());
    }

    public List<Title> getAll() {
        return titleRepository.findAll();
    }

    public Title getByName(String name) throws EntityNotFoundException {
        return titleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("title", "name", name));
    }

    // Qualifier is Title Name transformed to title-name
    public Title getByQualifier(String qualifier) throws EntityNotFoundException {
        return getByName(getTitleNameByQualifier(qualifier));
    }

    public static String getTitleNameByQualifier(String qualifier) {
        return String.join(
                " ",
                Arrays.stream(qualifier.split("-"))
                        .map(el-> el.substring(0, 1).toUpperCase() + el.substring(1)).toList()
        );
    }

    public Title addTitle(TitleAddDto titleAddDto) {
        Title title = titleRepository.save(new Title(
                titleAddDto.name,
                "0",
                titleAddDto.author,
                titleAddDto.releaseYear,
                titleAddDto.status,
                titleAddDto.photoUrl
        ));

        sceduleUpdate(title);

        return title;
    }

    public void sceduleUpdate(Title title) {
        taskScheduler.scheduleUpdate(title);
    }
}
