package inclinic.com.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import inclinic.com.R;
import inclinic.com.models.person.RatingModel;

public class CommentsListAdapter extends ArrayAdapter<RatingModel> {
    public CommentsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<RatingModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.row_review, parent , false);

        TextView username = view.findViewById(R.id.rowreview_username);
        TextView commentmsg = view.findViewById(R.id.rowreview_commentmsg);
        RatingBar ratingBar = view.findViewById(R.id.rowreview_ratingbar);

        RatingModel model = (RatingModel) getItem(position);

        username.setText(model.getUsername());
        commentmsg.setText(model.getRatemessage());
        ratingBar.setRating(model.getRatingstars());

        return view;
    }
}
