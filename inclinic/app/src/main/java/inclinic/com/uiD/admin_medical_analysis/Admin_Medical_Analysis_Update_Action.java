package inclinic.com.uiD.admin_medical_analysis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.MediTest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Admin_Medical_Analysis_Update_Action extends AppCompatActivity {
    private EditText A_MMTname , A_MTcode ,A_MFrange,A_MLrange,A_MLow,A_MHigh;
    private Button btn_M_update;
    int S_ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__medical__analysis__update__action);


//=========== Object Of DB   ================
//=========== Action Bar Change   ================
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Updating Medical Analysis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//=========== Action Bar Change   ================
        final Intent intent = getIntent();

        S_ID = intent.getIntExtra("ID",0);
        String S_MTN = intent.getStringExtra("mtn");
        String S_TC =intent.getStringExtra("tc");
        String S_FR = intent.getStringExtra("fr");
        String S_LR = intent.getStringExtra("lr");
        String S_LD = intent.getStringExtra("ld");
        String S_HD = intent.getStringExtra("hd");

//=========== Get IDs    ================
        A_MMTname = findViewById(R.id.Update_M_MTA);
        A_MTcode = findViewById(R.id.Update_M_TC);
        A_MFrange = findViewById(R.id.Update_M_FR);
        A_MLrange = findViewById(R.id.Update_M_LR);
        A_MLow = findViewById(R.id.Update_M_LD);
        A_MHigh = findViewById(R.id.Update_M_HD);
        btn_M_update = findViewById(R.id.btn_Update_M);
//=========== Set Data    ================
        A_MMTname.setText(S_MTN);
        A_MTcode.setText(S_TC);
        A_MFrange.setText(S_FR);
        A_MLrange.setText(S_LR);
        A_MLow.setText(S_LD);
        A_MHigh.setText(S_HD);
//=========== Update Click    ================
        btn_M_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //=========== Get Data    ================

                String S_MTN =      A_MMTname.getText().toString();
                String S_TC =      A_MTcode.getText().toString();
                String S_FR =      A_MFrange.getText().toString();
                String S_LR =     A_MLrange.getText().toString();
                String S_LD =       A_MLow.getText().toString();
                String S_HD =    A_MHigh.getText().toString();



                if (S_MTN.trim().equals("") || S_TC.trim().equals("") || S_FR.trim().equals("") || S_LR.trim().equals("") || S_LD.trim().equals("") || S_HD.trim().equals("")){

                    Toast.makeText(Admin_Medical_Analysis_Update_Action.this,"Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
                else {


                    //=========== Class Object    ================
                    MediTest c = new MediTest(S_ID, S_MTN, S_TC, S_FR, S_LR, S_LD, S_HD);
                    //=========== Update Function    ================
                    UpdateMediTestID(c);
                    //=========== Toast in the Screen    ================
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                    //=========== Redirect to the Screen    ================

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_admin__medical__analysis__update__action_id, new Admin_Medical_Analysis_Fragment()).commit();
                }
            }
        });

    }

    public void UpdateMediTestID(MediTest M){

        DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("MediTest").child(String.valueOf(M.getId())).setValue(M);

    }

}
