package vn.udn.vku.nmtri.handmade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.udn.vku.nmtri.handmade.activities.DetailedActivity;
import vn.udn.vku.nmtri.handmade.models.MyCartModel;

import static vn.udn.vku.nmtri.handmade.R.*;

public class PlacedOrderActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        List<MyCartModel> list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");
        if (list != null && list.size()>0){
          for (MyCartModel model : list){

              final HashMap<String,Object> cartMap = new HashMap<>();
              cartMap.put("productName",model.getProductName());
              cartMap.put("productPrice",model.getProductPrice());
              cartMap.put("productDate", model.getProductDate());
              cartMap.put("productTime",model.getProductTime());
              cartMap.put("totalQuantity",model.getTotalQuantity());
              cartMap.put("totalPrice",model.getTotalPrice());
              firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                      .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                  @Override
                  public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                      Toast.makeText(PlacedOrderActivity.this,"Bạn đã đặt hàng thành công!",Toast.LENGTH_SHORT).show();


                  }
              });
          }

        }
    }

}