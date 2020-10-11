package inclinic.com.Admin.AllDoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import inclinic.com.R;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.ui_activities_auth.splash.splash_loin_sign;

public class Doctor_thanks_for_edit_profile_data extends AppCompatActivity {


    Animation anim;
    ImageView imageView,imageView2;
    TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_thanks_for_edit_profile_data);


        imageView = (ImageView)findViewById(R.id.Logo_Edit);
        imageView2 = (ImageView)findViewById(R.id.Happy_Edit);
        textView = (TextView)findViewById(R.id.txt1_Edit);
        textView2 = (TextView)findViewById(R.id.txt2_Edit);


        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ads); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                final Intent i = new Intent(Doctor_thanks_for_edit_profile_data.this, Doctor_edit_information.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


        imageView.startAnimation(anim);
        imageView2.startAnimation(anim);
        textView.startAnimation(anim);
        textView2.startAnimation(anim);


    }
}