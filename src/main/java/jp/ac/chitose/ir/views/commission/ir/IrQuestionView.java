package jp.ac.chitose.ir.views.commission.ir;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.service.sample.SampleService;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("IRアンケート")
@Route(value = "IRアンケート", layout = MainLayout.class)
@PermitAll
public class IrQuestionView extends VerticalLayout {
    private SampleService sampleService;

    public IrQuestionView(SampleService sampleService){
        add(new H1("Commission_IRアンケート"));

        add(new Paragraph("IRアンケートに関する説明文"));

        Ir2024 ir2024 = new Ir2024();
        VerticalLayout ir2024Layout = ir2024.view();
        add(ir2024Layout);

    }
}

