package inclinic.com.uiD.admin_diagnosis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_Diagnosis_Add_Tab extends Fragment {

    public Admin_Diagnosis_Add_Tab() {
        // Required empty public constructor
    }
    private EditText A_idx,A_questions,A_a1,A_a2, A_a3,A_a4 ;
    private Button BtnAdd;
    DatabaseReference fb;
    private int Size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_admin_diagnosis_add_tab, container, false);

        A_idx = (EditText) v.findViewById(R.id.Add_D_idx);
        A_questions = (EditText) v.findViewById(R.id.Add_D_Question);
        A_a1 = (EditText) v.findViewById(R.id.Add_D_a1);
        A_a2 = (EditText) v.findViewById(R.id.Add_D_a2);
        A_a3 = (EditText) v.findViewById(R.id.Add_D_a3);
        A_a4 = (EditText) v.findViewById(R.id.Add_D_a4);

        BtnAdd = (Button) v.findViewById(R.id.btn_Add_D_A);


        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String idx = A_idx.getText().toString();
                String Question = A_questions.getText().toString();
                String a1 = A_a1.getText().toString();
                String a2 = A_a2.getText().toString();
                String a3 = A_a3.getText().toString();
                String a4 = A_a4.getText().toString();

                if (idx.trim().equals("") || Question.trim().equals("") || a1.trim().equals("") || a2.trim().equals("") || a3.trim().equals("") || a4.trim().equals("")){

                    Toast.makeText(getContext(),"Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
                else {

                    Questions Q = new Questions(idx, Question, a1, a2, a3, a4);

                    addQuestion(Q);
                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    //=========== Clear All Fields Function    ================
                    ClearAllFields();
                }
            }
        });


        return v;
    }

    public void ClearAllFields(){

        A_idx.setText("");
        A_questions.setText("");
        A_a1.setText("");
        A_a2.setText("");
        A_a3.setText("");
        A_a4.setText("");
        A_idx.requestFocus();
    }

    public void  addQuestion(final Questions Q){
        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();
                Q.setId(Size+1);
                fb.child("questions").child(String.valueOf(Size+1)).setValue(Q);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
