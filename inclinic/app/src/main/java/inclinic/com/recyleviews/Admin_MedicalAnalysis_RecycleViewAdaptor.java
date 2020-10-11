package inclinic.com.recyleviews;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import inclinic.com.R;
import inclinic.com.classes.MediTest;
import inclinic.com.uiD.admin_medical_analysis.Admin_Medical_Analysis_Update_Action;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Admin_MedicalAnalysis_RecycleViewAdaptor extends RecyclerView.Adapter<Admin_MedicalAnalysis_RecycleViewAdaptor.MyViewHolder> {

    Context mContext;
    List<MediTest> mData;
    Dialog myDialog;
    //Add
    private String S_MTN,S_TC,S_FR,S_LR,S_LD,S_HD;
    int S_ID;

    public Admin_MedicalAnalysis_RecycleViewAdaptor(Context context, List<MediTest> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_medical_analysis,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        //Dialog ini

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.item_details_medical_analysis);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_medical_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//============================ Get Items by id =======================================

                TextView details_MTN = (TextView) myDialog.findViewById(R.id.details_M_T_MTN);
                TextView details_TC = (TextView) myDialog.findViewById(R.id.details_M_T_TC);
                TextView details_FR = (TextView) myDialog.findViewById(R.id.details_M_T_FR);
                TextView details_LR = (TextView) myDialog.findViewById(R.id.details_M_T_LR);
                TextView details_LD = (TextView) myDialog.findViewById(R.id.details_M_T_LD);
                TextView details_HD = (TextView) myDialog.findViewById(R.id.details_M_T_HD);
//================================ Buttons    =========================================
                Button details_M_update_btn = (Button) myDialog.findViewById(R.id.details_M_T_update_btn);
                Button details_M_delete_btn = (Button) myDialog.findViewById(R.id.details_M_T_delete_btn);
//================================ Set Data    =========================================
                S_ID = mData.get(vHolder.getAdapterPosition()).getId();
                S_MTN =mData.get(vHolder.getAdapterPosition()).getMTname();
                S_TC =mData.get(vHolder.getAdapterPosition()).getTcode() ;
                S_FR = mData.get(vHolder.getAdapterPosition()).getFrange();
                S_LR = mData.get(vHolder.getAdapterPosition()).getLrange();
                S_LD = mData.get(vHolder.getAdapterPosition()).getLow();
                S_HD = mData.get(vHolder.getAdapterPosition()).getHigh();



                details_MTN.setText(S_MTN);
                details_TC.setText(S_TC);
                details_FR.setText(S_FR);
                details_LR.setText(S_LR);
                details_LD.setText(S_LD);
                details_HD.setText(S_HD);
//================================ Update Button    =========================================
                details_M_update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, Admin_Medical_Analysis_Update_Action.class);
                        intent.putExtra("ID",S_ID);
                        intent.putExtra("mtn",S_MTN);
                        intent.putExtra("tc",S_TC);
                        intent.putExtra("fr",S_FR);
                        intent.putExtra("lr",S_LR);
                        intent.putExtra("ld",S_LD);
                        intent.putExtra("hd",S_HD);
                        mContext.startActivity(intent);

                        myDialog.dismiss();

                        //       Toast.makeText(mContext,"Test Update Click",Toast.LENGTH_SHORT).show();

                    }
                });

//================================ Delete Button    =========================================
                details_M_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //=========== Delete Function    ================
                        DeleteMediTest(S_ID);
                        //=========== Toast in the Screen    ================
                        Toast.makeText(mContext,"Deleted", Toast.LENGTH_SHORT).show();



                        myDialog.dismiss();

                    }
                });

//========================================================================================
                myDialog.show();

            }
        });



        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.MTN.setText(mData.get(position).getMTname());
        holder.TC.setText(mData.get(position).getTcode());
        holder.FR.setText(mData.get(position).getFrange());
        holder.LR.setText(mData.get(position).getLrange());
        holder.LD.setText(mData.get(position).getLow());
        holder.HD.setText(mData.get(position).getHigh());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_medical_analysis;

        private TextView MTN,TC,FR,LR,LD,HD;

        public MyViewHolder(View itemView){
            super(itemView);

            item_medical_analysis = (LinearLayout)  itemView.findViewById(R.id.item_medical_analysis_id);

            MTN = (TextView) itemView.findViewById(R.id.List_M_T_MTN);
            TC = (TextView) itemView.findViewById(R.id.List_M_T_TC);
            FR = (TextView) itemView.findViewById(R.id.List_M_T_FR);
            LR = (TextView) itemView.findViewById(R.id.List_M_T_LR);
            LD = (TextView) itemView.findViewById(R.id.List_M_T_LD);
            HD = (TextView) itemView.findViewById(R.id.List_M_T_HD);
        }
    }




    public void DeleteMediTest(int id){

        DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query = fb.child("MediTest").child(String.valueOf(id));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.e(TAG, "onCancelled", error.toException());

            }
        });

    }



}
