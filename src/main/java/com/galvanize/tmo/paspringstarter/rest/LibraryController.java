package com.galvanize.tmo.paspringstarter.rest;

import com.galvanize.tmo.paspringstarter.data.model.Book;
import com.galvanize.tmo.paspringstarter.data.repositories.BookRepository;
import com.galvanize.tmo.paspringstarter.model.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@RestController
public class LibraryController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value="/api/books",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.POST)
    public ResponseEntity<Book> addBook(@RequestBody BookRequest bookRequest){
        Book entity = new Book();
        entity.setTitle(bookRequest.getTitle());
        entity.setAuthor(bookRequest.getAuthor());
        entity.setYearPublished(bookRequest.getYearPublished());

        Book bookResponse = bookRepository.save(entity);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }


    @RequestMapping(value="/api/books", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> findAllBooks(){
        List<Book> allBooks = Optional.ofNullable(bookRepository.findAll()).map(l -> l.stream().sorted(
                        comparing(Book::getTitle)).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }

    @RequestMapping(value="/api/books",
            method = RequestMethod.DELETE)
    public ResponseEntity removeAllBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/health")
    public void health() {

    }
}
