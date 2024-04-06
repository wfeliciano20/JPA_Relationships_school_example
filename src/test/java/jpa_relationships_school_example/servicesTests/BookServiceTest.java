package jpa_relationships_school_example.servicesTests;
import jpa_relationships_school_example.dtos.BookDto;
import jpa_relationships_school_example.entities.Book;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.BookRepository;
import jpa_relationships_school_example.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllBooksReturnsListOfBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void getBookByIdReturnsBook() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(1L);

        assertEquals(book, foundBook);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void getBookByIdThrowsExceptionWhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomAppException.class, () -> bookService.getBookById(1L));
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void saveBookSavesAndReturnsBook() {
        BookDto bookDto = new BookDto();
        bookDto.setBookName("Test Book");
        Book book = new Book();
        book.setBookName(bookDto.getBookName());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.SaveBook(bookDto);

        assertEquals(book, savedBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void updateBookUpdatesAndReturnsBook() {
        BookDto bookDto = new BookDto();
        bookDto.setBookName("Updated Book");
        Book book = new Book();
        book.setBookName("Test Book");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(1L, bookDto);

        assertEquals("Updated Book", updatedBook.getBookName());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void deleteBookDeletesBook() {
        Book book = new Book();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    public void deleteBookThrowsExceptionWhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomAppException.class, () -> bookService.deleteBook(1L));
        verify(bookRepository, times(1)).findById(1L);
    }
}