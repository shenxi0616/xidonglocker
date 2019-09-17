package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a10692.xidonglocker.R;

import java.util.List;

import model.ItemBean;
import model.ItemDetails;

public class DetailsAdapter extends RecyclerView.Adapter {
    private List<ItemDetails> mList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView useway,time,date,expenses,balance;
        public ViewHolder(View view){
            super(view);
            useway = view.findViewById(R.id.useway);
            time = view.findViewById(R.id.time);
            date = view.findViewById(R.id.date);
            expenses = view.findViewById(R.id.expenses);
            balance = view.findViewById(R.id.balance);
        }
    }
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item, parent, false);
        return new DetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemDetails itemDetails = mList.get(position);
        ((ViewHolder) holder).useway.setText(itemDetails.getUseway());
        ((ViewHolder) holder).date.setText(itemDetails.getDate());
        ((ViewHolder) holder).time.setText(itemDetails.getTime());
        ((ViewHolder) holder).expenses.setText((int) itemDetails.getExpenses()+"");
        ((ViewHolder) holder).balance.setText((int)itemDetails.getBalance()+"");

    }

    //传入展示数据,并且赋值给全局变量
    public DetailsAdapter(List<ItemDetails> List){
        mList=List;
    }

    //获取RecyclerView有多少子项
    @Override
    public int getItemCount() {
        return mList.size();
    }

}
