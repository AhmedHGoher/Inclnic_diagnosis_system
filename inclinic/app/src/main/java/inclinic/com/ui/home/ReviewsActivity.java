package inclinic.com.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import inclinic.com.R;
import inclinic.com.models.person.RatingModel;

public class ReviewsActivity extends AppCompatActivity {

    ListView listView;
    CommentsListAdapter commentsListAdapter;
    ArrayList<RatingModel>ratingModels = new ArrayList<RatingModel>();
    TextView back_txt , noreviews_txt ;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        listView = findViewById(R.id.reviews_listview);
        back_txt = findViewById(R.id.reviews_backtxt);
        noreviews_txt = findViewById(R.id.reviews_noresultstxt);

        ratingModels = (ArrayList<RatingModel>) getIntent().getSerializableExtra("reviewmodels");

        if (ratingModels.isEmpty()){
            listView.setVisibility(View.GONE);
            noreviews_txt.setVisibility(View.VISIBLE);
        }else {
            listView.setVisibility(View.VISIBLE);
            noreviews_txt.setVisibility(View.GONE);
            commentsListAdapter = new CommentsListAdapter(ReviewsActivity.this , R.layout.row_review , ratingModels);
            listView.setAdapter(commentsListAdapter);
        }
    }
}