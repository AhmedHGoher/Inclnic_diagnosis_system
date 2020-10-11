package inclinic.com.classes;

public class Questions {


    private int id;
    private String idx;
    private String Q;
    private String T;
    private String F;
    private String A;
    private String B;

    public Questions(String idx, String q, String t, String f, String a, String b) {
        this.idx = idx;
        Q = q;
        T = t;
        F = f;
        A = a;
        B = b;
    }

    public Questions(int id, String idx, String q, String t, String f, String a, String b) {
        this.id = id;
        this.idx = idx;
        Q = q;
        T = t;
        F = f;
        A = a;
        B = b;
    }

    public Questions(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }
}
