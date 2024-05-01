package jp.ac.chitose.ir.views.student;

import com.github.appreciated.apexcharts.ApexCharts;
import jp.ac.chitose.ir.service.student.StudentGrade;
import jp.ac.chitose.ir.service.student.StudentService;
import jp.ac.chitose.ir.views.component.Graph;

public interface GraphPattern {
    ApexCharts create(StudentService studentService, String schoolYear, String department);
}
