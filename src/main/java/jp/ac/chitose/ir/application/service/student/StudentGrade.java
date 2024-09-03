package jp.ac.chitose.ir.application.service.student;

public record StudentGrade(
        String 学籍番号,
        String 科目名,
        String 成績評価,
        int 開講年,
        String 学年,
        int 対象学年,
        String 対象学科,
        String 必選別,
        int 科目の単位数
) {

    public String schoolYear() {
        switch (対象学年()) {
            case 1:
                return "1年生";
            case 2:
                return "2年生";
            case 3:
                return "3年生";
            case 4:
                return "4年生";
            default:
                return String.valueOf(対象学年());
        }
    }

    public String department() {
        if ("理工学部 情報ｼｽﾃﾑ工学科".equals(対象学科())) return "情報システム工学科";
        return 対象学科();
    }
}
