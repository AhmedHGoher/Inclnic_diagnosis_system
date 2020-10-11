package inclinic.com.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import inclinic.com.R;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.ui_activities_auth.splash.splash_loin_sign;

public class Blocked_activity extends AppCompatActivity {

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocked_activity);



        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ads); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                final Intent i = new Intent(Blocked_activity.this, splash_loin_sign.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });


    }
}