package inclinic.com.ui.Book;

import java.io.Serializable;

public class BookModel implements Serializable {

    private String doctorid , userid , username , doctorname , bookday , booktime , userphone ,doctorarea,
            doctorimg, cardnumber, cvv, expirationdate, postalcode , doctorspecialization,doctorprice , key ;
    boolean isCredit , isDone;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

    public String getDoctorarea() {
        return doctorarea;
    }

    public void setDoctorarea(String doctorarea) {
        this.doctorarea = doctorarea;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getBookday() {
        return bookday;
    }

    public void setBookday(String bookday) {
        this.bookday = bookday;
    }

    public String getBooktime() {
        return booktime;
    }

    public void setBooktime(String booktime) {
        this.booktime = booktime;
    }

    public String getUserphone() {
        return userphone;
    }
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getDoctorimg() {
        return doctorimg;
    }

    public void setDoctorimg(String doctorimg) {
        this.doctorimg = doctorimg;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getDoctorspecialization() {
        return doctorspecialization;
    }

    public void setDoctorspecialization(String doctorspecialization) {
        this.doctorspecialization = doctorspecialization;
    }

    public String getDoctorprice() {
        return doctorprice;
    }

    public void setDoctorprice(String doctorprice) {
        this.doctorprice = doctorprice;
    }
}
