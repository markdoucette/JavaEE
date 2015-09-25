package ca.rhythmtech.doit.business.reminders.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representation of a ToDo for our application
 *
 * @author Mark Doucette
 */
@Entity
@NamedQuery(name = ToDo.findAll, query = "SELECT t from ToDo t")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDo {
    static final String PREFIX = "reminders.entity.ToDo.";
    public static final String findAll = PREFIX + "findAll";
    @Id
    @GeneratedValue
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
