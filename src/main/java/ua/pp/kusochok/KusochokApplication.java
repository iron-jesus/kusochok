package ua.pp.kusochok;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.pp.kusochok.models.Title;
import ua.pp.kusochok.models.enums.TitleStatus;
import ua.pp.kusochok.rest.dto.SignUpRequestDto;
import ua.pp.kusochok.rest.dto.TitleAddDto;
import ua.pp.kusochok.services.AuthService;
import ua.pp.kusochok.services.TitleService;

@SpringBootApplication
public class KusochokApplication {

    private final AuthService authService;
    private final TitleService titleService;

    public KusochokApplication(AuthService authService, TitleService titleService) {
        this.authService = authService;
        this.titleService = titleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(KusochokApplication.class, args);
    }

    @Bean
    ApplicationRunner appStarted() {
        return args -> {
            authService.register(new SignUpRequestDto(
                    "jajebav", "asdads", "asdasd", "asdasd"
            ));

            titleService.addTitle(
                    new TitleAddDto(
                            "One Piece",
                            "Eiichiro Oda",
                            1997,
                            TitleStatus.ONGOING,
                            "https://m.media-amazon.com/images/M/MV5BODcwNWE3OTMtMDc3MS00NDFjLWE1OTAtNDU3NjgxODMxY2UyXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg"
                    )
            );

            titleService.addTitle(
                    new TitleAddDto(
                            "Vinland Saga",
                            "Makoto Yukimura",
                            2005,
                            TitleStatus.ONGOING,
                            "https://uageek.space/wp-content/uploads/2021/02/Vinland-Saga-.jpg"
                    )
            );

            titleService.addTitle(
                    new TitleAddDto(
                            "Chainsaw Man",
                            "Tatsuki Fujimoto",
                            2018,
                            TitleStatus.ONGOING,
                            "https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781974709939/chainsaw-man-vol-1-9781974709939_hr.jpg"
                    )
            );

            // TODO: add services

//            titleService.addTitle(
//                    new TitleAddDto(
//                            "Dr. Stone",
//                            "Tatsuki Fujimoto",
//                            2018,
//                            TitleStatus.PAUSED,
//                            "https://c4.wallpaperflare.com/wallpaper/449/510/491/anime-boys-anime-dr-stone-hd-wallpaper-preview.jpg"
//                    )
//            );
//
//            titleService.addTitle(
//                    new TitleAddDto(
//                            "Tokyo Revengers",
//                            "Tatsuki Fujimoto",
//                            2018,
//                            TitleStatus.CLOSED,
//                            "https://kbimages1-a.akamaihd.net/51d5c16d-ec51-46c5-ad4d-2ccec0fc1247/1200/1200/False/tokyo-revengers-17.jpg"
//                    )
//            );
        };
    }
}
