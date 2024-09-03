package jp.ac.chitose.ir.application.service.commission;

public record NumberOfUnits(int compulsoryC, int re1C,
                            int re2C, int reEC, int electiveC,
                            int foreigin1, int foreigin2,
                            int allC
) { public int compulsoryC() {return compulsoryC;}
    public int re1C() {return re1C;}
    public int re2C() {return re2C;}
    public int reEC() {return reEC;}
    public int electiveC() {return electiveC;}
    public int foreigin1() {return foreigin1;}
    public int foreigin2() {return foreigin2;}
    public int allC() {return allC;}
}
