package jpa_relationships_school_example.repositories;

import jpa_relationships_school_example.entities.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long>{
}
