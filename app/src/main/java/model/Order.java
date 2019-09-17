package model;

import java.util.List;

public class Order{
    private int code;
    private String msg;
    private List<OrderList> data;

    public Order(int code, String msg, List<model.OrderList> orderList) {
        this.code = code;
        this.msg = msg;
        data = orderList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<model.OrderList> getData() {
        return data;
    }

    public void setData(List<model.OrderList> orderList) {
        data = orderList;
    }
}
