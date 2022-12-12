package ua.pp.kusochok.models;

import ua.pp.kusochok.models.enums.Level;
import ua.pp.kusochok.models.enums.Scope;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Level level;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Scope scope;

    @Column
    private LocalDateTime time;

    @Column
    private String message;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
}
