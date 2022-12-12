package ua.pp.kusochok.errors;

public class UsernameAlreadyExists extends Exception {
    private final String username;

    public UsernameAlreadyExists(String username) {
        super("User by username " + username + " already exists");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
