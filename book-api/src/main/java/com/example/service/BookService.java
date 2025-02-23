package com.example.service;
import java.util.List;

import com.example.dto.BookDto;
import com.example.entity.Book;
import com.example.repository.BookRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BookService {

    @Inject
    BookRepository repository;

    public List<Book> listAll() {
        return repository.listAll();
    }

    public Book findById(Long id) {
        return repository.findById(id);
    }
    
    @Transactional
    public Book create(BookDto dto) {
        Book book = new Book();
        book.setTitle(dto.title);
        book.setAuthor(dto.author);
        book.setPublicationYear(dto.publicationYear);
        repository.persist(book);
        return book;
    }
    
    @Transactional
    public Book update(Long id, BookDto dto) {
        Book book = repository.findById(id);
        if (book != null) {
            book.setTitle(dto.title);
            book.setAuthor(dto.author);
            book.setPublicationYear(dto.publicationYear);
            repository.persist(book);
        }
        return book;
    }
    @Transactional
    public boolean delete(Long id) {
        return repository.deleteById(id);
    }
}
