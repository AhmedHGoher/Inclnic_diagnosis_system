package inclinic.com.models.doctor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book_doctor_appointment_model {
   private  int start_hour , start_minute , end_hour , end_minute ;
    private String day_name="";
    private  String time_full_start ="";
    public  ArrayList<Times_appointments> times_appointments = new ArrayList<Times_appointments>();

    public String getTime_full_end() {
        return time_full_end;
    }

    public void setTime_full_end(String time_full_end) {
        this.time_full_end = time_full_end;
    }

    private  String time_full_end;

    public Book_doctor_appointment_model(int start_hour, int start_minute, int end_hour, int end_minute, String day_name, String date_full) {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.day_name = day_name;
        this.time_full_start = date_full;
    }

    public  Book_doctor_appointment_model (){

    }

    public String getTime_full_start() {
        return time_full_start;
    }

    public void setTime_full_start(String time_full) {
        this.time_full_start = time_full;
    }

    public Book_doctor_appointment_model(int start_hour, int start_minute, int end_hour, int end_minute, String day_name) {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        this.day_name = day_name;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(int start_minute) {
        this.start_minute = start_minute;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_minute() {
        return end_minute;
    }

    public void setEnd_minute(int end_minute) {
        this.end_minute = end_minute;
    }



    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }
}
