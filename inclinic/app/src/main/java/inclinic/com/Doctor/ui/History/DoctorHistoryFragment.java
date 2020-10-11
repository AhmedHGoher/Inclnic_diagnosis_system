package inclinic.com.Doctor.ui.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inclinic.com.Doctor.ui.CurrentAppointment.DocAppointRecyclerAdapter;
import inclinic.com.Doctor.ui.CurrentAppointment.DoctorAppointments;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.Book_doctor_appointment_model;
import inclinic.com.ui.Book.BookModel;

public class DoctorHistoryFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    List<BookModel> bookModels = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Spinner days_spinner ;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_history, container, false);

        days_spinner = view.findViewById(R.id.doctorhistory_daysspinner);
        refreshLayout = view.findViewById(R.id.history_swiperefresh_doctor_home_ui);
        recyclerView = view.findViewById(R.id.history_recyclerview_doctor_home_ui);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

                        if (!DoctorAppointments.days.isEmpty()) {

                            days_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, DoctorAppointments.days));
                            getData(DoctorAppointments.days.get(0));
                        }

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getData(days_spinner.getSelectedItem().toString());
            }
        });

        days_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData(days_spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
    private void getData(String book_day){

        reference.child("doctors").child(HomeActivity.user_realtime.getArea_location()).child(HomeActivity.user_realtime.getSpecialization()).child(user.getUid())
                .child("History").child(book_day).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookModels = new ArrayList<>();
                bookModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BookModel model = new BookModel();
                    model = snapshot.getValue(BookModel.class);
                    bookModels.add(model);
                }
                refreshLayout.setRefreshing(false);
                DocHistoryRecyclerAdapter adapter = new DocHistoryRecyclerAdapter(getContext() , bookModels);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}