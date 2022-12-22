package com.glc.bookservice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository implements IBookRepository<Book>{
    private Map<Integer, Book> repository;

    public BookRepository() {
        this.repository = new HashMap<>();
    }

    @Override
    public void save(Book book){
        repository.put(book.getId(), book);
    }

    @Override
    public Collection<Book> getAllBooks(){
        return repository.values();
    }

    @Override
    public Boolean deleteBook(Integer id){
        if(this.repository.containsKey(id)){
            this.repository.remove(id);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Boolean updateBook(Integer id, Book book) {
        if(this.repository.containsKey(id)){
            this.repository.put(id, book);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public Book getBookById(Integer id) {
        if(this.repository.containsKey(id)){
            return this.repository.get(id);
        }
        else {
            return new Book();
        }
    }
    
}
