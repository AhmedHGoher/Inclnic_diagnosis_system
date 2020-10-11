package inclinic.com.tabs;

import inclinic.com.uiD.admin_medical_analysis.Admin_Medical_Analysis_Add_Tab;
import inclinic.com.uiD.admin_medical_analysis.Admin_Medical_Analysis_Update_Tab;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class A_Medical_Analysis_PageAdapter extends FragmentPagerAdapter {
    private int numoftabs;
    public A_Medical_Analysis_PageAdapter(FragmentManager fm , int numOfTabs) {
        super(fm);
        this.numoftabs=numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new Admin_Medical_Analysis_Add_Tab();
            case 1:
                return new Admin_Medical_Analysis_Update_Tab();
       //     case 2:
     //           return new Admin_Medical_Analysis_Delete_Tab();
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
