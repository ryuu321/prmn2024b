package jp.ac.chitose.ir.application.service.commission;
//卒業単位：専門
public record MajorUnits(String academic_category, int loan_type,
                         int reservation, int enrollment, int emergency_support,
                         int total
) {
}
