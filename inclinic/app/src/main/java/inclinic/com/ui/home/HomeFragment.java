package inclinic.com.ui.home;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import inclinic.com.R;
import inclinic.com.ui.Search.SearchActivity;

public class HomeFragment extends Fragment {

    CardView general , dentist , orthopaedic , pulmonogist , cardiologist , opthalmologist ,
            neurologist , obstetrician_gynecologist, nephorlogist , dermatologist;
    TextView search_edittxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        Fade fade = new Fade();
        View decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);

        search_edittxt = root.findViewById(R.id.category_search);
        general = root.findViewById(R.id.general);
        dentist = root.findViewById(R.id.dentist);
        orthopaedic = root.findViewById(R.id.ortho);
        pulmonogist = root.findViewById(R.id.pulmonogist);
        cardiologist = root.findViewById(R.id.cardiologist);
        opthalmologist = root.findViewById(R.id.opthal);
        neurologist = root.findViewById(R.id.neurologist);
        obstetrician_gynecologist = root.findViewById(R.id.obstetrician);
        nephorlogist = root.findViewById(R.id.nephrologist);
        dermatologist = root.findViewById(R.id.dermatologist);

        search_edittxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(root.findViewById(R.id.category_search),"edittxt_trans");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);

                startActivity(intent,options.toBundle());
                Animatoo.animateFade(getContext());
            }
        });

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","General");

                startActivity(intent);

            }
        });
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Dentist");

                startActivity(intent);

            }
        });
        orthopaedic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Orthopaedic");

                startActivity(intent);

            }
        });

        pulmonogist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Pulmonogist");

                startActivity(intent);

            }
        });
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Cardiologist");

                startActivity(intent);

            }
        });
        opthalmologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Opthalmologist");

                startActivity(intent);

            }
        });
        neurologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Neurologist");

                startActivity(intent);

            }
        });
        obstetrician_gynecologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Obstetrician_Gynecologist");

                startActivity(intent);

            }
        });

        nephorlogist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Nephorlogist");

                startActivity(intent);

            }
        });

        dermatologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DoctorActivity.class);
                intent.putExtra("cardview","Dermatologist");

                startActivity(intent);

            }
        });


        return root;
    }
}