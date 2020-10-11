package inclinic.com.ui.Book;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.Book_doctor_appointment_model;
import inclinic.com.models.doctor.Times_appointments;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.ui.home.OnDoctorClickActivity;
import inclinic.com.ui_activities_auth.login.SginIn;

public class BookActivity extends AppCompatActivity {

    public static SharedPreferences SP ;
    public static String filename = "Keys";

    TextView appointmentsday, appointmentsfrom, appointmentsto, booktxt, doctorname;
    CircleImageView doctorimg;
    ImageButton backbtn;
    ProgressBar progressBar;
    Spinner days_spinner, time_spinner;
    BookModel bookModel = new BookModel();
    ArrayList<Book_doctor_appointment_model> appointmentModels = new ArrayList<Book_doctor_appointment_model>();
    Times_appointments timemodel = new Times_appointments();
    List<String>times_appointments= new ArrayList<>();
    List<String> days = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        final int[] pos_day_to_bool_book = {0};
        final int[] pos_time_to_bool_book = {0};

        doctorname = findViewById(R.id.bookactivity_doctorname);
        doctorimg = findViewById(R.id.bookactivity_doctorimg);
        appointmentsday = findViewById(R.id.bookactivity_day);
        appointmentsfrom = findViewById(R.id.bookactivity_from);
        appointmentsto = findViewById(R.id.bookactivity_to);
        backbtn = findViewById(R.id.bookactivity_backimgbtn);
        booktxt = findViewById(R.id.bookactivity_booktxt);
        days_spinner = findViewById(R.id.bookactivity_selectdayspinner);
        time_spinner = findViewById(R.id.bookactivity_selecttimespinner);

        doctorname.setText(getIntent().getStringExtra("doctorname") + " Appointments");

        try {
            URL url = new URL(getIntent().getStringExtra("doctorimg"));
            Glide.with(BookActivity.this)
                    .load(url.toString())
                    .into(doctorimg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        reference.child("doctors").child(getIntent().getStringExtra("arealocation")).child(getIntent().getStringExtra("specialization"))
                .child(getIntent().getStringExtra("doctorid")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("Appointments")) {
                    for (DataSnapshot snapshot : dataSnapshot.child("Appointments").getChildren()) {
                        Book_doctor_appointment_model model = snapshot.getValue(Book_doctor_appointment_model.class);
                        appointmentModels.add(model);
                    }
        if (!appointmentModels.isEmpty()) {
            for (int i = 0; i < appointmentModels.size(); i++)
            {
                if ( i == 0 && i == appointmentModels.size() - 1) {
                    days.add(i,appointmentModels.get(i).getDay_name());
                    appointmentsday.setText(appointmentModels.get(i).getDay_name()+"\n\n");
                    appointmentsfrom.setText(appointmentModels.get(i).getTime_full_start()+"\n\n");
                    appointmentsto.setText(appointmentModels.get(i).getTime_full_end()+"\n\n");
                }
                else if (i == appointmentModels.size() - 1) {
                    days.add(i,appointmentModels.get(i).getDay_name());
                    appointmentsday.append(appointmentModels.get(i).getDay_name()+"\n\n");
                    appointmentsfrom.append(appointmentModels.get(i).getTime_full_start()+"\n\n");
                    appointmentsto.append(appointmentModels.get(i).getTime_full_end()+"\n\n");
                } else if (i == 0) {
                    days.add(i,appointmentModels.get(i).getDay_name());
                    appointmentsday.setText(appointmentModels.get(i).getDay_name()+"\n\n");
                    appointmentsfrom.setText(appointmentModels.get(i).getTime_full_start()+"\n\n");
                    appointmentsto.setText(appointmentModels.get(i).getTime_full_end()+"\n\n");
                } else {
                    days.add(i,appointmentModels.get(i).getDay_name());
                    appointmentsday.append(appointmentModels.get(i).getDay_name()+"\n\n");
                    appointmentsfrom.append(appointmentModels.get(i).getTime_full_start()+"\n\n");
                    appointmentsto.append(appointmentModels.get(i).getTime_full_end()+"\n\n");
                }
            }
        }
        else {
            Toast.makeText(BookActivity.this, "Doctor doesn't specify his appointments", Toast.LENGTH_LONG).show();
        }
            if (!days.isEmpty()) {
                days_spinner.setAdapter(new ArrayAdapter<String>(BookActivity.this, android.R.layout.simple_spinner_dropdown_item, days));

            }

            if (dataSnapshot.child("Appointments").child("0").child("times_appointments").hasChildren()){
                times_appointments = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.child("Appointments").child("0").child("times_appointments").getChildren()){
                    Times_appointments timemodel = new Times_appointments();
                    timemodel = snapshot.getValue(Times_appointments.class);
                    times_appointments.add(timemodel.getStart_appoi()+" To "+timemodel.getEnd_appoi());
                }
                if (!times_appointments.isEmpty()){
                    time_spinner.setAdapter(new ArrayAdapter<String>(BookActivity.this, android.R.layout.simple_spinner_dropdown_item, times_appointments));
                }
            }
        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bookModel.setDoctorid(getIntent().getStringExtra("doctorid"));
        bookModel.setDoctorimg(getIntent().getStringExtra("doctorimg"));
        bookModel.setUserid(FirebaseAuth.getInstance().getUid());
        bookModel.setDoctorname(getIntent().getStringExtra("doctorname"));
        bookModel.setUsername(HomeActivity.user_realtime.getName());
        bookModel.setUserphone(HomeActivity.user_realtime.getPhonenumber());
        bookModel.setDoctorprice(getIntent().getDoubleExtra("doctorprice",0)+" L.E");
        bookModel.setDoctorspecialization(getIntent().getStringExtra("specialization"));
        bookModel.setDoctorarea(getIntent().getStringExtra("arealocation"));
        bookModel.setDone(false);

        days_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bookModel.setBookday(days.get(i).toString());
                pos_day_to_bool_book[0] = i;
                try {
                    setTime(i);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                pos_day_to_bool_book[0] = 0;

            }
        });
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos_time_to_bool_book[0] = i;
                bookModel.setBooktime(times_appointments.get(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        booktxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor edit = sharedPreferences.edit();

                edit.putString("pos_day" , String.valueOf(pos_day_to_bool_book[0]));
                edit.putString("pos_time", String.valueOf(pos_time_to_bool_book[0]) );
                edit.commit();

                CreditCardActivity cre = new CreditCardActivity(String.valueOf(pos_day_to_bool_book),String.valueOf(pos_time_to_bool_book));

                Intent intent = new Intent(BookActivity.this , cre.getClass() );

                intent.putExtra("bookmodel" , bookModel);
                intent.putExtra("day_bookk",pos_day_to_bool_book);
                intent.putExtra("time_bookk",pos_time_to_bool_book);
                startActivity(intent);

                Animatoo.animateSwipeLeft(BookActivity.this);
            }
        });

    }

    private void setTime (int position) throws ParseException {

        reference.child("doctors").child(getIntent().getStringExtra("arealocation")).child(getIntent().getStringExtra("specialization"))
                .child(getIntent().getStringExtra("doctorid")).child("Appointments").child(String.valueOf(position)).child("times_appointments")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        times_appointments = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Times_appointments timemodel = new Times_appointments();
                            timemodel = snapshot.getValue(Times_appointments.class);
                            if(timemodel.isIs_booked()==false)
                            times_appointments.add(timemodel.getStart_appoi()+" To "+timemodel.getEnd_appoi());
                        }
                        if (!times_appointments.isEmpty()){
                            time_spinner.setAdapter(new ArrayAdapter<String>(BookActivity.this, android.R.layout.simple_spinner_dropdown_item, times_appointments));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

