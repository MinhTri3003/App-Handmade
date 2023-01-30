package vn.udn.vku.nmtri.handmade.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import vn.udn.vku.nmtri.handmade.R;
import vn.udn.vku.nmtri.handmade.adapters.HomeAdapters;
import vn.udn.vku.nmtri.handmade.adapters.PopularAdapters;
import vn.udn.vku.nmtri.handmade.adapters.RecommendedAdapters;
import vn.udn.vku.nmtri.handmade.adapters.ViewAllAdapter;
import vn.udn.vku.nmtri.handmade.models.HomeCategory;
import vn.udn.vku.nmtri.handmade.models.PopularModel;
import vn.udn.vku.nmtri.handmade.models.RecommendedModel;
import vn.udn.vku.nmtri.handmade.models.ViewAllModel;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;

    RecyclerView popularRec,homeCatRec,recommendedRec, view_allREC;
    FirebaseFirestore db;
    //popular item
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;
    //Home category
    List<HomeCategory> categoryList;
    HomeAdapters homeAdapters;
    //Recommended item
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapters recommendedAdapters;
    //
    List<ViewAllModel> viewAllModelList;
    ViewAllAdapter viewAllAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      View root = inflater.inflate(R.layout.fragment_home,container,false);
      db =FirebaseFirestore.getInstance();
        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        view_allREC = root.findViewById(R.id.all_product_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //Popular item
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              PopularModel popularModel = document.toObject(PopularModel.class);
                              popularModelList.add(popularModel);
                              popularAdapters.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        view_allREC.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getActivity(), viewAllModelList);
        view_allREC.setAdapter(viewAllAdapter);
        db.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                    viewAllModelList.add(viewAllModel);
                    viewAllAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    view_allREC.setVisibility(View.VISIBLE);
                }

            }
        });

        //Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapters = new HomeAdapters(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapters);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapters.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //Recommended Item
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapters = new RecommendedAdapters(getActivity(),recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapters);

        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
                                recommendedModelList.add(recommendedModel);
                                recommendedAdapters.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //Popular item
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(),"Lỗi"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        return root;


    }
}