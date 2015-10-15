package ca.rhythmtech.doit.business.reminders.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import org.junit.Rule;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test the TodosResource class
 * @author Mark Doucette
 */
public class TodosResourceIT {
    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/DoIt/api/todos/");

    @Test
    public void save() {
        Random randgen = new Random();
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todo = todoBuilder.add("caption", "Newest Todo")
                .add("description", "A great new Todo")
                .add("priority", randgen.nextInt(100))
                .build();
        Response response = this.provider.target().request().post(Entity.json(todo));
        assertThat(response.getStatus(), is(201));
        String location = response.getHeaderString("Location");

        // now try to retrieve the ToDo that we saved
        JsonObject savedTodo = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(savedTodo.getString("description").equalsIgnoreCase("A great new Todo"));
        System.out.println("Saved Todo: " + savedTodo);
    }

    @Test
    public void saveNegative() {
        Random randgen = new Random();
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todo = todoBuilder
                .add("description", "A great new Todo")
                .add("priority", randgen.nextInt(100))
                .build();
        Response response = this.provider.target().request().post(Entity.json(todo));
        assertThat(response.getStatus(), is(400));
        // print out all headers in response
        response.getHeaders().entrySet().forEach(System.out::println);
        System.out.println();
    }

    @Test
    public void findById() {
        Response response = this.provider.target().path("3")
                .request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));

        JsonObject todo = response.readEntity(JsonObject.class);
        System.out.println("ToDo by id: " + todo);
        assertTrue(todo.getString("caption").contains("Newest"));
    }

    @Test
    public void findAll() {
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));

        JsonArray todos = response.readEntity(JsonArray.class);
        System.out.println("Find all ToDo's " + todos);
        assertTrue(todos.getJsonObject(2).getString("caption").contains("Newest"));
    }

    @Test
    public void update() {
        Random randgen = new Random();
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todo = todoBuilder.add("caption", "Test Update todo")
                .add("description", "Todo to be updated")
                .add("priority", randgen.nextInt(100))
                .build();
        // save our test ToDo
        Response saveResponse = this.provider.target().request(MediaType.APPLICATION_JSON)
                .post(Entity.json(todo));
        String location = saveResponse.getHeaderString("Location");

        // test updating the saved ToDo
        JsonObject updatedTodo = todoBuilder
                .add("caption", "Updated ToDo")
                .build();
        this.provider.client().target(location).request(MediaType.APPLICATION_JSON)
                .put(Entity.json(updatedTodo));

        // retrieve and validate
        JsonObject getTodo = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(getTodo.getString("caption").equalsIgnoreCase("Updated ToDo"));
    }

    @Test
    public void updateStatus() {
        Random randgen = new Random();
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todo = todoBuilder.add("caption", "Test Update Status of todo")
                .add("description", "Todo to update status on")
                .add("priority", randgen.nextInt(100))
                .add("done", false)
                .build();
        // save our test ToDo
        Response saveResponse = this.provider.target().request(MediaType.APPLICATION_JSON)
                .post(Entity.json(todo));
        String location = saveResponse.getHeaderString("Location");
        System.out.println(location);

        // retrieve
        JsonObject todoToUpdate = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        JsonObject statusUpdate = todoBuilder
                .add("done", true)
                .build();

        this.provider.client()
                .target(location)
                .path("status")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(statusUpdate));

        // retrieve and verify
        JsonObject updatedStatusTodo = this.provider.client()
                .target(location)
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);

        assertTrue(updatedStatusTodo.getBoolean("done"));
    }

    @Test
    public void updateStatusNegative() {
        /* Test trying to update the status on a ToDo that doesn't exist */
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject statusUpdate = todoBuilder
                .add("done", true)
                .build();

        Response putResponse = this.provider
                .target()
                .path("-42")
                .path("status")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(statusUpdate));

        assertThat(putResponse.getStatus(), is(400));
        assertFalse(putResponse.getHeaderString("reason").isEmpty());
    }

    @Test
    public void updateStatusMalformed() {
        /* try to update a ToDo status with a malformed property */
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject statusUpdate = todoBuilder
                .add("donediddy", true)
                .build();

        Response putResponse = this.provider
                .target()
                .path("1")
                .path("status")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(statusUpdate));

        assertThat(putResponse.getStatus(), is(400));
        assertFalse(putResponse.getHeaderString("reason").isEmpty());
    }

    @Test
    public void delete() {
        Response response = this.provider
                .target().path("5")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertThat(response.getStatus(), is(204));
    }


}
