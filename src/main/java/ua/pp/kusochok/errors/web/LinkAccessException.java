package ua.pp.kusochok.errors.web;

public class LinkAccessException  extends Exception {
    private final String link;
    private final Exception cause;

    public LinkAccessException(String link, Exception cause) {
        super("Failed to get " + link);
        this.link = link;
        this.cause = cause;
    }

    public String getLink() {
        return link;
    }

    @Override
    public Exception getCause() {
        return cause;
    }
}
