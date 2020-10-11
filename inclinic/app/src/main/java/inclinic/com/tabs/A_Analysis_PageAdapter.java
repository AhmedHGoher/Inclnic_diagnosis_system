package inclinic.com.tabs;

import inclinic.com.uiD.admin_dashboard.Analysis_Diagnosis;
import inclinic.com.uiD.admin_dashboard.Analysis_MedicalAnalysis;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class A_Analysis_PageAdapter extends FragmentPagerAdapter {
    private int numoftabs;

    public A_Analysis_PageAdapter(FragmentManager fm , int numOfTabs) {
        super(fm);
        this.numoftabs=numOfTabs;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new Analysis_Diagnosis();
            case 1:
                return new Analysis_MedicalAnalysis();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
