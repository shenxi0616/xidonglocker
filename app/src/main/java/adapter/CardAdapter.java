package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a10692.xidonglocker.R;

import java.util.List;

import model.ItemCard;
import model.ItemDetails;

public class CardAdapter extends RecyclerView.Adapter  {
    private List<ItemCard> mList;
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        public ViewHolder(View view){
            super(view);
            img = view.findViewById(R.id.img_card);

        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemCard itemCard = mList.get(position);
        ((ViewHolder) holder).img.setImageResource(itemCard.getImage());
    }

    public CardAdapter(List<ItemCard> List){
        mList=List;
    }

    //获取RecyclerView有多少子项
    @Override
    public int getItemCount() {
        return mList.size();
    }

}
