package inclinic.com.ui_activities_auth.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import inclinic.com.R;
import inclinic.com.uiD.user_diagnosis.User_Diagnosis_Question_Fragment;
import inclinic.com.ui_activities_auth.splash.splash_loin_sign;

public class Logout_activity extends AppCompatActivity {


    Animation anim;
    ImageView imageView,imageView2;
    TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout_activity);

        imageView = (ImageView)findViewById(R.id.Logo_logout);
        imageView2 = (ImageView)findViewById(R.id.Sad_logout);
        textView = (TextView)findViewById(R.id.txt1_logout);
        textView2 = (TextView)findViewById(R.id.txt2_logout);

        FirebaseAuth.getInstance().signOut();


        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ads); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                final Intent i = new Intent(Logout_activity.this, splash_loin_sign.class);
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