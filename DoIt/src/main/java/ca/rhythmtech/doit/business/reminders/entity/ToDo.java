package ca.rhythmtech.doit.business.reminders.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Representation of a ToDo for our application
 *
 * @author Mark Doucette
 */
@Entity
@NamedQuery(name = "ToDo.findAll", query = "SELECT t from ToDo t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDo {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Size(min = 1, max = 256) // restrain the caption input
    private String caption;

    private String description;
    private int priority;
    private LocalDate dueDate;
    private boolean done;

    @Version // enable optimistic locking
    private long version;

    public ToDo() {
        this.dueDate = LocalDate.ofYearDay(2015, 300);
    }

    public ToDo(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
        this.dueDate = LocalDate.ofYearDay(2015, 300);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
