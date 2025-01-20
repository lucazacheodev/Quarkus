package util.health;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.health.Liveness;

import java.time.Duration;

import org.eclipse.microprofile.health.HealthCheckResponse;

import jakarta.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
public class LivenessAsync implements AsyncHealthCheck {

    @Override
    public Uni<HealthCheckResponse> call() {
        return Uni.createFrom().item(HealthCheckResponse.up("liveness-reactive"))
                .onItem().delayIt().by(Duration.ofMillis(10));
    }
}
