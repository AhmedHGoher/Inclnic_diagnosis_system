package inclinic.com.ui.Appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import inclinic.com.R;
import inclinic.com.ui.Book.BookModel;

public class AppointmentFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    List<BookModel>bookModels = new ArrayList<>();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_appointment, container, false);

        refreshLayout = root.findViewById(R.id.userappointments_swiperefresh);
        recyclerView = root.findViewById(R.id.userappointments_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL , false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getData();
            }
        });

        getData();

        return root;
    }

    private void getData()
    {
        if(user==null)
        {
            Toast.makeText(getContext(), "You should Login into app to view appointments", Toast.LENGTH_LONG).show();
            return;
        }

        reference.child("users").child(user.getUid()).child("Booking").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    BookModel model = new BookModel();
                    model = snapshot.getValue(BookModel.class);
                    bookModels.add(model);
                }
                refreshLayout.setRefreshing(false);
                AppointmentRecyclerAdapter adapter = new AppointmentRecyclerAdapter(getContext() , bookModels);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}