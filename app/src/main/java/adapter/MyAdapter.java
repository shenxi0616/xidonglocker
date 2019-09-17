package adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.InfoChange.ChangeNameActivity;
import com.example.a10692.xidonglocker.InfoChange.ChangePhoneActivity;
import com.example.a10692.xidonglocker.InfoChange.IdentityActivity;
import com.example.a10692.xidonglocker.LoginActivity;
import com.example.a10692.xidonglocker.R;

import java.util.List;

import model.ItemBean;

import static android.content.Context.MODE_PRIVATE;


public class MyAdapter extends RecyclerView.Adapter{
    private List<ItemBean> mList;
    public static final int TYPE_BUTTON = 1;
    public static final int TYPE_VIEW = 0;
    private SharedPreferences sp;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleTextView;
        TextView dataTextView;
        RelativeLayout relativeLayout;



        public ViewHolder(View view){
            super(view);
            imageView=(ImageView)view.findViewById(R.id.image);
            titleTextView=(TextView)view.findViewById(R.id.title);
            dataTextView = view.findViewById(R.id.title_data);
            relativeLayout=(RelativeLayout) view.findViewById(R.id.layout);

        }
    }
    public class ButtonHolder extends RecyclerView.ViewHolder{
        Button btn;
        public ButtonHolder(View view){
            super(view);
            btn = view.findViewById(R.id.btn_exit);
        }
    }

    //传入展示数据,并且赋值给全局变量
    public MyAdapter(List<ItemBean> List){
        mList=List;
    }

    //创建ViewHolder实例,将item布局加载出来
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item, parent, false);
            return new ViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_item, parent, false);
            return new ButtonHolder(view);
        }
    }

    //对RecyclerView的子项数据进行赋值
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ItemBean itemBean = mList.get(position);

            ((ViewHolder) holder).imageView.setImageResource(itemBean.getIamgeId());
            ((ViewHolder) holder).titleTextView.setText(itemBean.getTitle());
            ((ViewHolder) holder).dataTextView.setText(itemBean.getTitle_data());
            //点击事件
            ((ViewHolder) holder).relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    ItemBean itemBean = mList.get(position);
                    Toast.makeText(v.getContext(), itemBean.getTitle() + "当前位置" + position, Toast.LENGTH_SHORT).show();

                    if(itemBean.getTitle().equals("昵称")){
                        ChangeNameActivity.actionStart(v.getContext());
                    }else if(itemBean.getTitle().equals("手机号")){
                        ChangePhoneActivity.actionStart(v.getContext());
                    }else if(itemBean.getTitle().equals("微信")){

                    }else if(itemBean.getTitle().equals("qq")){

                    }else if(itemBean.getTitle().equals("身份认证")){
                        IdentityActivity.actionStart(v.getContext());
                    }
                }
            });
        }else {//设置button点击事件
//            ItemBean itemBean = mList.get(position);

            ((ButtonHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemBean itemBean = mList.get(position);
                    Toast.makeText(v.getContext(), itemBean.getTitle() + "当前位置" + position, Toast.LENGTH_SHORT).show();
                    sp = v.getContext().getSharedPreferences("login_info", MODE_PRIVATE);
                    sp.edit().clear().commit();//清除SharedPreferences
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    v.getContext().startActivity(intent);
                }
            });


        }
    }
    //获取RecyclerView有多少子项
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int getItemViewType(int position) {
        ItemBean itemBean =mList.get(position);
        if (itemBean.getType() == 0) {
            return TYPE_VIEW;
        } else if (itemBean.getType() == 1) {
            return TYPE_BUTTON;
        }else {
            return 0;
        }
    }


}
