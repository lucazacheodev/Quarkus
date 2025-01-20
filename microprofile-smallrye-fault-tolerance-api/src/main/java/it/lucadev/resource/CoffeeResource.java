package it.lucadev.resource;

import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.jboss.logging.Logger;

import io.smallrye.faulttolerance.api.RateLimit;
import it.lucadev.entity.Coffee;
import it.lucadev.service.CoffeeRepositoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/coffee")
public class CoffeeResource {

    private static final Logger LOGGER = Logger.getLogger(CoffeeResource.class);
    
    @Inject
    CoffeeRepositoryService coffeeRepository;

    private AtomicLong counter = new AtomicLong(0);

    @GET
    // @Retry (microprofiles.faulttolerance) riprova la chiamata (max 4 volte)
    @Retry(maxRetries = 4) 
    // value = 2 ---> 2 chiamate
    // window = 10 ---> 10 unità di tempo
    // windowUnit = ChronoUnit.SECONDS ---> secondi
    // L'endpoint può essere chiamato al massimo 2 volte in un intervallo di 10 secondi.
    @RateLimit(value = 2, window = 10, windowUnit = ChronoUnit.SECONDS)
    public List<Coffee> coffees() {
        final Long invocationNumber = counter.getAndIncrement();

        maybeFail(String.format("CoffeeResource#coffees() invocation #%d failed", invocationNumber));

        LOGGER.infof("CoffeeResource#coffees() invocation #%d returning successfully", invocationNumber);
        return coffeeRepository.getAllCoffees();
    }

    private void maybeFail(String failureLogMessage) {
        if (new Random().nextBoolean()) {
            LOGGER.error(failureLogMessage);
            throw new RuntimeException("Resource failure.");
        }
    }

    @GET
    @Path("/{id}/recommendations")
    // se la richiesta impiega più di 250ms viene generata una TimeoutException
    @Timeout(250) 
    // aggiungendo @Fallback non viene più generata la TimeoutException
    // con l'inserimento di @Fallback è possibile rimuovere il catch
    @Fallback(fallbackMethod = "fallbackRecommendations") 
    public List<Coffee> recommendations(int id) {
        long started = System.currentTimeMillis();
        final long invocationNumber = counter.getAndIncrement();

        try {
            randomDelay();
            LOGGER.infof("CoffeeResource#recommendations() invocation #%d returning successfully", invocationNumber);
            return coffeeRepository.getRecommendations(id);
        } catch (InterruptedException e) {
            LOGGER.errorf("CoffeeResource#recommendations() invocation #%d timed out after %d ms",
                    invocationNumber, System.currentTimeMillis() - started);
            return null;
        }
    }
    //metodo che genera il delay random (0-500ms)
    private void randomDelay() throws InterruptedException {
        Thread.sleep(new Random().nextInt(500));
    }

    //metodo che viene chiamato da @Fallback in caso di @Timeout (>250ms)
    public List<Coffee> fallbackRecommendations(int id) {
        LOGGER.info("Falling back to RecommendationResource#fallbackRecommendations()");
        return Collections.singletonList(coffeeRepository.getCoffeeById(1));
    }

    @Path("/{id}/availability")
    @GET
    public Response availability(int id) {
        final Long invocationNumber = counter.getAndIncrement();

        Coffee coffee = coffeeRepository.getCoffeeById(id);        
        if (coffee == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            Integer availability = coffeeRepository.getAvailability(coffee);
            LOGGER.infof("CoffeeResource#availability() invocation #%d returning successfully", invocationNumber);
            return Response.ok(availability).build();
        } catch (RuntimeException e) {
            String message = e.getClass().getSimpleName() + ": " + e.getMessage();
            LOGGER.errorf("CoffeeResource#availability() invocation #%d failed: %s", invocationNumber, message);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }
}
