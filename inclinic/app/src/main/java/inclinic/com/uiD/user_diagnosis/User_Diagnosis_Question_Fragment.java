package inclinic.com.uiD.user_diagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import inclinic.com.Ads.Ads;
import inclinic.com.R;
import inclinic.com.classes.Questions;
import inclinic.com.classes.Visitors;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import hotchemi.android.rate.AppRate;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class User_Diagnosis_Question_Fragment extends Fragment {


    TextView Testoo ;
    TextView TxtQ ;
    Button BtnT ,BtnF ,BtnA ,BtnB,BtnNewDiagnosis;
    ImageView BtnBack;
    DatabaseReference fb;
    private int Size;

    Questions QA = new Questions();
    String idx , data ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user__diagnosis__question_, container, false);

//=========== Get Testoo  ID to get key for database   ================

        Testoo = v.findViewById(R.id.testoooo);

        Animatoo.animateFade(getContext());

        Bundle bundle = this.getArguments();
        data = bundle.getString("Key");
        idx = bundle.getString("idx");
        Testoo.setText(data);


        if (idx.equals("")) {
            if (data == "Hair loss") {
                idx = "HL1";
            } else if (data == "Headache") {
                idx = "HA1";
            } else if (data == "Chest pain") {
                idx = "CP1";
            } else if (data == "pain on Lower back") {
                idx = "LB1";
            }
        }




//=========== Get Items ID   ================
        TxtQ = v.findViewById(R.id.TxtQ);
        BtnT = v.findViewById(R.id.BtnT);
        BtnF = v.findViewById(R.id.BtnF);
        BtnA = v.findViewById(R.id.BtnA);
        BtnB = v.findViewById(R.id.BtnB);
        BtnBack = v.findViewById(R.id.BtnBack);

        BtnNewDiagnosis= v.findViewById(R.id.Btnnewdiagnosis);

//=========== Object Of DB   ================
//===========================================




        ShowQuestionsAndAnswers(idx);
        BtnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idx=idx+"1";
                ShowQuestionsAndAnswers(idx);
                CheckAds();

            }
        });

        BtnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idx=idx+"0";
                ShowQuestionsAndAnswers(idx);
                CheckAds();

            }
        });

        BtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idx=idx+"2";
                ShowQuestionsAndAnswers(idx);
                CheckAds();

            }
        });

        BtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idx=idx+"3";
                ShowQuestionsAndAnswers(idx);
                CheckAds();

            }
        });


        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (idx.length() > 3 ) {
                    idx = idx.substring(0, idx.length() - 1);
                    ShowQuestionsAndAnswers(idx);
                }
                else {

                }

            }
        });






        BtnNewDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User_Diagnosis_Fragment user_diagnosis_fragment = new User_Diagnosis_Fragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.UserDiagnosisFragmentContaner,user_diagnosis_fragment);
                transaction.commit();

            }
        });





        return v;
    }
    public void CheckRe(){
        if (BtnT.getText().equals("none") && BtnF.getText().equals("none") && BtnA.getText().equals("none") && BtnB.getText().equals("none")) {
            AddVisit("Diagnosis", data);


            AppRate.with(getContext()).setInstallDays(0).setLaunchTimes(3).setRemindInterval(1).monitor();
            AppRate.showRateDialogIfMeetsConditions(getActivity());
            AppRate.with(getContext()).showRateDialog(getActivity());



        }
    }

    public void CheckAds(){

        if (idx.length() == 5){

            Intent it = new Intent(getContext(), Ads.class);
            it.putExtra("data",data);
            it.putExtra("idx",idx);
            startActivity(it);

            DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

            Query query = fb;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    int No = Integer.parseInt(snapshot.child("profit").getValue().toString());

                    DatabaseReference numMesasReference = snapshot.getRef().child("profit");
                    numMesasReference.setValue(No + 1);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else{

        }

    }

    public void ShowQuestionsAndAnswers(String idx){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query = fb.child("questions").orderByChild("idx").equalTo(idx);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    QA.setIdx(dataSnapshot.child("idx").getValue().toString());
                    QA.setQ(dataSnapshot.child("q").getValue().toString());
                    QA.setT(dataSnapshot.child("t").getValue().toString());
                    QA.setF(dataSnapshot.child("f").getValue().toString());
                    QA.setA(dataSnapshot.child("a").getValue().toString());
                    QA.setB(dataSnapshot.child("b").getValue().toString());
                    QA.setId(Integer.parseInt(dataSnapshot.child("id").getValue().toString()));
                }


                TxtQ.setText(QA.getQ());

                BtnT.setText(QA.getT());
                BtnF.setText(QA.getF());
                BtnA.setText(QA.getA());
                BtnB.setText(QA.getB());

                if(BtnT.getText().equals("none")){
                    BtnT.setVisibility(View.GONE);}
                else {
                    BtnT.setVisibility(View.VISIBLE);
                }
                if(BtnF.getText().equals("none")){
                    BtnF.setVisibility(View.GONE);}
                else {
                    BtnF.setVisibility(View.VISIBLE);
                }
                if(BtnA.getText().equals("none")){
                    BtnA.setVisibility(View.GONE);}
                else {
                    BtnA.setVisibility(View.VISIBLE);
                }
                if(BtnB.getText().equals("none")){
                    BtnB.setVisibility(View.GONE);}
                else {
                    BtnB.setVisibility(View.VISIBLE);
                }




                CheckRe();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e(TAG, "onCancelled", error.toException());

            }
        });




    }


    public void AddVisit(final String Type , final String Result){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("Visitors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();
                Visitors V = new Visitors();
                V.setType(Type);
                V.setResult(Result);

                fb.child("Visitors").child(String.valueOf(Size+1)).setValue(V);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
