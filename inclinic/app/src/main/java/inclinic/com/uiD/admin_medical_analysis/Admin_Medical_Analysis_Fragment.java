package inclinic.com.uiD.admin_medical_analysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import inclinic.com.R;
import inclinic.com.tabs.A_Medical_Analysis_PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Admin_Medical_Analysis_Fragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem add_tab,update_tab;
    public A_Medical_Analysis_PageAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_admin_medical_analysis, container, false )  ;

        tabLayout = (TabLayout) v.findViewById(R.id.A_Medical_Analysis_tabLayout);

        add_tab = (TabItem) v.findViewById(R.id.A_Medical_Analysis_Add_Tab);

        update_tab = (TabItem) v.findViewById(R.id.A_Medical_Analysis_Update_Tab);


        viewPager = (ViewPager) v.findViewById(R.id.A_Medical_Analysis_ViewPager);

        pagerAdapter = new A_Medical_Analysis_PageAdapter(getFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0){ pagerAdapter.notifyDataSetChanged();}
                else if (tab.getPosition()==1){ pagerAdapter.notifyDataSetChanged();}
       //         else if (tab.getPosition()==2){ pagerAdapter.notifyDataSetChanged(); }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return v;

    }
}
