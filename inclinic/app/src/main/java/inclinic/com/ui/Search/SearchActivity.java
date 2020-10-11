package inclinic.com.ui.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;
import inclinic.com.ui.home.DoctorActivity;
import inclinic.com.ui.home.OnDoctorClickActivity;
import inclinic.com.ui.home.RecyclerItemClickListener;
import inclinic.com.ui.home.RecyclerViewAdapter;

public class SearchActivity extends AppCompatActivity {

    ImageButton searchimgbtn ;
    TextView backtxt;
    EditText searchedittxt ;
    RecyclerView recyclerView ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    List<User_Model>models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backtxt = findViewById(R.id.search_backtxt);
        searchedittxt = findViewById(R.id.searchactivity_searchedit);
        recyclerView = findViewById(R.id.search_recyclerview);
        searchimgbtn = findViewById(R.id.search_imgbtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this , LinearLayoutManager.VERTICAL , false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchedittxt.requestFocus();

        backtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchedittxt.getText().toString() == null){
                    Toast.makeText(SearchActivity.this, "Enter Doctor Name", Toast.LENGTH_SHORT).show();
                }
                else {

                    String searchInputToLower = searchedittxt.getText().toString().toLowerCase();
                    String searchInputTOUpper = searchedittxt.getText().toString().toUpperCase();


                    Query query = reference.child("all_doctors")
                            .orderByChild("lower_doctor_name_search")
                            .startAt(searchInputToLower)
                            .endAt(searchInputToLower + "\uf8ff")
                            ;

//                    Query query2 = reference.child("all_doctors")
//                            .orderByChild("name")
//                            .startAt(searchInputTOUpper)
//                            .endAt("\uf8ff")
//                            ;

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                models.clear();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    User_Model model = new User_Model();
                                    model = snapshot.getValue(User_Model.class);
                                    models.add(model);
                                }
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(SearchActivity.this , models);
                                recyclerView.setAdapter(adapter);
                                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent intent = new Intent(SearchActivity.this , OnDoctorClickActivity.class);
                                        intent.putExtra("doctormodel" , models.get(position));

                                        startActivity(intent);
                                        Animatoo.animateSlideUp(SearchActivity.this);
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {

                                    }
                                }));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

//                    query2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()){
//                                models.clear();
//
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                    User_Model model = new User_Model();
//                                    model = snapshot.getValue(User_Model.class);
//                                    models.add(model);
//                                }
//                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(SearchActivity.this , models);
//                                recyclerView.setAdapter(adapter);
//                                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(View view, int position) {
//                                        Intent intent = new Intent(SearchActivity.this , OnDoctorClickActivity.class);
//                                        intent.putExtra("doctormodel" , models.get(position));
//
//                                        startActivity(intent);
//                                        Animatoo.animateSlideUp(SearchActivity.this);
//                                    }
//
//                                    @Override
//                                    public void onLongItemClick(View view, int position) {
//
//                                    }
//                                }));
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
                }
            }
        });

    }
}