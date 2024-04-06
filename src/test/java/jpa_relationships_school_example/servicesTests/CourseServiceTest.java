package jpa_relationships_school_example.servicesTests;

import jpa_relationships_school_example.dtos.CourseDto;
import jpa_relationships_school_example.entities.Course;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.CourseRepository;
import jpa_relationships_school_example.services.CourseService;
import jpa_relationships_school_example.services.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentService enrollmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCourses_returnsAllCourses() {
        courseService.getAllCourses();
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    public void getCourseById_returnsCourse() {
        Course course = new Course();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        Course result = courseService.getCourseById(1L);
        assertEquals(course, result);
    }

    @Test
    public void getCourseById_throwsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> courseService.getCourseById(1L));
    }

    @Test
    public void saveCourse_savesAndReturnsCourse() {
        CourseDto courseDto = new CourseDto();
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course result = courseService.saveCourse(courseDto);
        assertEquals(course, result);
    }

    @Test
    public void updateCourse_updatesAndReturnsCourse() {
        CourseDto courseDto = new CourseDto();
        Course course = new Course();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);
        Course result = courseService.updateCourse(1L, courseDto);
        assertEquals(course, result);
    }

    @Test
    public void updateCourse_throwsExceptionWhenCourseNotFound() {
        CourseDto courseDto = new CourseDto();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> courseService.updateCourse(1L, courseDto));
    }

    @Test
    public void deleteCourse_deletesCourse() {
        Course course = new Course();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        courseService.deleteCourse(1L);
        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    public void deleteCourse_throwsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> courseService.deleteCourse(1L));
    }

    @Test
    public void addEnrollmentToCourse_addsEnrollment() {
        Course course = new Course();
        Enrollment enrollment = new Enrollment();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(enrollmentService.getEnrollmentById(anyLong())).thenReturn(enrollment);
        courseService.addEnrollmentToCourse(1L, 1L);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void addEnrollmentToCourse_throwsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> courseService.addEnrollmentToCourse(1L, 1L));
    }

    @Test
    public void removeEnrollmentFromCourse_removesEnrollment() {
        Course course = new Course();
        Enrollment enrollment = new Enrollment();
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(enrollmentService.getEnrollmentById(anyLong())).thenReturn(enrollment);
        courseService.removeEnrollmentFromCourse(1L, 1L);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void removeEnrollmentFromCourse_throwsExceptionWhenCourseNotFound() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> courseService.removeEnrollmentFromCourse(1L, 1L));
    }
}