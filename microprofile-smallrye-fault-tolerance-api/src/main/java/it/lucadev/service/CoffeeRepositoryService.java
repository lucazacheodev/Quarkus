package it.lucadev.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import it.lucadev.entity.Coffee;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CoffeeRepositoryService {
    private AtomicLong counter = new AtomicLong(0);
    private Map<Integer, Coffee> coffeeList = new HashMap<>();

    public CoffeeRepositoryService() {
        coffeeList.put(1, new Coffee(1, "Fernandez Espresso", "Colombia", 23));
        coffeeList.put(2, new Coffee(2, "La Scala Whole Beans", "Bolivia", 18));
        coffeeList.put(3, new Coffee(3, "Dak Lak Filter", "Vietnam", 25));
    }

    public List<Coffee> getAllCoffees() {
        return new ArrayList<>(coffeeList.values());
    }

    public Coffee getCoffeeById(Integer id) {
        return coffeeList.get(id);
    }

    public List<Coffee> getRecommendations(Integer id) {
        if (id == null) {
            return Collections.emptyList();
        }
        return coffeeList.values().stream()
                .filter(coffee -> !id.equals(coffee.id))
                .limit(2)
                .collect(Collectors.toList());
    }

    // CircuitBreaker.requestVolumeThreshold = 4 @CircuitBreaker si attiva dopo 4 chiamate
    // CircuitBreaker.failureRatio default 0.5 = se fallisce 1/2 delle chiamate @CircuitBreaker si apre e blocca le chiamate future
    // CircuitBreaker.delay default 5s = se @CircuitBreaker blocca le chiamate per 5s
    @CircuitBreaker(requestVolumeThreshold = 4)
    public Integer getAvailability(Coffee coffee) {
        maybeFail();
        return new Random().nextInt(30);
    }

    private void maybeFail() {        
        final Long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) {
            throw new RuntimeException("Service failed.");
        }
    }
}