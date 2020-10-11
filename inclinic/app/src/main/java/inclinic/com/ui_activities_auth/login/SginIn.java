package inclinic.com.ui_activities_auth.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inclinic.com.Admin.AdminActivity;
import inclinic.com.Admin.Blocked_activity;
import inclinic.com.Admin_Navigation;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.ui_activities_auth.doctor_signup.Doctor_Appointments;
import inclinic.com.ui_activities_auth.doctor_signup.Doctor_rejected_thanks;
import inclinic.com.ui_activities_auth.doctor_signup.Thanks_for_doctor_registeration;
import inclinic.com.ui_activities_auth.splash.Splash_Screen;

public class SginIn extends AppCompatActivity {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    // Write a message to the database
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;

    FirebaseUser user;
    public static SharedPreferences SP ;
    public static String filename = "Keys";
    public static boolean isConnected;

    final boolean[] will_block_counter = {false};

    protected EditText userEmail_et, userPassword_et;
    protected Button login_btn;
    protected TextView signup;
    protected TextView forgetPass;
    CheckBox remember_me;

    ProgressBar progressBar ;
    private boolean clickable = true;

    User_Model current_doctor_model = new User_Model();
    boolean is_hide_pass = true ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);

        userEmail_et = findViewById(R.id.ui_EmailAddress_EditText_LoginActivity);
        userPassword_et = findViewById(R.id.ui_Password_EditText_LoginActivity);
        login_btn = findViewById(R.id.ui_login_btn);
//        signup = findViewById(R.id.loginAct_createacc);
        SpannableString content = new SpannableString("Sign Up");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        signup.setText(content);
        progressBar = findViewById(R.id.main_progress);
        remember_me=findViewById(R.id.remember_me_ui);
        forgetPass=findViewById(R.id.forget_password);

        SP = getSharedPreferences(filename, MODE_PRIVATE);
        String getname = SP.getString("UserName_saved_key1","");
        String getpass = SP.getString("Password_Saved_key2","");

//        current_doctor_model.setIs_doctor(false);
//        current_doctor_model.setIs_user(false);
//        current_doctor_model.setVerified_from_inclinic_app_team(false);
//        current_doctor_model.setArea_location("null");
//        current_doctor_model.setSpecialization("null");
//        if (getname!=null && getpass!=null){
//
//            startActivity(new Intent(SginIn.this , MainActivity.class));
//        }

        userEmail_et.setText(getname);
        userPassword_et.setText(getpass);


        /*
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                preventTwoClick(v);
                <TextView
            android:id="@+id/loginAct_createacc"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:gravity="center"
            android:textColor="@color/colorPrimary"
            android:textSize="18sp"
            android:layout_marginTop="12dp"
            android:textStyle="bold"
            />
                progressBar.setVisibility(View.VISIBLE);
                final Intent i = new Intent(SginIn.this, signUP_activity.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
            }
        });
*/

        userPassword_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (userPassword_et.getRight() - userPassword_et.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if(is_hide_pass == true)
                        {
                            is_hide_pass = false;
                            userPassword_et.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        }
                        else
                        {
                            is_hide_pass = true;
                            userPassword_et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                        return true;
                    }
                }
                return false;
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                preventTwoClick(v);

                progressBar.setVisibility(View.VISIBLE);

                if(remember_me.isChecked())
                {
                    rememberMe(userEmail_et.toString(),userPassword_et.toString());
                }
                else
                {
                    SharedPreferences preferences =getSharedPreferences(filename,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("UserName_saved_key1");
                    editor.remove("Password_Saved_key2");
                    editor.apply();
                    editor.clear();
                    editor.commit();
                }
                signin(userEmail_et.getText().toString(), userPassword_et.getText().toString());
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                preventTwoClick(v);

                progressBar.setVisibility(View.VISIBLE);
                final Intent i = new Intent(SginIn.this, Forget_password_activity.class);
                startActivity(i);
                progressBar.setVisibility(View.GONE);
            }
        });



    }

    public void rememberMe(String user, String password){
        //save username and password in SharedPreferences

        SharedPreferences.Editor editit = SP.edit();
        editit.putString("UserName_saved_key1", userEmail_et.getText().toString());
        editit.putString("Password_Saved_key2",userPassword_et.getText().toString());
        editit.commit();

    }



    public static class InternetDialog
    {
        private Context context;

        public InternetDialog()
        {

        }

        public InternetDialog(Context context)
        {
            this.context = context;
        }

        public void showNoInternetDialog()
        {
            final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
            dialog1.setContentView(R.layout.dialog_no_internet);
            dialog1.setCancelable(true);
            dialog1.setCanceledOnTouchOutside(true);
            dialog1.findViewById(R.id.btnSpinAndWinRedeem).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(!isConnected)
                    {
                        dialog1.dismiss();
                        getInternetStatus();
                    }
                    else
                    {
                        dialog1.dismiss();
                    }
                }
            });
            dialog1.show();
        }



        public  boolean getInternetStatus()
        {
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if(!isConnected)
            {
                //show no internet dialog
                showNoInternetDialog();
            }
            else
            {

            }
            return isConnected;
        }
    }



    public static boolean isBlank(String value)
    {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isValidPasswordLenghth(final String password)
    {
        return password.length() >= 8;
    }


    void signin(final String email, final String password)
    {
        FirebaseAuth.getInstance().signOut();

        if(Splash_Screen.verion_on_server!=Splash_Screen.this_version_of_program&&Splash_Screen.verion_on_server!=null)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Inclinic");
            builder1.setMessage("New Update Is Found Please Install It To Improve The Performance");
            builder1.setCancelable(false);


            builder1.setNegativeButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    AlertDialog.Builder builder12 = new AlertDialog.Builder(getApplicationContext());
                    builder12.setTitle("Inclinic");
                    builder12.setMessage("Please Contact To Developer To Get The Update");
                    builder12.setCancelable(false);
                    AlertDialog alert112 = builder12.create();
                    alert112.show();
                }

            });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return;
        }


        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        if (networkInfo != null)
        {
            if (isBlank(userEmail_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (isBlank(userPassword_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Password Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if(!isEmailValid(userEmail_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }


            if(!isValidPasswordLenghth(userPassword_et.getText().toString()))
            {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "password must be at least 8 characters", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        user = FirebaseAuth.getInstance().getCurrentUser();



                        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals("Eg9OWuVpFHfTeCcT9BmR6QQAgH03")){
                            rememberMe(email,password);
                            TastyToast.makeText(getApplicationContext(), "Admin Signed in", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                            finishAffinity();
                            final Intent i = new Intent(SginIn.this,  Admin_Navigation.class);
                            startActivity(i);
                            return;
                        }

                        if (user.isEmailVerified())
                        {

                            reference.child("pending_block").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChildren())
                                    {
                                        will_block_counter[0] =true;
                                        reference.child("pending_block").child(user.getUid()).removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(SginIn.this, "Check Your connection", Toast.LENGTH_SHORT).show();
                                }
                            });

                            reference.child("users").child(user.getUid()).child("Personal Data").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    current_doctor_model=dataSnapshot.getValue(User_Model.class);

                                    if(current_doctor_model.getIs_user()==true)
                                    {
                                        if(current_doctor_model.getCounter_block()>=3)
                                        {
                                            final Intent i = new Intent(SginIn.this, Blocked_activity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                        else
                                        {
                                            if(will_block_counter[0] == true)
                                            {
                                                int current_block_counter = current_doctor_model.getCounter_block();
                                                current_block_counter++;
                                                current_doctor_model.setCounter_block(current_block_counter);
                                                reference.child("users").child(user.getUid()).child("Personal Data").setValue(current_doctor_model);
                                            }

                                            TastyToast.makeText(getApplicationContext(), "Login Successful", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            HomeActivity home = new HomeActivity(current_doctor_model);
                                            SetUserVisit();
                                            final Intent i = new Intent(SginIn.this, home.getClass());
                                            startActivity(i);
                                            finish();
                                        }

                                    }
                                    else if(current_doctor_model.isIs_doctor()==true)
                                    {
                                        Log.d("Sha3booony_debug", "User is Doctor");
                                        Log.d("Sha3booony_debug", ""+current_doctor_model.isVerified_from_inclinic_app_team());
                                        String get_location_area=current_doctor_model.getArea_location();
                                        String get_specialization=current_doctor_model.getSpecialization();

                                        if(current_doctor_model.isVerified_from_inclinic_app_team()==true)
                                            reference.child("doctors").child(get_location_area).child(get_specialization).addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.hasChild(user.getUid()))
                                                    {
                                                        TastyToast.makeText(getApplicationContext(), "Welcome Doctor", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                        HomeActivity home = new HomeActivity(current_doctor_model);
                                                        final Intent i = new Intent(SginIn.this, home.getClass());
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    FirebaseAuth.getInstance().signOut();
                                                    TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                    HomeActivity home = new HomeActivity(current_doctor_model);
                                                    final Intent i = new Intent(SginIn.this, home.getClass());                                                                startActivity(i);
                                                    finish();
                                                }
                                            });

                                        reference.child("pending_doctors").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user.getUid()))
                                                {
                                                    TastyToast.makeText(getApplicationContext(), "Welcome Doctor "+current_doctor_model.getName(), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                    final Intent i = new Intent(SginIn.this, Thanks_for_doctor_registeration.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                                else
                                                {
                                                    reference.child("pending_appointments").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.hasChild(user.getUid()))
                                                            {
                                                                TastyToast.makeText(getApplicationContext(), "you Accepted in our app please Choose your appointments", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                final Intent i = new Intent(SginIn.this, Doctor_Appointments.class);
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            FirebaseAuth.getInstance().signOut();
                                                            TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                            HomeActivity home = new HomeActivity(current_doctor_model);
                                                            final Intent i = new Intent(SginIn.this, home.getClass());
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
                                                HomeActivity home = new HomeActivity(current_doctor_model);
                                                final Intent i = new Intent(SginIn.this, home.getClass());                                                            startActivity(i);
                                                finish();
                                            }
                                        });
                                        reference.child("doctor_rejects").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild(user.getUid()))
                                                {
                                                    TastyToast.makeText(getApplicationContext(), "i'm Sorry for that", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                    final Intent i = new Intent(SginIn.this, Doctor_rejected_thanks.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                FirebaseAuth.getInstance().signOut();
                                                TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                HomeActivity home = new HomeActivity(current_doctor_model);
                                                final Intent i = new Intent(SginIn.this, home.getClass());
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
                                    HomeActivity home = new HomeActivity(current_doctor_model);
                                    final Intent i = new Intent(SginIn.this, home.getClass());
                                    startActivity(i);
                                    finish();
                                }
                            });


                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            TastyToast.makeText(getApplicationContext(), "Please Verify Your Email Address and sign again", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            return;
                        }


                    }
                    if(!task.isSuccessful())
                    {
                        try {
                            throw task.getException();
                        }
                        catch (Exception e) {
                            TastyToast.makeText(getApplicationContext(), "Error In :" + e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                    }
                }
            });
            return;

        }
        else
        {
            new InternetDialog(SginIn.this).getInternetStatus();
            progressBar.setVisibility(View.GONE);
            return;
        }

    }

    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }



    public void SetUserVisit(){

        DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query = fb;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int No = Integer.parseInt(snapshot.child("noVisitor").getValue().toString());

                DatabaseReference numMesasReference = snapshot.getRef().child("noVisitor");
                numMesasReference.setValue(No + 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }






}
