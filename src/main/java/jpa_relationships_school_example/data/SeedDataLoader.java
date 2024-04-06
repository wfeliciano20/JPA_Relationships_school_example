package jpa_relationships_school_example.data;
import jpa_relationships_school_example.entities.Book;
import jpa_relationships_school_example.entities.Course;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.entities.StudentIdCard;
import jpa_relationships_school_example.repositories.BookRepository;
import jpa_relationships_school_example.repositories.CourseRepository;
import jpa_relationships_school_example.repositories.EnrollmentRepository;
import jpa_relationships_school_example.repositories.StudentIdCardRepository;
import jpa_relationships_school_example.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class SeedDataLoader implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final StudentIdCardRepository studentIdCardRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public SeedDataLoader(BookRepository bookRepository,  StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository){

        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.studentIdCardRepository = studentIdCardRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;

    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Create students
        Student student1 = Student.builder().firstName("Alice").lastName("Johnson").email("alice@example.com").age(20).build();
        Student student2 = Student.builder().firstName("Bob").lastName("Smith").email("bob@example.com").age(22).build();
        studentRepository.saveAll(Arrays.asList(student1, student2));
        // Create books
        Book book1 = Book.builder().bookName("Book 1").createdAt(LocalDateTime.now()).build();
        Book book2 = Book.builder().bookName("Book 2").createdAt(LocalDateTime.now()).build();
        bookRepository.saveAll(Arrays.asList(book1, book2));

        // Create student ID cards
        StudentIdCard studentIdCard1 = StudentIdCard.builder().cardNumber("123456").build();
        StudentIdCard studentIdCard2 = StudentIdCard.builder().cardNumber("789012").build();;
        studentIdCardRepository.saveAll(Arrays.asList(studentIdCard1, studentIdCard2));

        // Create courses
        Course course1 = Course.builder().name("Course 1").department("Department A").build();
        Course course2 = Course.builder().name("Course 2").department("Department B").build();
        courseRepository.saveAll(Arrays.asList(course1, course2));

        // Create enrollments
        Enrollment enrollment1 = Enrollment.builder().createdAt(LocalDateTime.now()).build();
        enrollmentRepository.save(enrollment1);
    }
}
