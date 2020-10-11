package inclinic.com.uiD.user_case_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import inclinic.com.R;
import inclinic.com.classes.CaseAnalysis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class User_Case_Analysis_Fragment_2 extends Fragment {
    RadioGroup age,gender,history,inh;
    EditText times,expect;
    Spinner sp;
    Button Finish,cases;
    RadioButton rb1,rb2,rb3,rb4;
    String part,pain,symp,treat;
    DatabaseReference fb;
    private int Size;

    String ageee;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    final View v =inflater.inflate(R.layout.fragment_user_case_analysis_2, container, false ) ;

        Bundle bundle = this.getArguments();

        part = bundle.getString("Spin1");
        pain =  bundle.getString("Spin2");
        symp =  bundle.getString("et1");
        treat =  bundle.getString("et2");

        age =(RadioGroup) v.findViewById(R.id.groupage);
        gender =(RadioGroup) v.findViewById(R.id.groupGender);
        history =(RadioGroup) v.findViewById(R.id.grouphistory);
        inh = (RadioGroup) v.findViewById(R.id.groupInh);

        times = (EditText) v.findViewById(R.id.times);
        expect =(EditText) v.findViewById(R.id.expect);

        //spinner filling
        sp = (Spinner) v.findViewById(R.id.spinnercountry);

        times.setTransformationMethod(null);


        final String Spinnercount[] = {"Choose country","egypt","america","japan","australia","china","italy","france","germany"};

        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,Spinnercount);
        sp.setAdapter(adaptor);

//=========== Object Of DB   ================


        Finish = v.findViewById(R.id.btnfinish);

        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                int agee = age.getCheckedRadioButtonId();
                rb1 = (RadioButton) v.findViewById(agee);
                String ageee = rb1.getText().toString();

                int gend = gender.getCheckedRadioButtonId();
                rb2 = (RadioButton) v.findViewById(gend);
                String gende = rb2.getText().toString();

                int hist = history.getCheckedRadioButtonId();
                rb3 = (RadioButton) v.findViewById(hist);
                String histo = rb3.getText().toString();

                int inhe = inh.getCheckedRadioButtonId();
                rb4 = (RadioButton) v.findViewById(inhe);
                String inher = rb4.getText().toString();

                String time = times.getText().toString();
                String exp = expect.getText().toString();

                String spp = sp.getSelectedItem().toString();

                if (ageee.trim().equals("") || gende.trim().equals("") || histo.trim().equals("") || inher.trim().equals("") || time.trim().equals("") || exp.trim().equals("") || spp.trim().equals("")) {

                    Toast.makeText(getContext(), "Please Enter All Data At First", Toast.LENGTH_SHORT).show();
                }
                else {

                    CaseAnalysis c = new CaseAnalysis(part, pain, symp, treat, gende, histo, inher, spp, exp, ageee, time);

                    addcondition(c);

                    Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cases = v.findViewById(R.id.btncase);

        cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // bundle to move argument from fragment to another
                Bundle bundle = new Bundle();
                bundle.putString("symp",symp);
// object from class
                User_Case_Analysis_Fragment_3 user_case_analysis_fragment_3 = new User_Case_Analysis_Fragment_3();
                user_case_analysis_fragment_3.setArguments(bundle);
// move to another fragment by fragment container and targeted fragment (object from fragment class)
                getFragmentManager().beginTransaction().replace(R.id.User_Case_AnalysisFragmentContaner,user_case_analysis_fragment_3).commit();

            }
        });


        return v ;
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
