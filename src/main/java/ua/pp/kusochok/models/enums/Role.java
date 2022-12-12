package ua.pp.kusochok.models.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(
            Permission.USER_TITLES_READ,
            Permission.USER_TITLES_WRITE,
            Permission.USER_CHAPTER_READ,
            Permission.USER_CHAPTER_WRITE
    )),
    ADMIN(Set.of(
            Permission.USER_TITLES_READ,
            Permission.USER_TITLES_WRITE,
            Permission.USER_CHAPTER_READ,
            Permission.USER_CHAPTER_WRITE,

            Permission.TITLE_WRITE,
            Permission.CHAPTER_WRITE,
            Permission.USER_READ,
            Permission.USER_WRITE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
