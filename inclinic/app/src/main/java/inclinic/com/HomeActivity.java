package inclinic.com;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Map;

import inclinic.com.models.doctor.User_Model;

import inclinic.com.models.person.RatingModel;
import inclinic.com.ui.Book.BookModel;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.ui_activities_auth.login.SginIn;
import inclinic.com.ui_activities_auth.splash.Splash_Screen;

public class HomeActivity extends AppCompatActivity {

    public static User_Model user_realtime ;
    public static boolean is_doctor = false;
    public static boolean is_user = false;
    public  static boolean is_anon = false;
    private AppBarConfiguration mAppBarConfiguration;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    RatingModel model = new RatingModel();

    User_Model doctor_rate;
    BookModel bookModels = new BookModel();
    double current_total_star ;
    int rate_click;
    boolean is_reviewed = false;

    public HomeActivity()
    {

    }
    public  HomeActivity(User_Model doctor_realtime)
    {
        this.user_realtime=doctor_realtime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_appointment, R.id.nav_profile , R.id.nav_User_Diagnosis, R.id.nav_User_MedicalAnalysis, R.id.nav_User_CaseAnalysis , R.id.OCR, R.id.nav_aboutus, R.id.nav_contactus)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null)
            {
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("OLzcm012ErdqS2XkaQZ13eTxX693"))
                {
                    FirebaseAuth.getInstance().signOut();
                    TastyToast.makeText(getApplicationContext(), "Welcome Admin please sign in again to control the app", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    final Intent i = new Intent(HomeActivity.this, SginIn.class);
                    startActivity(i);
                    finish();
                    return;
                }
                else if(user_realtime.getIs_user()==true)
                {
//                    Toast.makeText(this, "Welcome User ( "+user_realtime.getName()+" )", Toast.LENGTH_SHORT).show();
                    is_user=true;

                    if(is_reviewed==false)
                    {
                        post_review();
                    }
                    else
                    {
                        return;
                    }

                }
                else if(user_realtime.isIs_doctor()==true)
                {
                    Toast.makeText(this, "Welcome Doctor ( "+user_realtime.getName()+" )", Toast.LENGTH_SHORT).show();
                    is_doctor=true;
                    finishAffinity();
                    final Intent i = new Intent(getApplicationContext(), Doctor_Home.class);
                    startActivity(i);
//                ActivityCompat.finishAffinity(this);
                }
            }
            else
            {
                is_anon = true;
                Toast.makeText(this, "Signed in anonymous", Toast.LENGTH_SHORT).show();
            }
        }
        else
    {
        new SginIn.InternetDialog(getApplicationContext()).getInternetStatus();
        return;
    }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        int id = item.getItemId();
//
//        if(id == R.id.action_settings_logout_item);
//        {
//            if(is_anon==true)
//            {
//                Toast.makeText(this, "You Haven't Sign in yet !", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                FirebaseAuth.getInstance().signOut();
//                final Intent i = new Intent(getApplicationContext(), Logout_activity.class);
//                startActivity(i);
//                finish();
//            }
//        }
        return super.onOptionsItemSelected(item);
    }

    public void doThis(MenuItem item) {
        if (is_anon == true) {
            Toast.makeText(this, "You Haven't Sign in yet !", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth.getInstance().signOut();
            final Intent i = new Intent(getApplicationContext(), Logout_activity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }

    void post_review()
    {
        reference.child("pending_rate").child(user_realtime.getUID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren())
                {
                    bookModels = dataSnapshot.getValue(BookModel.class);

                    final Dialog dialog = new Dialog(HomeActivity.this);
                    dialog.setContentView(R.layout.rate_dialog);
                    dialog.setCancelable(false);
                    final TextView details_appointmets=dialog.findViewById(R.id.day_name_appointment_and_time);
                    final RatingBar rate_doctor=dialog.findViewById(R.id.ratingBar_appointment_user_rate_dialog);
                    final EditText review_doctor=dialog.findViewById(R.id.review_edittext_appointment_rate_dialog);
                    final Button add_rev=dialog.findViewById(R.id.rate_add);
                    final Button cancel_rev=dialog.findViewById(R.id.rate_cancel);
                    rate_doctor.setRating(Float.parseFloat("3.5"));

                    details_appointmets.setText("day : "+bookModels.getBookday()+" at " +bookModels.getBooktime()+"\n doctor : "+bookModels.getDoctorname());

                    cancel_rev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            reference.child("pending_rate").child(user_realtime.getUID()).removeValue();
                            dialog.dismiss();
                            return;
                        }
                    });

                    add_rev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reference.child("all_doctors").child(bookModels.getDoctorid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    reference.child("pending_rate").child(user_realtime.getUID()).removeValue();

                                    preventTwoClick(add_rev);

                                    if (isBlank(review_doctor.getText().toString()))
                                    {
                                        TastyToast.makeText(getApplicationContext(), "Review Message Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                                        return;
                                    }

                                    doctor_rate=dataSnapshot.getValue(User_Model.class);
                                    current_total_star=doctor_rate.getTotal_star_rate();
                                    current_total_star=current_total_star+rate_doctor.getNumStars();
                                    doctor_rate.setTotal_star_rate(current_total_star);
                                    rate_click=doctor_rate.getRate_click();
                                    rate_click++;
                                    doctor_rate.setRate_click(rate_click);
                                    double shown_rate =current_total_star/Double.valueOf(rate_click);
                                    doctor_rate.setRate_total(shown_rate);

                                    reference.child("all_doctors").child(bookModels.getDoctorid()).setValue(doctor_rate);
                                    reference.child("users").child(doctor_rate.getDoctor_uid()).child("Personal Data").setValue(doctor_rate);

                                    reference.child("doctors").child(doctor_rate.getArea_location()).child(doctor_rate.getSpecialization()).child(doctor_rate.getDoctor_uid()).child("total_star_rate").setValue(current_total_star);
                                    reference.child("doctors").child(doctor_rate.getArea_location()).child(doctor_rate.getSpecialization()).child(doctor_rate.getDoctor_uid()).child("rate_click").setValue(rate_click);
                                    reference.child("doctors").child(doctor_rate.getArea_location()).child(doctor_rate.getSpecialization()).child(doctor_rate.getDoctor_uid()).child("rate_total").setValue(doctor_rate.getRate_total());

                                    model.setDoctorid(doctor_rate.getDoctor_uid());
                                    model.setUsername(user_realtime.getName());
                                    model.setUserimg(".");
                                    model.setUserid(user_realtime.getUID());
                                    model.setRatemessage(review_doctor.getText().toString());
                                    model.setRatingstars(rate_doctor.getRating());

                                    String key = dataSnapshot.getKey();
                                    reference.child("doctors").child(doctor_rate.getArea_location()).child(doctor_rate.getSpecialization()).child(doctor_rate.getDoctor_uid()).child("Reviews").push().setValue(model);

                                    TastyToast.makeText(getApplicationContext(), " Thanks For your review ", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                                    dialog.dismiss();




                                    return;
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(HomeActivity.this, "Check Your connection", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }
                    });
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Check Your Connection.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

}