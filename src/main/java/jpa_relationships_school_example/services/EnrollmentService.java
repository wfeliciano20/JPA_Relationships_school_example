package jpa_relationships_school_example.services;

import jpa_relationships_school_example.dtos.EnrollmentDto;
import jpa_relationships_school_example.entities.Course;
import jpa_relationships_school_example.entities.Enrollment;
import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;


    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(long id) {
        return enrollmentRepository.findById(id).orElseThrow(() -> new CustomAppException("Enrollment not found", HttpStatus.NOT_FOUND));
    }

    public Enrollment saveEnrollment(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = Enrollment.builder().enrollmentName(enrollmentDto.getEnrollmentName()).build();
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Long id, EnrollmentDto enrollmentDto) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new CustomAppException("Enrollment not found for update", HttpStatus.NOT_FOUND));
        enrollment.setEnrollmentName(enrollmentDto.getEnrollmentName());
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment deleteEnrollment(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(() -> new CustomAppException("Enrollment not found for deletion", HttpStatus.NOT_FOUND));
        enrollmentRepository.delete(enrollment);
        return enrollment;
    }

    public void addStudentToEnrollment(Long enrollmentId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new CustomAppException("Enrollment not found for add student to enrollment", HttpStatus.NOT_FOUND));
        Student student = studentService.getStudentById(studentId);
        if (!enrollment.getStudents().contains(student) && student!= null) {
            enrollment.addStudent(student);
            enrollmentRepository.save(enrollment);
        }
    }

    public void removeStudentFromEnrollment(Long enrollmentId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new CustomAppException("Enrollment not found for remove student from enrollment", HttpStatus.NOT_FOUND));
        Student student = studentService.getStudentById(studentId);
        if(enrollment.getStudents().contains(student) && student!= null) {
            enrollment.getStudents().remove(student);
            enrollmentRepository.save(enrollment);
        }
    }

    public void addCourseToEnrollment(Long enrollmentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new CustomAppException("Enrollment not found for add course to enrollment", HttpStatus.NOT_FOUND));
        Course course = courseService.getCourseById(courseId);
        if (course != null && !enrollment.getCourses().contains(course)) {
            enrollment.getCourses().add(course);
        }
        enrollmentRepository.save(enrollment);
    }

    public void removeCourseFromEnrollment(Long enrollmentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new CustomAppException("Enrollment not found for remove course from enrollment", HttpStatus.NOT_FOUND));
        Course course = courseService.getCourseById(courseId);
        if(course != null && enrollment.getCourses().contains(course)) {
            enrollment.getCourses().remove(course);
        }
        enrollmentRepository.save(enrollment);
    }
}
