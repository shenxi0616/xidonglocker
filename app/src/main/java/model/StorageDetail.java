package model;

import java.util.List;

public class StorageDetail {
    private int code;
    private String msg;
    private StorageDetailInfo data;

    public StorageDetail(int code, String msg,StorageDetailInfo orderList) {
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

    public StorageDetailInfo getData() {
        return data;
    }

    public void setData(StorageDetailInfo orderList) {
        data = orderList;
    }

}
