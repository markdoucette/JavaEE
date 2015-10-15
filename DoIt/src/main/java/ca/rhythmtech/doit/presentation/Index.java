package ca.rhythmtech.doit.presentation;

import ca.rhythmtech.doit.business.reminders.boundary.ToDoManager;
import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by mdoucette on 14/10/15.
 */
@Model
public class Index {
    @Inject
    ToDoManager boundary;

    ToDo todo;

    @PostConstruct
    public void init() {
        this.todo = new ToDo();
    }

    public ToDo getTodo() {
        return todo;
    }

    /**
     * Save a ToDo with the jsf action bound to the boundary method
     * @return null - no url
     */
    public Object save() {
        this.boundary.save(todo);
        return null;
    }

    public List<ToDo> getToDos() {
        return boundary.all();
    }
}
