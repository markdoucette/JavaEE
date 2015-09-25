package ca.rhythmtech.doit.business.reminders.boundary;

import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest resource for the ToDo's
 *
 * @author Mark Doucette
 */
@Stateless
@Path("todos")
public class TodosResource {
    /**
     * Inject the TodoManager
     */
    @Inject
    TodoManager manager;

    /**
     * Find a specific ToDo by it's id
     *
     * @param id The id of the ToDo to find
     * @return The ToDo matching the requested id
     */
    @GET
    @Produces("application/json")
    @Path("{id}")
    public ToDo find(@PathParam("id") int id) {
        return manager.findById(id);
    }

    /**
     * Find all ToDo's and return as a List
     * @return List of all ToDo's
     */
    @GET
    @Produces("application/json")
    public List<ToDo> all() {
        return manager.findAll();
    }

    /**
     * Save a new ToDo to the data store
     * @param toDo the ToDo to be saved
     */
    @POST
    public void save(ToDo toDo) {
        manager.save(toDo);
    }

    /**
     * Delete a ToDo by id
     * @param id The id of the ToDo to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        manager.delete(id);
    }

}
