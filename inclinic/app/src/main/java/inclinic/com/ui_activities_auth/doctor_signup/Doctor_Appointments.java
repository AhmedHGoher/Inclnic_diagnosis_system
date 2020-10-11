package inclinic.com.ui_activities_auth.doctor_signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Calendar;

import inclinic.com.HomeActivity;
import inclinic.com.R;
import inclinic.com.models.doctor.Book_doctor_appointment_model;
import inclinic.com.models.doctor.Times_appointments;
import inclinic.com.models.doctor.User_Model;

public class Doctor_Appointments extends AppCompatActivity {


Button submit_appoiments;
Switch saturday_switch;
Switch sunday_switch;
Switch monday_switch;
Switch tuesday_switch;
Switch wednesday_switch;
Switch thursday_switch;
Switch friday_switch;
Dialog dialog;
public ArrayList<Book_doctor_appointment_model> list_day_appoiments=new ArrayList<Book_doctor_appointment_model>();
private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
ProgressBar progressBar;
FirebaseAuth mAuth = FirebaseAuth.getInstance();
int switch_add_num_in_array_size [];

    public static SharedPreferences SP ;
    public static String filename = "Keys";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);

        submit_appoiments=findViewById(R.id.ui_submit_appoiments_Button_ui_doctor);
        saturday_switch=findViewById(R.id.Saturday_switch_ui_appoiment);
        sunday_switch=findViewById(R.id.sunday_switch_ui_appoiment);
        monday_switch=findViewById(R.id.Monday_switch_ui_appoiment);
        tuesday_switch=findViewById(R.id.Tuesday_switch_ui_appoiment);
        wednesday_switch=findViewById(R.id.Wednesday_switch_ui_appoiment);
        thursday_switch=findViewById(R.id.Thursday_switch_ui_appoiment);
        friday_switch=findViewById(R.id.Friday_switch_ui_appoiment);
        progressBar=findViewById(R.id.main_progress_appointments_doctor);


        init();
        showdialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);

        saturday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);
                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    day_to_appopintment.setText("day : "+"saturday");
                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;
                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }

                                    if(selectedMinute<10)
                                    {
                                    chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }

                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;
                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }

                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });


                    String current_day="Saturday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            saturday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);

                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[0]);
                }
            }
        });


        sunday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);

                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;
                    day_to_appopintment.setText("day : "+"sunday");

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });

                    String current_day="Sunday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            sunday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[1]);

                }
            }
        });

        monday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);

                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;
                    day_to_appopintment.setText("day : "+"Monday");

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });

                    String current_day="Monday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            monday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
//                            switch_add_num_in_array_size[2]=list_day_appoiments.size()-1;
                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[2]);

                }
            }
        });


        tuesday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);

                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;

                    day_to_appopintment.setText("day : "+"Tuesday");

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });

                    String current_day="Tuesday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            tuesday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[3]);
                }
            }
        });
        wednesday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);

                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;

                    day_to_appopintment.setText("day : "+"Wednesday");

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });


                    String current_day="Wednesday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            wednesday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[4]);
                }
            }
        });

        thursday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {

                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);

                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;

                    day_to_appopintment.setText("day : "+"Thursday");

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });


                    String current_day="Thursday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            thursday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[5]);
                }
            }
        });

        friday_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {

                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    final String[] amPm = new String[1];

                    final Dialog dialog = new Dialog(Doctor_Appointments.this);
                    dialog.setContentView(R.layout.onclick_dialog);
                    dialog.show();
                    dialog.setCancelable(false);
                    Book_doctor_appointment_model book_time = new Book_doctor_appointment_model(hour,minute,hour,minute,"");
                    final TextView day_to_appopintment = dialog.findViewById(R.id.day_name);
                    final Button add=dialog.findViewById(R.id.book_add);
                    final Button cancel=dialog.findViewById(R.id.book_cancel);

                    final EditText chooseTime_start = dialog.findViewById(R.id.timePicker_start_appointment_day_ui);
                    final EditText chooseTime_end = dialog.findViewById(R.id.timePicker_end_appointment_day_ui);

                    final int[] total_min_start = new int[1];
                    total_min_start[0] = 0 ;
                    final int[] total_min_end = new int[1];
                    total_min_end[0] = 0 ;

                    day_to_appopintment.setText("day : "+"Friday");

                    chooseTime_start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_start[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_start.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_start.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setStart_hour(selectedHour);
                                    book_time.setStart_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_start(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_start(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();

                        }
                    });

                    chooseTime_end.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog mTimePicker;
                            mTimePicker = new TimePickerDialog(Doctor_Appointments.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                    total_min_end[0] = ( selectedHour*60 ) + selectedMinute ;

                                    if (selectedHour >= 12) {
                                        amPm[0] = "PM";
                                        selectedHour=selectedHour-12;
                                    } else {
                                        amPm[0] = "AM";
                                    }
                                    if(selectedMinute<10)
                                    {
                                        chooseTime_end.setText( selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        chooseTime_end.setText( selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                    book_time.setEnd_hour(selectedHour);
                                    book_time.setEnd_minute(selectedMinute);
                                    if(selectedMinute<10)
                                    {
                                        book_time.setTime_full_end(selectedHour+":0"+selectedMinute+" "+amPm[0]);
                                    }
                                    else
                                    {
                                        book_time.setTime_full_end(selectedHour+":"+selectedMinute+" "+amPm[0]);
                                    }
                                }
                            }, hour, minute, false);
                            mTimePicker.setTitle("Select Time");
                            mTimePicker.show();
                        }
                    });


                    String current_day="Friday";
                    book_time.setDay_name(current_day);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            friday_switch.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            preventTwoClick(v);
                            if(book_time.getTime_full_start()==""||book_time.getTime_full_start()==null||book_time.getTime_full_end()==""||book_time.getTime_full_end()==null)
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

                            if ( total_min_end [0] > total_min_start [0] )
                            {
                                for(int i= 0 ; 0<1 ; i++)
                                {

                                    if ( total_min_end [0] > (total_min_start [0]) +30 )
                                    {
                                        int hours_start_final;
                                        int min_start_final;
                                        int hours_end_final;
                                        int min_end_final ;
                                        String am_or_pm_start = "AM";
                                        String am_or_pm_end = "AM";

                                        min_start_final =total_min_start [0] % 60;
                                        hours_start_final=total_min_start [0] / 60;
                                        min_end_final = ( ( total_min_start [0] ) +30 ) % 60 ;
                                        hours_end_final =( ( total_min_start [0] ) +30 ) /60 ;
                                        if((hours_start_final) -12 >= 0)
                                        {
                                            hours_start_final = hours_start_final-12;
                                            am_or_pm_start = "PM";
                                        }
                                        if((hours_end_final) -12 >= 0)
                                        {
                                            hours_end_final = hours_end_final-12;
                                            am_or_pm_end = "PM";
                                        }

                                        Times_appointments time;
                                        if(min_start_final<10)
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }

                                        }
                                        else
                                        {
                                            if(min_end_final<10)
                                            {
                                                time = new Times_appointments( (hours_start_final)+":"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":0"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                            else
                                            {
                                                time = new Times_appointments( (hours_start_final)+":0"+(min_start_final)+" "+am_or_pm_start ,
                                                        (hours_end_final)+":"+(min_end_final)+" "+am_or_pm_end ,
                                                        false );
                                            }
                                        }

                                        book_time.times_appointments.add( i,time) ;
                                        total_min_start [0] = total_min_start [0] + 30 ;
                                        continue ;

                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                Toast.makeText(Doctor_Appointments.this, "Please enter a valid time", Toast.LENGTH_LONG).show();
                                return;
                            }

//                            switch_add_num_in_array_size[0]=list_day_appoiments.size()-1;
                            list_day_appoiments.add(book_time);
                            TastyToast.makeText(getApplicationContext(), "Assigned Success", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            dialog.dismiss();
                        }
                    });
                }
                else
                {
//                    list_day_appoiments.remove(switch_add_num_in_array_size[6]);
                }
            }
        });

        submit_appoiments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_day_appoiments.size()==0)
                {
                    preventTwoClick(v);
                    TastyToast.makeText(getApplicationContext(), "Can't Submit Emphty Appointments", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    return;
                }
                else
                {
                    upload_all_appointments();
                }
            }
        });
    }

    public void init() {
        this.dialog = new Dialog(this);
    }

    public void showdialog() {
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
    }


    public void upload_all_appointments()
    {

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//          = preferences.getString("Doctor_selected_area", "");
//         String get_specialization = preferences.getString("Doctor_selected_specialization", "");
        //        SP = getSharedPreferences(filename, MODE_PRIVATE);
//        String get_location_area = SP.getString("Doctor_selected_area","null");
//        String get_specialization = SP.getString("Doctor_selected_specialization","null");

//

        reference.child("pending_appointments").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User_Model current_doctor =dataSnapshot.getValue(User_Model.class);
                    String get_location_area = current_doctor.getArea_location() ;
                String  get_specialization = current_doctor.getSpecialization().toString();
                current_doctor.setVerified_from_inclinic_app_team(true);

                reference.child("doctors").child(get_location_area).child(get_specialization).child(user.getUid()).setValue(current_doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            reference.child("doctors").child(get_location_area).child(get_specialization).child(user.getUid()).child("Appointments").setValue(list_day_appoiments).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        reference.child("users").child(user.getUid()).child("Personal Data").setValue(current_doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {
                                                    reference.child("all_doctors").child(user.getUid()).setValue(current_doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task)
                                                        {
                                                            if(task.isSuccessful())
                                                            {
                                                                reference.child("pending_appointments").child(user.getUid()).removeValue();
                                                                HomeActivity home = new HomeActivity(current_doctor);
                                                                TastyToast.makeText(getApplicationContext(), "Success Register Appointments - Welcome Doctor ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                final Intent i = new Intent(getApplicationContext(), home.getClass());
                                                                startActivity(i);
                                                                finish();
                                                            }
                                                            else
                                                                {
                                                                    FirebaseAuth.getInstance().signOut();
                                                                    TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                                    progressBar.setVisibility(View.GONE);
                                                                    final Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                    return;
                                                                }
                                                        }
                                                    });

                                                }
                                                else
                                                {
                                                    FirebaseAuth.getInstance().signOut();
                                                    TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                                    progressBar.setVisibility(View.GONE);
                                                    final Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                    return;
                                                }

                                            }
                                        });

                                    } else
                                    {
                                        FirebaseAuth.getInstance().signOut();
                                        TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                                        progressBar.setVisibility(View.GONE);
                                        final Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(i);
                                        finish();
                                        return;
                                    }
                                }
                            });
                        } else {
                            FirebaseAuth.getInstance().signOut();
                            TastyToast.makeText(getApplicationContext(), "Error in Network - please Check your connection", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                            progressBar.setVisibility(View.GONE);

                            final Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            finish();
                            return;
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                TastyToast.makeText(getApplicationContext(), "Error While Create User Data PLease Try again", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                return;
            }
        });



    }

    public static void preventTwoClick(final View view){
        view.setEnabled(false);
        view.postDelayed(new Runnable() {
            public void run() {
                view.setEnabled(true);
            }
        }, 1000);
    }
}