package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.commission.GradeService;
import jp.ac.chitose.ir.presentation.component.MainLayout;

@PageTitle("Commission")
@Route(value = "commission", layout = MainLayout.class)
@PermitAll
public class CommissionView extends VerticalLayout {

    private GradeService gradeService;

    public CommissionView(GradeService gradeService){

        this.gradeService = gradeService;

        SeisekiView seisekiView = new SeisekiView(gradeService);
        VerticalLayout seiseki = seisekiView.view();
        add(seiseki);

    }
}
