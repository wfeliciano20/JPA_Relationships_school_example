package jpa_relationships_school_example.services;

import jpa_relationships_school_example.dtos.EnrollmentDto;
import jpa_relationships_school_example.entities.Enrollment;
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


    public List<Enrollment> getAllEnrollments(){
        return enrollmentRepository.findAll();
    }

    public Enrollment getEnrollmentById(long id){
        return enrollmentRepository.findById(id).orElseThrow(() -> new CustomAppException("Enrollment not found", HttpStatus.NOT_FOUND));
    }

    public Enrollment saveEnrollment(EnrollmentDto enrollmentDto){
        Enrollment enrollment = Enrollment.builder()..build();
        return enrollmentRepository.save(enrollment);
    }
}
