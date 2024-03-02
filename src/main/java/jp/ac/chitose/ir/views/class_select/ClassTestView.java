package jp.ac.chitose.ir.views.class_select;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jp.ac.chitose.ir.service.class_select.ClassSelect;
import jp.ac.chitose.ir.views.MainLayout;

@PageTitle("class_test")
@Route(value = "class_select/test", layout = MainLayout.class)
public class ClassTestView extends VerticalLayout {

    private ClassSelect classSelect;
    public ClassTestView() {
        this.classSelect = classSelect;

        H1 title = new H1("test");
        add(title);
    }
}
