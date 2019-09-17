package model;

public class ItemBean {
    private int IamgeId;
    private String Title;
    private String title_data;



    private int type = 0;

    public ItemBean(int IamgeId, String Title,String title_data,int type) {
        this.IamgeId=IamgeId;
        this.Title=Title;
        this.title_data = title_data;
        this.type = type;
    }

    public int getIamgeId() {
        return IamgeId;
    }

    public void setIamgeId(int iamgeId) {
        IamgeId = iamgeId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public String getTitle_data() {
        return title_data;
    }

    public void setTitle_data(String title_data) {
        this.title_data = title_data;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}