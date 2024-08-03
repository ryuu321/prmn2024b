package jp.ac.chitose.ir.presentation.views.commission.seiseki;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.commission.CommissionService;
import jp.ac.chitose.ir.application.service.commission.GradeService;
import jp.ac.chitose.ir.presentation.component.MainLayout;

@PageTitle("Commission")
@Route(value = "commission", layout = MainLayout.class)
@PermitAll
public class CommissionView extends VerticalLayout {
    private CommissionService commissionService;
    private GradeService gradeService;

    public CommissionView(GradeService gradeService,CommissionService commissionService){
        this.commissionService = commissionService;
        this.gradeService = gradeService;

        SeisekiView seisekiView = new SeisekiView(commissionService,gradeService);
        VerticalLayout seiseki = seisekiView.view();
        add(seiseki);

    }
}
