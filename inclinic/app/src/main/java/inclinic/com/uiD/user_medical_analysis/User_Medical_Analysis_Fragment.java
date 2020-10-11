package inclinic.com.uiD.user_medical_analysis;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.MediTest;
import inclinic.com.classes.Visitors;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class User_Medical_Analysis_Fragment extends Fragment {

    String CK ;
    EditText e1, e2, e3, e4, e5, e6;
    TextView REV1, REV2, REV3, REV4, REV5, REV6;
    Button BtnC;
    ArrayList<MediTest> M = new ArrayList<>() ;
    ArrayList<EditText> BMET = new ArrayList<>();
    ArrayList<TextView> REV = new ArrayList<>();
    ArrayList<String> Mt = new ArrayList<>();
    ArrayAdapter<String> myAdapter ;
    DatabaseReference fb;
    Spinner mySpinner;
    private int Size;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_user_medical_analysis, container, false ) ;


        ClearAll();

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");



        mySpinner = v.findViewById(R.id.NameTest);



        //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getContext(),R.layout.my_spinner_item , Mt);
       // myAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        //mySpinner.setAdapter(myAdapter);


        Query query = fb.child("MediTest");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {

                    String C = dataSnapshot.child("mtname").getValue().toString();

                    if(!Mt.contains(C)){
                        Mt.add(C);
                    }


                }


                myAdapter = new ArrayAdapter<String>(getContext(),R.layout.my_spinner_item ,Mt);
                myAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
                mySpinner.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();


                mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, final View view, final int i, long l) {
                        e1 = v.findViewById(R.id.Tc1);
                        e2 = v.findViewById(R.id.Tc2);
                        e3 = v.findViewById(R.id.Tc3);
                        e4 = v.findViewById(R.id.Tc4);
                        e5 = v.findViewById(R.id.Tc5);
                        e6 = v.findViewById(R.id.Tc6);
                        BtnC = v.findViewById(R.id.Check);
                        REV1 = v.findViewById(R.id.ResultView1);
                        REV2 = v.findViewById(R.id.ResultView2);
                        REV3 = v.findViewById(R.id.ResultView3);
                        REV4 = v.findViewById(R.id.ResultView4);
                        REV5 = v.findViewById(R.id.ResultView5);
                        REV6 = v.findViewById(R.id.ResultView6);


                        e1.setTransformationMethod(null);
                        e2.setTransformationMethod(null);
                        e3.setTransformationMethod(null);
                        e4.setTransformationMethod(null);
                        e5.setTransformationMethod(null);
                        e6.setTransformationMethod(null);


                        BMET.add(e1);
                        BMET.add(e2);
                        BMET.add(e3);
                        BMET.add(e4);
                        BMET.add(e5);
                        BMET.add(e6);

                        REV.add(REV1);
                        REV.add(REV2);
                        REV.add(REV3);
                        REV.add(REV4);
                        REV.add(REV5);
                        REV.add(REV6);



                        Clear();


                        Query query = fb.child("MediTest").orderByChild("mtname").equalTo(Mt.get(i));
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                    MediTest MDA = new MediTest();
                                    MDA.setMTname(dataSnapshot.child("mtname").getValue().toString());
                                    MDA.setTcode(dataSnapshot.child("tcode").getValue().toString());
                                    MDA.setFrange(dataSnapshot.child("frange").getValue().toString());
                                    MDA.setLrange(dataSnapshot.child("lrange").getValue().toString());
                                    MDA.setLow(dataSnapshot.child("low").getValue().toString());
                                    MDA.setHigh(dataSnapshot.child("high").getValue().toString());
                                    MDA.setId(Integer.parseInt(dataSnapshot.child("id").getValue().toString()));

                                    M.add(MDA);
                                }



                                for (int d = 0 ; d < M.size() ; d++ ){

                                    BMET.get(d).setVisibility(view.VISIBLE);
                                    BMET.get(d).setHint(M.get(d).getTcode());

                                }

                                for (int d = 0 ; d < 5 - M.size() ; d++ ){

                                    BMET.get(4 - d).setVisibility(view.GONE);

                                }



                                BtnC.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.P)
                                    @Override
                                    public void onClick(View view) {


                                        if (e1.getText().toString().trim().equals("") || (e2.getText().toString().trim().equals("") && e2.getVisibility() == View.VISIBLE) || (e3.getText().toString().trim().equals("") && e3.getVisibility() == View.VISIBLE) || (e4.getText().toString().trim().equals("") && e4.getVisibility() == View.VISIBLE) || (e5.getText().toString().trim().equals("") && e5.getVisibility() == View.VISIBLE) || (e6.getText().toString().trim().equals("") && e6.getVisibility() == View.VISIBLE)){
                                            Toast.makeText(getContext(), "Add Value At First", Toast.LENGTH_SHORT).show();
                                        }
                                        else {


                                            for (int d = 0; d < M.size(); d++) {
                                                REV.get(d).setVisibility(view.VISIBLE);

                                            }

                                            for (int y = 0; y < M.size(); y++) {
                                                if (Integer.parseInt(M.get(y).getFrange()) > Integer.parseInt(BMET.get(y).getText().toString())) {
                                                    CK = M.get(y).getTcode() + " : IS Low ," + M.get(y).getLow();
                                                } else if (Integer.parseInt(M.get(y).getLrange()) < Integer.parseInt(BMET.get(y).getText().toString())) {
                                                    CK = M.get(y).getTcode() + " : IS High ," + M.get(y).getHigh();
                                                } else {
                                                    CK = M.get(y).getTcode() + " : Is Normal";
                                                }

                                                REV.get(y).setText(CK);

                                            }

                                            AddVisit("Medical Test", Mt.get(i));
                                        }


                                    }
                                });


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Log.e(TAG, "onCancelled", error.toException());

                            }
                        });



                        BtnC.setVisibility(view.VISIBLE);
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        e4.setText("");
                        e5.setText("");
                        e6.setText("");
                        REV1.setText("");
                        REV2.setText("");
                        REV3.setText("");
                        REV4.setText("");
                        REV5.setText("");
                        REV6.setText("");


                        e1.setVisibility(view.GONE);
                        e2.setVisibility(view.GONE);
                        e3.setVisibility(view.GONE);
                        e4.setVisibility(view.GONE);
                        e5.setVisibility(view.GONE);
                        e6.setVisibility(view.GONE);
                        REV1.setVisibility(view.GONE);
                        REV2.setVisibility(view.GONE);
                        REV3.setVisibility(view.GONE);
                        REV4.setVisibility(view.GONE);
                        REV5.setVisibility(view.GONE);
                        REV6.setVisibility(view.GONE);










                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });












        return v;



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


    private void ClearAll(){
        if (Mt != null ){
            Mt.clear();

            if (myAdapter != null ){
                myAdapter.notifyDataSetChanged();
            }

        }
    }

    private  void Clear(){

        if (M != null){
            M.clear();
        }
    }


}
