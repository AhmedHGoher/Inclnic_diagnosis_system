package inclinic.com.classes;

public class CaseAnalysis {

    private String Part;
    private String pain;
    private String Symp;
    private String treat;
    private String Gender;
    private String History;
    private String Inheritance;
    private String Country;
    private String Expected;
    private String Age;
    private String times;
    private int id;

    public CaseAnalysis(int id , String part, String pain, String symp, String treat, String gender, String history, String inheritance, String country, String expected, String age, String times) {
        this.id = id;
        this.Part = part;
        this.pain = pain;
        Symp = symp;
        this.treat = treat;
        Gender = gender;
        History = history;
        Inheritance = inheritance;
        Country = country;
        Expected = expected;
        this.Age = age;
        this.times = times;
    }

    public CaseAnalysis(String part, String pain, String symp, String treat, String gender, String history, String inheritance, String country, String expected, String age, String times) {
        Part = part;
        this.pain = pain;
        Symp = symp;
        this.treat = treat;
        Gender = gender;
        History = history;
        Inheritance = inheritance;
        Country = country;
        Expected = expected;
        Age = age;
        this.times = times;
    }

    public CaseAnalysis(){}

    public String getPart() {
        return Part;
    }


    public void setPart(String part) {
        Part = part;
    }

    public String getPain() {
        return pain;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public String getSymp() {
        return Symp;
    }

    public void setSymp(String symp) {
        Symp = symp;
    }

    public String getTreat() {
        return treat;
    }

    public void setTreat(String treat) {
        this.treat = treat;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHistory() {
        return History;
    }

    public void setHistory(String history) {
        History = history;
    }

    public String getInheritance() {
        return Inheritance;
    }

    public void setInheritance(String inheritance) {
        Inheritance = inheritance;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getExpected() {
        return Expected;
    }

    public void setExpected(String expected) {
        Expected = expected;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}





