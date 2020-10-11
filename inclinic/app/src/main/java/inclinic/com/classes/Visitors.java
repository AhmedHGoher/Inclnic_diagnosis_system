package inclinic.com.classes;

public class Visitors {

    private String Type;
    private String Result;

    public Visitors(String type, String result){
        Type = type;
        Result = result;
    }
    public Visitors(){}

    public String getResult() {
        return Result;
    }

    public String getType() {
        return Type;
    }

    public void setResult(String result) {
        Result = result;
    }

    public void setType(String type) {
        Type = type;
    }
}
