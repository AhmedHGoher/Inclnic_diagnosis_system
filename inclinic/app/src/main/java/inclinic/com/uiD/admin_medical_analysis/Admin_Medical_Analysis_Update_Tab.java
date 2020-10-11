package inclinic.com.uiD.admin_medical_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import inclinic.com.classes.MediTest;
import inclinic.com.recyleviews.Admin_MedicalAnalysis_RecycleViewAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Admin_Medical_Analysis_Update_Tab extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private List<MediTest> lstMeditest= new ArrayList<>() ;
    Admin_MedicalAnalysis_RecycleViewAdaptor admin_medicalAnalysis_recycleViewAdaptor;
    private DatabaseReference fb;

    public Admin_Medical_Analysis_Update_Tab() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_admin__medical__analysis__update__tab, container, false);

        ClearAll();

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");


        mRecyclerView = v.findViewById(R.id.Admin_Medical_Analysis_Recyle_View);
        //Admin_MedicalAnalysis_RecycleViewAdaptor admin_medicalAnalysis_recycleViewAdaptor = new Admin_MedicalAnalysis_RecycleViewAdaptor(getContext(),lstMeditest);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // mRecyclerView.setAdapter(admin_medicalAnalysis_recycleViewAdaptor);



        Query query = fb.child("MediTest");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    MediTest M = new MediTest();
                    M.setMTname(dataSnapshot.child("mtname").getValue().toString());
                    M.setTcode(dataSnapshot.child("tcode").getValue().toString());
                    M.setFrange(dataSnapshot.child("frange").getValue().toString());
                    M.setLrange(dataSnapshot.child("lrange").getValue().toString());
                    M.setLow(dataSnapshot.child("low").getValue().toString());
                    M.setHigh(dataSnapshot.child("high").getValue().toString());
                    M.setId(Integer.parseInt(dataSnapshot.child("id").getValue().toString()));


                    lstMeditest.add(M);

                }


                admin_medicalAnalysis_recycleViewAdaptor = new Admin_MedicalAnalysis_RecycleViewAdaptor(getContext(),lstMeditest);
                mRecyclerView.setAdapter(admin_medicalAnalysis_recycleViewAdaptor);
                admin_medicalAnalysis_recycleViewAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v ;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    private void ClearAll(){
        if (lstMeditest != null ){
            lstMeditest.clear();

            if (admin_medicalAnalysis_recycleViewAdaptor != null ){
                admin_medicalAnalysis_recycleViewAdaptor.notifyDataSetChanged();
            }

        }
    }



}
