package jpa_relationships_school_example.repositories;

import jpa_relationships_school_example.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
