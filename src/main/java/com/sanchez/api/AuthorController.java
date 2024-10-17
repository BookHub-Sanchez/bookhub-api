package com.sanchez.api;


import com.sanchez.model.entity.Author;
import com.sanchez.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        List<Author> authors = authorService.getAll();
        return new ResponseEntity<List<Author>>(authors,HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Author>> paginateAuthors(@PageableDefault(page =  0, size = 10, sort = "firstName") Pageable pageable){
        Page<Author> authors = authorService.paginate(pageable);
        return new ResponseEntity<Page<Author>>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Integer id){
        Author author = authorService.findById(id);
        return new ResponseEntity<Author>(author,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        Author newAuthor = authorService.create(author);
        return new ResponseEntity<Author>(newAuthor,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") Integer id, @RequestBody Author author){
        Author updateAuthor = authorService.update(id, author);
        return new ResponseEntity<Author>(updateAuthor,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Author> deleteAuthor(@PathVariable("id") Integer id){
        authorService.delete(id);
        return new ResponseEntity<Author>(HttpStatus.NO_CONTENT);
    }
}