package jp.ac.chitose.ir.application.service.student;

public record Target(
        String question_1,
        String answer_1_1
) {
    public String question() {
        return question_1;
    }

    public String answer() {
        return answer_1_1;
    }
}