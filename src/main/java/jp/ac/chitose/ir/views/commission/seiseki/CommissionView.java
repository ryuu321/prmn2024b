package jp.ac.chitose.ir.views.commission.seiseki;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.service.commission.CommissionService;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("Commission")
@Route(value = "commission", layout = MainLayout.class)
@PermitAll
public class CommissionView extends VerticalLayout {
    private CommissionService commissionService;

    public CommissionView(CommissionService commissionService){
        this.commissionService = commissionService;

        SeisekiView seisekiView = new SeisekiView(commissionService);
        VerticalLayout seiseki = seisekiView.view();
        add(seiseki);

    }
}
