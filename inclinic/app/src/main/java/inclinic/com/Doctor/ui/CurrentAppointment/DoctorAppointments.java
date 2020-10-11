package inclinic.com.Doctor.ui.CurrentAppointment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import inclinic.com.Doctor_Home;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.Book_doctor_appointment_model;
import inclinic.com.ui.Appointment.AppointmentRecyclerAdapter;
import inclinic.com.ui.Book.BookActivity;
import inclinic.com.ui.Book.BookModel;


public class DoctorAppointments extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    List<BookModel> bookModels = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static List<String> days = new ArrayList<>();
    Spinner days_spinner ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_appointments, container, false);

        days_spinner = view.findViewById(R.id.doctorappointments_daysspinner);
        refreshLayout = view.findViewById(R.id.appointments_swiperefresh_doctor_home_ui);
        recyclerView = view.findViewById(R.id.appointments_recyclerview_doctor_home_ui);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Book_doctor_appointment_model> appointmentModels = new ArrayList<Book_doctor_appointment_model>();

        reference.child("doctors").child(HomeActivity.user_realtime.getArea_location()).child(HomeActivity.user_realtime.getSpecialization())
                .child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                days.clear();
                if (dataSnapshot.hasChild("Appointments")) {
                    for (DataSnapshot snapshot : dataSnapshot.child("Appointments").getChildren()) {
                        Book_doctor_appointment_model model = snapshot.getValue(Book_doctor_appointment_model.class);
                        appointmentModels.add(model);
                    }
                    if (!appointmentModels.isEmpty()) {
                        for (int i = 0; i < appointmentModels.size(); i++) {
                            if (i == 0 && i == appointmentModels.size() - 1) {
                                days.add(i, appointmentModels.get(i).getDay_name());

                            } else if (i == appointmentModels.size() - 1) {
                                days.add(i, appointmentModels.get(i).getDay_name());

                            } else if (i == 0) {
                                days.add(i, appointmentModels.get(i).getDay_name());

                            } else {
                                days.add(i, appointmentModels.get(i).getDay_name());

                            }
                        }
                        if (!days.isEmpty()){

                            days_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, days));
                            getData(days.get(0));
                        }

                    }
                }
            }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
                .child("Booking").child(book_day).addListenerForSingleValueEvent(new ValueEventListener() {
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
                DocAppointRecyclerAdapter adapter = new DocAppointRecyclerAdapter(getContext() , bookModels);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}