package inclinic.com.models.doctor;

import java.io.Serializable;

public class User_Model implements Serializable{
    String  specialization;

    public User_Model(String specialization, String lower_doctor_name_search, int rate_click, String url_image_profile, Integer counter_block, Boolean is_user, String location_text, String location_maps, Double price_per_hour, String area_location, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, boolean is_doctor, double rate_total, int total_kashf, double total_star_rate, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        this.lower_doctor_name_search = lower_doctor_name_search;
        this.rate_click = rate_click;
        Url_image_profile = url_image_profile;
        this.counter_block = counter_block;
        this.is_user = is_user;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.area_location = area_location;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.rate_total = rate_total;
        this.total_kashf = total_kashf;
        this.total_star_rate = total_star_rate;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
    }

    String lower_doctor_name_search;

    public String getLower_doctor_name_search() {
        return lower_doctor_name_search;
    }

    public void setLower_doctor_name_search(String lower_doctor_name_search) {
        this.lower_doctor_name_search = lower_doctor_name_search;
    }

    public User_Model(String specialization, int rate_click, String url_image_profile, Integer counter_block, Boolean is_user, String location_text, String location_maps, Double price_per_hour, String area_location, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, boolean is_doctor, double rate_total, int total_kashf, double total_star_rate, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        this.rate_click = rate_click;
        Url_image_profile = url_image_profile;
        this.counter_block = counter_block;
        this.is_user = is_user;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.area_location = area_location;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.rate_total = rate_total;
        this.total_kashf = total_kashf;
        this.total_star_rate = total_star_rate;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
    }

    public int getRate_click() {
        return rate_click;
    }

    public void setRate_click(int rate_click) {
        this.rate_click = rate_click;
    }

    int rate_click;
    String Url_image_profile;

    public User_Model(String specialization, String url_image_profile, Integer counter_block, Boolean is_user, String location_text, String location_maps, Double price_per_hour, String area_location, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, boolean is_doctor, double rate_total, int total_kashf, double total_star_rate, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        Url_image_profile = url_image_profile;
        this.counter_block = counter_block;
        this.is_user = is_user;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.area_location = area_location;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.rate_total = rate_total;
        this.total_kashf = total_kashf;
        this.total_star_rate = total_star_rate;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
    }

    public Integer getCounter_block() {
        return counter_block;
    }

    public void setCounter_block(Integer counter_block) {
        this.counter_block = counter_block;
    }

    Integer counter_block;

    public User_Model(String specialization, String url_image_profile, Boolean is_user, String location_text, String location_maps, Double price_per_hour, String area_location, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, boolean is_doctor, double rate_total, int total_kashf, double total_star_rate, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        Url_image_profile = url_image_profile;
        this.is_user = is_user;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.area_location = area_location;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.rate_total = rate_total;
        this.total_kashf = total_kashf;
        this.total_star_rate = total_star_rate;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
    }

    public Boolean getIs_user() {
        return is_user;
    }

    public void setIs_user(Boolean is_user) {
        this.is_user = is_user;
    }

    Boolean is_user;

    public String getUrl_image_profile() {
        return Url_image_profile;
    }

    public void setUrl_image_profile(String url_image_profile) {
        Url_image_profile = url_image_profile;
    }

    public User_Model(String url_image_profile, String specialization, String location_text, String location_maps, Double price_per_hour, String area_location, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, boolean is_doctor, double rate_total, int total_kashf, double total_star_rate, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        this.Url_image_profile=url_image_profile;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.area_location = area_location;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.rate_total = rate_total;
        this.total_kashf = total_kashf;
        this.total_star_rate = total_star_rate;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
        ;
    }

    String location_text;
    String location_maps;
    Double price_per_hour;
    String area_location;

    public String getArea_location() {
        return area_location;
    }

    public void setArea_location(String area_location) {
        this.area_location = area_location;
    }

    String id_number;
    String doctor_uid;
    boolean verified_from_inclinic_app_team ;
    String UID;
    String email;
    boolean is_doctor;

    public User_Model()
    {

    }
    public User_Model(String specialization, String location_text, String location_maps, Double price_per_hour, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, boolean is_doctor, double rate_total, int total_kashf, double total_star_rate, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.rate_total = rate_total;
        this.total_kashf = total_kashf;
        this.total_star_rate = total_star_rate;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
    }

    double rate_total;

    public double getRate_total() {
        return rate_total;
    }

    public void setRate_total(double rate_total) {
        this.rate_total = rate_total;
    }

    public int getTotal_kashf() {
        return total_kashf;
    }

    public void setTotal_kashf(int total_kashf) {
        this.total_kashf = total_kashf;
    }

    public double getTotal_star_rate() {
        return total_star_rate;
    }

    public void setTotal_star_rate(double total_star_rate) {
        this.total_star_rate = total_star_rate;
    }

    int total_kashf;
    double total_star_rate;
    public User_Model(String specialization, String location_text, String location_maps, Double price_per_hour, String id_number, String doctor_uid, boolean verified_from_inclinic_app_team, String UID, String email, Boolean bool_check_dataMedical, boolean is_doctor, int age, String name, String phonenumber, String facilty_name, Integer graduation_year, String about_doctor) {
        this.specialization = specialization;
        this.location_text = location_text;
        this.location_maps = location_maps;
        this.price_per_hour = price_per_hour;
        this.id_number = id_number;
        this.doctor_uid = doctor_uid;
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
        this.UID = UID;
        this.email = email;
        this.is_doctor = is_doctor;
        this.age = age;
        this.name = name;
        this.phonenumber = phonenumber;
        this.facilty_name = facilty_name;
        this.graduation_year = graduation_year;
        this.about_doctor = about_doctor;
    }

    int age;
    String name;
    String phonenumber;
    String facilty_name;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public String getLocation_maps() {
        return location_maps;
    }

    public void setLocation_maps(String location_maps) {
        this.location_maps = location_maps;
    }

    public Double getPrice_per_hour() {
        return price_per_hour;
    }

    public void setPrice_per_hour(Double price_per_hour) {
        this.price_per_hour = price_per_hour;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getDoctor_uid() {
        return doctor_uid;
    }

    public void setDoctor_uid(String doctor_uid) {
        this.doctor_uid = doctor_uid;
    }

    public boolean isVerified_from_inclinic_app_team() {
        return verified_from_inclinic_app_team;
    }

    public void setVerified_from_inclinic_app_team(boolean verified_from_inclinic_app_team) {
        this.verified_from_inclinic_app_team = verified_from_inclinic_app_team;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isIs_doctor() {
        return is_doctor;
    }

    public void setIs_doctor(boolean is_doctor) {
        this.is_doctor = is_doctor;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFacilty_name() {
        return facilty_name;
    }

    public void setFacilty_name(String facilty_name) {
        this.facilty_name = facilty_name;
    }

    public Integer getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(Integer graduation_year) {
        this.graduation_year = graduation_year;
    }

    public String getAbout_doctor() {
        return about_doctor;
    }

    public void setAbout_doctor(String about_doctor) {
        this.about_doctor = about_doctor;
    }

    Integer graduation_year;
    String about_doctor;




}
