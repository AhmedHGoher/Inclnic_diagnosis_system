package inclinic.com.uiD.admin_case_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.CaseAnalysis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;


public class Admin_CaseAnalysis_Add_Tab extends Fragment {


    private EditText part,pain,symp,treat, age,gender,history,times,inherit , country,expect ;
    private Button Case_btn_add;
    DatabaseReference fb;
    private int Size;

    public Admin_CaseAnalysis_Add_Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_admin__case_analysis__add__tab, container, false);
//=========== Object Of DB   ================
//=========== Get IDs    ================
        part = v.findViewById(R.id.part_Add_A);
        pain = v.findViewById(R.id.pain_Add_A);
        symp = v.findViewById(R.id.symp_Add_A);
        treat = v.findViewById(R.id.treat_Add_A);
        age = v.findViewById(R.id.age_Add_A);
        gender = v.findViewById(R.id.gender_Add_A);
        history = v.findViewById(R.id.history_Add_A);
        times = v.findViewById(R.id.times_Add_A);
        inherit = v.findViewById(R.id.inherit_Add_A);
        country = v.findViewById(R.id.country_Add_A);
        expect = v.findViewById(R.id.expect_Add_A);
        Case_btn_add = v.findViewById(R.id.btn_Add_A);


        //=========== Add Click    ================
        Case_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //=========== Get Data    ================
                String S_part =      part.getText().toString();
                String S_pain =      pain.getText().toString();
                String S_symp =      symp.getText().toString();
                String S_treat =     treat.getText().toString();
                String S_age =       age.getText().toString();
                String S_gender =    gender.getText().toString();
                String S_history =   history.getText().toString();
                String S_times =     times.getText().toString();
                String S_inherit  =  inherit.getText().toString();
                String S_country =   country.getText().toString();
                String S_expect =    expect.getText().toString();

                if (S_part.trim().equals("") || S_pain.trim().equals("") || S_symp.trim().equals("") || S_treat.trim().equals("") || S_age.trim().equals("") || S_gender.trim().equals("") || S_history.trim().equals("") || S_times.trim().equals("") || S_inherit.trim().equals("") || S_country.trim().equals("") || S_expect.trim().equals("")){

                    Toast.makeText(getContext(),"Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
                else {

                    //=========== Class Object    ================
                    CaseAnalysis c = new CaseAnalysis(S_part, S_pain, S_symp, S_treat, S_gender, S_history, S_inherit, S_country, S_expect, S_age, S_times);
                    //=========== Add Function    ================
                    addcondition(c);
                    //=========== Toast in the Screen    ================
                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                    //=========== Clear All Fields Function    ================
                    ClearAllFields();
                }
            }
        });

        return v;
    }

    public void ClearAllFields(){

        part.setText("");
        pain.setText("");
        symp.setText("");
        treat.setText("");
        age.setText("");
        gender.setText("");
        history.setText("");
        times.setText("");
        inherit.setText("");
        country.setText("");
        expect.setText("");
        part.requestFocus();
    }


    public void  addcondition(final CaseAnalysis c){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("condition").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();
                c.setId(Size+1);
                fb.child("condition").child(String.valueOf(Size+1)).setValue(c);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
