package jp.ac.chitose.ir.application.service.commission;

public record ScholarShipJasso(String academictype,
                               String type,
                               int reserve,
                               int enrollment,
                               int emergency,
                               int all
) {
    public String academictype() {return academictype;}
    public String type() {return type;}
    public int reserve() {return reserve;}
    public int enrollment() {return enrollment;}
    public int emergency() {return emergency;}
    public int all() {return all;}

}
