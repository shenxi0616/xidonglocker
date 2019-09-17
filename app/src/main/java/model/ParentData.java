package model;

import java.util.List;

/**
 * Created by wei on 2017/10/11.
 */

public class ParentData {
    //父选项下面的子选项的集合
    private List<ChildrenData> childrenDataList;
    //图片
    private int image;
    //上方文本
    private String top;
    //下方文本
    private String bottom;
    //外界调用时需要将数据都传入(在这里是因为数据是预先准备好的，所以需要直接填入)
    public ParentData(List<ChildrenData> childrenDataList,int image,String top,String bottom )
    {
        this.childrenDataList=childrenDataList;
        this.image=image;
        this.top=top;
        this.bottom=bottom;
    }
    public List<ChildrenData> getChildrenDataList() {
        return childrenDataList;
    }

    public void setChildrenDataList(List<ChildrenData> childrenDataList) {
        this.childrenDataList = childrenDataList;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }
}
