package model;

import java.io.Serializable;

public class ItemOrder implements Serializable{//实现Serializable接口，将一个对象转换成可存储或可传输的状态
    private String create_time;//创建时间
    private String start_time;//开始时间
    private String end_time;//结束时间,如果状态为1则为当前时间
    private String address;//储物柜地址
    private String boxtype;//订单类型
    private String boxsize;//储物柜类型
    private String coupon;//优惠卷
    private String payway;//支付方式
    private String paid;//已支付金额
    private int overtime = 0;//超时
    private int status;//储物柜状态,0代表超时待支付，1代表进行中,2代表结束
    private String id;//订单编号


    public ItemOrder(String start_time, String end_time, String address, String boxtype, int status, String coupon) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.address = address;
        this.boxtype = boxtype;
        this.status = status;
        this.coupon = coupon;
    }
    public ItemOrder(String start_time,String end_time,String address, String boxtype, int status) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.address = address;
        this.boxtype = boxtype;
        this.status = status;
    }
    public ItemOrder(String address, String boxtype, int status) {
        this.address = address;
        this.boxtype = boxtype;
        this.status = status;
    }
    public ItemOrder(String create_time,String address,String boxtype,String paid,String id,int status) {
        this.create_time = create_time;
        this.address = address;
        this.boxtype = boxtype;
        this.status = status;
        this.paid = paid;
        this.id = id;
    }
    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBoxtype() {
        return boxtype;
    }

    public void setBoxtype(String boxtype) {
        this.boxtype = boxtype;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getBoxsize() {
        return boxsize;
    }

    public void setBoxsize(String boxsize) {
        this.boxsize = boxsize;
    }
}
