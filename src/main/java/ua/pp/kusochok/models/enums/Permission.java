package ua.pp.kusochok.models.enums;

public enum Permission {
    USER_TITLES_READ("user_titles:read"),
    USER_TITLES_WRITE("user_titles:write"),
    CHAPTER_WRITE("chapter:write"),
    TITLE_WRITE("title:write"),
    USER_CHAPTER_READ("user_chapter:read"),
    USER_CHAPTER_WRITE("user_chapter:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
