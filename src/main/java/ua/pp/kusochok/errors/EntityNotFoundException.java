package ua.pp.kusochok.errors;

public class EntityNotFoundException extends Exception {

    private String entity;
    private String field;
    private String value;


    public EntityNotFoundException(String entity, String field, String value) {
        super("No " + entity + " by " + field + "='" + value + "' found");

        this.entity = entity;
        this.field = field;
        this.value = value;
    }
}
