package adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a10692.xidonglocker.MainActivity;
import com.example.a10692.xidonglocker.Order.OrderInfoActivity;
import com.example.a10692.xidonglocker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ItemOrder;
import model.ItemOtherOrder;

public class OrderAdapter extends RecyclerView.Adapter {
    private List<ItemOrder> mList;//数据源

    private Context context;    // 上下文Context
    String TAG = OrderAdapter.class.getCanonicalName();
    private int normalType = 0;     // 第一种ViewType，正常的item
//    private int footType = 1;       // 第二种ViewType，底部的提示View
    private int otherType = 2;       // 第三种ViewType，非储物柜类型的item
    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView start_time,date,address,boxtype,str_boxtype,status,order_type,price;
        private LinearLayout linearLayout;
        private RelativeLayout rel_overtime;
        public ViewHolder(View view){
            super(view);
            start_time = view.findViewById(R.id.order_time);
            date = view.findViewById(R.id.order_date);
            address = view.findViewById(R.id.order_adress);
            boxtype = view.findViewById(R.id.order_boxtype);
            str_boxtype = view.findViewById(R.id.str_boxtype);
            order_type = view.findViewById(R.id.order_type);
            status = view.findViewById(R.id.order_status);
            linearLayout = view.findViewById(R.id.lin_order_item);
            rel_overtime = view.findViewById(R.id.rel_overtime);
            price = view.findViewById(R.id.price);
        }
    }

    class OtherHolder extends RecyclerView.ViewHolder{
        TextView start_time,date,status,order_type;
        private LinearLayout linearLayout;
        public OtherHolder(View view){
            super(view);
            start_time = view.findViewById(R.id.order_time);
            date = view.findViewById(R.id.order_date);
            order_type = view.findViewById(R.id.order_type);
            status = view.findViewById(R.id.order_status);
            linearLayout = view.findViewById(R.id.lin_order_item);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == normalType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            return new OrderAdapter.ViewHolder(view);
        }else if(viewType == otherType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_other_item, parent, false);
            return new OrderAdapter.OtherHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_other_item, parent, false);
            return new OrderAdapter.OtherHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        // 如果是正常的item，直接设置TextView的值
        if (holder instanceof ViewHolder) {
            final ItemOrder itemOrder = mList.get(position);
            ((ViewHolder) holder).address.setText(itemOrder.getAddress());
            ((ViewHolder) holder).date.setText(itemOrder.getCreate_time().substring(0,10));
            ((ViewHolder) holder).start_time.setText(itemOrder.getCreate_time().substring(11));
            ((ViewHolder) holder).order_type.setText(itemOrder.getBoxtype());

            ((ViewHolder) holder).boxtype.setText(itemOrder.getBoxsize());
            ((ViewHolder) holder).price.setText(itemOrder.getPaid());
            //0超时待支付，1进行中，2已结束
            if (itemOrder.getStatus() == 0){//判断订单是否在进行
                ((ViewHolder) holder).status.setText("待支付");

            }else if(itemOrder.getStatus() == 1){
                ((ViewHolder) holder).status.setText("进行中");

            }else {
                ((ViewHolder) holder).status.setText("已结束");
            }
            //判断是否超时
            Log.e(TAG,"OrderAdapter-->itemOrder.getOvertime()-->"+itemOrder.getOvertime());
            if (itemOrder.getOvertime() == 1){
                ((ViewHolder) holder).rel_overtime.setVisibility(View.VISIBLE);

            }

            ((ViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderInfoActivity.actionStart(v.getContext(),itemOrder);
                }
            });
        }else if(holder instanceof OtherHolder){//不属于储物柜订单的item的布局
            final ItemOrder itemOrder = mList.get(position);
            ((OtherHolder) holder).date.setText(itemOrder.getStart_time().substring(0,10));
            ((OtherHolder) holder).start_time.setText(itemOrder.getStart_time().substring(11));
            ((OtherHolder) holder).order_type.setText(itemOrder.getBoxtype());
            Log.e(TAG,"tag-->"+itemOrder.getBoxsize());
            if (itemOrder.getStatus() == 0){//判断订单是否在进行
                ((OtherHolder) holder).status.setText("待支付");
            }else if(itemOrder.getStatus() == 1){
                ((OtherHolder) holder).status.setText("进行中");
            }else {
            }
            ((OtherHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderInfoActivity.actionStart(v.getContext(),itemOrder);
                }
            });
        }else {

        }

    }


    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        mList = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<ItemOrder> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            mList.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    //传入展示数据,并且赋值给全局变量
    public OrderAdapter(List<ItemOrder> List){
        mList=List;
    }
    public OrderAdapter(List<ItemOrder> List,Context context, boolean hasMore){
        this.mList=List;
        this.context = context;
        this.hasMore = hasMore;
    }
//    //获取RecyclerView有多少子项
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return mList.size();
    }

//
    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (!(mList.get(position).getBoxtype().equals("使用储物柜"))) {
            return otherType;
        } else {
            return normalType;
        }
    }
}
