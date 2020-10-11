package inclinic.com.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import inclinic.com.R;
import inclinic.com.models.doctor.Book_doctor_appointment_model;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.models.person.RatingModel;
import inclinic.com.ui.Book.BookActivity;

public class OnDoctorClickActivity extends AppCompatActivity {

    ImageView doctorimg ;
    ImageButton back_imgbtn;
    Button book_btn ;
    RatingBar ratingBar;
    TextView name_txtview, specialization_txtview , price_txtview, arealocation_txtview, appointments_txt_view, phone_txtview, allappointments_txtview,
    allstars_txtview, rate_txtview , locationdiscrip_txtview, appointmetdays_txt, appointmentsfrom_txt, appointmentsto_txt , aboutme_txt, allreviews_txt , noreviews_txt ;
    ListView reviews_listview ;
    ArrayList<RatingModel>ratingModels = new ArrayList<RatingModel>();
    ArrayList<Book_doctor_appointment_model>appointmentModels = new ArrayList<Book_doctor_appointment_model>();
    CommentsListAdapter commentsListAdapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    User_Model doctormodel = new User_Model() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_doctor_click);

        initialize();

        doctormodel = (User_Model) getIntent().getSerializableExtra("doctormodel");
        reference.child("doctors").child(doctormodel.getArea_location()).child(doctormodel.getSpecialization()).child(doctormodel.getDoctor_uid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Appointments")) {
                    for (DataSnapshot snapshot : dataSnapshot.child("Appointments").getChildren()) {
                        Book_doctor_appointment_model model = snapshot.getValue(Book_doctor_appointment_model.class);
                        appointmentModels.add(model);
                    }
                    if (!appointmentModels.isEmpty()) {
                        for (int i = 0; i < appointmentModels.size(); i++) {
                             if (i == 0 && i == appointmentModels.size() - 1) {
                                appointments_txt_view.setText("Appointments: "+appointmentModels.get(i).getDay_name());
                                appointmetdays_txt.setText(appointmentModels.get(i).getDay_name()+"\n");
                                appointmentsfrom_txt.setText(appointmentModels.get(i).getTime_full_start()+"\n");
                                appointmentsto_txt.setText(appointmentModels.get(i).getTime_full_end()+"\n");
                            }
                            else if (i == appointmentModels.size() - 1) {
                                appointments_txt_view.append(", " + appointmentModels.get(i).getDay_name() + ".");
                                appointmetdays_txt.append(appointmentModels.get(i).getDay_name()+"\n");
                                appointmentsfrom_txt.append(appointmentModels.get(i).getTime_full_start()+"\n");
                                appointmentsto_txt.append(appointmentModels.get(i).getTime_full_end()+"\n");
                            } else if (i == 0) {
                                appointments_txt_view.setText("Appointments: "+appointmentModels.get(i).getDay_name());
                                appointmetdays_txt.setText(appointmentModels.get(i).getDay_name()+"\n");
                                appointmentsfrom_txt.setText(appointmentModels.get(i).getTime_full_start()+"\n");
                                appointmentsto_txt.setText(appointmentModels.get(i).getTime_full_end()+"\n");
                            } else {
                                appointments_txt_view.append(", " + appointmentModels.get(i).getDay_name());
                                appointmetdays_txt.append(appointmentModels.get(i).getDay_name()+"\n");
                                appointmentsfrom_txt.append(appointmentModels.get(i).getTime_full_start()+"\n");
                                appointmentsto_txt.append(appointmentModels.get(i).getTime_full_end()+"\n");
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(OnDoctorClickActivity.this, "Doctor doesn't specify his appointments", Toast.LENGTH_LONG).show();
                }

                if (dataSnapshot.hasChild("Reviews")){
                    reviews_listview.setVisibility(View.VISIBLE);
                    allreviews_txt.setVisibility(View.VISIBLE);
                    noreviews_txt.setVisibility(View.GONE);
                    for (DataSnapshot snapshot : dataSnapshot.child("Reviews").getChildren()){
                        RatingModel model = snapshot.getValue(RatingModel.class);
                        ratingModels.add(model);
                    }
                    if (!ratingModels.isEmpty()){
                        commentsListAdapter = new CommentsListAdapter(OnDoctorClickActivity.this , R.layout.row_review , ratingModels);
                        reviews_listview.setAdapter(commentsListAdapter);
                        allreviews_txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(OnDoctorClickActivity.this , ReviewsActivity.class);
                                intent.putExtra("reviewmodels" , ratingModels);
                                startActivity(intent);
                                Animatoo.animateSlideLeft(OnDoctorClickActivity.this);
                            }
                        });
                    }

                }
                else{
                    reviews_listview.setVisibility(View.GONE);
                    allreviews_txt.setVisibility(View.GONE);
                    noreviews_txt.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Glide.with(this)
                .load(doctormodel.getUrl_image_profile())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(doctorimg);

        name_txtview.setText(doctormodel.getName());
        specialization_txtview.setText("Specialize in "+doctormodel.getSpecialization());
        price_txtview.setText("Appointment Price: "+doctormodel.getPrice_per_hour()+" L.E");
        arealocation_txtview.setText("Doctor Location: "+doctormodel.getArea_location());
        phone_txtview.setText("Doctor Phone: "+doctormodel.getPhonenumber());
        locationdiscrip_txtview.setText(doctormodel.getLocation_text());
        aboutme_txt.setText(doctormodel.getAbout_doctor());
        ratingBar.setRating(Float.valueOf((float) doctormodel.getRate_total()));
        allstars_txtview.append(""+doctormodel.getTotal_star_rate());
        rate_txtview.append(""+doctormodel.getRate_total());
        allappointments_txtview.append(""+doctormodel.getTotal_kashf());


        back_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null){
                    Snackbar.make(view , "To book, You should sign up" , Snackbar.LENGTH_LONG).show();
                }
                else {
                    if (!appointmentModels.isEmpty()) {

                        Intent intent = new Intent(OnDoctorClickActivity.this, BookActivity.class);
                        intent.putExtra("doctorid" , doctormodel.getDoctor_uid());
                        intent.putExtra("doctorname" , doctormodel.getName());
                        intent.putExtra("doctorimg" , doctormodel.getUrl_image_profile());
                        intent.putExtra("doctorprice" , doctormodel.getPrice_per_hour());
                        intent.putExtra("specialization" , doctormodel.getSpecialization());
                        intent.putExtra("arealocation" , doctormodel.getArea_location());

                        startActivity(intent);
                        Animatoo.animateSwipeLeft(OnDoctorClickActivity.this);
                    }
                }

            }
        });


    }

    private void initialize(){
        name_txtview = findViewById(R.id.ondoctorclick_name);
        specialization_txtview = findViewById(R.id.ondoctorclick_specialization);
        price_txtview = findViewById(R.id.ondoctorclick_price);
        arealocation_txtview = findViewById(R.id.ondoctorclick_location);
        appointments_txt_view = findViewById(R.id.ondoctorclick_appointments);
        phone_txtview = findViewById(R.id.ondoctorclick_phone);
        allappointments_txtview = findViewById(R.id.ondoctorclick_allappointments);
        allstars_txtview = findViewById(R.id.ondoctorclick_allstars);
        rate_txtview = findViewById(R.id.ondoctorclick_ratetxt);
        locationdiscrip_txtview = findViewById(R.id.ondoctorclick_cliniclocation);
        appointmetdays_txt = findViewById(R.id.ondoctorclick_day);
        appointmentsfrom_txt = findViewById(R.id.ondoctorclick_from);
        appointmentsto_txt = findViewById(R.id.ondoctorclick_to);
        aboutme_txt = findViewById(R.id.ondoctorclick_about);
        allreviews_txt = findViewById(R.id.ondoctorclick_allreviews);
        reviews_listview = findViewById(R.id.ondoctorclick_reviewslistview);
        noreviews_txt = findViewById(R.id.ondoctorclick_noreviews);
        book_btn = findViewById(R.id.ondoctorclick_bookbtn);
        doctorimg=findViewById(R.id.ondoctorclick_image);
        back_imgbtn=findViewById(R.id.ondoctorclick_backimgbtn);
        ratingBar = findViewById(R.id.ondoctorclick_ratingbar);

    }
}