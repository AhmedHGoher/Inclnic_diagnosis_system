package inclinic.com.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;

public class DoctorActivity extends AppCompatActivity {

    ImageView imageView ;
    ImageButton backbtn ;
    TextView textView ;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    Spinner locations_spinner ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    List<User_Model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        backbtn = findViewById(R.id.doctor_backimgbtn);
        imageView = findViewById(R.id.doctor_imageview);
        textView = findViewById(R.id.doctor_textview);
        refreshLayout = findViewById(R.id.doctoractivity_swiperefresh);

        locations_spinner = findViewById(R.id.doctor_spinnerlocation);
        recyclerView = findViewById(R.id.doctor_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(DoctorActivity.this , LinearLayoutManager.VERTICAL , false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String cardview = getIntent().getStringExtra("cardview");

        getData(locations_spinner.getSelectedItem().toString(),cardview);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getData(locations_spinner.getSelectedItem().toString(),cardview);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (cardview.equals("General")) {
            imageView.setImageResource(R.drawable.general);
            textView.setText("Doctors in General");
        }

        if (cardview.equals("Dentist")) {
            imageView.setImageResource(R.drawable.dentist);
            textView.setText("Doctors in Dentist");
        }

        if (cardview.equals("Orthopaedic")) {
            imageView.setImageResource(R.drawable.orthopaedic);
            textView.setText("Doctors in Orthopaedic");
        }

        if (cardview.equals("Pulmonogist")) {
            imageView.setImageResource(R.drawable.pulmonologists);
            textView.setText("Doctors in Pulmonogist");
        }

        if (cardview.equals("Cardiologist")) {
            imageView.setImageResource(R.drawable.cardiologists);
            textView.setText("Doctors in Cardiologist");
        }

        if (cardview.equals("Opthalmologist")) {
            imageView.setImageResource(R.drawable.ophthalmologists);
            textView.setText("Doctors in Opthalmologist");
        }

        if (cardview.equals("Neurologist")) {
            imageView.setImageResource(R.drawable.neurologists);
            textView.setText("Doctors in Neurologist");
        }

        if (cardview.equals("Obstetrician_Gynecologist")) {
            imageView.setImageResource(R.drawable.obstetriciansandgynecologists);
            textView.setText("Doctors in Obstetrician and Gynecologist");
        }

        if (cardview.equals("Nephorlogist")) {
            imageView.setImageResource(R.drawable.nephrologists);
            textView.setText("Doctors in Nephorlogist");
        }

        if (cardview.equals("Dermatologist")) {
            imageView.setImageResource(R.drawable.dermatologists);
            textView.setText("Doctors in Dermatologist");
        }

        locations_spinner.setAdapter(new ArrayAdapter<String>(DoctorActivity.this,android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.locations)));

        locations_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refreshLayout.setRefreshing(true);
                getData(locations_spinner.getSelectedItem().toString(),cardview);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getData(String location , String specialization){
        databaseReference.child("doctors").child(location).child(specialization).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User_Model model = new User_Model();
                    model = snapshot.getValue(User_Model.class);
                    modelList.add(model);
                }
                refreshLayout.setRefreshing(false);
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(DoctorActivity.this , modelList);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(DoctorActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(DoctorActivity.this , OnDoctorClickActivity.class);
                        intent.putExtra("doctormodel" , modelList.get(position));

                        startActivity(intent);
                        Animatoo.animateSlideUp(DoctorActivity.this);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}