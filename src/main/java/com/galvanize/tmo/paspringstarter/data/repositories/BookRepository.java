package com.galvanize.tmo.paspringstarter.data.repositories;

import com.galvanize.tmo.paspringstarter.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

}
