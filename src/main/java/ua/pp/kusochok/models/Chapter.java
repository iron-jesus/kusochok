package ua.pp.kusochok.models;

import org.hibernate.annotations.GenericGenerator;
import ua.pp.kusochok.errors.InvalidInput;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(columnDefinition = "serial")
    private Long id;

    @Column
    private Double number;

    @Column
    private Integer volume;

    @Column
    private String parseUrl;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "title_id")
    private Title title;

    private static Long lastId = 0L;

    public Chapter(Double number, Integer volume, String parseUrl, Title title) {
        this.id = lastId;
        lastId += 1;
        this.number = number;
        this.volume = volume;
        this.parseUrl = parseUrl;
        this.title = title;
    }

    public Chapter(String number, Integer volume, String parseUrl, Title title) throws InvalidInput {
        this(getFromParseNumber(number), volume, parseUrl, title);
    }

    public Chapter() {

    }

    public Long getId() {
        return id;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getParseUrl() {
        return parseUrl;
    }

    public void setParseUrl(String parseUrl) {
        this.parseUrl = parseUrl;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public static Double getFromParseNumber(String number) throws InvalidInput {
        String finalNum = "";
        String timeRegex = "(^[0-9]*)[.]?([0-9])?(.*)?";

        Pattern pattern = Pattern.compile(timeRegex);
        Matcher matcher = pattern.matcher(number);

        if (matcher.matches() || matcher.group(1).isEmpty()) {
            finalNum += matcher.group(1);
            if (matcher.group(2) != null) {
                finalNum += "." + matcher.group(2);
            } else {
                finalNum += ".0";
            }
        } else {
            throw new InvalidInput();
        }

        return Double.parseDouble(finalNum);
    }

    public static void setLastId(Long lastId) {
        Chapter.lastId = lastId;
    }
}
