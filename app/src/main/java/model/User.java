package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("code")
    private int code;
    private String msg;
    private List<Data> data;
    public User(){}

    public User(int code, String msg, List<Data> data) {
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
