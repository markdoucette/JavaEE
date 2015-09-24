package ca.rhythmtech.doit.business.reminders.boundary;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Test the TodosResource class
 * @author Mark Doucette
 */
public class TodosResourceIT {

    private Client client;
    private WebTarget tut;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target("http://localhost:8080/DoIt/api/todos/");
    }

    @Test
    public void fetchTodos() {
                this.tut.request(MediaType.TEXT_PLAIN_TYPE);
    }
}
