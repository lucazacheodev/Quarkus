package it.lucadev;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/api/entity")
public class MyController {

    private final MyRepository repository;

    @Inject
    public MyController(MyRepository repository) {
        this.repository = repository;
    }

    @POST
    @Path("")
    @Transactional
    public Response post(MyEntity entity) {
        repository.persist(entity);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        return Response.ok(repository.findById(id)).build();
    }

    @GET
    @Path("")
    public Response getAll() {
        return Response.ok(repository.listAll()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        repository.deleteById(id);
        return Response.ok().build();
    }
}