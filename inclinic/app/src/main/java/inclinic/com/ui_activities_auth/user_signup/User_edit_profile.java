package inclinic.com.ui_activities_auth.user_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inclinic.com.Admin.AllDoc.Doctor_edit_information;
import inclinic.com.Admin.AllDoc.Doctor_thanks_for_edit_profile_data;
import inclinic.com.HomeActivity;
import inclinic.com.R;

public class User_edit_profile extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    EditText username;
    EditText age;
    EditText phoneNum;
    Button confirm;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        username=findViewById(R.id.name_ui_EditText_userEdit_profile);
        age=findViewById(R.id.age_ui_EditText_userEdit_profile);
        phoneNum=findViewById(R.id.phonenum_ui_EditText_userEdit_profile);
        confirm=findViewById(R.id.ui_btn_submit_ui_userEdit_profile);
        progressBar=findViewById(R.id.main_progress_userEdit_profile);

        username.setText( HomeActivity.user_realtime.getName());
        age.setText(String.valueOf(HomeActivity.user_realtime.getAge()));
        phoneNum.setText(HomeActivity.user_realtime.getPhonenumber());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                HomeActivity.user_realtime.setName(username.getText().toString());
                HomeActivity.user_realtime.setAge(Integer.parseInt(age.getText().toString()));
                HomeActivity.user_realtime.setPhonenumber(phoneNum.getText().toString());

                if (isBlank(username.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Name can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(age.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "age can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(phoneNum.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "phoneNum can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }
                uploadData();
            }
        });

    }
    void uploadData(){
        reference.child("users").child(HomeActivity.user_realtime.getUID()).child("Personal Data").setValue(HomeActivity.user_realtime).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                TastyToast.makeText(getApplicationContext(), "Edit Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                final Intent i = new Intent(User_edit_profile.this, Doctor_thanks_for_edit_profile_data.class);
                startActivity(i);
                finish();
                finish();
            }
        });
    }

    public static boolean validateLetters(String txt) {
        String regx = "[a-zA-Z]+\\.?";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 16;
        }
        return false;
    }

    public static boolean isBlank(String value) {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isValidNumberLenghth(final EditText number) {
        return number.toString().length() >= 10;
    }

}