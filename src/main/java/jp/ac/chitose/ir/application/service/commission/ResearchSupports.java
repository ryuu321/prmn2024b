package jp.ac.chitose.ir.application.service.commission;

public record ResearchSupports(String type, int number) {
    public String type(){return type;}
    public int number(){return number;}
}
