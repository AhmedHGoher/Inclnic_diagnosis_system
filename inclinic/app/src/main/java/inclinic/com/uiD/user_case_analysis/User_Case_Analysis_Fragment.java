package inclinic.com.uiD.user_case_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import inclinic.com.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class User_Case_Analysis_Fragment extends Fragment {
    AutoCompleteTextView symp,treat;

    Button btnadd, btnadd2, btnNext;
    LinearLayout mainLayout;
    Spinner Part, pain;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_user_case_analysis, container, false ) ;

        Part = v.findViewById(R.id.spinner1);
        pain = v.findViewById(R.id.spinner2);
        symp = v.findViewById(R.id.SymptomsETxt);
        treat = v.findViewById(R.id.treatETxt);

        btnNext = v.findViewById(R.id.btnNext);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(symp.getText().toString().trim().equals("") || treat.getText().toString().trim().equals("") || pain.getSelectedItem().toString().trim().equals("") || Part.getSelectedItem().toString().trim().equals("") ) {
                    Toast.makeText(getContext(), "Please Enter All Data At First", Toast.LENGTH_SHORT).show();
                }
                else {
                    String s1 = Part.getSelectedItem().toString();
                    String s2 = pain.getSelectedItem().toString();
                    String ett1 = symp.getText().toString();
                    String ett2 = treat.getText().toString();

                    // bundle to move argument from fragment to another
                    Bundle bundle = new Bundle();
                    bundle.putString("Spin1", s1);
                    bundle.putString("Spin2", s2);
                    bundle.putString("et1", ett1);
                    bundle.putString("et2", ett2);
    // object from class

                    User_Case_Analysis_Fragment_2 user_case_analysis_fragment_2 = new User_Case_Analysis_Fragment_2();
                    user_case_analysis_fragment_2.setArguments(bundle);
    // move to another fragment by fragment container and targeted fragment (object from fragment class)
                    getFragmentManager().beginTransaction().replace(R.id.User_Case_AnalysisFragmentContaner, user_case_analysis_fragment_2).commit();

                    //overridePendingTransition(R.anim.slideinright,R.anim.slideoutleft);
                }
            }
        });

        ///////////////////////////////////SPINNER///////////////////////////
        ///////////////////////////////////SPINNER///////////////////////////



        final String Spinner1Com[] = {"Choose part","Head and neck","Chest and back"};
        final String HeadNeck[] = {"Hair loss","Headache"};
    //    final String Eyes[] = {"Dry eyes and mouth","Eyes secretions","Eyes pain"};
        final String ChestBack[] = {"Chest pain","Pain on lower back"};
  //      final String Legs[] = {"Foot pain","Knee pain","Gout"};
        final String choose[] = {"Choose Pain"};




        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,Spinner1Com);
        Part.setAdapter(adaptor);

        Part.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String itemSelect = Spinner1Com[position];

                if(position == 0){
                    ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,choose);
                    pain.setAdapter(adaptor);
                }
                else if(position == 1 ){
                    ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,HeadNeck);
                    pain.setAdapter(adaptor);
                }

                else if(position == 2 ){
                    ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,ChestBack);
                    pain.setAdapter(adaptor);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //////////////////////////////////EDITTEXTS////////////////////////////////////////
        //////////////////////////////////EDITTEXTS////////////////////////////////////////

        return v;
    }
}
