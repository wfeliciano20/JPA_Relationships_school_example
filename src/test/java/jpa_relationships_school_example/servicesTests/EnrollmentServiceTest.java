package jpa_relationships_school_example.servicesTests;
import jpa_relationships_school_example.dtos.EnrollmentDto;
import jpa_relationships_school_example.entities.Course;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.EnrollmentRepository;
import jpa_relationships_school_example.services.CourseService;
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

public class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllEnrollments_returnsAllEnrollments() {
        enrollmentService.getAllEnrollments();
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    public void getEnrollmentById_returnsEnrollment() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        Enrollment result = enrollmentService.getEnrollmentById(1L);
        assertEquals(enrollment, result);
    }

    @Test
    public void getEnrollmentById_throwsExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.getEnrollmentById(1L));
    }

    @Test
    public void saveEnrollment_savesAndReturnsEnrollment() {
        EnrollmentDto enrollmentDto = new EnrollmentDto();
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        Enrollment result = enrollmentService.saveEnrollment(enrollmentDto);
        assertEquals(enrollment, result);
    }

    @Test
    public void updateEnrollment_updatesAndReturnsEnrollment() {
        EnrollmentDto enrollmentDto = new EnrollmentDto();
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);
        Enrollment result = enrollmentService.updateEnrollment(1L, enrollmentDto);
        assertEquals(enrollment, result);
    }

    @Test
    public void updateEnrollment_throwsExceptionWhenEnrollmentNotFound() {
        EnrollmentDto enrollmentDto = new EnrollmentDto();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.updateEnrollment(1L, enrollmentDto));
    }

    @Test
    public void deleteEnrollment_deletesEnrollment() {
        Enrollment enrollment = new Enrollment();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        enrollmentService.deleteEnrollment(1L);
        verify(enrollmentRepository, times(1)).delete(enrollment);
    }

    @Test
    public void deleteEnrollment_throwsExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.deleteEnrollment(1L));
    }

    @Test
    public void addStudentToEnrollment_addsStudent() {
        Enrollment enrollment = new Enrollment();
        Student student = new Student();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        when(studentService.getStudentById(anyLong())).thenReturn(student);
        enrollmentService.addStudentToEnrollment(1L, 1L);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    public void addStudentToEnrollment_throwsExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.addStudentToEnrollment(1L, 1L));
    }

    @Test
    public void removeStudentFromEnrollment_removesStudent() {
        Enrollment enrollment = new Enrollment();
        Student student = new Student();
        enrollment.getStudents().add(student);
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        when(studentService.getStudentById(anyLong())).thenReturn(student);
        enrollmentService.removeStudentFromEnrollment(1L, 1L);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    public void removeStudentFromEnrollment_throwsExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.removeStudentFromEnrollment(1L, 1L));
    }

    @Test
    public void addCourseToEnrollment_addsCourse() {
        Enrollment enrollment = new Enrollment();
        Course course = new Course();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        when(courseService.getCourseById(anyLong())).thenReturn(course);
        enrollmentService.addCourseToEnrollment(1L, 1L);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    public void addCourseToEnrollment_throwsExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.addCourseToEnrollment(1L, 1L));
    }

    @Test
    public void removeCourseFromEnrollment_removesCourse() {
        Enrollment enrollment = new Enrollment();
        Course course = new Course();
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.of(enrollment));
        when(courseService.getCourseById(anyLong())).thenReturn(course);
        enrollmentService.removeCourseFromEnrollment(1L, 1L);
        verify(enrollmentRepository, times(1)).save(enrollment);
    }

    @Test
    public void removeCourseFromEnrollment_throwsExceptionWhenEnrollmentNotFound() {
        when(enrollmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> enrollmentService.removeCourseFromEnrollment(1L, 1L));
    }
}