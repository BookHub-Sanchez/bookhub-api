package com.sanchez.service.impl;

import com.sanchez.model.entity.Author;
import com.sanchez.model.entity.Book;
import com.sanchez.model.entity.Category;
import com.sanchez.repository.AuthorRepository;
import com.sanchez.repository.BookRepository;
import com.sanchez.repository.CategoryRepository;
import com.sanchez.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Book> paginate(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Book findById(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    @Transactional
    @Override
    public Book create(Book book) {
        //Asigna la categoria y el autor antes de guardar
        Category category = categoryRepository.findById(book.getCategory().getId()).orElseThrow(() -> new RuntimeException("Category not found with id: "
                + book.getCategory().getId()));
        Author author = authorRepository.findById(book.getAuthor().getId()).orElseThrow(() -> new RuntimeException("Author not found with id: "
                + book.getAuthor().getId()));

        book.setCategory(category);
        book.setAuthor(author);
        book.setCreatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Override
    public Book update(Integer id, Book updatedBook) {
        Book bookFromDb = findById(id);
        Category category = categoryRepository.findById(updatedBook.getCategory().getId()).orElseThrow(() -> new RuntimeException("Category not found with id: "
                + updatedBook.getCategory().getId()));
        Author author = authorRepository.findById(updatedBook.getAuthor().getId()).orElseThrow(() -> new RuntimeException("Author not found with id: "
                + updatedBook.getAuthor().getId()));

        //Actualizacion de los campos del libro
        bookFromDb.setTitle(updatedBook.getTitle());
        bookFromDb.setDescription(updatedBook.getDescription());
        bookFromDb.setPrice(updatedBook.getPrice());
        bookFromDb.setSlug(updatedBook.getSlug());
        bookFromDb.setCoverPath(updatedBook.getCoverPath());
        bookFromDb.setFilePath(updatedBook.getFilePath());
        bookFromDb.setCategory(category);
        bookFromDb.setAuthor(author);
        bookFromDb.setUpdatedAt(LocalDateTime.now());

        return bookRepository.save(bookFromDb);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}
