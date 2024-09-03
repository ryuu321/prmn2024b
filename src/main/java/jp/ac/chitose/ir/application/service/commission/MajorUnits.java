package jp.ac.chitose.ir.application.service.commission;

public record MajorUnits(String major, int compulsoryM,
                         int reM, int electiveM, int allM,
                         int others, int all
) { public String major() {return major;}
    public int compulsoryM() {return compulsoryM;}
    public int reM() {return reM;}
    public int electiveM() {return electiveM;}
    public int allM() {return allM;}
    public int others() {return others;}
    public int all() {return all;}
}
