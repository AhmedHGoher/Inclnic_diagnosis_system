package inclinic.com.Admin.ui.PendingDoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;

public class PendingOnClickActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name , specialization , college , location , graduation ,age , phone , location_description , about;
    Button reject_btn , accept_btn ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    User_Model model = new User_Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_on_click);

         model = (User_Model) getIntent().getSerializableExtra("model");

         imageView = findViewById(R.id.onpendingdoctor_image);
         name = findViewById(R.id.onpendingdoctor_name);
         specialization = findViewById(R.id.onpendingdoctor_specialization);
         college = findViewById(R.id.onpendingdoctor_faculty);
         location = findViewById(R.id.onpendingdoctor_location);
         graduation = findViewById(R.id.onpendingdoctor_gradeyears);
         age = findViewById(R.id.onpendingdoctor_age);
         phone = findViewById(R.id.onpendingdoctor_phone);
         location_description = findViewById(R.id.onpendingdoctor_cliniclocation);
         about = findViewById(R.id.onpendingdoctor_about);
         reject_btn = findViewById(R.id.onpendingdoctor_reject);
         accept_btn = findViewById(R.id.onpendingdoctor_accept);

        Glide.with(PendingOnClickActivity.this)
                .load(model.getUrl_image_profile())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
         name.setText(model.getName());
         specialization.setText(model.getSpecialization());
         college.setText(model.getFacilty_name());
         location.setText(model.getArea_location());
         graduation.setText("GraduationYear: "+model.getGraduation_year());
         age.setText("Age: "+model.getAge());
         phone.setText(model.getPhonenumber());
         location_description.setText(model.getLocation_text());
         about.setText(model.getAbout_doctor());
         model.setTotal_kashf(0);
         model.setTotal_star_rate(0.0);
         model.setRate_click(0);
         model.setRate_total(5.0);
         String searchInputToLower = model.getName().toLowerCase();
         model.setLower_doctor_name_search(searchInputToLower);

         reject_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 reference.child("doctor_rejects").child(model.getDoctor_uid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         reference.child("pending_doctors").child(model.getDoctor_uid()).removeValue();
                         Toast.makeText(PendingOnClickActivity.this, "Rejected", Toast.LENGTH_SHORT).show();
                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(PendingOnClickActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         });

         accept_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 reference.child("pending_appointments").child(model.getDoctor_uid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         reference.child("pending_doctors").child(model.getDoctor_uid()).removeValue();
                         Toast.makeText(PendingOnClickActivity.this, "Accepted", Toast.LENGTH_SHORT).show();
                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(PendingOnClickActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });

             }
         });
    }
}