package jpa_relationships_school_example.servicesTests;

import jpa_relationships_school_example.dtos.StudentDto;
import jpa_relationships_school_example.entities.Book;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.StudentRepository;
import jpa_relationships_school_example.services.BookService;
import jpa_relationships_school_example.services.EnrollmentService;
import jpa_relationships_school_example.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private BookService bookService;

    @Mock
    private EnrollmentService enrollmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllStudents_returnsAllStudents() {
        studentService.getAllStudentes();
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void getStudentById_returnsStudent() {
        Student student = new Student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        Student result = studentService.getStudentById(1L);
        assertEquals(student, result);
    }

    @Test
    public void getStudentById_throwsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    public void saveStudent_savesAndReturnsStudent() {
        StudentDto studentDto = new StudentDto();
        Student student = new Student();
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentService.saveStudent(studentDto);
        assertEquals(student, result);
    }

    @Test
    public void updateStudent_updatesAndReturnsStudent() {
        StudentDto studentDto = new StudentDto();
        Student student = new Student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentService.updateStudent(1L, studentDto);
        assertEquals(student, result);
    }

    @Test
    public void updateStudent_throwsExceptionWhenStudentNotFound() {
        StudentDto studentDto = new StudentDto();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.updateStudent(1L, studentDto));
    }

    @Test
    public void deleteStudent_deletesStudent() {
        Student student = new Student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    public void deleteStudent_throwsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.deleteStudent(1L));
    }

    @Test
    public void addBook_addsBookToStudent() {
        Student student = new Student();
        Book book = new Book();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(bookService.getBookById(anyLong())).thenReturn(book);
        studentService.addBook(1L, 1L);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void addBook_throwsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.addBook(1L, 1L));
    }

    @Test
    public void removeBook_removesBookFromStudent() {
        Student student = new Student();
        Book book = new Book();
        student.getBooks().add(book);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(bookService.getBookById(anyLong())).thenReturn(book);
        studentService.removeBook(1L, 1L);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void removeBook_throwsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.removeBook(1L, 1L));
    }

    @Test
    public void addEnrollment_addsEnrollmentToStudent() {
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(enrollmentService.getEnrollmentById(anyLong())).thenReturn(enrollment);
        studentService.addEnrollment(1L, 1L);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void addEnrollment_throwsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.addEnrollment(1L, 1L));
    }

    @Test
    public void removeEnrollment_removesEnrollmentFromStudent() {
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        student.getEnrollments().add(enrollment);
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(enrollmentService.getEnrollmentById(anyLong())).thenReturn(enrollment);
        studentService.removeEnrollment(1L, 1L);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void removeEnrollment_throwsExceptionWhenStudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentService.removeEnrollment(1L, 1L));
    }
}