package vn.udn.vku.nmtri.handmade.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import vn.udn.vku.nmtri.handmade.R;
import vn.udn.vku.nmtri.handmade.models.MyCartModel;
import vn.udn.vku.nmtri.handmade.models.MyOrderModel;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    Context context;
    List<MyOrderModel> orderModelList;
    int totalPrice=0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyOrderAdapter(Context context, List<MyOrderModel> orderModelListr) {
        this.context = context;
        this.orderModelList = orderModelListr;
        firestore = FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.name.setText(orderModelList.get(position).getProductName());
        holder.date.setText(orderModelList.get(position).getProductDate());
        holder.time.setText(orderModelList.get(position).getProductTime());
        holder.price.setText(orderModelList.get(position).getProductPrice());
        holder.totalPrice.setText(String.valueOf(orderModelList.get(position).getTotalPrice()));
        holder.quantity.setText(orderModelList.get(position).getTotalQuantity());


        //pass total amout to My Cart Fragment
        totalPrice = totalPrice + orderModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,date,time,quantity,totalPrice;
        ImageView deleteItem;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name =itemView.findViewById(R.id.product_name);
            price =itemView.findViewById(R.id.product_price);
            date =itemView.findViewById(R.id.product_Date);
            time =itemView.findViewById(R.id.product_Time);
            quantity =itemView.findViewById(R.id.total_quantity);
            totalPrice =itemView.findViewById(R.id.total_price);

        }
    }
}
