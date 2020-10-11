package inclinic.com.Admin.AllDoc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.R;
import inclinic.com.models.doctor.User_Model;

public class AllDocRecyclerViewAdapter extends RecyclerView.Adapter<AllDocRecyclerViewAdapter.Holder>{
    List<User_Model> doctormodels ;
    Context context;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public AllDocRecyclerViewAdapter(Context context, List<User_Model> doctormodels) {
        this.context = context;
        this.doctormodels = doctormodels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_alldoctors , parent , false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        User_Model model = doctormodels.get(position);

        holder.info.setText(model.getAbout_doctor());
        holder.price.setText(model.getPrice_per_hour()+" L.E");
        holder.name.setText(model.getName());
        holder.specialist.setText(model.getSpecialization());
        holder.location.setText(model.getArea_location());
        holder.ratingBar.setRating(Float.valueOf((float) model.getRate_total()));
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

    public class Holder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView  ;
        TextView name , specialist , location , info , price ;
        RatingBar ratingBar ;
        View view ;
        public Holder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.rowalldoctor_image);
            name = itemView.findViewById(R.id.rowalldoctor_name);
            specialist = itemView.findViewById(R.id.rowalldoctor_specialization);
            location = itemView.findViewById(R.id.rowalldoctor_location);
            info = itemView.findViewById(R.id.rowalldoctor_info);
            ratingBar = itemView.findViewById(R.id.rowalldoctor_rating);
            price = itemView.findViewById(R.id.rowalldoctor_price);

            view = itemView;

        }

    }



}
