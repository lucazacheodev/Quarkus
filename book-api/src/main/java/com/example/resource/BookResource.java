package com.example.resource;

import com.example.dto.BookDto;
import com.example.entity.Book;
import com.example.service.BookService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    @Inject
    BookService service;

    @GET
    public List<Book> getAll() {
        return service.listAll();
    }

    @GET
    @Path("/{id}")
    public Book getById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(BookDto dto) {
        Book book = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, BookDto dto) {
        Book updated = service.update(id, dto);
        return updated != null 
            ? Response.ok(updated).build() 
            : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);
        return deleted 
            ? Response.noContent().build() 
            : Response.status(Response.Status.NOT_FOUND).build();
    }
}
