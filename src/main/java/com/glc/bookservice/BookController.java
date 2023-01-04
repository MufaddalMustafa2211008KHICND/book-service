package com.glc.bookservice;

import java.util.Collection;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @Autowired
    private RabbitTemplate template;

    @PostMapping("")  // (POST) https://localhost:8080/books
    public String publishMessage(@RequestBody Book book) {
        template.convertAndSend("exchange_books", "please_collect_books", book);
        return "Message Published "+book.getTitle();
    }

    @RabbitListener(queues = "sendBooks")
    public void listener(Book book) {
        repository.save(book);
        System.out.println(book.getTitle());
    }

    @GetMapping("/all") // (GET) https://localhost:8080/books/all
    public Collection<Book> getAllBooks(){
        return this.repository.getAllBooks();
    }

    @DeleteMapping("/delete/{id}")  // (DELETE) https://localhost:8080/books/id
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        if(this.repository.deleteBook(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Book successfully deleted!");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book successfully not found!");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody Book book) {
        if(this.repository.updateBook(id, book)){
            return ResponseEntity.status(HttpStatus.OK).body("Book successfully updated!");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = this.repository.getBookById(id);
        if(book.getId() == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(book);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }
    }

}
