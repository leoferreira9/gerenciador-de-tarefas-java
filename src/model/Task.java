package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    private int id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private TaskStatus status;

    public Task(String title, String description, LocalDate dueDate, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "\nID: " + getId() +
                "\nTítulo: " + getTitle() +
                "\nDescrição: " + getDescription() +
                "\nStatus: " + getStatus() +
                "\nNível de prioridade: " + getPriority() +
                "\nData limite: " + getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
