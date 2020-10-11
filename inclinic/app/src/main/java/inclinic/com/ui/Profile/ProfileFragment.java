package inclinic.com.ui.Profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.Admin.AllDoc.Doctor_edit_information;
import inclinic.com.HomeActivity;
import inclinic.com.ui_activities_auth.login.Logout_activity;
import inclinic.com.R;

import inclinic.com.ui_activities_auth.doctor_signup.Doctor_signup;
import inclinic.com.ui_activities_auth.login.SginIn;
import inclinic.com.ui_activities_auth.user_signup.SignUP_activity;
import inclinic.com.ui_activities_auth.user_signup.User_edit_profile;

public class ProfileFragment extends Fragment {


    Button settingsSignin;
    Button settingsSignup;
    Button settingsSignupAsDoctor;
    TextView contactus;
    TextView aboutus;
    TextView termsandconditions;
    LinearLayout signServices;
    LinearLayout doctor_linear_info;
    LinearLayout user_linear_info;
    CircleImageView circleImageView_doctor  ;
    TextView name_doctor;
    TextView spec_doctor;
    TextView location_doctor;
    Button change_doctor_info;
    Button logout_user;
//    ImageView doctor_logout_img;
    ImageView user_profile_pic;
//    TextView doctor_logout_text;
    TextView name_user;
    TextView age_user;
    TextView phone_user;
//    ImageView logout_user_img;
//    TextView logout_user_text;
    Button change_user_info;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        settingsSignin=root.findViewById(R.id.settings_signin);
        settingsSignup=root.findViewById(R.id.settings_signup);
        settingsSignupAsDoctor=root.findViewById(R.id.settings_signup_as_doctor);
//        contactus=root.findViewById(R.id.contactus);
//        aboutus=root.findViewById(R.id.aboutus);
//        termsandconditions=root.findViewById(R.id.termsandconditions);
        signServices=(LinearLayout) root.findViewById(R.id.setting_linear_signServices_auth);
        doctor_linear_info=(LinearLayout) root.findViewById(R.id.setting_linear_doctor_data);
        circleImageView_doctor=root.findViewById(R.id.settings_ui_image_doctor);
        name_doctor=root.findViewById(R.id.settings_ui_doctor_name);
        spec_doctor= root.findViewById(R.id.settings_ui_specialization);
        location_doctor=root.findViewById(R.id.settings_ui_location_doctor);
        change_doctor_info=root.findViewById(R.id.settings_btn_changeInfo_doctor);
//        doctor_logout_img=root.findViewById(R.id.image_doctor_logout);
//        doctor_logout_text=root.findViewById(R.id.text_doctor_logout);
        user_linear_info=root.findViewById(R.id.setting_linear_user_data);
        user_profile_pic=root.findViewById(R.id.settings_ui_image_user);
        name_user=root.findViewById(R.id.settings_ui_user_name);
        age_user=root.findViewById(R.id.settings_ui_age_user);
        phone_user=root.findViewById(R.id.settings_ui_phone_user);
//        logout_user_img=root.findViewById(R.id.image_user_logout);
//        logout_user_text=root.findViewById(R.id.text_user_logout);
        change_user_info=root.findViewById(R.id.settings_btn_changeInfo_user);
        logout_user=root.findViewById(R.id.logout_profile_user_ui);

        logout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logout Success", Toast.LENGTH_SHORT).show();
                final Intent i = new Intent(getActivity(), Logout_activity.class);
                startActivity(i);
                ActivityCompat.finishAffinity(getActivity());

            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            if(HomeActivity.user_realtime.getDoctor_uid()!=null)
            {
                doctor_linear_info.setVisibility(View.VISIBLE);

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
                name_doctor.setText(HomeActivity.user_realtime.getName());
                spec_doctor.setText(HomeActivity.user_realtime.getSpecialization());
                location_doctor.setText(HomeActivity.user_realtime.getArea_location());

            }
            else if(HomeActivity.user_realtime.getIs_user()==true)
            {
                user_linear_info.setVisibility(View.VISIBLE);

                if(HomeActivity.user_realtime.getUrl_image_profile()!=null)
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
                        .into(user_profile_pic);
                name_user.setText(HomeActivity.user_realtime.getName());
                age_user.setText(String.valueOf(HomeActivity.user_realtime.getAge()));
                phone_user.setText(HomeActivity.user_realtime.getPhonenumber());

            }

        }
        else
        {
            signServices.setVisibility(View.VISIBLE);
        }


        settingsSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getActivity(), SginIn.class);
                startActivity(i);
            }
        });

        settingsSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getActivity(), SignUP_activity.class);
                startActivity(i);
            }
        });

        settingsSignupAsDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(getActivity(), Doctor_signup.class);
                startActivity(i);
            }
        });

        change_doctor_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SginIn.preventTwoClick(v);
                final Intent i = new Intent(getActivity(),Doctor_edit_information.class);
                startActivity(i);
            }
        });

//        doctor_logout_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                final Intent i = new Intent(getActivity(), Logout_activity.class);
//                startActivity(i);
//                getActivity().onBackPressed();
//            }
//        });

//        logout_user_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                final Intent i = new Intent(getActivity(), Logout_activity.class);
//                startActivity(i);
//               getActivity().onBackPressed();
//           }
//       });

//        doctor_logout_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                final Intent i = new Intent(getActivity(), Logout_activity.class);
//                startActivity(i);
//                getActivity().onBackPressed();
//            }
//        });

//        logout_user_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                final Intent i = new Intent(getActivity(), Logout_activity.class);
//                startActivity(i);
//                getActivity().onBackPressed();
//            }
//        });


        change_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO HERE WILL PUT Change activity info
                SginIn.preventTwoClick(v);
                final Intent i = new Intent(getActivity(), User_edit_profile.class);
                startActivity(i);
            }
        });

        return root;
    }
}

/*

USER

        <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="56dp"
                    android:id="@+id/image_user_logout"
                    android:src="@drawable/ic_baseline_subdirectory_arrow_left_24"
                    ></ImageView>
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="LOGOUT"
                    android:textStyle="bold"
                    android:id="@+id/text_user_logout"
                    android:layout_gravity="center"
                    android:gravity="center"
                    android:textColor="#ffffff"
                    >
                </TextView>
 */


/*

Doctor Profile


            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:layout_gravity="center"
                >

                <ImageView
                    android:layout_width="match_parent"
                    android:layout_height="56dp"
                    android:id="@+id/image_doctor_logout"
                    android:src="@drawable/ic_baseline_subdirectory_arrow_left_24"
                    ></ImageView>
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="LOGOUT"
                    android:textStyle="bold"
                    android:id="@+id/text_doctor_logout"
                    android:layout_gravity="center"
                    android:gravity="center"
                    android:textColor="#ffffff"
                    >
                </TextView>
            </LinearLayout>

 */