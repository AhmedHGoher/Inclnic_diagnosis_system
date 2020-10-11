package inclinic.com.models.person;

import java.io.Serializable;

public class RatingModel implements Serializable {
    private Float ratingstars ;
    private String username , userimg , userid , doctorid , ratemessage;

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getRatemessage() {
        return ratemessage;
    }

    public void setRatemessage(String ratemessage) {
        this.ratemessage = ratemessage;
    }

    public Float getRatingstars() {
        return ratingstars;
    }

    public void setRatingstars(Float ratingstars) {
        this.ratingstars = ratingstars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }
}
