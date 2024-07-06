package jp.ac.chitose.ir.presentation.views.commission.subject;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class SubjectV {

    private SubjectButton subjectButton;
    private SubjectGraphView subjectGraphView;
    public SubjectV(){
        subjectButton=new SubjectButton();
        subjectGraphView=new SubjectGraphView(subjectButton);
    }

    public VerticalLayout view(){
        VerticalLayout main = new VerticalLayout();
        main.add(subjectButton.view());
        main.add(subjectGraphView.view());
        subjectButton.getYear().addValueChangeListener(e->{subjectButton.getSelect().setVisible(!(e.getValue().equals("1年生")));
                                                           subjectButton.getSelect2().setVisible(e.getValue().equals("1年生"));});


        return main;
    }


}
