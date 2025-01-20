import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Timed;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class PrimeNumberChecker {

    private long highestPrimeNumberSoFar = 2;

    @GET
    @Path("/{number}")
    @Produces(MediaType.TEXT_PLAIN)
    // creazione del counter chiamato "performedChecks" che monitora quante volte il metodo "checkIfPrime" viene chiamato
    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    // creazione del timer in millisecondi che monitora il tempo impiegato dal metodo a rispondere
    @Timed(name = "checksTimer", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
    public String checkIfPrime(long number) {
        if (number < 1) {
            return "Only natural numbers can be prime numbers.";
        }
        if (number == 1) {
            return "1 is not prime.";
        }
        if (number == 2) {
            return "2 is prime.";
        }
        if (number % 2 == 0) {
            return number + " is not prime, it is divisible by 2.";
        }
        for (int i = 3; i < Math.floor(Math.sqrt(number)) + 1; i = i + 2) {
            if (number % i == 0) {
                return number + " is not prime, is divisible by " + i + ".";
            }
        }
        if (number > highestPrimeNumberSoFar) {
            highestPrimeNumberSoFar = number;
        }
        return number + " is prime.";
    }
    // metrica di tipo Gauge (che può variare in modo arbitrario, non lineare) salva il più alto numero primo
    @Gauge(name = "highestPrimeNumberSoFar", unit = MetricUnits.NONE, description = "Highest prime number so far.")
    public Long highestPrimeNumberSoFar() {
        return highestPrimeNumberSoFar;
    }

}