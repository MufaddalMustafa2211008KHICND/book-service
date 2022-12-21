package com.glc.bookservice;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")  // Any address like https://localhost:8080/books
public class BookController {
    private final BookRepository repository;

    public BookController(BookRepository repository){
        this.repository = repository;
    }

    @PostMapping("")  // (POST) https://localhost:8080/books
    public void createBook(@RequestBody Book book) {
        this.repository.save(book);
    }

    @GetMapping("/all") // (GET) https://localhost:8080/books/all
    public Collection<Book> getAllBooks(){
        return this.repository.getAllBooks();
    }

    @DeleteMapping("/{id}")  // (DELETE) https://localhost:8080/books/id
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        if(this.repository.deleteBook(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Book successfully deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book successfully not found!");
        }
    }

}
