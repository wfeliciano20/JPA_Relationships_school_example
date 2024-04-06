package jpa_relationships_school_example.services;

import jpa_relationships_school_example.dtos.StudentDto;
import jpa_relationships_school_example.entities.Book;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final BookService bookService;
    private final EnrollmentService enrollmentService;

    public List<Student> getAllStudentes() {
        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new CustomAppException("Student not found", HttpStatus.NOT_FOUND));
    }

    public Student saveStudent(StudentDto studentDto) {
        Student newStudent = Student.builder().firstName(studentDto.getFirstName()).lastName(studentDto.getLastName()).email(studentDto.getEmail()).age(studentDto.getAge()).build();
        return studentRepository.save(newStudent);
    }

    public Student updateStudent(long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new CustomAppException("Student not found for update", HttpStatus.NOT_FOUND));
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setAge(studentDto.getAge());
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new CustomAppException("Student not found for deletion", HttpStatus.NOT_FOUND));
        studentRepository.delete(student);
    }

    public void addBook(long studentId, long bookId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new CustomAppException("Student not found for adding book", HttpStatus.NOT_FOUND));
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            student.addBook(book);
            studentRepository.save(student);
        }
    }

    public void removeBook(long studentId, long bookId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new CustomAppException("Student not found for removing book", HttpStatus.NOT_FOUND));
        Book book = bookService.getBookById(bookId);
        if (book != null) {
            student.removeBook(book);
            studentRepository.save(student);
        }
    }

    public void addEnrollment(long studentId, long enrollmentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new CustomAppException("Student not found for adding enrollment", HttpStatus.NOT_FOUND));
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (enrollment != null) {
            student.addEnrollment(enrollment);
            studentRepository.save(student);
        }
    }

    public void removeEnrollment(long studentId, long enrollmentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new CustomAppException("Student not found for removing enrollment", HttpStatus.NOT_FOUND));
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (enrollment != null) {
            student.removeEnrollment(enrollment);
            studentRepository.save(student);
        }
    }
}