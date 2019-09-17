package model;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("code")
    private int user_code;
    private String nickname;
    private String account;
    private int identification_is_identify;
    private String register_time;
    private String email;
    private String profile_photo_url;
    private int balance;
    private String token;

    public Data(int user_code, String nickname, String account, int identification_is_identify, String register_time, String email, String profile_photo_url, int balance, String token) {
        this.user_code = user_code;
        this.nickname = nickname;
        this.account = account;
        this.identification_is_identify = identification_is_identify;
        this.register_time = register_time;
        this.email = email;
        this.profile_photo_url = profile_photo_url;
        this.balance = balance;
        this.token = token;
    }

    public int getUser_code() {
        return user_code;
    }

    public void setUser_code(int user_code) {
        this.user_code = user_code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getIdentification_is_identify() {
        return identification_is_identify;
    }

    public void setIdentification_is_identify(int identification_is_identify) {
        this.identification_is_identify = identification_is_identify;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_photo_url() {
        return profile_photo_url;
    }

    public void setProfile_photo_url(String profile_photo_url) {
        this.profile_photo_url = profile_photo_url;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
