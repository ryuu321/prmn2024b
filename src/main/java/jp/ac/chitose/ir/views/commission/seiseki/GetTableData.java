package jp.ac.chitose.ir.views.commission.seiseki;

public class GetTableData {
    private String subject;
    private int human;
    private float average;
    private float mid;
    private float min;
    private float max;
    private float std;

    public GetTableData(String subject,int human,float average,float mid,float min,float max,float std){
        this.subject = subject;
        this.human = human;
        this.average = average;
        this.mid = mid;
        this.min = min;
        this.max = max;
        this.std = std;
    }
    public String subject(){
        return subject;
    }
    public int human(){
        return human;
    }
    public float average(){
        return average;
    }
    public float mid(){
        return mid;
    }
    public float min(){
        return min;
    }
    public float max(){
        return max;
    }
    public float std(){
        return std;
    }
}
