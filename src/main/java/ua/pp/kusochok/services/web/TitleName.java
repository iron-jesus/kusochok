package ua.pp.kusochok.services.web;

public enum TitleName {
    ONE_PIECE("One Piece"),
    CHAINSAW_MAN("Chainsaw Man");

    private final String name;

    TitleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
