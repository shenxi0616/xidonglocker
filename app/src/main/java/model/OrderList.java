package model;

public class OrderList {
    private String order_status;
    private String create_time;
    private String location;
    private String storage_order_code;
    private String account;
    private String type;
    private float has_pay_fare;
    private int has_overtime;

    public OrderList(String order_status, String create_time, String location, String storage_order_code, String account, float has_pay_fare, int has_overtime) {
        this.order_status = order_status;
        this.create_time = create_time;
        this.location = location;
        this.storage_order_code = storage_order_code;
        this.account = account;
        this.has_pay_fare = has_pay_fare;
        this.has_overtime = has_overtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHas_pay_fare(float has_pay_fare) {
        this.has_pay_fare = has_pay_fare;
    }

    public void setHas_overtime(int has_overtime) {
        this.has_overtime = has_overtime;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStorage_order_code() {
        return storage_order_code;
    }

    public void setStorage_order_code(String storage_order_code) {
        this.storage_order_code = storage_order_code;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public float getHas_pay_fare() {
        return has_pay_fare;
    }

    public void setHas_pay_fare(Integer has_pay_fare) {
        this.has_pay_fare = has_pay_fare;
    }

    public int getHas_overtime() {
        return has_overtime;
    }

    public void setHas_overtime(Integer has_overtime) {
        this.has_overtime = has_overtime;
    }
}
