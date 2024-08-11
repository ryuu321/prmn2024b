package jp.ac.chitose.ir.presentation.component;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import jp.ac.chitose.ir.application.service.management.SecurityService;
import jp.ac.chitose.ir.presentation.views.class_select.QPOJFICHKVJBView;
import jp.ac.chitose.ir.presentation.views.commission.ir.IrQuestionView;
import jp.ac.chitose.ir.presentation.views.commission.seiseki.CommissionView;
import jp.ac.chitose.ir.presentation.views.commission.university.UniversityView;
import jp.ac.chitose.ir.presentation.views.questionnaire.QuestionnaireTopView;
import jp.ac.chitose.ir.presentation.views.student.StudentView;
import jp.ac.chitose.ir.presentation.views.top.TopView;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIcon;


/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

    }

    private SecurityService securityService;
    public MainLayout(@Autowired SecurityService securityService) {
//        addToNavbar(createHeaderContent());
//        setDrawerOpened(false);

        this.securityService = securityService;

        if (securityService.getAuthenticatedUser() != null){
            addToNavbar(createHeaderContent());
            setDrawerOpened(false);
        }else {
            H5 username = new H5(securityService.getLoginUser().getUsername() + " ");
            HorizontalLayout header;
            header = new HorizontalLayout(username);
            addToNavbar(header);
        }
        // addToNavbar(header);
    }


    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("CIST IR-Web");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);

        H5 username = new H5(securityService.getLoginUser().getUsername() + "　");
        Button logout = new Button("ログアウト", click -> securityService.logout());

        layout.add(appName,username, logout);

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);

        }

//        Button logout = new Button("Logout", click -> securityService.logout());

        header.add(layout, nav);
        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Top", LineAwesomeIcon.GLOBE_SOLID.create(), TopView.class), //
                new MenuItemInfo("成績情報", LineAwesomeIcon.ACCESSIBLE_ICON.create(), StudentView.class),//
                new MenuItemInfo("成績統計",LineAwesomeIcon.ANGLE_DOUBLE_DOWN_SOLID.create(), CommissionView.class),//
                new MenuItemInfo("アンケート", LineAwesomeIcon.CHART_BAR.create(), QuestionnaireTopView.class),//
                new MenuItemInfo("IRアンケート",LineAwesomeIcon.FILE_ALT.create(), IrQuestionView.class),//
                new MenuItemInfo("Teacher", LineAwesomeIcon.CHART_AREA_SOLID.create(), QPOJFICHKVJBView.class),//
                new MenuItemInfo("大学情報",LineAwesomeIcon.CHALKBOARD_TEACHER_SOLID.create(), UniversityView.class),//
        };
    }

}
