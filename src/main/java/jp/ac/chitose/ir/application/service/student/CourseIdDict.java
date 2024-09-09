package jp.ac.chitose.ir.application.service.student;

import java.util.List;
import java.util.Map;

public record CourseIdDict (
        Map<String, List<String>> courseIdDict
) {
}
