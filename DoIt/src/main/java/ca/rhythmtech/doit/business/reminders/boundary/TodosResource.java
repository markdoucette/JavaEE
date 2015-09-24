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

    @GET
    @Produces("application/json")
    @Path("{id}")
    public ToDo find(@PathParam("id") int id) {
        return new ToDo("Implement REST endpoint with id #" + id,
                "Here we have implemented a simple rest end point", PRIORITY_HIGH);
    }

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

    @POST
    public void save(ToDo toDo) {
        System.out.println("Saved toDo = [" + toDo + "]");
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        System.out.println("Deleted id = [" + id + "]");
    }

}
