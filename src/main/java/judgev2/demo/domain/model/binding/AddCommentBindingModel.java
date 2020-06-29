package judgev2.demo.domain.model.binding;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

public class AddCommentBindingModel {

    private int score;
    private String textContent;
    private String homeworkId;

    public AddCommentBindingModel() {
    }

    @Min(value = 2, message = "score must be btw 2 and 6")
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }
    @Length(min = 3, message = "Length must be at least 3")
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
