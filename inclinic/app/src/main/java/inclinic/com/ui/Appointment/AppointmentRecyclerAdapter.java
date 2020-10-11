package inclinic.com.ui.Appointment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class AppointmentRecyclerAdapter extends RecyclerView.Adapter<AppointmentRecyclerAdapter.Holder>{
    List<BookModel> bookModels ;
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public AppointmentRecyclerAdapter(Context context, List<BookModel> bookModels) {
        this.context = context;
        this.bookModels = bookModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rowappointment_user , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        BookModel model = bookModels.get(position);

        holder.date.setText("Appointment day: "+model.getBookday());
        holder.price.setText(model.getDoctorprice());
        holder.name.setText(model.getDoctorname());
        holder.specialist.setText("Specialization: "+model.getDoctorspecialization());
        holder.location.setText("Location: "+model.getDoctorarea());
        holder.time.setText("Time: "+model.getBooktime());
        Glide.with(context)
                .load(model.getDoctorimg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
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
                .into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircleImageView circleImageView  ;
        TextView name , specialist , location , date , time , price ;
        Button cancelbtn;
        View view ;
        public Holder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.rowappointmentuser_image);
            name = itemView.findViewById(R.id.rowappointmentuser_name);
            specialist = itemView.findViewById(R.id.rowappointmentuser_specialization);
            location = itemView.findViewById(R.id.rowappointmentuser_location);
            date = itemView.findViewById(R.id.rowappointmentuser_date);
            time = itemView.findViewById(R.id.rowappointmentuser_time);
            price = itemView.findViewById(R.id.rowappointmentuser_price);
            cancelbtn = itemView.findViewById(R.id.rowappointmentuser_cancelbtn);

            cancelbtn.setOnClickListener(this);

            view = itemView;

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.rowappointmentuser_cancelbtn){
                Dialog dialog = new Dialog(context,R.style.DownloadTheme);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setCancelable(true);

                TextView canceltxt_dialog = (TextView) dialog.findViewById(R.id.dialog_canceltxt_appointment);
                TextView closetxt_dialog = (TextView) dialog.findViewById(R.id.dialog_closetxt_appointment);
                closetxt_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                canceltxt_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BookModel bookModel = bookModels.get(getAdapterPosition());
                        reference.child("users").child(bookModel.getUserid()).child("Booking").child(bookModel.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                                            .child("Booking").child(bookModel.getBookday()).child(bookModel.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                if (bookModel.isCredit()) {
                                                    reference.child("pending_cancel").child(bookModel.getKey()).setValue(bookModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Snackbar.make(view, "Cancelled Done" , Snackbar.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                }
                                                else {
                                                    int will_count = HomeActivity.user_realtime.getCounter_block()+1;
                                                    HomeActivity.user_realtime.setCounter_block(will_count);

                                                    reference.child("users").child(user.getUid()).child("Personal Data").setValue(HomeActivity.user_realtime).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                reference.child("users").child(bookModel.getUserid()).child("Booking").child(bookModel.getKey()).removeValue();
                                                                reference.child("doctors").child(bookModel.getDoctorarea()).child(bookModel.getDoctorspecialization()).child(bookModel.getDoctorid())
                                                                        .child("Booking").child(bookModel.getBookday()).child(bookModel.getKey()).removeValue();
                                                                new AlertDialog.Builder(context)
                                                                        .setTitle("Cancel appointment")
                                                                        .setMessage("The appointment has been successfully cleared. \nbut if you retry to cancel any appointment you will blocked from using our services")

                                                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                dialog.dismiss();
                                                                               return;
                                                                            }
                                                                        })

                                                                        // A null listener allows the button to dismiss the dialog and take no further action.
                                                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                                        .show();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(context, "Error While delete appointment", Toast.LENGTH_SHORT).show();
                                                                return;
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                dialog.show();
            }
        }
    }



}
