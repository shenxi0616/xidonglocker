package model;

import java.util.List;

public class Storage {
    private int code;
    private String msg;
    private DataStorage data;

    public Storage(int code, String msg, DataStorage data) {
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

    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }
}
