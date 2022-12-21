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

            Title onePiece = titleService.addTitle(
                    new TitleAddDto(
                            "One Piece",
                            "Eiichiro Oda",
                            1997,
                            TitleStatus.ONGOING,
                            ""
                    )
            );

//            titleService.sceduleUpdate(onePiece);

            Title vinlandSaga = titleService.addTitle(
                    new TitleAddDto(
                            "Vinland Saga",
                            "Makoto Yukimura",
                            2005,
                            TitleStatus.ONGOING,
                            ""
                    )
            );

//            titleService.sceduleUpdate(vinlandSaga);
        };
    }
}
