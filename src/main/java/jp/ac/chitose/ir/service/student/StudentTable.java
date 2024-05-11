package jp.ac.chitose.ir.service.student;

public record StudentTable(
        String 学籍番号,
        String 科目名,
        String 対象学科,
        int 対象学年,
        String 必選別,
        String 成績評価,
        int 科目の単位数
) {
}
