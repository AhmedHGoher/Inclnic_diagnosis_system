package inclinic.com.Ads;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import inclinic.com.R;
import inclinic.com.uiD.user_diagnosis.User_Diagnosis_Question_Fragment;

import androidx.appcompat.app.AppCompatActivity;

public class Ads extends AppCompatActivity {

    Animation anim;
    ImageView imageView,imageView2;
    String data,idx;
    // TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads);

        imageView=(ImageView)findViewById(R.id.imageView2);                            // Declare an imageView to show the animation.
        imageView2=(ImageView)findViewById(R.id.imageView4);                            // Declare an imageView to show the animation.
        // textView=(TextView)findViewById(R.id.textView2);


        Intent it = getIntent();

        data = it.getStringExtra("data");
        idx = it.getStringExtra("idx");


        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ads); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                // bundle to move argument from fragment to another
                Bundle bundle = new Bundle();
                bundle.putString("Key",data);
                bundle.putString("idx", idx);
                // object from class
                User_Diagnosis_Question_Fragment user_diagnosis_question_fragment = new User_Diagnosis_Question_Fragment();
                user_diagnosis_question_fragment.setArguments(bundle);
                // move to another fragment by fragment container and targeted fragment (object from fragment class)
                getSupportFragmentManager().beginTransaction().replace(R.id.Ads_id,user_diagnosis_question_fragment).commit();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
        imageView2.startAnimation(anim);
        // textView.startAnimation(anim);
    }

}
