package Gson;

public class CreateOrderList {
    /**
     * storage_order_code : 024461035335
     * now_storage_code : 12345
     * start_time : null
     * end_time : null
     * pre_end_time : 2019-08-30 12:35:28.0
     * create_time : 2019-08-30 10:35:28.0
     * order_status : 0
     * has_overtime : null
     * account : 17765602446
     * has_pay_fare : 3.0
     */

    private String storage_order_code;
    private String now_storage_code;
    private Object start_time;
    private Object end_time;
    private String pre_end_time;
    private String create_time;
    private int order_status;
    private Object has_overtime;
    private String account;
    private float has_pay_fare;

    public String getStorage_order_code() {
        return storage_order_code;
    }

    public void setStorage_order_code(String storage_order_code) {
        this.storage_order_code = storage_order_code;
    }

    public String getNow_storage_code() {
        return now_storage_code;
    }

    public void setNow_storage_code(String now_storage_code) {
        this.now_storage_code = now_storage_code;
    }

    public Object getStart_time() {
        return start_time;
    }

    public void setStart_time(Object start_time) {
        this.start_time = start_time;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
        this.end_time = end_time;
    }

    public String getPre_end_time() {
        return pre_end_time;
    }

    public void setPre_end_time(String pre_end_time) {
        this.pre_end_time = pre_end_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public Object getHas_overtime() {
        return has_overtime;
    }

    public void setHas_overtime(Object has_overtime) {
        this.has_overtime = has_overtime;
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

    public void setHas_pay_fare(float has_pay_fare) {
        this.has_pay_fare = has_pay_fare;
    }
}
