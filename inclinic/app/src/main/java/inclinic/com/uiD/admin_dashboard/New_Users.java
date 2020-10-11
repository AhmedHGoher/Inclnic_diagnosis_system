package inclinic.com.uiD.admin_dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import inclinic.com.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class New_Users extends AppCompatActivity {

    int NOUS;
   // ArrayList<String> Mon = new ArrayList<>();
    private TextView txtNoUsers;
    private int  nousers = 0;
    int Size;
    DatabaseReference fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__users);
//=========== Action Bar Change   ================
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Users");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//=========== Get text ID   ================
    txtNoUsers = findViewById(R.id.txt_NEW_No_Users);
//=========== Set Text   ================

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();

                txtNoUsers.setText( String.valueOf(Size));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//=========== Chart Call   ================
        setupBarChart();
    }
    //============= New Try With DB    =======================
    //========== Bar Chart   =========

    private void setupBarChart() {


        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        fb.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get total available quest
                Size = (int) dataSnapshot.getChildrenCount();

                List<BarEntry> barEntries = new ArrayList<>();
                barEntries.add(new BarEntry(0, Float.parseFloat(String.valueOf(Size))));
                // Float.parseFloat(Disease_Name[i])
                BarDataSet dataSet = new BarDataSet(barEntries,"Users Bar");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData data = new BarData(dataSet);

                BarChart chart = (BarChart) findViewById(R.id.barchart);
//===================== Description  ===================
                Description description = chart.getDescription();
                // enable or disable the description
                description.setEnabled(false);
// set the description text
                description.setText("Bar Chartoooooo");
// set the position of the description on the screen
                //    description.setPosition(float x, float y);

//=======================================
//===========   Style    ============================
                chart.setDrawBarShadow(false);
                // chart.setDrawValueAboveBar(true);
                // chart.setDrawGridBackground(true);

//=======================================

                data.setBarWidth(0.9f); // set custom bar width
                chart.setData(data);
                chart.setFitBars(true); // make the x-axis fit exactly all bars
                chart.animateY(3000);
                chart.invalidate();// refresh
//===============Extra========================
                XAxis xAxis = chart.getXAxis();
//        xAxis.setValueFormatter( new Analysis.MyXAxisValueFormatter(Mon));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(10f);
                xAxis.setTextColor(Color.BLUE);
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawGridLines(false);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //==============================================================

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;
        public MyXAxisValueFormatter( String[] values){
            this.mValues = values;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }
    //============================================================================




}
