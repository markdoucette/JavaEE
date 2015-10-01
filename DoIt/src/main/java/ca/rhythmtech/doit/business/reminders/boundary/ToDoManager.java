package ca.rhythmtech.doit.business.reminders.boundary;

import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Hard Boundary for our TodoResource
 *
 * @author Mark Doucette
 */
@Stateless
public class ToDoManager {
    private static final int PRIORITY_HIGH = 100;

    @PersistenceContext(unitName = "prod")
    EntityManager em;

    /**
     * Find a specific ToDo by it's id
     *
     * @param id The id of the ToDo to find
     * @return The ToDo matching the requested id
     */
    public ToDo findTodoById(long id) {
        return em.find(ToDo.class, id);
    }

    /**
     * Find all ToDo's and return as a List
     *
     * @return List of all ToDo's
     */
    public List<ToDo> all() {
        return em.createNamedQuery("ToDo.findAll", ToDo.class).getResultList();
    }

    /**
     * Save a new ToDo to the data store
     *
     * @param todo the ToDo to be saved
     */
    public ToDo save(ToDo todo) {
        return em.merge(todo);
    }

    /**
     * Delete a ToDo by id
     *
     * @param id The id of the ToDo to delete
     */
    public void delete(long id) {
        try {
            ToDo reference = em.getReference(ToDo.class, id);
            em.remove(reference);
        } catch (EntityNotFoundException e) {
            // ok to ignore because we wanted to delete it anyway.
        }
    }

    public ToDo updateStatus(long id, boolean doneStatus) {
        ToDo todo = this.findTodoById(id);
        if (todo == null) {
            return null;
        }
        todo.setDone(true);
        return todo;
    }
}
