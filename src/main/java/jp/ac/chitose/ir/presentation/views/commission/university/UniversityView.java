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
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.views.commission.university.components.BackButton;
import jp.ac.chitose.ir.presentation.views.commission.university.components.SelectButton;
import jp.ac.chitose.ir.presentation.views.commission.university.layouts.people.*;

import java.util.ArrayList;

@PageTitle("University")
@Route(value = "university", layout = MainLayout.class)
@PermitAll
public class UniversityView extends VerticalLayout {
    private BackButton backButton;
    private VerticalLayout mainLayout;
    private RadioButtonGroup<String> category;
    private ArrayList<ArrayList<Button>> buttons;
    private  ArrayList<Button> classwork;
    private  ArrayList<Button> course;
    private ArrayList<Button> exam;
    private ArrayList<Button> people;
    private ArrayList<VerticalLayout> layouts;
    private FormLayout buttonLayout;
    public UniversityView() {
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
        category.setItems("授業","進路","入試","人数");
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
        //教員数
        VerticalLayout numberOfTeachers = new NumberOfTeachers();
        setLayout(numberOfTeachers,"教員数",people);
        add(numberOfTeachers);

        //学生数
        VerticalLayout numberOfStudents = new NumberOfStudents();
        setLayout(numberOfStudents,"学生数",people);
        add(numberOfStudents);

        //学部生と大学院生の比率
        VerticalLayout studentRatio = new StudentRatio();
        setLayout(studentRatio,"学部生と大学院生の比率",people);
        add(studentRatio);

        //教員と学生の比率
        VerticalLayout teacherStudentRatio = new TeacherStudentRatio();
        setLayout(teacherStudentRatio,"教員と学生の比率",people);
        add(teacherStudentRatio);

        //外国人教員数
        VerticalLayout foreignTeacher = new ForeignTeacher();
        setLayout(foreignTeacher,"外国人教員数",people);
        add(foreignTeacher);


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