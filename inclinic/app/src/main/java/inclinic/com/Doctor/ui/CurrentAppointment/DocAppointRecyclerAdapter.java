package inclinic.com.Doctor.ui.CurrentAppointment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.ui.Book.BookModel;

public class DocAppointRecyclerAdapter extends RecyclerView.Adapter<DocAppointRecyclerAdapter.Holder>{
    List<BookModel> bookModels ;
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public DocAppointRecyclerAdapter(Context context, List<BookModel> bookModels) {
        this.context = context;
        this.bookModels = bookModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowdoctor_appointment , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        BookModel model = bookModels.get(position);

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

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, paytype, phone, date, time, price;
        Button missedbtn, donebtn;
        View view;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rowdoctorappoint_name);
            paytype = itemView.findViewById(R.id.rowdoctorappoint_paytype);
            phone = itemView.findViewById(R.id.rowdoctorappoint_phone);
            date = itemView.findViewById(R.id.rowdoctorappoint_date);
            time = itemView.findViewById(R.id.rowdoctorappoint_time);
            price = itemView.findViewById(R.id.rowdoctorappoint_price);
            missedbtn = itemView.findViewById(R.id.rowdoctorappoint_missedbtn);
            donebtn = itemView.findViewById(R.id.rowdoctorappoint_donebtn);

            donebtn.setOnClickListener(this);
            missedbtn.setOnClickListener(this);

            view = itemView;

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.rowdoctorappoint_donebtn) {

                BookModel bookModel = bookModels.get(getAdapterPosition());
                reference.child("users").child(bookModel.getUserid()).child("Booking").child(bookModel.getKey()).removeValue();
                reference.child("users").child(bookModel.getUserid()).child("History_Booking").child(bookModel.getKey()).setValue(bookModel);
                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                        .child("Booking").child(bookModel.getBookday()).child(bookModel.getKey()).removeValue();
                bookModel.setDone(true);
                int total_kashf = HomeActivity.user_realtime.getTotal_kashf();
                total_kashf++;
                HomeActivity.user_realtime.setTotal_kashf(total_kashf);
                reference.child("all_doctors").child(user.getUid()).child("total_kashf").setValue(total_kashf);
                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid()).child("total_kashf").setValue(total_kashf);
                reference.child("pending_rate").child(bookModel.getUserid()).setValue(bookModel);
                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                        .child("History").child(bookModel.getBookday()).child(bookModel.getKey()).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view, "Appointment Done", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, "Failed. Please check your connection", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
            } else if (view.getId() == R.id.rowdoctorappoint_missedbtn) {
                BookModel bookModel = bookModels.get(getAdapterPosition());
                reference.child("users").child(bookModel.getUserid()).child("Booking").child(bookModel.getKey()).removeValue();
                reference.child("users").child(bookModel.getUserid()).child("History_Booking").child(bookModel.getKey()).setValue(bookModel);

                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                        .child("Booking").child(bookModel.getBookday()).child(bookModel.getKey()).removeValue();
                bookModel.setDone(false);
                reference.child("pending_block").child(bookModel.getUserid()).child("Booking").child(bookModel.getKey()).removeValue();

                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                        .child("History").child(bookModel.getBookday()).child(bookModel.getKey()).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(view, "Done", Snackbar.LENGTH_LONG).show();
                        }
                        else
                        {
                            Snackbar.make(view, "Failed. Please check your connection", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
            }
        }

        }


}
