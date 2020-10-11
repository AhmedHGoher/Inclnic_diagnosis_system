package inclinic.com.ui_activities_auth.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.ui.Profile.ProfileFragment;
import inclinic.com.ui_activities_auth.doctor_signup.Doctor_signup;
import inclinic.com.ui_activities_auth.login.SginIn;
import inclinic.com.ui_activities_auth.user_signup.SignUP_activity;

public class splash_loin_sign extends AppCompatActivity {
Button btn_signin;
Button btn_signup_user;
Button btn_signup_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_loin_sign);
        btn_signin=findViewById(R.id.Login_id);
        btn_signup_user=findViewById(R.id.SignUp_User);
        btn_signup_doctor=findViewById(R.id.SignUp_Doctor);


        Animatoo.animateFade(this);


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Intent i = new Intent(splash_loin_sign.this, SginIn.class);
                startActivity(i);
            }
        });

        btn_signup_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(splash_loin_sign.this, SignUP_activity.class);
                startActivity(i);
            }
        });

        btn_signup_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(splash_loin_sign.this, Doctor_signup.class);
                startActivity(i);
            }
        });
    }
}