package jpa_relationships_school_example.services;

import jpa_relationships_school_example.dtos.BookDto;
import jpa_relationships_school_example.entities.Book;
import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;;
    private final StudentService studentService;;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBookById(long id){
        return bookRepository.findById(id).orElseThrow(() -> new CustomAppException("Book not found", HttpStatus.NOT_FOUND));
    }

    public Book SaveBook(BookDto bookDto){
        Book newBook = Book.builder().bookName(bookDto.getBookName()).build();
        return bookRepository.save(newBook);
    }

    public Book updateBook(long id, BookDto bookDto){
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomAppException("Book not found for uppdate", HttpStatus.NOT_FOUND));
        book.setBookName(bookDto.getBookName());
        return bookRepository.save(book);
    }

    public void deleteBook(long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomAppException("Book not found for deletion", HttpStatus.NOT_FOUND));
        bookRepository.delete(book);
    }

    public Book setStudent(long bookId,long studentId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new CustomAppException("Book not found", HttpStatus.NOT_FOUND));
        Student student = studentService.getStudentById(studentId);
        if(student != null){
            book.setStudent(student);
        }
       else{
           return book;
        }
        return bookRepository.save(book);
    }

}
