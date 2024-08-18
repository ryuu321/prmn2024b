package jp.ac.chitose.ir.presentation.views.commission.university;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.commission.GradeService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.views.commission.university.components.BackButton;
import jp.ac.chitose.ir.presentation.views.commission.university.components.SelectButton;
import jp.ac.chitose.ir.presentation.views.commission.university.layouts.annual.teacherTraining.TeacherTraining;
import jp.ac.chitose.ir.presentation.views.commission.university.layouts.classwork.GraduationCredits.GraduationCredits;
import jp.ac.chitose.ir.presentation.views.commission.university.layouts.people.numberOfStudents.NumberOfStudents;

import java.util.ArrayList;

@PageTitle("University")
@Route(value = "university", layout = MainLayout.class)
@PermitAll
public class UniversityView extends VerticalLayout {
    private GradeService gradeService;
    private BackButton backButton;
    private VerticalLayout mainLayout;
    private RadioButtonGroup<String> category;
    private ArrayList<ArrayList<Button>> buttons;
    private  ArrayList<Button> classwork;
    private  ArrayList<Button> course;
    private ArrayList<Button> exam;
    private ArrayList<Button> people;
    private ArrayList<Button> annualReport;
    private ArrayList<VerticalLayout> layouts;
    private FormLayout buttonLayout;
    public UniversityView(GradeService gradeService) {

        this.gradeService = gradeService;

        mainLayout = new VerticalLayout();
        add(mainLayout);

        mainLayout.add(new H1("大学情報"));
        mainLayout.add(new Paragraph("説明"));

        //backButtonを追加
        backButton = new BackButton();
        backButton.setVisible(false);
        add(backButton);

        //カテゴリ別ラジオボタンを追加
        category = new RadioButtonGroup<>();
        category.setItems("授業","進路","入試","人数","年報");
        mainLayout.add(category);
        selectRadio();
        
        buttons = new ArrayList<>();
        classwork = new ArrayList<>();
        buttons.add(classwork);
        course = new ArrayList<>();
        buttons.add(course);
        exam = new ArrayList<>();
        buttons.add(exam);
        people = new ArrayList<>();
        buttons.add(people);
        annualReport = new ArrayList<>();
        buttons.add(annualReport);

        layouts = new ArrayList<>();

        buttonLayout = new FormLayout();
        buttonLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1),
                new FormLayout.ResponsiveStep("300px",2),
                new FormLayout.ResponsiveStep("600px",3),
                new FormLayout.ResponsiveStep("900px",4)
        );
        mainLayout.add(buttonLayout);

        //各レイアウトのボタン、レイアウトを追加
        //人数に関するボタン
        //教員数
//        VerticalLayout numberOfTeachers = new NumberOfTeachers();
//        setLayout(numberOfTeachers,"教員数",people);
//        add(numberOfTeachers);

        //学生数
        VerticalLayout numberOfStudents = new NumberOfStudents(gradeService);
        setLayout(numberOfStudents,"学生数",people);
        add(numberOfStudents);

        //学部生と大学院生の比率
//        VerticalLayout studentRatio = new StudentRatio();
//        setLayout(studentRatio,"学部生と大学院生の比率",people);
//        add(studentRatio);

        //教員と学生の比率
//        VerticalLayout teacherStudentRatio = new TeacherStudentRatio();
//        setLayout(teacherStudentRatio,"教員と学生の比率",people);
//        add(teacherStudentRatio);

        //外国人教員数
//        VerticalLayout foreignTeacher = new ForeignTeacher();
//        setLayout(foreignTeacher,"外国人教員数",people);
//        add(foreignTeacher);

        //社会人学生数
//        VerticalLayout workingAdultStudent =new WorkingAdultStudent();
//        setLayout(workingAdultStudent,"社会人学生数",people);
//        add(workingAdultStudent);

        //休学者数
//        VerticalLayout leaveOfAbsence = new LeaveOfAbsence();
//        setLayout(leaveOfAbsence,"休学者数",people);
//        add(leaveOfAbsence);

        //退学、除籍者数
//        VerticalLayout dropoutOrExpelled = new DropoutOrExpelled();
//        setLayout(dropoutOrExpelled,"退学、除籍者数",people);
//        add(dropoutOrExpelled);


        //入試に関するボタン
        //入学定員
//        VerticalLayout enrollmentCapacity = new EnrollmentCapacity();
//        setLayout(enrollmentCapacity,"入学定員",exam);
//        add(enrollmentCapacity);


        //授業に関するボタン
        //外国語科目数
//        VerticalLayout numberOFForeignLanguageClass = new NumberOfForeignLanguageClass();
//        setLayout(numberOFForeignLanguageClass,"外国語科目数",classwork);
//        add(numberOFForeignLanguageClass);

        //アクティブラーニング実施率
//        VerticalLayout activeLearning = new ActiveLearning();
//        setLayout(activeLearning,"アクティブラーニング実施率",classwork);
//        add(activeLearning);

        //卒業単位数
        VerticalLayout graduationCredits = new GraduationCredits();
        setLayout(graduationCredits,"卒業単位数",classwork);
        add(graduationCredits);

        //大学年報
        VerticalLayout teacherTraining = new TeacherTraining();
        setLayout(teacherTraining,"教職課程",annualReport);
        add(teacherTraining);

        backButton.addClickListener(e -> {
            for (VerticalLayout layout : layouts) {
                layout.setVisible(false);
            }
            mainLayout.setVisible(true);
            backButton.setVisible(false);
        });
    }

    private void setLayout(VerticalLayout layout, String name, ArrayList<Button> buttons) {
        layout.setVisible(false);
        SelectButton sR = new SelectButton(name,layout,mainLayout,backButton);
        sR.setVisible(false);
        buttonLayout.add(sR);
        buttons.add(sR);
        layouts.add(layout);
    }

    private void selectRadio(){
        category.addValueChangeListener(e -> {
            if(e.getValue().equals("授業")){
                deleteAll();
                for(Button button : classwork) {
                    button.setVisible(true);
                }
            }
            else if(e.getValue().equals("進路")){
                deleteAll();
                for(Button button : course) {
                    button.setVisible(true);
                }
            }
            else if(e.getValue().equals("入試")){
                deleteAll();
                for(Button button : exam) {
                    button.setVisible(true);
                }
            } else if(e.getValue().equals("人数")) {
                deleteAll();
                for(Button button : people) {
                    button.setVisible(true);
                }
            }
            else if(e.getValue().equals("年報")){
                deleteAll();
                for(Button button : annualReport) {
                    button.setVisible(true);
                }
            }
        });
    }
    private void deleteAll(){
        for(ArrayList<Button> buttonAndLayout : buttons) {
                for(Button button : buttonAndLayout) {
                    button.setVisible(false);
                }
            }
    }

}
