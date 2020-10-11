package inclinic.com.uiD.admin_medical_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.MediTest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_Medical_Analysis_Add_Tab extends Fragment {

    public Admin_Medical_Analysis_Add_Tab() {
        // Required empty public constructor
    }


    private EditText MMTname , MTcode ,MFrange,MLrange,MLow,MHigh;
    private Button BtnAdd;

    DatabaseReference fb;
    private int Size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_admin__medical__analysis__add__tab, container, false);

        MMTname = (EditText) v.findViewById(R.id.Add_M_MTA);
        MTcode = (EditText) v.findViewById(R.id.Add_M_TC);
        MFrange = (EditText) v.findViewById(R.id.Add_M_FR);
        MLrange = (EditText) v.findViewById(R.id.Add_M_LR);
        MLow = (EditText) v.findViewById(R.id.Add_M_LD);
        MHigh = (EditText) v.findViewById(R.id.Add_M_HD);

        BtnAdd = (Button) v.findViewById(R.id.btn_Add_M);


        BtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String MTname = MMTname.getText().toString();
                String Tcode = MTcode.getText().toString();
                String Frange = MFrange.getText().toString();
                String Lrange = MLrange.getText().toString();
                String Low = MLow.getText().toString();
                String High = MHigh.getText().toString();


                if (MTname.trim().equals("") || Tcode.trim().equals("") || Frange.trim().equals("") || Lrange.trim().equals("") || Low.trim().equals("") || High.trim().equals("")){

                    Toast.makeText(getContext(),"Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
                else {

                    MediTest M = new MediTest(MTname, Tcode, Frange, Lrange, Low, High);

                    addMediTest(M);


                    Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    //=========== Clear All Fields Function    ================
                    ClearAllFields();
                }

            }
        });


        return v ;
    }


    public void ClearAllFields(){

        MMTname.setText("");
        MTcode.setText("");
        MFrange.setText("");
        MLrange.setText("");
        MLow.setText("");
        MHigh.setText("");
        MMTname.requestFocus();
    }



    public void  addMediTest(final MediTest M){

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("MediTest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();
                M.setId(Size+1);
                fb.child("MediTest").child(String.valueOf(Size+1)).setValue(M);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
