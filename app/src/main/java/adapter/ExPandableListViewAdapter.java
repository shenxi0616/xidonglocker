package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.R;

import java.util.ArrayList;
import java.util.List;

import model.ChildrenData;
import model.FatherData;

public class ExPandableListViewAdapter extends BaseExpandableListAdapter {
    // 定义一个Context
    private Context context;
    // 定义一个LayoutInflater
    private LayoutInflater mInflater;
    // 定义一个List来保存列表数据
    private ArrayList<FatherData> data_list = new ArrayList<>();
    private ChildrenData son_List ;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private final int TYPE_1 = 0;
    private final int TYPE_2 = 1;
    int num = 0;
    ArrayList<RadioButton> test = new ArrayList<>();
    String TAG =ExPandableListViewAdapter.class.getCanonicalName();

    // 定义一个构造方法
    public ExPandableListViewAdapter(Context context, ArrayList<FatherData> datas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data_list = datas;
    }

    // 刷新数据
    public void flashData(ArrayList<FatherData> datas) {
        this.data_list = datas;
        this.notifyDataSetChanged();
    }

    // 获取二级列表的内容
    @Override
    public Object getChild(int arg0, int arg1) {
        return data_list.get(arg0).getList().get(arg1);
    }

    // 获取二级列表的ID
    @Override
    public long getChildId(int arg0, int arg1) {
        return arg1;
    }

    //获取子项的布局类型
    public int getChildType(int groupPosition, int childPosition) {
//        HolderView childrenView = new HolderView();
        int type = data_list.get(groupPosition).getList().get(childPosition).getType();
        if (type == 0) {
            return TYPE_1;
        }
        return TYPE_2;
    }
//    !!!获取子布局类型的个数，不加这个会报数组越界错误
    public int getChildTypeCount() {
        return 2;
    }


    // 定义二级列表中的数据
    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {
        // 定义一个二级列表的视图类

        int type = getChildType(arg0,arg1);
        son_List = data_list.get(arg0).getList().get(arg1);
        HolderView2 childrenView2 = null;
        switch (type){
            case 0:
                HolderView childrenView;
                if (arg3 == null) {

                    childrenView = new HolderView();
                    // 获取子视图的布局文件
                    arg3 = mInflater.inflate(R.layout.expand_child_item, arg4, false);
                    childrenView.titleView = (TextView) arg3.findViewById(R.id.alarm_clock_tv1);
                    childrenView.descView = (TextView) arg3.findViewById(R.id.alarm_clock_tv2);
                    // 这个函数是用来将holderview设置标签,相当于缓存在view当中
                    arg3.setTag(childrenView);
                } else {
                    childrenView = (HolderView) arg3.getTag();
                }

                /**
                 * 设置相应控件的内容
                 */
                // 设置标题上的文本信息
                childrenView.titleView.setText(son_List.getTitle());
                // 设置副标题上的文本信息
                childrenView.descView.setText(son_List.getDesc());

                break;
            case 1:
                if (arg3 == null) {
                    childrenView2 = new HolderView2();
                    // 获取子视图的布局文件
                    arg3 = mInflater.inflate(R.layout.expand_child_item2, arg4, false);
                    childrenView2.radioGroup = arg3.findViewById(R.id.radiogroup);

                    addview(childrenView2.radioGroup,son_List.getCouponType());

                    // 这个函数是用来将holderview设置标签,相当于缓存在view当中
                    arg3.setTag(childrenView2);

                } else {
                    childrenView2 = (HolderView2) arg3.getTag();
                }
                Log.e(TAG,"after-->"+childrenView2.radioGroup.getChildCount()+"id-->");
                break;
        }

        return arg3;


    }
    private void addview(RadioGroup radioGroup,String text){
        radioButton=new RadioButton(context);
        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        //设置RadioButton边距 (int left, int top, int right, int bottom)
        lp.setMargins(15,0,0,0);
        LayoutInflater inflater = LayoutInflater.from(context);
        radioButton = (RadioButton) inflater.inflate(R.layout.expand_radio_item, null);

        radioButton.setId(num++);
        radioButton.setText(text);

        Log.e(TAG,"buttonid-->"+radioButton.getId());
        Log.e(TAG,"radioGroup.getChildCount()-->"+radioGroup.getChildCount());

        radioGroup.addView(radioButton);

    }


    // 保存二级列表的视图类
    private class HolderView {
        TextView titleView;
        TextView descView;
    }

    private class HolderView2 {
        RadioGroup radioGroup;
    }

    // 获取二级列表的数量
    @Override
    public int getChildrenCount(int arg0) {
        return data_list.get(arg0).getList().size();
    }

    // 获取一级列表的数据
    @Override
    public Object getGroup(int arg0) {
        return data_list.get(arg0);
    }

    // 获取一级列表的个数
    @Override
    public int getGroupCount() {
        return data_list.size();
    }

    // 获取一级列表的ID
    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    // 设置一级列表的view
    @Override
    public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
        HodlerViewFather hodlerViewFather;
        if (arg2 == null) {
            hodlerViewFather = new HodlerViewFather();
            arg2 = mInflater.inflate(R.layout.expand_item, arg3, false);
            hodlerViewFather.titlev = (TextView) arg2.findViewById(R.id.alarm_clock_father_tv);
            // 新建一个TextView对象，用来显示一级标签上的大体描述的信息
            hodlerViewFather.group_state = (ImageView) arg2.findViewById(R.id.group_state);
            arg2.setTag(hodlerViewFather);
        } else {
            hodlerViewFather = (HodlerViewFather) arg2.getTag();
        }
        // 一级列表右侧判断箭头显示方向
        if (arg1) {
            hodlerViewFather.group_state.setImageResource(R.drawable.down16);
        } else {
            hodlerViewFather.group_state.setImageResource(R.drawable.next16);
        }
        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        hodlerViewFather.titlev.setText(data_list.get(arg0).getTitle());

        // 返回一个布局对象
        return arg2;
    }

    // 定义一个 一级列表的view类
    private class HodlerViewFather {
        TextView titlev;
        ImageView group_state;
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 当选择子节点的时候，调用该方法(点击二级列表)
     */
    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }
}