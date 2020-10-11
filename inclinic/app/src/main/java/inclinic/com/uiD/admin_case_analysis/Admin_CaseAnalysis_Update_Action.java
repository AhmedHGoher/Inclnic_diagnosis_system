package inclinic.com.uiD.admin_case_analysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.CaseAnalysis;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Admin_CaseAnalysis_Update_Action extends AppCompatActivity {

    private EditText part,pain,symp,treat, age,gender,history,times,inherit , country,expect;
    private Button btn_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__case_analysis__update__action);

//=========== Object Of DB   ================
//=========== Action Bar Change   ================
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Updating Case Analysis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//=========== Action Bar Change   ================
        Intent intent = getIntent();

        final int S_ID = intent.getIntExtra("ID",0);
        String S_part =     intent.getStringExtra("part");
        String S_pain =     intent.getStringExtra("pain");
        String S_symp =     intent.getStringExtra("sym");
        String S_treat =    intent.getStringExtra("treat");
        String S_age =      intent.getStringExtra("age");
        String S_gender =   intent.getStringExtra("gender");
        String S_history =  intent.getStringExtra("history");
        String S_times =    intent.getStringExtra("times");
        String S_inherit  = intent.getStringExtra("inherit");
        String S_country =  intent.getStringExtra("country");
        String S_expect =   intent.getStringExtra("expect");
//=========== Get IDs    ================
        part = findViewById(R.id.part_Update_A);
        pain = findViewById(R.id.pain_Update_A);
        symp = findViewById(R.id.symp_Update_A);
        treat = findViewById(R.id.treat_Update_A);
        age = findViewById(R.id.age_Update_A);
        gender = findViewById(R.id.gender_Update_A);
        history = findViewById(R.id.history_Update_A);
        times = findViewById(R.id.times_Update_A);
        inherit = findViewById(R.id.inherit_Update_A);
        country = findViewById(R.id.country_Update_A);
        expect = findViewById(R.id.expect_Update_A);
        btn_update = findViewById(R.id.btn_Update_A);
//=========== Set Data    ================
        part.setText(S_part);
        pain.setText(S_pain);
        symp.setText(S_symp);
        treat.setText(S_treat);
        age.setText(S_age);
        gender.setText(S_gender);
        history.setText(S_history);
        times.setText(S_times);
        inherit.setText(S_inherit);
        country.setText(S_country);
        expect.setText(S_expect);
//=========== Update Click    ================
        btn_update.setOnClickListener(new View.OnClickListener() {
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

                    Toast.makeText(Admin_CaseAnalysis_Update_Action.this,"Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
                else {

                    //=========== Class Object    ================
                    CaseAnalysis c = new CaseAnalysis (S_ID, S_part, S_pain, S_symp, S_treat, S_gender, S_history, S_inherit, S_country, S_expect, S_age, S_times);
                    //=========== Update Function    ================
                    UpdateCaseID(c);
                    //=========== Toast in the Screen    ================
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    //=========== Redirect to the Screen    ================

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.Admin_CaseAnalysis_Update_Action_id, new Admin_Case_Analysis_Fragment()).commit();
                }


            }
        });












    }

    public void UpdateCaseID(CaseAnalysis C){

        DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("condition").child(String.valueOf(C.getId())).setValue(C);

    }
}
