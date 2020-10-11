package inclinic.com.ui_activities_auth.splash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import inclinic.com.Admin.Blocked_activity;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.ui.Book.BookModel;
import inclinic.com.ui_activities_auth.doctor_signup.Doctor_Appointments;
import inclinic.com.ui_activities_auth.doctor_signup.Doctor_rejected_thanks;
import inclinic.com.ui_activities_auth.doctor_signup.Thanks_for_doctor_registeration;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.ui_activities_auth.login.SginIn;

public class Splash_Screen extends AppCompatActivity {

    ImageView logo_splash_screen;
    private FirebaseAuth mAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    User_Model current_doctor_model = new User_Model();


    public static String loginPrefs = "m.ypre";
    public static String PREF_USERNAME, PREF_PASSWORD;


    public static SharedPreferences SP;
    public static String filename = "Keys";

    public static Integer this_version_of_program=1;
    public static Integer verion_on_server;

    public String username;
    public String password;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        SharedPreferences pref = getSharedPreferences(filename, MODE_PRIVATE);
        username = pref.getString("UserName_saved_key1", "");
        password = pref.getString("Password_Saved_key2", "");


        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        YoYo.with(Techniques.Pulse)
                .duration(300)
                .repeat(5)
                .playOn(findViewById(R.id.img_spash));

        current_doctor_model.setIs_doctor(false);
        current_doctor_model.setIs_user(false);
        current_doctor_model.setVerified_from_inclinic_app_team(false);
        current_doctor_model.setArea_location("null");
        current_doctor_model.setSpecialization("null");
        final boolean[] will_block_counter = {false};

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                        reference.addListenerForSingleValueEvent(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                verion_on_server=dataSnapshot.child("version_update").getValue(Integer.class);

                                if(verion_on_server==null)
                                {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Splash_Screen.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("New updates - Fix's")
                                            .setMessage("Error in database.\n" +
                                                    "please contact to the developer .")
                                            .setCancelable(false)
                                            .show();
                                    return;
                                }

                                if(verion_on_server!=1)
                                {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Splash_Screen.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("New updates - Fix's")
                                            .setMessage("To improve application performance you have to update our application to the last version .\n" +
                                                    "please contact to the developer .")
                                            .setCancelable(false)
                                            .show();
                                    return;
                                }

                                if (username != null || password != null)
                                {
                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    if (user!=null)
                                    {


                                        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("Eg9OWuVpFHfTeCcT9BmR6QQAgH03"))
                                        {
                                            FirebaseAuth.getInstance().signOut();
                                            TastyToast.makeText(getApplicationContext(), "Welcome Admin please sign in again to control the app", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                            startActivity(i);
                                            finish();
                                            return;
                                        }

                                        if (user.isEmailVerified()) {

                                            reference.child("pending_block").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.hasChildren()) {
                                                        will_block_counter[0] = true;
                                                        reference.child("pending_block").child(user.getUid()).removeValue();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    Toast.makeText(Splash_Screen.this, "Check Your connection", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            reference.child("users").child(user.getUid()).child("Personal Data").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    current_doctor_model = dataSnapshot.getValue(User_Model.class);

                                                    if (current_doctor_model.getIs_user() == true) {
                                                        if (current_doctor_model.getCounter_block() >= 3) {
                                                            final Intent i = new Intent(Splash_Screen.this, Blocked_activity.class);
                                                            startActivity(i);
                                                            finish();
                                                        } else {
                                                            if (will_block_counter[0] == true) {
                                                                int current_block_counter = current_doctor_model.getCounter_block();
                                                                current_block_counter++;
                                                                current_doctor_model.setCounter_block(current_block_counter);
                                                                reference.child("users").child(user.getUid()).child("Personal Data").setValue(current_doctor_model);
                                                            }


                                                            TastyToast.makeText(getApplicationContext(), "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                           // HomeActivity home = new HomeActivity(current_doctor_model);
                                                            final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                            startActivity(i);
                                                            finish();
                                                        }

                                                    } else if (current_doctor_model.isIs_doctor() == true) {

                                                        String get_location_area = current_doctor_model.getArea_location();
                                                        String get_specialization = current_doctor_model.getSpecialization();

                                                        if (current_doctor_model.isVerified_from_inclinic_app_team() == true)
                                                            reference.child("doctors").child(get_location_area).child(get_specialization).addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    if (dataSnapshot.hasChild(user.getUid())) {
                                                                        TastyToast.makeText(getApplicationContext(), "Welcome Doctor", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                        //HomeActivity home = new HomeActivity(current_doctor_model);
                                                                        final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                                        startActivity(i);
                                                                        finish();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                    FirebaseAuth.getInstance().signOut();
                                                                    TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                                    //HomeActivity home = new HomeActivity(current_doctor_model);
                                                                    final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                }
                                                            });

                                                        reference.child("pending_doctors").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.hasChild(user.getUid())) {
                                                                    TastyToast.makeText(getApplicationContext(), "Welcome Doctor " + current_doctor_model.getName(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                    final Intent i = new Intent(Splash_Screen.this, Thanks_for_doctor_registeration.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                } else {
                                                                    reference.child("pending_appointments").addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.hasChild(user.getUid())) {
                                                                                TastyToast.makeText(getApplicationContext(), "you Accepted in our app please Choose your appointments", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                                final Intent i = new Intent(Splash_Screen.this, Doctor_Appointments.class);
                                                                                startActivity(i);
                                                                                finish();
                                                                            }
                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                            FirebaseAuth.getInstance().signOut();
                                                                            TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                                           // HomeActivity home = new HomeActivity(current_doctor_model);
                                                                            final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                                            startActivity(i);
                                                                            finish();
                                                                        }
                                                                    });
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                FirebaseAuth.getInstance().signOut();
                                                                TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                               // HomeActivity home = new HomeActivity(current_doctor_model);
                                                                final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                        });
                                                        reference.child("doctor_rejects").addValueEventListener(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.hasChild(user.getUid())) {
                                                                    TastyToast.makeText(getApplicationContext(), "i'm Sorry for that", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                                    final Intent i = new Intent(Splash_Screen.this, Doctor_rejected_thanks.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                FirebaseAuth.getInstance().signOut();
                                                                TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                                //HomeActivity home = new HomeActivity(current_doctor_model);
                                                                final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                        });


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    FirebaseAuth.getInstance().signOut();
                                                    TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                   // HomeActivity home = new HomeActivity(current_doctor_model);
                                                    final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            });


                                        }
                                        else
                                        {
                                            TastyToast.makeText(getApplicationContext(), "Please Verify Your Email Address and sign again", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                            final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                            startActivity(i);
                                            finish();
                                            return;
                                        }

                                    }
                                    else
                                    {
                                        FirebaseAuth.getInstance().signOut();
                                        //HomeActivity home = new HomeActivity(current_doctor_model);
                                        final Intent i = new Intent(Splash_Screen.this, splash_loin_sign.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                else
                                {
                                    new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
                                    return;
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                new SginIn.InternetDialog(Splash_Screen.this).getInternetStatus();
                                return;
                            }
                        });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();

    }



}