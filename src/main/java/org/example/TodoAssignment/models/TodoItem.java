package org.example.TodoAssignment.models;

import org.example.TodoAssignment.sequencers.TodoItemIdSequencer;

import java.time.LocalDate;
import java.util.Objects;

public class TodoItem {
    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Person creator;

    public TodoItem(String title, String description, LocalDate deadLine, boolean done) {
        setTitle(title);
        setDescription(description);
        setDeadLine(deadLine);
        setDone(done);
    }

    public TodoItem(String title, String description, LocalDate deadLine, boolean done, Person creator) {
        this(title, description, deadLine, done);
        setCreator(creator);
    }

    public TodoItem(int id, String title, String description, LocalDate deadLine, boolean done, Person creator) {
        this(title, description, deadLine, done);
        this.id = id;
        setCreator(creator);
    }

    public TodoItem(int id, String title, String description, LocalDate deadLine, boolean done) {
        this(title, description, deadLine, done);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be Null or Empty");

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be Null or Empty");

        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        if (deadLine == null || deadLine.toString().trim().isEmpty())
            throw new IllegalArgumentException("Deadline cannot be Null or Empty");

        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public boolean isOverdue() {
        return getDeadLine().isBefore(LocalDate.now());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return getId() == todoItem.getId() && isDone() == todoItem.isDone() && Objects.equals(getTitle(), todoItem.getTitle()) && Objects.equals(getDescription(), todoItem.getDescription()) && Objects.equals(getDeadLine(), todoItem.getDeadLine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getDeadLine(), isDone());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("--------------------------------").append("\n").append("Todo id: ").append(getId()).append("\n")
                .append("Todo name: ").append(getTitle()).append("\n")
                .append("Description: ").append(getDescription()).append("\n")
                .append("The deadline: ").append(getDeadLine()).append("\n")
                .append("Is done status: ").append(isDone()).append("\n");
        if (getCreator() == null || getCreator().getId() == 0) {
            sb.append("No assigned to anyone").append("\n");
        } else {
            sb.append("Assigned to id: ").append(getCreator().getId()).append("\n");
        }

        return sb.toString();
    }

}
