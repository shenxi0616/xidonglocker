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

import java.util.ArrayList;
import java.util.List;

import model.ItemOrder;


//已完成订单的adapter
public class OrderUnDoneAdapter extends RecyclerView.Adapter {
    private List<ItemOrder> mList;//数据源
    String TAG = MainActivity.class.getCanonicalName();
    private Context context;    // 上下文Context

    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private int otherType = 2;      // 第三钟ViewType，非储物柜类型的item
    
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
            rel_overtime = view.findViewById(R.id.rel_overtime);
            linearLayout = view.findViewById(R.id.lin_order_item);
            price = view.findViewById(R.id.price);
        }
    }
    // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }
    //非储物柜类型的ViewHolder
    class OtherHolder extends RecyclerView.ViewHolder {
        TextView start_time, date, status, order_type;
        private LinearLayout linearLayout;

        public OtherHolder(View view) {
            super(view);
            start_time = view.findViewById(R.id.order_time);
            date = view.findViewById(R.id.order_date);
            order_type = view.findViewById(R.id.order_type);
            status = view.findViewById(R.id.order_status);
            linearLayout = view.findViewById(R.id.lin_order_item);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == normalType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            return new OrderUnDoneAdapter.ViewHolder(view);
        }else if (viewType == otherType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_other_item, parent, false);
            return new OrderUnDoneAdapter.OtherHolder(view);
        }else {
            return new OrderUnDoneAdapter.FootHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.footview, null));
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        // 如果是正常的imte，直接设置TextView的值
        if (holder instanceof OrderUnDoneAdapter.ViewHolder) {
            final ItemOrder itemOrder = mList.get(position);
            Log.e(TAG,"tag-->"+itemOrder.getBoxtype());
            ((OrderUnDoneAdapter.ViewHolder) holder).address.setText(itemOrder.getAddress());
            ((OrderUnDoneAdapter.ViewHolder) holder).boxtype.setText(itemOrder.getBoxtype());
            ((OrderUnDoneAdapter.ViewHolder) holder).date.setText(itemOrder.getCreate_time().substring(0,10));
            ((OrderUnDoneAdapter.ViewHolder) holder).start_time.setText(itemOrder.getCreate_time().substring(11));
            ((OrderUnDoneAdapter.ViewHolder) holder).order_type.setText(itemOrder.getBoxtype());
            ((OrderUnDoneAdapter.ViewHolder) holder).price.setText(itemOrder.getPaid());
            if (!(itemOrder.getBoxsize() == null)){//判断订单是否是使用储物柜
                ((OrderUnDoneAdapter.ViewHolder) holder).boxtype.setText(itemOrder.getBoxsize());
            }
            //判断是否超时
            Log.e(TAG,"OrderAdapter-->itemOrder.getOvertime()-->"+itemOrder.getOvertime());
            if (itemOrder.getOvertime() == 1){
                ((OrderUnDoneAdapter.ViewHolder) holder).rel_overtime.setVisibility(View.VISIBLE);

            }
            ((OrderUnDoneAdapter.ViewHolder) holder).status.setText("已结束");
            ((OrderUnDoneAdapter.ViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderInfoActivity.actionStart(v.getContext(),itemOrder);
                }
            });
        }else if(holder instanceof OrderUnDoneAdapter.OtherHolder){
            final ItemOrder itemOrder = mList.get(position);
            ((OrderUnDoneAdapter.OtherHolder) holder).date.setText(itemOrder.getCreate_time().substring(0,10));
            ((OrderUnDoneAdapter.OtherHolder) holder).start_time.setText(itemOrder.getCreate_time().substring(11));
            ((OrderUnDoneAdapter.OtherHolder) holder).order_type.setText(itemOrder.getBoxtype());
//            Log.e(TAG,"tag-->"+itemOrder.getBoxsize());
            ((OrderUnDoneAdapter.OtherHolder) holder).status.setText("已结束");
            ((OrderUnDoneAdapter.OtherHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderInfoActivity.actionStart(v.getContext(),itemOrder);
                }
            }); 
        }else {
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((OrderUnDoneAdapter.FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (mList.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((OrderUnDoneAdapter.FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (mList.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((OrderUnDoneAdapter.FootHolder) holder).tips.setText("没有更多数据了");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((OrderUnDoneAdapter.FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }
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
    public OrderUnDoneAdapter(List<ItemOrder> List){
        mList=List;
    }
    public OrderUnDoneAdapter(List<ItemOrder> List,Context context, boolean hasMore){
        this.mList=List;
        this.context = context;
        this.hasMore = hasMore;
    }
//    //获取RecyclerView有多少子项
//    @Override
//    public int getItemCount() {
//        return mList.size();
//    }

    // 获取条目数量，之所以要加1是因为增加了一条footView
    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return mList.size();
    }


    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        }else if(!(mList.get(position).getBoxtype().equals("使用储物柜"))){
            return otherType;
        }else {
            return normalType;
        }
    }
}
