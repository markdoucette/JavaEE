package ca.rhythmtech.doit.business.reminders.boundary;

import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Hard Boundary for our TodoResource
 * @author Mark Doucette
 */
@Stateless
public class TodoManager {
    private static final int PRIORITY_HIGH = 100;

    @PersistenceContext(unitName = "prod")
    EntityManager em;

    /**
     * Find a specific ToDo by it's id
     *
     * @param id The id of the ToDo to find
     * @return The ToDo matching the requested id
     */
    public ToDo findById(int id) {
        return em.find(ToDo.class, id);
    }

    /**
     * Delete a ToDo by id
     * @param id The id of the ToDo to delete
     */
    public void delete(int id) {
        ToDo reference = em.getReference(ToDo.class, id);
        em.remove(reference);
    }

    /**
     * Find all ToDo's and return as a List
     * @return List of all ToDo's
     */
    public List<ToDo> findAll() {
        return em.createNamedQuery(ToDo.findAll, ToDo.class).getResultList();
    }

    /**
     * Save a new ToDo to the data store
     * @param toDo the ToDo to be saved
     */
    public void save(ToDo toDo) {
        em.merge(toDo);
    }
}
