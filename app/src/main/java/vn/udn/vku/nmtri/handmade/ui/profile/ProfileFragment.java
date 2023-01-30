package vn.udn.vku.nmtri.handmade.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.udn.vku.nmtri.handmade.MainActivity;
import vn.udn.vku.nmtri.handmade.R;
import vn.udn.vku.nmtri.handmade.models.UserModel;


public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    EditText name,email,number,address;
    Button update;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile,container,false);

        auth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        number = root.findViewById(R.id.profile_number);
        email = root.findViewById(R.id.profile_email);
        address = root.findViewById(R.id.profile_address);
        update = root.findViewById(R.id.update);
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if(userModel.getProfileImg()!=null){
                            Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                        }
                        name.setText(userModel.getName());
                        number.setText(userModel.getSdt());
                        address.setText(userModel.getAddr());
                        email.setText(userModel.getEmail());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });




        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserProfile();
//                Toast.makeText(getContext(),"Upload successfully!",Toast.LENGTH_SHORT).show();
            }
        });



        return root;
    }

    private void updateUserProfile() {
        String userSdt = number.getText().toString().trim();
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("sdt").setValue(userSdt);
        String userAddress = address .getText().toString().trim();
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .child("addr").setValue(userAddress);

        startActivity(new Intent(getContext(), MainActivity.class));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null){
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getContext(),"Uploaded",Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                    .child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(),"Avatar photo has been uploaded!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}