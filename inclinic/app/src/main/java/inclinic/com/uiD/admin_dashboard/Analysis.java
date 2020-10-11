package inclinic.com.uiD.admin_dashboard;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import inclinic.com.R;
import inclinic.com.tabs.A_Analysis_PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Analysis extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem diagnosis_tab,medical_tab;
 //   public A_Diagnosis_PageAdapter pagerAdapter;

    public A_Analysis_PageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        //=========== Action Bar Change   ================
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Analysis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //=================================================

        tabLayout = (TabLayout) findViewById(R.id.A_Analysis_tabLayout);

        diagnosis_tab = (TabItem) findViewById(R.id.A_Analysis_Diagnosis_Tab);

        medical_tab = (TabItem) findViewById(R.id.A_Analysis_Medical_Tab);


        viewPager = (ViewPager) findViewById(R.id.A_Analysis_ViewPager);

        pagerAdapter = new A_Analysis_PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());


        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0){ pagerAdapter.notifyDataSetChanged();}
                else if (tab.getPosition()==1){ pagerAdapter.notifyDataSetChanged();}
                //        else if (tab.getPosition()==2){ pagerAdapter.notifyDataSetChanged(); }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //===========================================================





    }


    public ActionBar getSupportActionBar() {
        return null;
    }



}
