package inclinic.com.uiD.admin_diagnosis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import inclinic.com.tabs.A_Diagnosis_PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Admin_Diagnosis_Fragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem add_tab,update_tab;
    public A_Diagnosis_PageAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_admin_diagnosis, container, false ) ;

        tabLayout = (TabLayout) v.findViewById(R.id.A_Diagnosis_tabLayout);

        add_tab = (TabItem) v.findViewById(R.id.A_Diagnosis_Add_Tab);

        update_tab = (TabItem) v.findViewById(R.id.A_Diagnosis_Update_Tab);


        viewPager = (ViewPager) v.findViewById(R.id.A_Diagnosis_ViewPager);

        pagerAdapter = new A_Diagnosis_PageAdapter(getFragmentManager(),tabLayout.getTabCount());

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



        return v;
    }
}
