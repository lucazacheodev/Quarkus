package it.lucadev;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/send")
public class ProducerResource {

    @Channel("messages-out")
    Emitter<String> emitter;

    @POST
    public Response send(String message) {
        emitter.send(message);
        return Response.ok("Message sent: " + message).build();
    }
}
