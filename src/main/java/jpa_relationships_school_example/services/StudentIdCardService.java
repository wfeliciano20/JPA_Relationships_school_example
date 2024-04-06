package jpa_relationships_school_example.services;

import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.entities.StudentIdCard;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.StudentIdCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentIdCardService {

    private final StudentIdCardRepository studentIdCardRepository;
    private final StudentService studentService;


    public List<StudentIdCard> getAllStudentIdCards() {
        return studentIdCardRepository.findAll();
    }

    public StudentIdCard getStudentIdCardById(Long id) {
        return studentIdCardRepository.findById(id).orElseThrow(() -> new CustomAppException("StudentIdCard not found", HttpStatus.NOT_FOUND));
    }

    public StudentIdCard saveStudentIdCard(StudentIdCard studentIdCard) {
        return studentIdCardRepository.save(studentIdCard);
    }

    public StudentIdCard updateStudentIdCard(Long id, StudentIdCard studentIdCard) {
        StudentIdCard studentIdCardToUpdate = studentIdCardRepository.findById(id).orElseThrow(() -> new CustomAppException("StudentIdCard not found for update", HttpStatus.NOT_FOUND));
        studentIdCardToUpdate.setCardNumber(studentIdCard.getCardNumber());
        return studentIdCardRepository.save(studentIdCardToUpdate);
    }

    public void deleteStudentIdCard(Long id) {
        StudentIdCard studentIdCard = studentIdCardRepository.findById(id).orElseThrow(() -> new CustomAppException("StudentIdCard not found for deletion", HttpStatus.NOT_FOUND));
        studentIdCardRepository.delete(studentIdCard);
    }

    public void assignStudentIdCard(Long studentId, Long studentIdCardId) {
        Student student = studentService.getStudentById(studentId);
        StudentIdCard studentIdCard = getStudentIdCardById(studentIdCardId);
        if (studentIdCard != null && student != null) {
            studentIdCard.setStudent(student);
            studentIdCardRepository.save(studentIdCard);
        }
    }
}
