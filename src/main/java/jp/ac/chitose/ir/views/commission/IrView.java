package jp.ac.chitose.ir.views.commission;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import jp.ac.chitose.ir.service.sample.SampleService;

import java.util.ArrayList;

public class IrView implements View{
    private SampleService sampleService;

    public IrView(SampleService sampleService){
        this.sampleService = sampleService;
    }
    public VerticalLayout view(){
        VerticalLayout main = new VerticalLayout();

        main.add(new H2("IRアンケート分析結果"));

        main.add(new Paragraph("IRアンケート画面に関する説明文"));

        Select<String> select = new Select<>();
        select.setLabel("質問内容を検索");
        select.setItems("進路希望", "希望職種","学びについての満足度","共通教育での学び（カリキュラム）についての満足度",
        "所属学科での学び（カリキュラム）についての満足度","ためになった科目（共通教育）","ためにならなかった科目（共通教育）","ためになった科目（学科科目）",
        "ためにならなかった科目（学科科目）","授業外学習時間","学科配属の時期"," 学生生活（サークル・学生活動）の満足度","友人関係",
        "設備・環境の満足度","窓口対応","力を入れたいこと","社会で活かしたい力");
        main.add(select);

        ArrayList<VerticalLayout> selectAll = new ArrayList<>();

        VerticalLayout course = new VerticalLayout();//進路希望
        VerticalLayout job = new VerticalLayout();//希望職種
        VerticalLayout learningSatisfaction = new VerticalLayout();//学びについての満足度
        VerticalLayout commonSatisfaction = new VerticalLayout();//共通教育での学び（カリキュラム）についての満足度
        VerticalLayout majorSatisfaction = new VerticalLayout();//所属学科での学び（カリキュラム）についての満足度
        VerticalLayout commonGoodSubject = new VerticalLayout();//ためになった科目（共通教育）
        VerticalLayout commonBadSubject = new VerticalLayout();//ためにならなかった科目（共通教育）
        VerticalLayout majorGoodSubject = new VerticalLayout();//ためになった科目（学科科目）
        VerticalLayout majorBadSubject = new VerticalLayout();//ためにならなかった科目（学科科目）
        VerticalLayout timeOfStudy = new VerticalLayout();//授業外学習時間
        VerticalLayout seasonOfMajor = new VerticalLayout();//学科配属の時期
        VerticalLayout schoolSatisfaction = new VerticalLayout();//学生生活（サークル・学生活動）の満足度
        VerticalLayout friend = new VerticalLayout();//友人関係
        VerticalLayout facilitySatisfaction = new VerticalLayout();//設備・環境の満足度
        VerticalLayout counter = new VerticalLayout();//窓口対応
        VerticalLayout powerOfWant = new VerticalLayout();//力を入れたいこと
        VerticalLayout powerOfSociety = new VerticalLayout();//社会で活かしたい力

        selectAll.add(course);
        selectAll.add(job);
        selectAll.add(learningSatisfaction);
        selectAll.add(commonSatisfaction);
        selectAll.add(majorSatisfaction);
        selectAll.add(commonGoodSubject);
        selectAll.add(commonBadSubject);
        selectAll.add(majorGoodSubject);
        selectAll.add(majorBadSubject);
        selectAll.add(timeOfStudy);
        selectAll.add(seasonOfMajor);
        selectAll.add(schoolSatisfaction);
        selectAll.add(friend);
        selectAll.add(facilitySatisfaction);
        selectAll.add(counter);
        selectAll.add(powerOfWant);
        selectAll.add(powerOfSociety);

        selectAll.forEach(main::add);

//        selectAll.forEach(layout -> layout.setVisible(false));

//        select.addValueChangeListener(e -> {
//            if(e.getValue().equals("全ての質問")){//未解決
//                for(VerticalLayout layout : selectAll){
//                    layout.setVisible(true);
//                }
//            }


        select.addValueChangeListener(e -> course.setVisible(e.getValue().equals("進路希望")));
        select.addValueChangeListener(e -> job.setVisible(e.getValue().equals("希望職種")));
        select.addValueChangeListener(e -> learningSatisfaction.setVisible(e.getValue().equals("学びについての満足度")));
        select.addValueChangeListener(e -> commonSatisfaction.setVisible(e.getValue().equals("共通教育での学び（カリキュラム）についての満足度")));
        select.addValueChangeListener(e -> majorSatisfaction.setVisible(e.getValue().equals("所属学科での学び（カリキュラム）についての満足度")));
        select.addValueChangeListener(e -> commonGoodSubject.setVisible(e.getValue().equals("ためになった科目（共通教育）")));
        select.addValueChangeListener(e -> commonBadSubject.setVisible(e.getValue().equals("ためにならなかった科目（共通教育）")));
        select.addValueChangeListener(e -> majorGoodSubject.setVisible(e.getValue().equals("ためになった科目（学科科目）")));
        select.addValueChangeListener(e -> majorBadSubject.setVisible(e.getValue().equals("ためにならなかった科目（学科科目）")));
        select.addValueChangeListener(e -> timeOfStudy.setVisible(e.getValue().equals("授業外学習時間")));
        select.addValueChangeListener(e -> seasonOfMajor.setVisible(e.getValue().equals("学科配属の時期")));
        select.addValueChangeListener(e -> schoolSatisfaction.setVisible(e.getValue().equals("学生生活（サークル・学生活動）の満足度")));
        select.addValueChangeListener(e -> friend.setVisible(e.getValue().equals("友人関係")));
        select.addValueChangeListener(e -> facilitySatisfaction.setVisible(e.getValue().equals("設備・環境の満足度")));
        select.addValueChangeListener(e -> counter.setVisible(e.getValue().equals("窓口対応")));
        select.addValueChangeListener(e -> powerOfWant.setVisible(e.getValue().equals("力を入れたいこと")));
        select.addValueChangeListener(e -> powerOfSociety.setVisible(e.getValue().equals("社会で活かしたい力")));




        course.add(new H2("進路希望"));
        course.add(new Paragraph("質問内容 : 現時点における希望進路を教えてください。"));

        job.add(new H2("希望職種"));
        job.add(new Paragraph("質問内容 : 現時点における将来の希望職種を教えてください。"));

        learningSatisfaction.add(new H2("学びについての満足度"));
        learningSatisfaction.add(new Paragraph("質問内容 : 本学での入学から今までの学びについて総合的に満足していますか"));

        commonSatisfaction.add(new H2("共通教育での学び（カリキュラム）についての満足度"));
        commonSatisfaction.add(new Paragraph("質問内容 : 共通教育での学び（カリキュラム）について満足していますか。"));

        majorSatisfaction.add(new H2("所属学科での学び（カリキュラム）についての満足度"));
        majorSatisfaction.add(new Paragraph("質問内容 : 所属学科での学び（カリキュラム）について満足していますか。"));

        commonGoodSubject.add(new H2("ためになった科目（共通教育）"));
        commonGoodSubject.add(new Paragraph("質問内容 : 共通教育科目の中で、自分のためになった科目を書いてください。"));

        commonBadSubject.add(new H2("ためにならなかった科目（共通教育）"));
        commonBadSubject.add(new Paragraph("質問内容 : 共通教育科目の中で、自分のためにならなかった科目を書いてください。"));

        majorGoodSubject.add(new H2("ためになった科目（学科科目）"));
        majorGoodSubject.add(new Paragraph("質問内容 : 学科科目の中で、自分のためになった科目を書いてください"));

        majorBadSubject.add(new H2("ためにならなかった科目（学科科目）"));
        majorBadSubject.add(new Paragraph("質問内容 : 学科科目の中で、自分のためにならなかった科目を書いてください。"));

        timeOfStudy.add(new H2("授業外学習時間"));
        timeOfStudy.add(new Paragraph("質問内容 : 授業の予習・復習などの授業外学習（全ての授業）に1週間あたり平均何時間取り組みましたか。整数を半角で入力してください。"));

        seasonOfMajor.add(new H2("学科配属の時期"));
        seasonOfMajor.add(new Paragraph("質問内容 : 適切だと思う学科配属の時期について聞かせてください。"));

        schoolSatisfaction.add(new H2("学生生活（サークル・学生活動）の満足度"));
        schoolSatisfaction.add(new Paragraph("質問内容 : 授業以外の学生生活（サークル・学生活動）について満足していますか。"));

        friend.add(new H2("友人関係"));
        friend.add(new Paragraph("質問内容 : 大学時代に親しい友人を得ることができましたか。"));

        facilitySatisfaction.add(new H2("設備・環境の満足度"));
        facilitySatisfaction.add(new Paragraph("本学の設備・環境には満足していますか。"));

        counter.add(new H2("窓口対応"));
        counter.add(new Paragraph("質問内容 : 本学の事務職員の窓口対応は適切ですか。"));

        powerOfWant.add(new H2("力を入れたいこと"));
        powerOfWant.add(new Paragraph("質問内容 : 今後、本学での学びで力を入れたいことを選んでください（複数回答可）。"));

        powerOfSociety.add(new H2("社会で生かしたい力"));
        powerOfSociety.add(new Paragraph("質問内容 : 将来、社会で活かしたい力はどれでしょうか（複数回答可）"));






        return main;
    }
}
