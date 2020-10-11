package inclinic.com.Admin.ui.PendingDoc;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;

public class PendDoctorRecyclerViewAdapter extends RecyclerView.Adapter<PendDoctorRecyclerViewAdapter.Holder>{
    List<User_Model> doctormodels ;
    Context context;

    public PendDoctorRecyclerViewAdapter(Context context, List<User_Model> doctormodels) {
        this.context = context;
        this.doctormodels = doctormodels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_doctoradmin , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        User_Model model = doctormodels.get(position);

        holder.about.setText(model.getAbout_doctor());
        holder.age.setText("Age: "+model.getAge());
        holder.price.setText("Price: "+model.getPrice_per_hour()+" L.E");
        holder.name.setText(model.getName());
        holder.specialist.setText(model.getSpecialization());
        holder.location.setText(model.getArea_location());
        holder.phone.setText(model.getPhonenumber());
        Glide.with(context)
                .load(model.getUrl_image_profile())
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
        return doctormodels.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView circleImageView  ;
        TextView name , specialist , location , phone , price ,age , about;
        Button info_btn;
        View view ;
        public Holder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.rowdoctoradmin_image);
            name = itemView.findViewById(R.id.rowdoctoradmin_name);
            specialist = itemView.findViewById(R.id.rowdoctoradmin_specialization);
            location = itemView.findViewById(R.id.rowdoctoradmin_location);
            phone = itemView.findViewById(R.id.rowdoctoradmin_phone);
            price = itemView.findViewById(R.id.rowdoctoradmin_price);
            age = itemView.findViewById(R.id.rowdoctoradmin_age);
            about = itemView.findViewById(R.id.rowdoctoradmin_about);
            info_btn = itemView.findViewById(R.id.rowdoctoradmin_infobtn);
            info_btn.setOnClickListener(this);

            view = itemView;

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.rowdoctoradmin_infobtn) {
                Intent intent = new Intent(context , PendingOnClickActivity.class);
                intent.putExtra("doctor_id" , doctormodels.get(getAdapterPosition()).getDoctor_uid());
                intent.putExtra("model" , doctormodels.get(getAdapterPosition()));
                context.startActivity(intent);
            }
        }
    }



}
