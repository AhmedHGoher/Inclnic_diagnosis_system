package inclinic.com.uiD.admin_diagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.Questions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Admin_Diagnosis_Update_Action extends AppCompatActivity {
    private EditText idx,questions,a1,a2, a3,a4 ;
    private Button btn_d_update;
    int S_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__diagnosis__update__action);

//=========== Object Of DB   ================
//=========== Action Bar Change   ================
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Updating Diagnosis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//=========== Action Bar Change   ================
        Intent intent = getIntent();

        S_ID = intent.getIntExtra("ID",0);
        String S_idx = intent.getStringExtra("index");
        String S_question =intent.getStringExtra("question");
        String S_a1 = intent.getStringExtra("a1");
        String S_a2 = intent.getStringExtra("a2");
        String S_a3 = intent.getStringExtra("a3");
        String S_a4 = intent.getStringExtra("a4");

//=========== Get IDs    ================
        idx = findViewById(R.id.Update_D_idx);
        questions = findViewById(R.id.Update_D_Question);
        a1 = findViewById(R.id.Update_D_a1);
        a2 = findViewById(R.id.Update_D_a2);
        a3 = findViewById(R.id.Update_D_a3);
        a4 = findViewById(R.id.Update_D_a4);
        btn_d_update = findViewById(R.id.btn_Update_D_A);
//=========== Set Data    ================
        idx.setText(S_idx);
        questions.setText(S_question);
        a1.setText(S_a1);
        a2.setText(S_a2);
        a3.setText(S_a3);
        a4.setText(S_a4);
//=========== Update Click    ================
        btn_d_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //=========== Get Data    ================
                String S_idx =      idx.getText().toString();
                String S_questions =      questions.getText().toString();
                String S_a1 =      a1.getText().toString();
                String S_a2 =     a2.getText().toString();
                String S_a3 =       a3.getText().toString();
                String S_a4 =    a4.getText().toString();



                if (S_idx.trim().equals("") || S_questions.trim().equals("") || S_a1.trim().equals("") || S_a2.trim().equals("") || S_a3.trim().equals("") || S_a4.trim().equals("")){

                    Toast.makeText(Admin_Diagnosis_Update_Action.this,"Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
                else {


                    //=========== Class Object    ================
                    Questions c = new Questions(S_ID, S_idx, S_questions, S_a1, S_a2, S_a3, S_a4);
                    //=========== Update Function    ================
                    UpdateQuestion(c);
                    //=========== Toast in the Screen    ================
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                    //=========== Redirect to the Screen    ================

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_admin__diagnosis__update__action_id, new Admin_Diagnosis_Fragment()).commit();
                }


            }
        });


    }

    public void UpdateQuestion(Questions Q){

        DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("questions").child(String.valueOf(Q.getId())).setValue(Q);

    }

}
