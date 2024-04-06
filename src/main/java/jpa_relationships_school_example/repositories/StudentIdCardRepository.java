package jpa_relationships_school_example.repositories;

import jpa_relationships_school_example.entities.Student_Id_Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentIdCardRepository extends JpaRepository<Student_Id_Card, Long>{
}
