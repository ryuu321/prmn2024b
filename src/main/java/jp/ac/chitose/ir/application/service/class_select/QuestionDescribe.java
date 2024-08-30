package jp.ac.chitose.ir.application.service.class_select;

import com.vaadin.flow.component.html.H6;

import java.util.List;

public class QuestionDescribe {

    private final ClassSelect classSelect;
    public QuestionDescribe(ClassSelect classSelect) {
        this.classSelect = classSelect;
    }
    private H6 stat(int questionNum ,String subject_id){
        var statics = classSelect.getReviewDescribe(subject_id).data().get(0);
        List resultList = null;
        switch (questionNum){
            case 4-> resultList = statics.q4();
            case 5-> resultList = statics.q5();
            case 6-> resultList = statics.q6();
            case 7-> resultList = statics.q7();
            case 8-> resultList = statics.q8();
            case 9-> resultList = statics.q9();
            case 10-> resultList = statics.q10();
            case 11-> resultList = statics.q11();
            case 12-> resultList = statics.q12();
            case 13-> resultList = statics.q13();
            case 14-> resultList = statics.q14();
            case 15-> resultList = statics.q15();
            case 16-> resultList = statics.q16();
        }
        String formatted = "要素数:"+ resultList.get(0) +", 平均:"+resultList.get(1)+", 分散:"+ String.format("%.2f",resultList.get(2))+", 最小値:"+resultList.get(3)+", 25%:"+ resultList.get(4)+", 50%:"+resultList.get(5)+", 75%:"+resultList.get(6)+", 最大値:"+resultList.get(7);
        return new H6(formatted);
    }

    public H6 getStatics(int questionnum,String subject_id){
        return stat(questionnum,subject_id);
    }

}
