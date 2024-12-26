package jp.ac.chitose.ir.presentation.views.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jp.ac.chitose.ir.application.service.HelloService;
import jp.ac.chitose.ir.application.service.sample.SampleService;
import jp.ac.chitose.ir.presentation.component.MainLayout;
import jp.ac.chitose.ir.presentation.component.graph.*;

import java.util.*;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@PermitAll
public class  HelloWorldView extends VerticalLayout {

    private HelloService helloService;

    public HelloWorldView(HelloService helloService) {
        this.helloService = helloService;
        H1 title = new H1("IR System & Data Project にようこそ");
        Paragraph p = new Paragraph("ここに表⽰されているものはサンプルです。プログラムの書き⽅と⾒え⽅の参考程度です。みなさんと⼀緒に作っていきます。");
        Button sampleButton = new Button("Button");

        add(title, p, sampleButton);

        sampleButton.addClickListener(event -> {
            /*
            ここに追加
             */
        });
    }


}
