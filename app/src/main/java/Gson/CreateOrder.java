package Gson;

import java.util.List;

import model.OrderList;

public class CreateOrder {
    private int code;
    private String msg;
    private CreateOrderList data;

    public CreateOrder(int code, String msg, CreateOrderList data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public CreateOrderList getData() {
        return data;
    }

    public void setData(CreateOrderList data) {
        this.data = data;
    }
}
