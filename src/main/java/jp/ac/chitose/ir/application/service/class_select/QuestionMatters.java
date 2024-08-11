package jp.ac.chitose.ir.application.service.class_select;

import com.vaadin.flow.component.html.H3;
import jp.ac.chitose.ir.presentation.component.scroll.ScrollManager;

public class QuestionMatters {
    private ClassSelect classSelect;
    private ScrollManager scrollManager;
    public QuestionMatters(ClassSelect classSelect, ScrollManager scrollManager){
        this.classSelect = classSelect;
        this.scrollManager = scrollManager;
    }

    private H3 title(int i, String subject_id) {
        var Classtitle = classSelect.getReviewTitle(subject_id).data();
        String Title = String.valueOf(Classtitle.get(i));

        if (Title != null && Title.length() > 0) {
            String newTitle = Title.substring(0, Title.length() - 1);
            String[] parts = newTitle.split("=");
            StringBuilder num = new StringBuilder();
            num.append("Q").append(i+4).append(":");
            H3 title = new H3(num + parts[1]);
            String id = "title-" + i+4;
            title.setId(id);
            scrollManager.add(title, id);
            return title;
        }
        return null;
    }

    public H3 generateQuestionMatters(int i,String subject_id){

        return title(i,subject_id);
    }
}

