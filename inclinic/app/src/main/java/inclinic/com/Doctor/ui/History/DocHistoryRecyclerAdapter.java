package inclinic.com.Doctor.ui.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import inclinic.com.R;
import inclinic.com.ui.Book.BookModel;

public class DocHistoryRecyclerAdapter extends RecyclerView.Adapter<DocHistoryRecyclerAdapter.Holder>{
    List<BookModel> bookModels ;
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DocHistoryRecyclerAdapter(Context context, List<BookModel> bookModels) {
        this.context = context;
        this.bookModels = bookModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowdoctor_history , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        BookModel model = bookModels.get(position);

        if (model.isDone()){
            holder.state.setText("Done");
            holder.state.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        else{
            holder.state.setText("Missed");
            holder.state.setTextColor(context.getResources().getColor(R.color.material_red_400));
        }

        holder.date.setText("Appointment day: "+model.getBookday());
        holder.price.setText(model.getDoctorprice());
        holder.name.setText(model.getUsername());
        if (model.isCredit())
        holder.paytype.setText("Pay Type: Credit");
        else
            holder.paytype.setText("Pay Type: Cash");
        holder.phone.setText("Location: "+model.getUserphone());
        holder.time.setText("Time: "+model.getBooktime());

    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name, paytype, phone, date, time, price, state;
        View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rowdoctorhistory_name);
            paytype = itemView.findViewById(R.id.rowdoctorhistory_paytype);
            phone = itemView.findViewById(R.id.rowdoctorhistory_phone);
            date = itemView.findViewById(R.id.rowdoctorhistory_date);
            time = itemView.findViewById(R.id.rowdoctorhistory_time);
            price = itemView.findViewById(R.id.rowdoctorhistory_price);
            state = itemView.findViewById(R.id.rowdoctorhistory_state);

            view = itemView;

        }
    }
}
