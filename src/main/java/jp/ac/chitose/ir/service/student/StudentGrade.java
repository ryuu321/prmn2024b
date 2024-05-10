package jp.ac.chitose.ir.service.student;

public record StudentGrade(
        String 学籍番号,
        String 科目名,
        String 成績評価,
        int 開講年,
        String 学年
) {
}
