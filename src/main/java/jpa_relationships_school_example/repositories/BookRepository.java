package jpa_relationships_school_example.repositories;

import jpa_relationships_school_example.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
