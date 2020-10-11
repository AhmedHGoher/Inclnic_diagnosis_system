package inclinic.com.Doctor.ui.Profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.Admin.AllDoc.Doctor_edit_information;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.ui_activities_auth.login.SginIn;

public class DocProfileFragment extends Fragment {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CircleImageView circleImageView_doctor  ;
    TextView name_doctor;
    TextView spec_doctor;
    TextView location_doctor;
    Button change_doctor_info;
    Button logout_doctor_info;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_doc_profile, container, false);

        circleImageView_doctor= root.findViewById(R.id.image_doctor_home_ui);
        name_doctor = root.findViewById(R.id.name_doctor_home_ui);
        spec_doctor=root.findViewById(R.id.specialization_doctor_home_ui);
        location_doctor= root.findViewById(R.id.location_doctor_home_ui);
        change_doctor_info= root.findViewById(R.id.changeInfo_doctor_doctor_home_ui);
        logout_doctor_info= root.findViewById(R.id.logout_doctor_home_ui);

        name_doctor.setText(HomeActivity.user_realtime.getName());
        spec_doctor.setText(HomeActivity.user_realtime.getSpecialization());
        location_doctor.setText(HomeActivity.user_realtime.getArea_location());


        change_doctor_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SginIn.preventTwoClick(v);
                final Intent i = new Intent(getContext(), Doctor_edit_information.class);
                startActivity(i);
            }
        });

        logout_doctor_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SginIn.preventTwoClick(v);
                final Intent i = new Intent(getContext(), Logout_activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        Glide.with(getContext())
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
                .into(circleImageView_doctor);
        return root;
    }

}