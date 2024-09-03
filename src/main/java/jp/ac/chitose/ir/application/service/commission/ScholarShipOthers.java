package jp.ac.chitose.ir.application.service.commission;

public record ScholarShipOthers(String name,
                                String type,
                                String academictype,
                                int number
) { public String name() { return name; }
    public String type() { return type;}
    public String academictype() { return academictype;}
    public int number() { return number;}
}
