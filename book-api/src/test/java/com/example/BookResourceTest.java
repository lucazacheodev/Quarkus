package com.example;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import com.example.dto.BookDto;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.HttpHeaders;

@QuarkusTest
public class BookResourceTest {

    @Test
    void shouldGetAllBooks() {
        // Sono presenti due libri (shouldCreateBook, shouldUpdateBook)
        given()
                .header(HttpHeaders.ACCEPT, "application/json")
                .when().get("/books")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void shouldCreateBook() {
        //creo DTO (senza ID)
        BookDto newBookDto = new BookDto();
        newBookDto.title = "Libro2";
        newBookDto.author = "Autore2";
        newBookDto.publicationYear = 2022;
        //chiamata POST con il DTO viene generata entità con ID
        given()
                .contentType(ContentType.JSON)
                .body(newBookDto)
                .when().post("/books")
                .then()
                //verifico status code
                .statusCode(201)
                //verifico dati passati dal DTO
                .body("title", is("Libro2"))
                .body("author", is("Autore2"))
                .body("publicationYear", is(2022))
                //verifico la creazione dell'id
                .body("id", is(2));
    }

    @Test
    void shouldUpdateBook() {
        // Creo dto
        BookDto newBookDto = new BookDto();
        newBookDto.title = "Libro3";
        newBookDto.author = "Autore3";
        newBookDto.publicationYear = 2023;
        // salvo l'ID creato durante la chiamata POST
        int bookId = given()
                .contentType(ContentType.JSON)
                .body(newBookDto)
                .when().post("/books")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");

        // Aggiorno libro con l'ID salvato
        BookDto updatedBookDto = new BookDto();
        updatedBookDto.title = "Libro3 Updated";
        updatedBookDto.author = "Autore3 Updated";
        updatedBookDto.publicationYear = 2024;

        given()
                .contentType(ContentType.JSON)
                .body(updatedBookDto)
                .when().put("/books/" + bookId)
                .then()
                .statusCode(200)
                //verifico i campi modificati
                .body("title", is("Libro3 Updated"))
                .body("author", is("Autore3 Updated"))
                .body("publicationYear", is(2024))
                .body("id", is(bookId));
    }

    @Test
    void shouldDeleteBook() {  
        //creo libro      
        BookDto newBookDto = new BookDto();
        newBookDto.title = "Libro2";
        newBookDto.author = "Autore2";
        newBookDto.publicationYear = 2022;
        // salvo l'ID creato durante la chiamata POST
        int bookId = given()
                .contentType(ContentType.JSON)
                .body(newBookDto)
                .when().post("/books")
                .then()
                .statusCode(201)
                .extract().jsonPath().getInt("id");
        //chiamata DELETE con l'id appena salvato
        given()
                .when().delete("/books/" + bookId)
                .then()
                .statusCode(204);
        //mi assicuro che il libro non esista più
        given()
                .when().get("/books/" + bookId)
                .then()
                .statusCode(204); 
    }
}