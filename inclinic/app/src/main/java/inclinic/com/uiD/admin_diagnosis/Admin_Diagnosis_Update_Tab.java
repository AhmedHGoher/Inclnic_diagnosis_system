package inclinic.com.uiD.admin_diagnosis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import inclinic.com.classes.Questions;
import inclinic.com.recyleviews.Admin_Diagnosis_RecycleViewAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_Diagnosis_Update_Tab extends Fragment {


    private RecyclerView mRecyclerView;
    private List<Questions> lstQuestions= new ArrayList<>() ;
    private DatabaseReference fb;
    Admin_Diagnosis_RecycleViewAdaptor admin_diagnosis_recycleViewAdaptor;

    View v ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_admin_diagnosis_update_tab, container, false);


        ClearAll();

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");


        mRecyclerView = v.findViewById(R.id.Admin_Diagnosis_Recyle_View);
       // Admin_Diagnosis_RecycleViewAdaptor admin_diagnosis_recycleViewAdaptor = new Admin_Diagnosis_RecycleViewAdaptor( getContext(),lstQuestions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // mRecyclerView.setAdapter(admin_diagnosis_recycleViewAdaptor);




        Query query = fb.child("questions");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    Questions Qd = new Questions();;

                    Qd.setIdx(dataSnapshot.child("idx").getValue().toString());
                    Qd.setQ(dataSnapshot.child("q").getValue().toString());
                    Qd.setT(dataSnapshot.child("t").getValue().toString());
                    Qd.setF(dataSnapshot.child("f").getValue().toString());
                    Qd.setA(dataSnapshot.child("a").getValue().toString());
                    Qd.setB(dataSnapshot.child("b").getValue().toString());
                    Qd.setId(Integer.parseInt(dataSnapshot.child("id").getValue().toString()));

                    lstQuestions.add(Qd);

                }


                admin_diagnosis_recycleViewAdaptor = new Admin_Diagnosis_RecycleViewAdaptor(getContext(),lstQuestions);
                mRecyclerView.setAdapter(admin_diagnosis_recycleViewAdaptor);
                admin_diagnosis_recycleViewAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;

    }

    private void ClearAll(){
        if (lstQuestions != null ){
            lstQuestions.clear();

            if (admin_diagnosis_recycleViewAdaptor != null ){
                admin_diagnosis_recycleViewAdaptor.notifyDataSetChanged();
            }

        }
    }



}
