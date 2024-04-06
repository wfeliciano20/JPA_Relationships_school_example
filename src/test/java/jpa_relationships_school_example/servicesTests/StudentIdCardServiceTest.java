package jpa_relationships_school_example.servicesTests;

import jpa_relationships_school_example.entities.Student;
import jpa_relationships_school_example.entities.StudentIdCard;
import jpa_relationships_school_example.exceptions.CustomAppException;
import jpa_relationships_school_example.repositories.StudentIdCardRepository;
import jpa_relationships_school_example.services.StudentIdCardService;
import jpa_relationships_school_example.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentIdCardServiceTest {

    @InjectMocks
    private StudentIdCardService studentIdCardService;

    @Mock
    private StudentIdCardRepository studentIdCardRepository;

    @Mock
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllStudentIdCards_returnsAllStudentIdCards() {
        studentIdCardService.getAllStudentIdCards();
        verify(studentIdCardRepository, times(1)).findAll();
    }

    @Test
    public void getStudentIdCardById_returnsStudentIdCard() {
        StudentIdCard studentIdCard = new StudentIdCard();
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.of(studentIdCard));
        StudentIdCard result = studentIdCardService.getStudentIdCardById(1L);
        assertEquals(studentIdCard, result);
    }

    @Test
    public void getStudentIdCardById_throwsExceptionWhenStudentIdCardNotFound() {
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentIdCardService.getStudentIdCardById(1L));
    }

    @Test
    public void saveStudentIdCard_savesAndReturnsStudentIdCard() {
        StudentIdCard studentIdCard = new StudentIdCard();
        when(studentIdCardRepository.save(any(StudentIdCard.class))).thenReturn(studentIdCard);
        StudentIdCard result = studentIdCardService.saveStudentIdCard(studentIdCard);
        assertEquals(studentIdCard, result);
    }

    @Test
    public void updateStudentIdCard_updatesAndReturnsStudentIdCard() {
        StudentIdCard studentIdCard = new StudentIdCard();
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.of(studentIdCard));
        when(studentIdCardRepository.save(any(StudentIdCard.class))).thenReturn(studentIdCard);
        StudentIdCard result = studentIdCardService.updateStudentIdCard(1L, studentIdCard);
        assertEquals(studentIdCard, result);
    }

    @Test
    public void updateStudentIdCard_throwsExceptionWhenStudentIdCardNotFound() {
        StudentIdCard studentIdCard = new StudentIdCard();
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentIdCardService.updateStudentIdCard(1L, studentIdCard));
    }

    @Test
    public void deleteStudentIdCard_deletesStudentIdCard() {
        StudentIdCard studentIdCard = new StudentIdCard();
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.of(studentIdCard));
        studentIdCardService.deleteStudentIdCard(1L);
        verify(studentIdCardRepository, times(1)).delete(studentIdCard);
    }

    @Test
    public void deleteStudentIdCard_throwsExceptionWhenStudentIdCardNotFound() {
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentIdCardService.deleteStudentIdCard(1L));
    }

    @Test
    public void assignStudentIdCard_assignsStudentIdCard() {
        StudentIdCard studentIdCard = new StudentIdCard();
        Student student = new Student();
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.of(studentIdCard));
        when(studentService.getStudentById(anyLong())).thenReturn(student);
        studentIdCardService.assignStudentIdCard(1L, 1L);
        verify(studentIdCardRepository, times(1)).save(studentIdCard);
    }

    @Test
    public void assignStudentIdCard_throwsExceptionWhenStudentIdCardNotFound() {
        when(studentIdCardRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CustomAppException.class, () -> studentIdCardService.assignStudentIdCard(1L, 1L));
    }
}