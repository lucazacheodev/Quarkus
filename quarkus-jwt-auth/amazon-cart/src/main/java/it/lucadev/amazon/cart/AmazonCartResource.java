package it.lucadev.amazon.cart;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/cart")
@ApplicationScoped
public class AmazonCartResource {
    List<AmazonItem> items = new ArrayList<>();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems(){
        return Response.ok(items).build();
    }

    @POST
    @RolesAllowed({"admin", "writer"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(AmazonItem item){
        items.add(item);
        return Response.ok(items).build();
    }
    
    @Path("{id}")
    @DELETE
    @RolesAllowed("admin")
    public Response deleteItem(@PathParam("id") Long id){
        items.stream().filter(x -> x.getId().equals(id)).findFirst().ifPresent(x -> items.remove(x));
        return Response.noContent().build();
    }
}