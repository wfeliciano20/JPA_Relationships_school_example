package jpa_relationships_school_example.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String  enrollmentName;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
          name = "enrollment_student",
          joinColumns = @JoinColumn(name = "enrollment_id"),
          inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();

    @JsonBackReference
    @ManyToMany
    @JoinTable (
            name = "enrollment_course",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    private LocalDateTime createdAt;

    public void addStudent(Student student) {
        this.students.add(student);
        if (!student.getEnrollments().contains(this)) {
            student.getEnrollments().add(this);
        }
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        if (student.getEnrollments().contains(this)) {
            student.getEnrollments().remove(this);
        }
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        if (!course.getEnrollments().contains(this)) {
            course.getEnrollments().add(this);
        }
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        if (course.getEnrollments().contains(this)) {
            course.getEnrollments().remove(this);
        }
    }
}
