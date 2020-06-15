package judgev2.demo.domain.model.service;

import judgev2.demo.domain.entity.Exercise;
import judgev2.demo.domain.entity.User;

import java.time.LocalDateTime;

public class HomeworkServiceModel extends BaseServiceModel{
    private LocalDateTime addedOn;
    private String gitAddress;
    private User author;
    private Exercise exercise;

    public HomeworkServiceModel() {
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public String getGitAddress() {
        return gitAddress;
    }

    public void setGitAddress(String gitAddress) {
        this.gitAddress = gitAddress;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
