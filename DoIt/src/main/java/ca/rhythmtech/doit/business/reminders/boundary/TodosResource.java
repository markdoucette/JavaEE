package ca.rhythmtech.doit.business.reminders.boundary;

import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest resource for the ToDo's
 *
 * @author Mark Doucette
 */
@Path("todos")
public class TodosResource {

    private static final int PRIORITY_HIGH = 1;

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
        return new ToDo("Implement REST endpoint with id #" + id,
                "Here we have implemented a simple rest end point", PRIORITY_HIGH);
    }

    /**
     * Find all ToDo's and return as a List
     * @return List of all ToDo's
     */
    @GET
    @Produces("application/json")
    public List<ToDo> all() {
        List<ToDo> todos = new ArrayList<>();
        todos.add(find(42));
        todos.add(find(17));
        todos.add(find(22));
        todos.add(find(34));
        todos.add(find(33));

        return todos;
    }

    /**
     * Save a new ToDo to the data store
     * @param toDo the ToDo to be saved
     */
    @POST
    public void save(ToDo toDo) {
        System.out.println("Saved toDo = [" + toDo + "]");
    }

    /**
     * Delete a ToDo by id
     * @param id The id of the ToDo to delete
     */
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        System.out.println("Deleted id = [" + id + "]");
    }

}
