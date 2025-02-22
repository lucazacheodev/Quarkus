package it.lucadev;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "myentity")
public class MyEntity extends PanacheEntity {
    private String field;

    public MyEntity() {
    }

    public MyEntity(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public MyEntity field(String field) {
        setField(field);
        return this;
    }

}
