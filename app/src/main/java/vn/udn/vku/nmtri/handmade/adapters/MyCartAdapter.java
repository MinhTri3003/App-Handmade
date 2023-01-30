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

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice=0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelListr) {
        this.context = context;
        this.cartModelList = cartModelListr;
        firestore = FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.name.setText(cartModelList.get(position).getProductName());
        holder.date.setText(cartModelList.get(position).getProductDate());
        holder.time.setText(cartModelList.get(position).getProductTime());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                              if (task.isSuccessful()){
                                  cartModelList.remove(cartModelList.get(position));
                                  notifyDataSetChanged();
                                  Toast.makeText(context,"Đã xóa thành công",Toast.LENGTH_SHORT).show();
                              }
                              else{
                                  Toast.makeText(context,"Lỗi: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                              }
                            }
                        });
            }
        });
        //pass total amout to My Cart Fragment
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
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
            deleteItem = itemView.findViewById(R.id.delete);
        }
    }
}
