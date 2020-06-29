package judgev2.demo.domain.model.service;

import judgev2.demo.domain.entity.Homework;
import judgev2.demo.domain.entity.User;

public class CommentServiceModel extends BaseServiceModel{
    private Integer score;
    private String textContent;
    private User author;
    private Homework homework;

    public CommentServiceModel() {
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }
}
