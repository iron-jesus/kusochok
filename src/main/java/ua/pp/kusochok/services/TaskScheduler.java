package ua.pp.kusochok.services;

import org.springframework.stereotype.Service;
import ua.pp.kusochok.errors.EntityNotFoundException;
import ua.pp.kusochok.errors.web.LinkAccessException;
import ua.pp.kusochok.models.Title;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class TaskScheduler {

    private final ChapterService chapterService;

    private final int sec = 1000;
    private final int min = 60*sec;

    public TaskScheduler(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    public void scheduleUpdate(Title title) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("UPDATE STARTED FOR TITLE " + title.getName() + " " + LocalDateTime.now());
                    chapterService.reGetChaptersByTitleNameAndQualifyIfChapterExists(title.getName());
                    System.out.println("UPDATE FINISHED FOR TITLE " + title.getName() + " " + LocalDateTime.now());
                } catch (LinkAccessException | EntityNotFoundException e) {
                    if (e instanceof LinkAccessException) {
                        System.out.println("LINK ACCESS EXCEPTION LINK: " + ((LinkAccessException) e).getLink() + "\n" + "LINK ACCESS EXCEPTION CAUSE: " + ((LinkAccessException) e).getCause().getMessage());
                    } else {
                        System.out.println("ERROR FOR TITLE " + title.getName() + " " + e.getMessage());
                    }
                    e.printStackTrace();
                    this.cancel();
                }
            }
        }, 5*sec, 30*sec);
    }
}
