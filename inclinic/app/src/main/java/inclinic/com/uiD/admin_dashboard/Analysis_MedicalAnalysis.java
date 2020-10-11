package inclinic.com.uiD.admin_dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Analysis_MedicalAnalysis extends Fragment {
    public Analysis_MedicalAnalysis() {
        // Required empty public constructor
    }

    View v ;

    ArrayList<String> DiaNA = new ArrayList<>();
    ArrayList<String> DiaNANew = new ArrayList<>();
    // ArrayList<String> Mon = new ArrayList<>();
    DatabaseReference fb;
    int Size;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v =  inflater.inflate(R.layout.fragment_analysis__medical_analysis, container, false);

        //=========== Object Of DB   ================


        setupBarChart();
        setupPieChart();

        return v ;

    }


    //========== Bar Chart   =========

    private void setupBarChart() {


        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query =  fb.child("Visitors").orderByChild("type").equalTo("Medical Test");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String name = dataSnapshot.child("result").getValue().toString();
                    DiaNA.add(name);
                }

                DiaNANew = removeDuplicates(DiaNA);

                ArrayList<Integer> NODia = new ArrayList<>();
                for (int i = 0 ; i < DiaNANew.size(); i++){
                    NODia.add(Collections.frequency(DiaNA, DiaNANew.get(i)));
                }



                List<BarEntry> barEntries = new ArrayList<>();
                for (int i=0 ; i< NODia.size(); i++){
                    barEntries.add(new BarEntry(i, Float.parseFloat(String.valueOf(NODia.get(i)))));
                }
                // Float.parseFloat(Disease_Name[i])
                BarDataSet dataSet = new BarDataSet(barEntries,"Medical Analysis Bar");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                BarData data = new BarData(dataSet);

                BarChart chart = (BarChart) v.findViewById(R.id.barchart);
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
                //      xAxis.setValueFormatter( new MyXAxisValueFormatter(DiaNA));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(10f);
                xAxis.setTextColor(Color.RED);
                xAxis.setDrawAxisLine(true);
                xAxis.setDrawGridLines(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e(TAG, "onCancelled", error.toException());

            }
        });



    }


    //==============================================================

    public static class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;
        public MyXAxisValueFormatter(ArrayList<String> values){
            this.mValues = values.toArray(new String[0]);
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }
    }
    //============================================================================
    //========== Pie Chart   ==========
    private void setupPieChart() {

        fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query =  fb.child("Visitors").orderByChild("type").equalTo("Medical Test");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String name = dataSnapshot.child("result").getValue().toString();
                    DiaNA.add(name);
                }

                DiaNANew = removeDuplicates(DiaNA);

                ArrayList<Integer> NODia = new ArrayList<>();
                for (int i = 0 ; i < DiaNANew.size(); i++){
                    NODia.add(Collections.frequency(DiaNA, DiaNANew.get(i)));
                }


                List<PieEntry> pieEntries = new ArrayList<>();
                for (int i=0 ; i< NODia.size(); i++){
                    pieEntries.add(new PieEntry(Float.parseFloat(String.valueOf(NODia.get(i))),DiaNANew.get(i)));
                }
                PieDataSet dataSet = new PieDataSet(pieEntries , "");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                PieData data = new PieData(dataSet);
                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);
                data.setValueFormatter(new PercentFormatter());
                PieChart chart = (PieChart) v.findViewById(R.id.piechart);

                //===================== Description  ===================
                Description description = chart.getDescription();
                // enable or disable the description
                description.setEnabled(false);
// set the description text
                description.setText("Pie Chartoooooo");
// set the position of the description on the screen
                //    description.setPosition(float x, float y);
//=======================================
                //================  Style         =======================
                chart.setUsePercentValues(true);
                chart.setExtraOffsets(5,10,5,5);

                chart.setDragDecelerationFrictionCoef(0.95f);

                chart.setCenterText("Disease");
                chart.setCenterTextColor(Color.BLACK);
                chart.setCenterTextSize(10);
                chart.setCenterTextRadiusPercent(50);
                chart.setDrawHoleEnabled(true);
                chart.setHoleColor(Color.WHITE);
                chart.setHoleRadius(30);
                chart.setTransparentCircleRadius(40);
                chart.setTransparentCircleColor(Color.BLUE);
                chart.setTransparentCircleAlpha(50);
                //=======================================
                chart.setData(data);
                //   chart.setUsePercentValues(true);
                chart.animateY(1000);
                chart.invalidate();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e(TAG, "onCancelled", error.toException());

            }
        });

    }



    public static ArrayList<String> removeDuplicates(ArrayList<String> list)
    {

        // Create a new ArrayList
        ArrayList<String> newList = new ArrayList<String>();

        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }




}
