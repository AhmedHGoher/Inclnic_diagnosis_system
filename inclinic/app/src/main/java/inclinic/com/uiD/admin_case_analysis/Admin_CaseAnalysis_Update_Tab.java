package inclinic.com.uiD.admin_case_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import inclinic.com.classes.CaseAnalysis;
import inclinic.com.recyleviews.RecycleViewAdaptor_CaseAnalysis;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_CaseAnalysis_Update_Tab extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private List<CaseAnalysis> lstCaseAnalyses = new ArrayList<>() ;
    private int Size = 0;
    private DatabaseReference fb;
    RecycleViewAdaptor_CaseAnalysis recycleViewAdaptor_caseAnalysis;


    public Admin_CaseAnalysis_Update_Tab() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin__case_analysis__update__tab, container, false);

        ClearAll();

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        mRecyclerView = v.findViewById(R.id.Admin_Case_Analysis_Recyle_View);
        //final RecycleViewAdaptor_CaseAnalysis recycleViewAdaptor_caseAnalysis = new RecycleViewAdaptor_CaseAnalysis( getContext(),lstCaseAnalyses);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // mRecyclerView.setAdapter(recycleViewAdaptor_caseAnalysis);


        Query query = fb.child("condition");
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


                recycleViewAdaptor_caseAnalysis = new RecycleViewAdaptor_CaseAnalysis(getContext(),lstCaseAnalyses);
                mRecyclerView.setAdapter(recycleViewAdaptor_caseAnalysis);
                recycleViewAdaptor_caseAnalysis.notifyDataSetChanged();
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

            if (recycleViewAdaptor_caseAnalysis != null ){
                recycleViewAdaptor_caseAnalysis.notifyDataSetChanged();
            }

        }
}

}
