package jp.ac.chitose.ir.application.service.commission.commission_subject;

public record Subject(
        int 開講年,
        String 科目名,
        String 学科名,
        float 秀,
        float 優,
        float 良,
        float 可,
        float 不可,
        float 欠席
) {
    public int years(){return 開講年();}
    public String subjects(){return 科目名();}
    public String majors(){return 学科名();}
    public float S(){return 秀();}
    public float A(){
        return 優();
    }
    public float B(){
        return 良();
    }
    public float C(){
        return 可();
    }
    public float D(){return 不可();}
    public float E(){return 欠席();}
}
