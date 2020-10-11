package inclinic.com.uiD.admin_dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import inclinic.com.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Admin_Dashboard_Fragment extends Fragment {

    private CardView analysis,profit,new_visitors,new_users,diagnosis,medical_anaysis;
    private TextView txtVisitor ,txtProfit ,txtUsers,txtDiagnosis,txtMediA   ;
    private int novisitors ,totalpro ,nousers,nodiagnosis,nomedi;
    int Size;
    DatabaseReference fb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View v =  inflater.inflate(R.layout.fragment_admin_dashboard , container, false ) ;

//=========== Object Of DB   ================
//=========== Get Values from DB   ================
        getTotalVisit();
        getNumberOfUser();
        getNoDiagnosis();
        getNoMediTest();
        getTotalProfit();
//=========== Get text ID   ================
        txtVisitor = v.findViewById(R.id.tvVistorsNumber);
        txtProfit = v.findViewById(R.id.tvProfit);
        txtUsers = v.findViewById(R.id.tvNewUsers);
        txtDiagnosis = v.findViewById(R.id.tvDiagnosis);
        txtMediA = v.findViewById(R.id.tvMedicalAnalysis);
//=========== Set Text   ================


//=========== Get Card ID   ================
        analysis = v.findViewById(R.id.card_analysis);
        profit = v.findViewById(R.id.card_profit);
        new_visitors = v.findViewById(R.id.card_new_visitors);
        new_users = v.findViewById(R.id.card_new_users);
        diagnosis =v.findViewById(R.id.card_diagnosis);
        medical_anaysis =v.findViewById(R.id.card_medical_analysis);
//=========== Click Cards    ================
//=========== Analysis ================
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Analysis.class));
            }
        });
//=========== Profit ================
        profit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         startActivity(new Intent(getActivity(),Profit.class));
            }
        });
//=========== new_visitors ================
        new_visitors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getActivity(),New_Visitors.class));
            }
        });
//=========== new_users ================
        new_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getActivity(),New_Users.class));
            }
        });
//=========== diagnosis ================
        diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(getActivity(),No_Diagnosis.class));
            }
        });
//=========== medical_anaysis ================
        medical_anaysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getActivity(),No_Medical_Analysis.class));
            }
        });
//===========================

        return v;
    }




    public void getNumberOfUser(){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();

                txtUsers.setText("Total number of Users :" + String.valueOf(Size));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getTotalVisit(){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query = fb;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int No = Integer.parseInt(snapshot.child("noVisitor").getValue().toString());

                txtVisitor.setText("Total number of Visitors :" + String.valueOf(No));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void getTotalProfit(){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query = fb;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int No = Integer.parseInt(snapshot.child("profit").getValue().toString());

                txtProfit.setText("Total money we earn :" + String.valueOf(No) +"$");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public void getNoMediTest(){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("Visitors").orderByChild("type").equalTo("Medical Test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();
                txtMediA.setText("Total number of Medical Analysis :" + String.valueOf(Size));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void getNoDiagnosis(){
        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("Visitors").orderByChild("type").equalTo("Diagnosis").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();
                txtDiagnosis.setText("Total number of Diagnosis :" + String.valueOf(Size));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





}
