package ca.rhythmtech.doit.business.reminders.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representation of a ToDo for our application
 * @author Mark Doucette
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDo {
    private long id;
    private String caption;
    private String description;
    private int priority;

    public ToDo() {
    }

    public ToDo(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }
}
