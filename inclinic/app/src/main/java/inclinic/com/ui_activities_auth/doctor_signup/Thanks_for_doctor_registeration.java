package inclinic.com.ui_activities_auth.doctor_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import inclinic.com.R;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.ui_activities_auth.splash.splash_loin_sign;

public class Thanks_for_doctor_registeration extends AppCompatActivity {


    Animation anim;
    ImageView imageView,imageView2;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_for_doctor_registeration);


        imageView = (ImageView)findViewById(R.id.Logo_Thanks);
        imageView2 = (ImageView)findViewById(R.id.image_Thanks);
        textView = (TextView)findViewById(R.id.txt_Thanks);


        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ads); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                final Intent i = new Intent(Thanks_for_doctor_registeration.this, splash_loin_sign.class);
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


    }
}