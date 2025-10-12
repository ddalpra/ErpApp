package it.ddalpra.acme.erpApp.item.entity;

public enum ItemStatus {
    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive");

    private final int code;
    private final String description;

    ItemStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }   
}
