package jp.ac.chitose.ir.application.service.questionnaire;

public record QuestionnaireTopGrid(
        String 科目名,
        String 開講年,
        int 対象学年,
        String 対象学科,
        String 必選別,
        String 単位数
){
}
