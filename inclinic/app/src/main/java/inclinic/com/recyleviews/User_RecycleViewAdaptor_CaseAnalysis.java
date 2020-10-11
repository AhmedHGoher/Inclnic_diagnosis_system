package inclinic.com.recyleviews;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import inclinic.com.R;
import inclinic.com.classes.CaseAnalysis;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class User_RecycleViewAdaptor_CaseAnalysis extends RecyclerView.Adapter<User_RecycleViewAdaptor_CaseAnalysis.MyViewHolder> {

    Context mContext;
    List<CaseAnalysis> mData;
    Dialog myDialog;

    public User_RecycleViewAdaptor_CaseAnalysis(Context context, List<CaseAnalysis> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
         v = LayoutInflater.from(mContext).inflate(R.layout.item_case_analysis,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
/*
        //Dialog ini

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.item_details_case_analysis);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_case_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//============================ Get Items by id =======================================
                TextView     details_part = (TextView) myDialog.findViewById(R.id.details_part);
                TextView    details_pain = (TextView) myDialog.findViewById(R.id.details_pain);
                TextView   details_symp = (TextView) myDialog.findViewById(R.id.details_symp);
                TextView   details_treat = (TextView) myDialog.findViewById(R.id.details_treat);
                TextView   details_age = (TextView) myDialog.findViewById(R.id.details_age);
                TextView   details_gender = (TextView) myDialog.findViewById(R.id.details_gender);
                TextView  details_history = (TextView) myDialog.findViewById(R.id.details_history);
                TextView   details_times = (TextView) myDialog.findViewById(R.id.details_times);
                TextView    details_inherit = (TextView) myDialog.findViewById(R.id.details_inherted);
                TextView   details_country = (TextView) myDialog.findViewById(R.id.details_country);
                TextView  details_expect = (TextView) myDialog.findViewById(R.id.details_expected);
//================================ Buttons    =========================================
                Button details_update_btn = (Button) myDialog.findViewById(R.id.details_update_btn);
                Button details_delete_btn = (Button) myDialog.findViewById(R.id.details_delete_btn);
//================================ Set Data    =========================================
                details_part.setText(mData.get(vHolder.getAdapterPosition()).getPart());
                details_pain.setText(mData.get(vHolder.getAdapterPosition()).getPain());
                details_symp.setText(mData.get(vHolder.getAdapterPosition()).getSymp());
                details_treat.setText(mData.get(vHolder.getAdapterPosition()).getTreat());
                details_age.setText(mData.get(vHolder.getAdapterPosition()).getAge());
                details_gender.setText(mData.get(vHolder.getAdapterPosition()).getGender());
                details_history.setText(mData.get(vHolder.getAdapterPosition()).getHistory());
                details_times.setText(mData.get(vHolder.getAdapterPosition()).getTimes());
                details_inherit.setText(mData.get(vHolder.getAdapterPosition()).getInheritance());
                details_country.setText(mData.get(vHolder.getAdapterPosition()).getCountry());
                details_expect.setText(mData.get(vHolder.getAdapterPosition()).getExpected());
//================================ Update Button    =========================================
                details_update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"Test Update Click",Toast.LENGTH_SHORT).show();
                    }
                });
//================================ Delete Button    =========================================
                details_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"Test Delete Click",Toast.LENGTH_SHORT).show();

                    }
                });
//========================================================================================
                myDialog.show();

            }
        });

*/

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    holder.part.setText(mData.get(position).getPart());
    holder.pain.setText(mData.get(position).getPain());
    holder.symp.setText(mData.get(position).getSymp());
    holder.treat.setText(mData.get(position).getTreat());
    holder.age.setText(mData.get(position).getAge());
    holder.gender.setText(mData.get(position).getGender());
    holder.history.setText(mData.get(position).getHistory());
    holder.times.setText(mData.get(position).getTimes());
    holder.inherit.setText(mData.get(position).getInheritance());
    holder.country.setText(mData.get(position).getCountry());
    holder.expect.setText(mData.get(position).getExpected());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

//    private LinearLayout item_case_analysis;

     private TextView part,pain,symp,treat,age,gender,history,times,inherit ,country,expect;

        public MyViewHolder(View itemView){
        super(itemView);

    //        item_case_analysis = (LinearLayout)  itemView.findViewById(R.id.item_case_analysis_id);
             part = (TextView) itemView.findViewById(R.id.ITxtpart);
             pain = (TextView) itemView.findViewById(R.id.ITxtpain);
             symp = (TextView) itemView.findViewById(R.id.ITxtsymp);
             treat = (TextView) itemView.findViewById(R.id.ITxttreat);
             age = (TextView) itemView.findViewById(R.id.ITxtage);
             gender = (TextView) itemView.findViewById(R.id.ITxtgender);
             history = (TextView) itemView.findViewById(R.id.ITxthistory);
             times = (TextView) itemView.findViewById(R.id.ITxttimes);
             inherit = (TextView) itemView.findViewById(R.id.ITxtinherted);
             country = (TextView) itemView.findViewById(R.id.ITxtcountry);
             expect = (TextView) itemView.findViewById(R.id.ITxtexpected);


            }
    }

}
