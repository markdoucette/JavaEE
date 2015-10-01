package ca.rhythmtech.doit.business.reminders.boundary;

import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
    ToDoManager manager;

    /**
     * Find a specific ToDo by it's id
     *
     * @param id The id of the ToDo to find
     * @return The ToDo matching the requested id
     */
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TodoResource findTodoById(@PathParam("id") long id) {
        return new TodoResource(id, manager);
    }

    /**
     * Find all ToDo's and return as a List
     * @return List of all ToDo's
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ToDo> all() {
        return manager.all();
    }

    /**
     * Save a new ToDo to the data store
     * @param toDo the ToDo to be saved
     */
    @POST
    public Response save(ToDo todo, @Context UriInfo info) {
        ToDo savedTodo = manager.save(todo);
        long todoId = savedTodo.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + todoId).build();
        return Response.created(uri).build();
    }



}
