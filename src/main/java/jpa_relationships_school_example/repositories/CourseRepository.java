package jpa_relationships_school_example.repositories;

import jpa_relationships_school_example.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{
}
