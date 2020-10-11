package inclinic.com.Admin.AllDoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.ui_activities_auth.doctor_signup.Thanks_for_doctor_registeration;
import inclinic.com.ui_activities_auth.splash.Splash_Screen;

public class Doctor_edit_information extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    ImageView profile_img;
Button udate_img_btn;
Button submit_edit_btn;
EditText full_name;
EditText phone_num;
EditText age;
Spinner spinner_location_area_doctor;
EditText location_text;
EditText location_link_maps;
EditText price;
EditText about_me;
String selected_location_area;
ProgressBar progressBar;

Uri FilePathUri;
public String url;
public String old_area;
boolean update_img_or_not;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_information);


        Animatoo.animateFade(this);

        profile_img=findViewById(R.id.imgPersonToUploadUi_editprofile_doctor);
        udate_img_btn=findViewById(R.id.buttontouploadui_editprofile_doctor);
        full_name=findViewById(R.id.name_ui_EditText_doctor_editprofile_doctor);
        phone_num=findViewById(R.id.phonenum_ui_EditText_doctor);
        age=findViewById(R.id.age_ui_EditText_doctor_editprofile_doctor);
        location_text=findViewById(R.id.clinic_location_ui_EditText_doctor_editprofile_doctor);
        location_link_maps=findViewById(R.id.clinic_location_maps_link_ui_EditText_doctor_editprofile_doctor);
        price=findViewById(R.id.price_per_hour_ui_EditText_doctor_editprofile_doctor);
        about_me=findViewById(R.id.about_me_ui_EditText_doctor_editprofile_doctor);
        progressBar=findViewById(R.id.main_progress_doctor_editprofile_doctor);
        submit_edit_btn=findViewById(R.id.ui_submit_Button_ui_doctor_editprofile_doctor);

//        spinner_location_area_doctor = (Spinner) findViewById(R.id.clinic_area_ui_spinner_doctor_editprofile_doctor);
//
//        spinner_location_area_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selected_location_area = parent.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                selected_location_area = HomeActivity.user_realtime.getArea_location();
//            }
//        });


        /*
         <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:gravity="center"
                android:text="The clinic area"
                android:layout_marginTop="4dp"
                android:textColor="@color/colorPrimary"
                android:textSize="16sp"
                android:textStyle="bold"/>

            <Spinner
                android:id="@+id/clinic_area_ui_spinner_doctor_editprofile_doctor"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:hint="Enter your clinic location "
                android:entries="@array/location_pickup"
                android:textColor="#0A0A0A"
                android:layout_gravity="center"
                android:ellipsize="end"
                android:padding="14dp"
                android:background="@drawable/et_style"
                android:layout_marginTop="8dp"
                android:layout_marginHorizontal="14dp"
                />

         */

        Glide.with(getApplicationContext())
                .load(HomeActivity.user_realtime.getUrl_image_profile())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(profile_img);

        full_name.setText(HomeActivity.user_realtime.getName());
        phone_num.setText(HomeActivity.user_realtime.getPhonenumber());
        age.setText(String.valueOf(HomeActivity.user_realtime.getAge()));
        location_text.setText(HomeActivity.user_realtime.getLocation_text());
        location_link_maps.setText(HomeActivity.user_realtime.getLocation_maps());
        price.setText(HomeActivity.user_realtime.getPrice_per_hour().toString());
        about_me.setText(HomeActivity.user_realtime.getAbout_doctor());
        selected_location_area = HomeActivity.user_realtime.getArea_location();

        udate_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                update_img_or_not=true;
                selectImage();
            }
        });

        submit_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(update_img_or_not==true)
                {
                    if (FilePathUri == null)
                    {
                        TastyToast.makeText(getApplicationContext(), " Please select image ", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        selectImage();
                        return;
                    }
                    else
                    {
                        storageRef.child("profileImages").child(HomeActivity.user_realtime.getDoctor_uid()).putFile(FilePathUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                            {
                                if (task.isSuccessful())
                                {
                                    storageRef.child("profileImages").child(HomeActivity.user_realtime.getDoctor_uid()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                url = task.getResult().toString();
                                            }
                                            else
                                            {
                                                progressBar.setVisibility(View.GONE);
                                                TastyToast.makeText(getApplicationContext(), "Error While Getting Link Upload The Photo", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                                return;
                                            }
                                        }
                                    });
                                }
                                else
                                {
                                    progressBar.setVisibility(View.GONE);
                                    TastyToast.makeText(getApplicationContext(), "Error While Upload The Photo", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                    return;

                                }
                            }
                        });
                    }
                   HomeActivity.user_realtime.setUrl_image_profile(url);
                }

                if (isBlank(full_name.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Location TEXT can't be Emphty", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (!validateLetters(full_name.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Name isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(phone_num.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "PhoneNumber isn't Valid", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(age.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Age can't be blank !", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(location_text.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "location text can't be blank !", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(location_link_maps.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Location maps can't be blank !", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(price.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Price can't be blank !", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                if (isBlank(about_me.getText().toString())) {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "About you can't be blank !", TastyToast.LENGTH_SHORT, TastyToast.CONFUSING);
                    return;
                }

                HomeActivity.user_realtime.setName(full_name.getText().toString());
                HomeActivity.user_realtime.setPhonenumber(phone_num.getText().toString());
                HomeActivity.user_realtime.setAge(Integer.parseInt(age.getText().toString()));
                HomeActivity.user_realtime.setArea_location(selected_location_area);
                HomeActivity.user_realtime.setLocation_text(location_text.getText().toString());
                HomeActivity.user_realtime.setLocation_maps(location_link_maps.getText().toString());
                HomeActivity.user_realtime.setPrice_per_hour(Double.parseDouble(price.getText().toString()));
                HomeActivity.user_realtime.setAbout_doctor(about_me.getText().toString());
                old_area= HomeActivity.user_realtime.getArea_location();

                upload_data();
            }
        });



    }

    void upload_data()
    {

        reference.child("users").child(HomeActivity.user_realtime.getDoctor_uid()).child("Personal Data").setValue(HomeActivity.user_realtime).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    reference.child("doctors").child(HomeActivity.user_realtime.getArea_location()).child(HomeActivity.user_realtime.getSpecialization()).child(HomeActivity.user_realtime.getUID()).setValue(HomeActivity.user_realtime).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                TastyToast.makeText(getApplicationContext(), "Edit Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                                final Intent i = new Intent(Doctor_edit_information.this, Doctor_thanks_for_edit_profile_data.class);
                                startActivity(i);
                                finish();
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                TastyToast.makeText(getApplicationContext(), "Error While Create User Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                return;
                            }
                        }
                    });
                }

                else
                {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Error While Create User Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    return;
                }
            }
        });


        reference.child("all_doctors").child(HomeActivity.user_realtime.getUID()).setValue(HomeActivity.user_realtime).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    TastyToast.makeText(getApplicationContext(), "Edit Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    final Intent i = new Intent(Doctor_edit_information.this, Doctor_thanks_for_edit_profile_data.class);
                    startActivity(i);
                    finish();
                    finish();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    TastyToast.makeText(getApplicationContext(), "Error While Create User Data", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    return;
                }
            }
        });


    }

    public static boolean validateLetters(String txt) {
        String regx = "[a-zA-Z]+\\.?";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 16;
        }
        return false;
    }

    public static boolean isBlank(String value) {
        return (value == null || value.equals("") || value.equals("null") || value.trim().equals(""));
    }

    public static boolean isValidNumberLenghth(final EditText number) {
        return number.toString().length() >= 10;
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
            profile_img.setImageURI(FilePathUri);
        }
    }
}