package inclinic.com.uiD.user_case_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import inclinic.com.classes.CaseAnalysis;
import inclinic.com.recyleviews.User_RecycleViewAdaptor_CaseAnalysis;
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

public class User_Case_Analysis_Fragment_3 extends Fragment {



    View v;
    private RecyclerView mRecyclerView;
    private List<CaseAnalysis> lstCaseAnalyses= new ArrayList<>() ;
    User_RecycleViewAdaptor_CaseAnalysis user_recycleViewAdaptor_caseAnalysis;
    private DatabaseReference fb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_case_analysis_3, container, false ) ;


        ClearAll();

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");
        Bundle bundle = getArguments();


        mRecyclerView = v.findViewById(R.id.Admin_Case_Analysis_Recyle_View);
       // User_RecycleViewAdaptor_CaseAnalysis user_recycleViewAdaptor_caseAnalysis = new User_RecycleViewAdaptor_CaseAnalysis(getContext(),lstCaseAnalyses);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // mRecyclerView.setAdapter(user_recycleViewAdaptor_caseAnalysis);

        Query query = fb.child("condition").orderByChild("symp").equalTo(bundle.getString("symp"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    CaseAnalysis C = new CaseAnalysis();

                    C.setAge(dataSnapshot.child("age").getValue().toString());
                    C.setCountry(dataSnapshot.child("country").getValue().toString());
                    C.setExpected(dataSnapshot.child("expected").getValue().toString());
                    C.setGender(dataSnapshot.child("gender").getValue().toString());
                    C.setHistory(dataSnapshot.child("history").getValue().toString());
                    C.setId(Integer.parseInt(dataSnapshot.child("id").getValue().toString()));
                    C.setInheritance(dataSnapshot.child("inheritance").getValue().toString());
                    C.setPain(dataSnapshot.child("pain").getValue().toString());
                    C.setPart(dataSnapshot.child("part").getValue().toString());
                    C.setSymp(dataSnapshot.child("symp").getValue().toString());
                    C.setTimes(dataSnapshot.child("times").getValue().toString());
                    C.setTreat(dataSnapshot.child("treat").getValue().toString());

                    lstCaseAnalyses.add(C);

                }


                user_recycleViewAdaptor_caseAnalysis = new User_RecycleViewAdaptor_CaseAnalysis(getContext(),lstCaseAnalyses);
                mRecyclerView.setAdapter(user_recycleViewAdaptor_caseAnalysis);
                user_recycleViewAdaptor_caseAnalysis.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    private void ClearAll(){
        if (lstCaseAnalyses != null ){
            lstCaseAnalyses.clear();

            if (user_recycleViewAdaptor_caseAnalysis != null ){
                user_recycleViewAdaptor_caseAnalysis.notifyDataSetChanged();
            }

        }
    }




}
