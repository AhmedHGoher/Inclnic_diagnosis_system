package inclinic.com.models.doctor;

public class Times_appointments {
    private String start_appoi;
    private  String end_appoi;
    private boolean is_booked;

    public Times_appointments(){}

    public String getStart_appoi() {
        return start_appoi;
    }

    public Times_appointments(String start_appoi, String end_appoi, boolean is_booked) {
        this.start_appoi = start_appoi;
        this.end_appoi = end_appoi;
        this.is_booked = is_booked;
    }

    public void setStart_appoi(String start_appoi) {
        this.start_appoi = start_appoi;
    }

    public String getEnd_appoi() {
        return end_appoi;
    }

    public void setEnd_appoi(String end_appoi) {
        this.end_appoi = end_appoi;
    }

    public boolean isIs_booked() {
        return is_booked;
    }

    public void setIs_booked(boolean is_booked) {
        this.is_booked = is_booked;
    }
}
