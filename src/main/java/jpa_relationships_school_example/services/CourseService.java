package jpa_relationships_school_example.services;

import jpa_relationships_school_example.dtos.CourseDto;
import jpa_relationships_school_example.entities.Course;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentService enrollmentService;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CustomAppException("Course not found", HttpStatus.NOT_FOUND));
    }

    public Course saveCourse(CourseDto courseDto) {
       Course course = Course.builder().name(courseDto.getName()).department(courseDto.getDepartment()).build();
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CustomAppException("Course not found for update", HttpStatus.NOT_FOUND));
        course.setName(courseDto.getName());
        course.setDepartment(courseDto.getDepartment());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CustomAppException("Course not found for deletion", HttpStatus.NOT_FOUND));
        courseRepository.delete(course);
    }

    public void addEnrollmentToCourse(Long courseId, Long enrollmentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CustomAppException("Course not found for adding student", HttpStatus.NOT_FOUND));
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (enrollment != null) {
            course.addEnrollment(enrollment);
            courseRepository.save(course);
        }
    }

    public void removeEnrollmentFromCourse(Long courseId, Long enrollmentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CustomAppException("Course not found for removing student", HttpStatus.NOT_FOUND));
        Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
        if (enrollment != null) {
            course.removeEnrollment(enrollment);
            courseRepository.save(course);
        }
    }

}
