package inclinic.com.recyleviews;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import inclinic.com.R;
import inclinic.com.classes.Questions;
import inclinic.com.uiD.admin_diagnosis.Admin_Diagnosis_Update_Action;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Admin_Diagnosis_RecycleViewAdaptor extends RecyclerView.Adapter<Admin_Diagnosis_RecycleViewAdaptor.MyViewHolder> {

    Context mContext;
    List<Questions> mData;
    Dialog myDialog;
    //Add
    private String S_idx,S_question,S_a1,S_a2,S_a3,S_a4;
    private int S_ID;


    public Admin_Diagnosis_RecycleViewAdaptor(Context context, List<Questions> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_diagnosis,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        //Dialog ini

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.item_details_diagnosis);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//============================ Get Items by id =======================================
                TextView details_idx = myDialog.findViewById(R.id.details_D_T_idx);
                TextView details_question = myDialog.findViewById(R.id.details_D_T_Question);
                TextView details_a1 = myDialog.findViewById(R.id.details_D_T_A1);
                TextView details_a2 = myDialog.findViewById(R.id.details_D_T_A2);
                TextView details_a3 = myDialog.findViewById(R.id.details_D_T_A3);
                TextView details_a4 = myDialog.findViewById(R.id.details_D_T_A4);
//================================ Buttons    =========================================
                Button details_D_update_btn = myDialog.findViewById(R.id.details_D_T_update_btn);
                Button details_D_delete_btn = myDialog.findViewById(R.id.details_D_T_delete_btn);
//================================ Set Data    =========================================
                S_ID = mData.get(vHolder.getAdapterPosition()).getId();
                S_idx =mData.get(vHolder.getAdapterPosition()).getIdx();
                S_question =mData.get(vHolder.getAdapterPosition()).getQ() ;
                S_a1 = mData.get(vHolder.getAdapterPosition()).getT();
                S_a2 = mData.get(vHolder.getAdapterPosition()).getF();
                S_a3 = mData.get(vHolder.getAdapterPosition()).getA();
                S_a4 = mData.get(vHolder.getAdapterPosition()).getB();

                details_idx.setText(S_idx);
                details_question.setText(S_question);
                details_a1.setText(S_a1);
                details_a2.setText(S_a2);
                details_a3.setText(S_a3);
                details_a4.setText(S_a4);

//================================ Update Button    =========================================
                details_D_update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent = new Intent(mContext, Admin_Diagnosis_Update_Action.class);
                        intent.putExtra("ID",S_ID);
                        intent.putExtra("index",S_idx);
                        intent.putExtra("question",S_question);
                        intent.putExtra("a1",S_a1);
                        intent.putExtra("a2",S_a2);
                        intent.putExtra("a3",S_a3);
                        intent.putExtra("a4",S_a4);

                        mContext.startActivity(intent);
                        myDialog.dismiss();


                        //       Toast.makeText(mContext,"Test Update Click",Toast.LENGTH_SHORT).show();


                    }
                });
//================================ Delete Button    =========================================
                details_D_delete_btn.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        //=========== Delete Function    ================

                        DeleteContact(S_ID);
                        //=========== Toast in the Screen    ================
                        Toast.makeText(mContext,"Delete", Toast.LENGTH_SHORT).show();


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

        holder.idx.setText(mData.get(position).getIdx());
        holder.question.setText(mData.get(position).getQ());
        holder.a1.setText(mData.get(position).getT());
        holder.a2.setText(mData.get(position).getF());
        holder.a3.setText(mData.get(position).getA());
        holder.a4.setText(mData.get(position).getB());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout item_diagnosis;

        private TextView idx,question,a1,a2,a3,a4;


        public MyViewHolder(View itemView){
            super(itemView);

            item_diagnosis = itemView.findViewById(R.id.item_diagnosis_id);
            idx = itemView.findViewById(R.id.List_D_T_idx);
            question = itemView.findViewById(R.id.List_D_T_Question);
            a1 = itemView.findViewById(R.id.List_D_T_A1);
            a2 = itemView.findViewById(R.id.List_D_T_A2);
            a3 = itemView.findViewById(R.id.List_D_T_A3);
            a4 = itemView.findViewById(R.id.List_D_T_A4);


        }
    }


    public void DeleteContact(int id){

        DatabaseReference fb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foura-7efbd.firebaseio.com/");

        Query query = fb.child("questions").child(String.valueOf(id));
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
