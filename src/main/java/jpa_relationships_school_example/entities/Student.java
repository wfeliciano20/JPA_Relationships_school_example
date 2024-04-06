package jpa_relationships_school_example.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Students")
public class Student {

        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private long id;

        private String firstName;

        private String lastName;

        @Column(unique = true)
        private String email;

        private int age;

        @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private Student_Id_Card studentIdCard;

        @JsonManagedReference
        @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Book> books;

        @JsonManagedReference
        @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<Enrollment> enrollments;

    public void setStudentIdCard(Student_Id_Card studentIdCard) {
        this.studentIdCard = studentIdCard;
        if (studentIdCard.getStudent() != this) {
            studentIdCard.setStudent(this);
        }
    }

    public void addBook(Book book) {
        this.books.add(book);
        if (book.getStudent() != this) {
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        if (book.getStudent() == this) {
            book.setStudent(null);
        }
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        if (!enrollment.getStudents().contains(this)) {
            enrollment.getStudents().add(this);
        }
    }

    public void removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        if (enrollment.getStudents().contains(this)) {
            enrollment.getStudents().remove(this);
        }
    }
}
