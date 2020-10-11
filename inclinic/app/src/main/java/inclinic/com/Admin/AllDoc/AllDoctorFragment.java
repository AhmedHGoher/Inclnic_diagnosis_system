package inclinic.com.Admin.AllDoc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inclinic.com.Admin.ui.PendingDoc.PendDoctorRecyclerViewAdapter;
import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.ui.home.RecyclerItemClickListener;


public class AllDoctorFragment extends Fragment {

    SwipeRefreshLayout refreshLayout ;
    RecyclerView recyclerView ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<User_Model> models = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_doctor, container, false);

        refreshLayout = view.findViewById(R.id.alluser_pulltorefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                RefreshData();
            }
        });

        recyclerView = view.findViewById(R.id.alluser_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseReference.child("all_doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User_Model model = new User_Model();
                    model = snapshot.getValue(User_Model.class);
                    models.add(model);
                }
                PendDoctorRecyclerViewAdapter adapter = new PendDoctorRecyclerViewAdapter(getContext() , models);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext() , AllDocOnClickActivity.class);
                        intent.putExtra("doctormodel" , models.get(position));
                        startActivity(intent);
                        Animatoo.animateSlideUp(getContext());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    public void RefreshData(){
        models = new ArrayList<>();
        databaseReference.child("all_doctors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User_Model model = new User_Model();
                    model = snapshot.getValue(User_Model.class);
                    models.add(model);
                }
                PendDoctorRecyclerViewAdapter adapter = new PendDoctorRecyclerViewAdapter(getContext() , models);
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}