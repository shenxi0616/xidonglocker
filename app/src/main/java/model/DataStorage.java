package model;

public class DataStorage {
    private String now_storage_code;
    private String son_storage_code;
    private String storage_code;
    private String location;
    private String dimensions;
    private String type;
    private String description;
    private String storage_order_code;
    private int status;

    public DataStorage(String now_storage_code, String son_storage_code, String storage_code, String location, String dimensions, String type, String description, int status) {
        this.now_storage_code = now_storage_code;
        this.son_storage_code = son_storage_code;
        this.storage_code = storage_code;
        this.location = location;
        this.dimensions = dimensions;
        this.type = type;
        this.description = description;
        this.status = status;
    }

    public String getStorage_order_code() {
        return storage_order_code;
    }

    public void setStorage_order_code(String storage_order_code) {
        this.storage_order_code = storage_order_code;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNow_storage_code() {
        return now_storage_code;
    }

    public void setNow_storage_code(String now_storage_code) {
        this.now_storage_code = now_storage_code;
    }

    public String getSon_storage_code() {
        return son_storage_code;
    }

    public void setSon_storage_code(String son_storage_code) {
        this.son_storage_code = son_storage_code;
    }

    public String getStorage_code() {
        return storage_code;
    }

    public void setStorage_code(String storage_code) {
        this.storage_code = storage_code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
