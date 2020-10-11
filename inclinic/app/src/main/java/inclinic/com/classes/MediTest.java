package inclinic.com.classes;

public class MediTest {

    private int id;
    private String MTname;
    private String Tcode;
    private String Frange;
    private String Lrange;
    private String Low;
    private String High;

    public MediTest(String mTname, String tcode, String frange, String lrange, String low, String high) {
        this.MTname = mTname;
        Tcode = tcode;
        Frange = frange;
        Lrange = lrange;
        Low = low;
        High = high;
    }

    public MediTest(int id, String mTname, String tcode, String frange, String lrange, String low, String high) {
        this.id = id;
        this.MTname = mTname;
        Tcode = tcode;
        Frange = frange;
        Lrange = lrange;
        Low = low;
        High = high;
    }

    public MediTest(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMTname() {
        return MTname;
    }

    public void setMTname(String mTname) {
        this.MTname = mTname;
    }

    public String getTcode() {
        return Tcode;
    }

    public void setTcode(String tcode) {Tcode = tcode; }

    public String getFrange() {
        return Frange;
    }

    public void setFrange(String frange) {
        Frange = frange;
    }

    public String getLrange() {
        return Lrange;
    }

    public void setLrange(String lrange) {
        Lrange = lrange;
    }

    public String getLow() {
        return Low;
    }

    public void setLow(String low) {
        Low = low;
    }

    public String getHigh() {
        return High;
    }

    public void setHigh(String high) {
        High = high;
    }


}
