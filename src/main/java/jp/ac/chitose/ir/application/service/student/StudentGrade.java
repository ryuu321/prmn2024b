package jp.ac.chitose.ir.application.service.student;

public record StudentGrade(
        String course_id,
        String pre_year_course_id,
        String lecture_name,
        String grading,
        int target_grade,
        String target_department,
        String compulsory_subjects,
        int number_credits_course
) {

    public String schoolYear() {
        switch (target_grade()) {
            case 1:
                return "1年生";
            case 2:
                return "2年生";
            case 3:
                return "3年生";
            case 4:
                return "4年生";
            default:
                return String.valueOf(target_grade());
        }
    }

    public String department() {
        if ("理工学部 情報ｼｽﾃﾑ工学科".equals(target_department())) return "情報システム工学科";
        return target_department();
    }
}
