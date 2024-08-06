package jp.ac.chitose.ir.presentation.views.usermanagement;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import jp.ac.chitose.ir.presentation.component.MainLayout;

@PageTitle("UserDelete")
@Route(value = "/user_management/delete", layout = MainLayout.class)
@RolesAllowed({"administrator"})
public class UserDeleteView extends VerticalLayout {

}
