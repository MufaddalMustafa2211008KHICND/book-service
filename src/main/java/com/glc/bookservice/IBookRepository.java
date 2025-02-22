package com.glc.bookservice;

import java.util.Collection;

public interface IBookRepository<T> {
    public void save(T t);

    public Collection<T> getAllBooks();

    public Boolean deleteBook(Integer id);

    public Boolean updateBook(Integer id, T t);

    public Book getBookById(Integer id);
}
