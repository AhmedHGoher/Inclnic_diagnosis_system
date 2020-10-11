package inclinic.com.ui_activities_auth.doctor_signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.ui_activities_auth.login.SginIn;

import static java.lang.Integer.parseInt;

public class Doctor_signup extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    public static SharedPreferences SP;
    public static String filename = "Keys";
    public static boolean isConnected;

    public User_Model doctor_person = new User_Model();
    public String url;
    public String uid;
    EditText email_address;
    EditText Password;
    EditText repeated_password;
    EditText fullname;
    EditText phone_num;
    EditText about_doctor;

    EditText location_text;
    EditText location_maps;
    EditText price_per_hour;
    EditText id_number;
    EditText facilty_name;
    EditText graduation_year;
    EditText age;
    Button Sign_up_doctor;
    String selected_specialization;
    String selected_location_area;
    ImageView img_upload_select;
    Button btn_upload_photo;
    Uri FilePathUri;




    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        progressBar = findViewById(R.id.main_progress_doctor);
        email_address = findViewById(R.id.ui_EmailAddress_EditText_doctor);
        Password = findViewById(R.id.ui_Password_EditText_doctor);
        fullname = findViewById(R.id.name_ui_EditText_doctor);
        phone_num = findViewById(R.id.phonenum_ui_EditText_doctor);
        Sign_up_doctor = findViewById(R.id.ui_signup_Button_ui_doctor);
        repeated_password = findViewById(R.id.ui_Password_confirm_EditText_doctor);
        id_number = findViewById(R.id.id_number_ui_EditText_doctor);
        facilty_name = findViewById(R.id.facultyname_and_collageName_ui_EditText_doctor);
        graduation_year = findViewById(R.id.graduation_year_ui_EditText_doctor);
        location_text = findViewById(R.id.clinic_location_ui_EditText_doctor);
        location_maps = findViewById(R.id.clinic_location_maps_link_ui_EditText_doctor);
        price_per_hour = findViewById(R.id.price_per_hour_ui_EditText_doctor);
        about_doctor = findViewById(R.id.about_me_ui_EditText_doctor);
        age = findViewById(R.id.age_ui_EditText_doctor);
        Spinner spinner_specialization = (Spinner) findViewById(R.id.specialization_ui_spinner_doctor);
        Spinner spinner_location_area = (Spinner) findViewById(R.id.clinic_area_ui_spinner_doctor);
        img_upload_select=findViewById(R.id.imgPersonToUploadUi);
        btn_upload_photo=findViewById(R.id.buttontouploadui);

        doctor_person.setIs_user(false);
        doctor_person.setIs_doctor(true);
        doctor_person.setVerified_from_inclinic_app_team(false);
        Sign_up_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                preventTwoClick(v);

                createAccount(email_address.getText().toString(), Password.getText().toString(), fullname.getText().toString(), phone_num.getText().toString());
            }
        });

        spinner_specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_specialization = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_specialization = "General";
            }
        });

        spinner_location_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_location_area = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_specialization = "Zamalek";
            }
        });

        btn_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });


    }

    private void selectImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        startActivityForResult(pickPhoto, 10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            FilePathUri = data.getData();
            img_upload_select.setImageURI(FilePathUri);
        }
    }

    private void uploadUserImage(final String uid)
    {
        if (FilePathUri == null)
        {
            TastyToast.makeText(getApplicationContext(), " Please select image ", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            selectImage();
            return;
        }
        else
        {
            storageRef.child("profileImages").child(uid).putFile(FilePathUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        storageRef.child("profileImages").child(uid).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                                if (task.isSuccessful())
                                {
                                    url = task.getResult().toString();
                                    setData(url,email_address.getText().toString(), Integer.valueOf(age.getText().toString()), fullname.toString(), phone_num.getText().toString(), selected_specialization.toString(),
                                            facilty_name.getText().toString(), Integer.parseInt(graduation_year.getText().toString()), id_number.getText().toString(),
                                            Double.valueOf(price_per_hour.getText().toString()), location_text.getText().toString(),
                                            location_maps.getText().toString(), uid.toString(), false);                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                    final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                    currentUser.delete();
                                    TastyToast.makeText(getApplicationContext(), "Error While Getting Link Upload The Photo", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                    return;
                                }
                            }
                        });
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        currentUser.delete();
                        TastyToast.makeText(getApplicationContext(), "Error While Upload The Photo", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        return;

                    }
                }
            });
        }

    }

    private void createAccount(final String email, String password, final String name, final String phone) {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (isBlank(email)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (!isEmailValid(email)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Email isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (isBlank(name)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Name Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }


            if (!validateLetters(name)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Name isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }


            if (!isValidNumberLenghth(phone_num)) {

                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (!isValidMobile_build_in_adnroid_stuido(phone_num.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (!isValidMobile(phone_num.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }


            if (isBlank(password)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Password Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (isBlank(repeated_password.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Repeated Password Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (!isValidPasswordLenghth(password)) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "password must be at least 8 characters", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (!password.equals(repeated_password.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "passwords Don't Match", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (isBlank(id_number.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "ID Number can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (isBlank(location_text.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Location TEXT can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (isBlank(location_maps.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Location Link Maps can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (isBlank(price_per_hour.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Price Per hour can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            if (!isValidMobile(id_number.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "ID Number isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (Integer.valueOf(graduation_year.getText().toString()) < 1900 || Integer.valueOf(graduation_year.getText().toString()) > year) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Please Enter a valid Graduation Year", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }

            if (isBlank(about_doctor.getText().toString())) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "About You Can't Be Empty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                return;
            }

            // Code For Create The Account


            FirebaseAuth.getInstance().signOut();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        uid = task.getResult().getUser().getUid();

                        uploadUserImage(uid);
                        //uploadUserImage(uid);

                    }
                    if (!task.isSuccessful()) {
                        try {

                            throw task.getException();
                        }
                        // if user enters wrong email.
                        catch (FirebaseAuthWeakPasswordException weakPassword) {

                            TastyToast.makeText(getApplicationContext(), "Weak password .. Please Choose A Valid Password", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;

                        }
                        // if user enters wrong password.
                        catch (FirebaseAuthInvalidCredentialsException malformedEmail) {

                            TastyToast.makeText(getApplicationContext(), "Malformed Email .. Please Change Your Email", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;


                        } catch (FirebaseAuthUserCollisionException existEmail) {


                            TastyToast.makeText(getApplicationContext(), "Exist Email .. Please Change Your Email", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;


                        } catch (Exception e) {
                            TastyToast.makeText(getApplicationContext(), "Error In :" + e.getMessage(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            progressBar.setVisibility(View.GONE);
                            return;
                        }
                    }

                }
            });
            return;

        } else {
            progressBar.setVisibility(View.GONE);

            if (new SginIn.InternetDialog(this).getInternetStatus()) {
            }
            return;
        }


    }


    public static void preventTwoClick(final View view) {
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }

    private boolean isValidMobile_build_in_adnroid_stuido(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 16;
        }
        return false;
    }

    // Checkers Et
    public static boolean isBlank(String value) {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static boolean isValidPasswordLenghth(final String password) {
        return password.length() >= 8;
    }


    public static boolean isValidNumberLenghth(final EditText number) {
        return number.toString().length() >= 10;
    }


    public static boolean validateLetters(String txt) {
        String regx = "[a-zA-Z]+\\.?";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }


    public void setData(final String url_imageprofile,final String email, final int age, final String name, final String phonenummmm, String speciali,
                        String fac_name, Integer graduation_yeear, String id_num, Double price_hour,
                        String text_location, String text_location_maps, String doctor_uiddddd, boolean verified) {
        String searchInputToLower = fullname.getText().toString().toLowerCase();
        doctor_person.setLower_doctor_name_search(searchInputToLower);
        doctor_person.setEmail(email);
        doctor_person.setName(fullname.getText().toString());
        doctor_person.setUrl_image_profile(url_imageprofile);
        doctor_person.setAge(age);
        doctor_person.setPhonenumber(phonenummmm);
        doctor_person.setUID(uid);
        doctor_person.setIs_doctor(true);
        doctor_person.setSpecialization(speciali);
        doctor_person.setFacilty_name(fac_name);
        doctor_person.setGraduation_year(graduation_yeear);
        doctor_person.setId_number(id_num);
        doctor_person.setPrice_per_hour(price_hour);
        doctor_person.setArea_location(selected_location_area);
        doctor_person.setAbout_doctor(about_doctor.getText().toString());
        doctor_person.setLocation_text(text_location);
        doctor_person.setLocation_maps(text_location_maps);
        doctor_person.setDoctor_uid(doctor_uiddddd);
        doctor_person.setVerified_from_inclinic_app_team(false);
        doctor_person.setTotal_kashf(0);
        doctor_person.setRate_total(0);
        doctor_person.setTotal_star_rate(0);
        doctor_person.setRate_click(0);

        upload_doctor_user();

    }

    public void upload_doctor_user() {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Doctor_selected_area",selected_location_area);
        editor.putString("Doctor_selected_specialization",selected_specialization);
        editor.apply();

//        SharedPreferences.Editor editit = SginIn.SP.edit();
//        editit.putString("UserName_saved_key1", Email.getText().toString());
//        editit.putString("Password_Saved_key2", Password.getText().toString());
//        editit.putString("Doctor_selected_area", selected_location_area);
//        editit.putString("Doctor_selected_specialization", selected_specialization);
//        editit.putString("Doctor_uid", uid);
//        editit.commit();

        doctor_person.setVerified_from_inclinic_app_team(false);
        doctor_person.setIs_doctor(true);

        databaseReference.child("users").child(uid).child("Personal Data").setValue(doctor_person).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    final FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                              upload_doctor_aprroved();
                                upload_doctor_pending();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                currentUser.delete();
                                TastyToast.makeText(getApplicationContext(), "SignUP Failed .. " + task.getException(), TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                return;
                            }
                        }
                    });

                } else {
                    progressBar.setVisibility(View.GONE);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    currentUser.delete();
                    TastyToast.makeText(getApplicationContext(), "Error While Create User Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    return;
                }

            }
        });
    }


    public void upload_doctor_pending() {
//        databaseReference.child("pending_doctors").child(selected_location_area).child(selected_specialization).child(uid).child("Personal Data").setValue(doctor_person).addOnCompleteListener(new OnCompleteListener<Void>() {
        databaseReference.child("pending_doctors").child(uid).setValue(doctor_person).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    final FirebaseUser user = mAuth.getCurrentUser();
                    final Intent i = new Intent(Doctor_signup.this, Thanks_for_doctor_registeration.class);
                    startActivity(i);
                    finish();

                } else {
                    progressBar.setVisibility(View.GONE);
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    currentUser.delete();
                    TastyToast.makeText(getApplicationContext(), "Error While Create User Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    return;
                }

            }
        });
    }


}