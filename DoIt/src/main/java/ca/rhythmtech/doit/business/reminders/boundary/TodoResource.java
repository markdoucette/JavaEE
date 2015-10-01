package ca.rhythmtech.doit.business.reminders.boundary;

import ca.rhythmtech.doit.business.reminders.entity.ToDo;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by mdoucette on 01/10/15.
 */
public class TodoResource {
    long id;
    ToDoManager manager;

    public TodoResource(long id, ToDoManager manager) {
        this.id = id;
        this.manager = manager;
    }

    /**
     * Find a specific ToDo by it's id
     *
     * @param id The id of the ToDo to find
     * @return The ToDo matching the requested id
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ToDo findTodoById() {
        return manager.findTodoById(id);
    }

    @PUT
    public ToDo update(ToDo todo) {
        todo.setId(id);
        return manager.save(todo);
    }

    @PUT
    @Path("/status")
    public Response updateStatus(JsonObject payload) {
        if(!payload.containsKey("done")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("reason", "JSON should contain field \"done \"")
                    .build();
        }
        boolean doneStatus = payload.getBoolean("done");

        ToDo todo = manager.updateStatus(id, doneStatus);
        if (todo == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .header("reason", "ToDo with id " + id + " not found")
                    .build();
        } else {
            return Response.ok(todo).build();
        }
    }

    /**
     * Delete a ToDo by id
     */
    @DELETE
    public void delete() {
        manager.delete(id);
    }
}
