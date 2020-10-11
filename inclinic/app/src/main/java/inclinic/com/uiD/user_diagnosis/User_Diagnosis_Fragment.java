package inclinic.com.uiD.user_diagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;
import inclinic.com.Admin_Navigation;
import inclinic.com.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import inclinic.com.uiD.admin_medical_analysis.Admin_Medical_Analysis_Fragment;
import inclinic.com.ui_activities_auth.login.SginIn;

public class User_Diagnosis_Fragment extends Fragment {

    private Button btnhead,btnhairloss,btnheadache,btnchestandback,btnchestpain,btnLowerback   ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_diagnosis, container, false ) ;

//=========== Get Button ID   ================
        btnhead = v.findViewById(R.id.butoon111);

        btnhairloss = v.findViewById(R.id.butoon112);
        btnheadache = v.findViewById(R.id.butoon113);
        btnchestandback = v.findViewById(R.id.butoon114);
        btnchestpain = v.findViewById(R.id.butoon115);
        btnLowerback = v.findViewById(R.id.butoon116);



// Click Head to view Hairloss & Headache  -- Click again to Hide Them
        btnhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnhairloss.getVisibility()== View.GONE && btnheadache.getVisibility()== View.GONE){
                    btnhairloss.setVisibility(View.VISIBLE);
                    btnheadache.setVisibility(View.VISIBLE);

                } else {
                    btnhairloss.setVisibility(View.GONE);
                    btnheadache.setVisibility(View.GONE);

                }
            }
        });
// Click Chest and back to view chest & back  -- Click again to Hide Them
        btnchestandback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnchestpain.getVisibility()== View.GONE && btnLowerback.getVisibility()== View.GONE){
                    btnchestpain.setVisibility(View.VISIBLE);
                    btnLowerback.setVisibility(View.VISIBLE);

                } else {
                    btnchestpain.setVisibility(View.GONE);
                    btnLowerback.setVisibility(View.GONE);

                }

            }
        });

//Click Eye to start Actual diagnosis
        btnhairloss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// bundle to move argument from fragment to another
                Bundle bundle = new Bundle();
                bundle.putString("Key","Hair loss");
                bundle.putString("idx","");
// object from class
                User_Diagnosis_Question_Fragment user_diagnosis_question_fragment = new User_Diagnosis_Question_Fragment();
                user_diagnosis_question_fragment.setArguments(bundle);
// move to another fragment by fragment container and targeted fragment (object from fragment class)
                getFragmentManager().beginTransaction().replace(R.id.UserDiagnosisFragmentContaner,user_diagnosis_question_fragment).commit();


            }
        });

        btnheadache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // bundle to move argument from fragment to another
                Bundle bundle = new Bundle();
                bundle.putString("Key","Headache");
                bundle.putString("idx","");
// object from class
                User_Diagnosis_Question_Fragment user_diagnosis_question_fragment = new User_Diagnosis_Question_Fragment();
                user_diagnosis_question_fragment.setArguments(bundle);
// move to another fragment by fragment container and targeted fragment (object from fragment class)
                getFragmentManager().beginTransaction().replace(R.id.UserDiagnosisFragmentContaner,user_diagnosis_question_fragment).commit();


            }
        });

        btnchestpain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bundle to move argument from fragment to another
                Bundle bundle = new Bundle();
                bundle.putString("Key","Chest pain");
                bundle.putString("idx","");
// object from class
                User_Diagnosis_Question_Fragment user_diagnosis_question_fragment = new User_Diagnosis_Question_Fragment();
                user_diagnosis_question_fragment.setArguments(bundle);
// move to another fragment by fragment container and targeted fragment (object from fragment class)
                getFragmentManager().beginTransaction().replace(R.id.UserDiagnosisFragmentContaner,user_diagnosis_question_fragment).commit();


            }
        });

        btnLowerback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bundle to move argument from fragment to another
                Bundle bundle = new Bundle();
                bundle.putString("Key","pain on Lower back");
                bundle.putString("idx","");
// object from class
                User_Diagnosis_Question_Fragment user_diagnosis_question_fragment = new User_Diagnosis_Question_Fragment();
                user_diagnosis_question_fragment.setArguments(bundle);
// move to another fragment by fragment container and targeted fragment (object from fragment class)
                getFragmentManager().beginTransaction().replace(R.id.UserDiagnosisFragmentContaner,user_diagnosis_question_fragment).commit();

            }
        });


        return v;
    }
}
