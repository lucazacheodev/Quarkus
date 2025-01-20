package resource;

import java.util.ArrayList;
import java.util.List;

import entity.FruitEntity;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/fruits")
public class FruitResource {

    private static List<FruitEntity> fruits = new ArrayList<>();

    public FruitResource() {
        fruits.add(new FruitEntity("Apple", "Winter fruit"));
        fruits.add(new FruitEntity("Pineapple", "Tropical fruit"));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FruitEntity> list() {
        return fruits;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FruitEntity> add(FruitEntity fruit) {
        fruits.add(fruit);
        return fruits;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FruitEntity> delete(FruitEntity fruit) {
        fruits.removeIf(existingFruit -> existingFruit.name.contentEquals(fruit.name));
        return fruits;
    }
}
