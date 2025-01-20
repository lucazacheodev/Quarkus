package util.health;

import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class DataHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("Health check with data")
                .up()
                .withData("foo", "fooValue")
                .withData("bar", "barValue")
                .build();

        // esempio con trycatch
        // try {
        //     simulateDatabaseConnectionVerification();
        //     responseBuilder.up();
        // } catch (IllegalStateException e) {
        //     // cannot access the database
        //     responseBuilder.down()
        //             .withData("error", e.getMessage()); // pass the exception message
        // }
    }

}