package jp.ac.chitose.ir.views.commission.seiseki;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
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

        H1 title = new H1("Commission");
//        add(title);

//        add(new Paragraph("Commission全体の説明文"));

        Select<String> select = new Select<>();
        select.setLabel("分析内容を検索");
        select.setItems("成績情報", "IRアンケート");
//        add(select);

        VerticalLayout layout = new VerticalLayout();
        Paragraph bar = new Paragraph("ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー");
        layout.setAlignItems(Alignment.STRETCH);
        layout.add(bar);
//        add(layout);



        SeisekiView seisekiView = new SeisekiView(commissionService);
        VerticalLayout seiseki = seisekiView.view();
        add(seiseki);
//        seiseki.setVisible(false);

//        IrView irView = new IrView(commissionService);
//        VerticalLayout ir = irView.view();
//        add(ir);
//        ir.setVisible(false);

//        select.addValueChangeListener(e -> seiseki.setVisible(e.getValue().equals("成績情報")));
//        select.addValueChangeListener(e -> ir.setVisible(e.getValue().equals("IRアンケート")));
    }
}
