package it.lucadev;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/receive")
public class ConsumerResource {


    private final List<String> messages = new CopyOnWriteArrayList<>();

    @Incoming("messages-in")
    public void receive(String message) {
        messages.add(message);
    }

    @GET
    public List<String> getMessages() {
        return messages;
    }
}

