package ua.pp.kusochok.models;

import ua.pp.kusochok.models.enums.ImageFill;
import ua.pp.kusochok.models.enums.PageSwitch;
import ua.pp.kusochok.models.enums.ScrollVariant;

import javax.persistence.*;

@Entity
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ScrollVariant scrollVariant;

    @Column
    @Enumerated(EnumType.STRING)
    private ImageFill imageFill;

    @Column
    @Enumerated(EnumType.STRING)
    private PageSwitch pageSwitch;

    @Column()
    private Long containerWidth;

    @OneToOne(mappedBy = "settings")
    private User user;

    public Settings(ScrollVariant scrollVariant, ImageFill imageFill, PageSwitch pageSwitch, Long containerWidth) {
        this.scrollVariant = scrollVariant;
        this.imageFill = imageFill;
        this.pageSwitch = pageSwitch;
        this.containerWidth = containerWidth;
    }

    public Settings(User user) {
        this(ScrollVariant.VERTICAL, ImageFill.CONTAINTER_WIDTH, PageSwitch.ALL_SCREEN, 1100L);
        this.user = user;
    }

    public Settings() {
        this(ScrollVariant.VERTICAL, ImageFill.CONTAINTER_WIDTH, PageSwitch.ALL_SCREEN, 1100L);
    }

    public Long getId() {
        return id;
    }

    public ScrollVariant getScrollVariant() {
        return scrollVariant;
    }

    public ImageFill getImageFill() {
        return imageFill;
    }

    public PageSwitch getPageSwitch() {
        return pageSwitch;
    }

    public Long getContainerWidth() {
        return containerWidth;
    }

    public User getUser() {
        return user;
    }

    public void setScrollVariant(ScrollVariant scrollVariant) {
        this.scrollVariant = scrollVariant;
    }

    public void setImageFill(ImageFill imageFill) {
        this.imageFill = imageFill;
    }

    public void setPageSwitch(PageSwitch pageSwitch) {
        this.pageSwitch = pageSwitch;
    }

    public void setContainerWidth(Long containerWidth) {
        this.containerWidth = containerWidth;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
