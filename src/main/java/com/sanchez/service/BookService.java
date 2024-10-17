package com.sanchez.service;

import com.sanchez.model.entity.Author;
import com.sanchez.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Page<Book> paginate(Pageable pageable);
    Book findById(Integer id);
    Book create(Book book);
    Book update(Integer id, Book updatedBook);
    void delete(Integer id);
}
