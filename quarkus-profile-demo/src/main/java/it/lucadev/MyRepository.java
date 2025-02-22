package it.lucadev;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyRepository implements PanacheRepository<MyEntity> {

}
