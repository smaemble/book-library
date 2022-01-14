package com.galvanize.tmo.paspringstarter.rest;

import com.galvanize.tmo.paspringstarter.data.model.Book;
import com.galvanize.tmo.paspringstarter.data.repositories.BookRepository;
import com.galvanize.tmo.paspringstarter.model.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        return new ResponseEntity<>(bookResponse, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value="/api/books",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Book>> findAllBooks(@RequestBody BookRequest bookRequest){
        List<Book> allBooks = bookRepository.findAll();
        return new ResponseEntity<>(allBooks, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value="/api/books",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.DELETE)
    public ResponseEntity removeAllBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/health")
    public void health() {

    }
}
