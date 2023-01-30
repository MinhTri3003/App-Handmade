package vn.udn.vku.nmtri.handmade.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.udn.vku.nmtri.handmade.R;
import vn.udn.vku.nmtri.handmade.activities.ViewAllActivity;
import vn.udn.vku.nmtri.handmade.models.PopularModel;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {
     private Context context;
     private List<PopularModel> popularModelList;
     public PopularAdapters(Context context,List<PopularModel> popularModelList){
         this.context=context;
         this.popularModelList = popularModelList;
     }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(popularModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(popularModelList.get(position).getName());
        holder.rating.setText(popularModelList.get(position).getRating());
        holder.discount.setText(popularModelList.get(position).getDiscount());
        holder.description.setText(popularModelList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewAllActivity.class);
                intent.putExtra("type",popularModelList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularModelList.size();
    }

    public void setPopularModelList(List<PopularModel> popularModelList) {
        this.popularModelList = popularModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,description,rating,discount;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            discount = itemView.findViewById(R.id.pop_dis);
            rating = itemView.findViewById(R.id.pop_rating);



        }
    }
}
