package ca.rhythmtech.doit.business.reminders.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import org.junit.Rule;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test the TodosResource class
 * @author Mark Doucette
 */
public class TodosResourceIT {
    @Rule
    public JAXRSClientProvider provider = JAXRSClientProvider.buildWithURI("http://localhost:8080/DoIt/api/todos/");

    @Test
    public void fetchTodos() {
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));

        JsonArray allTodos = response.readEntity(JsonArray.class);
        System.out.println(allTodos);
        System.out.println("Size of allTodos: " + allTodos.size());
        assertFalse(allTodos.isEmpty());

        JsonObject todo = allTodos.getJsonObject(3);
        System.out.println(todo);
        assertTrue(todo.getString("caption").contains("REST"));
    }

    @Test
    public void fetchTodo() {
        JsonObject todo = this.provider.target()
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .get(JsonObject.class);
        assertTrue(todo.getString("caption").contains("42"));
        System.out.println("Fetch todo: " + todo);
    }

    @Test
    public void deleteTodo()  {
        Response deleteResponse = this.provider.target()
                .path("42")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }
}
