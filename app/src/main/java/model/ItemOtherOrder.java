package model;

import java.io.Serializable;

public class ItemOtherOrder implements Serializable {
    private String start_time;//开始时间
    private String payway;//支付方式
    private String boxtype;//订单类型
    private String orderId;//订单编号
    private String coupom;//优惠卷类型

    public ItemOtherOrder(String start_time, String payway, String boxtype, String orderId, String coupom) {
        this.start_time = start_time;
        this.payway = payway;
        this.boxtype = boxtype;
        this.orderId = orderId;
        this.coupom = coupom;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getBoxtype() {
        return boxtype;
    }

    public void setBoxtype(String boxtype) {
        this.boxtype = boxtype;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCoupom() {
        return coupom;
    }

    public void setCoupom(String coupom) {
        this.coupom = coupom;
    }
}
